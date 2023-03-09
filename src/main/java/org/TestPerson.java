package org;

import cn.hyperchain.sdk.account.Account;
import cn.hyperchain.sdk.account.Algo;
import cn.hyperchain.sdk.common.utils.Decoder;
import cn.hyperchain.sdk.common.utils.FileUtil;
import cn.hyperchain.sdk.exception.RequestException;
import cn.hyperchain.sdk.provider.DefaultHttpProvider;
import cn.hyperchain.sdk.provider.ProviderManager;
import cn.hyperchain.sdk.response.ReceiptResponse;
import cn.hyperchain.sdk.response.TxHashResponse;
import cn.hyperchain.sdk.service.AccountService;
import cn.hyperchain.sdk.service.ContractService;
import cn.hyperchain.sdk.service.ServiceManager;
import cn.hyperchain.sdk.transaction.Transaction;
import org.legalperson.InvokeMyContract;
import org.legalperson.Person;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

public class TestPerson {
    //合约jar包路径
    String jarPath = "C:\\Users\\furao\\IdeaProjects\\hyperTest\\hyperTest\\target\\hyperTest-1.0-SNAPSHOT.jar";
    String defaultURL = "192.168.204.130:8081";

    String addr = "0x1153e48c882494e3822bf89e9cba5066d3f1a419";
    public static void main(String[] args) throws IOException, RequestException {
        TestPerson testPerson = new TestPerson();
        //String addr = testPerson.deploy();
        //testPerson.setPerson();
        testPerson.getPErson();

    }


    /**
     * 获取节点管理器
     * @return
     */
    public ProviderManager getProviderManager(){
        //负责管理与节点的连接
        DefaultHttpProvider defaultHttpProvider = new DefaultHttpProvider.Builder().setUrl(defaultURL).build();
        //rovideManager负责集成、管理这些HttpProvider
        return ProviderManager.createManager(defaultHttpProvider);
    }

    public String deploy() throws IOException, RequestException {
        InputStream is = FileUtil.readFileAsStream(jarPath);
        ProviderManager providerManager = getProviderManager();
        ContractService contractService = ServiceManager.getContractService(providerManager);
        AccountService accountService = ServiceManager.getAccountService(providerManager);
        Account account = accountService.genAccount(Algo.ECRAW);
        //部署合约
        Transaction transaction = new Transaction.HVMBuilder(account.getAddress()).deploy(is).build();
        transaction.sign(account);
        TxHashResponse send = contractService.deploy(transaction).send();
        ReceiptResponse receiptResponse = send.polling();
        //获取合约地址
        String contractAddress = receiptResponse.getContractAddress();
        System.out.println("contract address: " + contractAddress);
        return contractAddress;
    }

    public void setPerson() throws RequestException {
        ProviderManager providerManager = getProviderManager();
        ContractService contractService = ServiceManager.getContractService(providerManager);
        AccountService accountService = ServiceManager.getAccountService(providerManager);
        Account account = accountService.genAccount(Algo.ECRAW);
        Person zhangsan = new Person();
        zhangsan.setId("002");
        zhangsan.setAge(19);
        zhangsan.setName("李四");
        zhangsan.setCopAddr("地址");

        //创建指定invoke bean的交易
        Transaction transaction = new Transaction.HVMBuilder(account.getAddress()).invoke(addr, new InvokeMyContract(1,zhangsan,"")).build();
        transaction.sign(account);
        ReceiptResponse receiptResponse1 = contractService.invoke(transaction).send().polling();
        //对交易执行结果进行解码
        String decodeHVM = Decoder.decodeHVM(receiptResponse1.getRet(), String.class);
        System.out.println("decode: " + decodeHVM);
    }

    public void getPErson() throws RequestException {
        ProviderManager providerManager = getProviderManager();
        ContractService contractService = ServiceManager.getContractService(providerManager);
        AccountService accountService = ServiceManager.getAccountService(providerManager);
        Account account = accountService.genAccount(Algo.ECRAW);
        Person p1 = new Person();
        //创建指定invoke bean的交易
        Transaction transaction = new Transaction.HVMBuilder(account.getAddress()).invoke(addr, new InvokeMyContract(2,p1,"003")).build();
        transaction.sign(account);
        ReceiptResponse receiptResponse1 = contractService.invoke(transaction).send().polling();
        //对交易执行结果进行解码
        String decodeHVM = Decoder.decodeHVM(receiptResponse1.getRet(), String.class);
        System.out.println("decode: " + decodeHVM);
    }

}
