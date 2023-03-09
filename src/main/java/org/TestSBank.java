package org;

import cn.hyperchain.sdk.account.Account;
import cn.hyperchain.sdk.account.Algo;
import cn.hyperchain.sdk.common.utils.Decoder;
import cn.hyperchain.sdk.common.utils.FileUtil;
import cn.hyperchain.sdk.exception.RequestException;
import cn.hyperchain.sdk.provider.DefaultHttpProvider;
import cn.hyperchain.sdk.provider.ProviderManager;
import cn.hyperchain.sdk.request.Request;
import cn.hyperchain.sdk.response.ReceiptResponse;
import cn.hyperchain.sdk.response.node.NodeResponse;
import cn.hyperchain.sdk.response.tx.TxResponse;
import cn.hyperchain.sdk.service.*;
import cn.hyperchain.sdk.transaction.Transaction;
import org.demo.InvokeBank;

import java.io.IOException;
import java.io.InputStream;

public class TestSBank {
    //合约jar包路径
    String jarPath = "C:\\Users\\furao\\IdeaProjects\\hyperTest\\hyperTest\\target\\hyperTest-1.0-SNAPSHOT.jar";
    String defaultURL = "192.168.204.130:8081";
    String contractAddress = "0xedf662da7fcbd7a73096b101b0f00859a1b73b8a";

    public static void main(String[] args) throws Exception {
        TestSBank testSBank = new TestSBank();

        //调用合约
        //testSBank.testSBank();

        //获取节点信息
//        testSBank.getNodeInfo();

        //查询区块
        //testSBank.getBlock();

        //查询交易
        testSBank.getTxByBlockHash();
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

    /**
     * 部署合约
     * @throws IOException
     * @throws RequestException
     */
    public void deployContract() throws IOException, RequestException {
        InputStream is = FileUtil.readFileAsStream(jarPath);
        ProviderManager providerManager = getProviderManager();
        ContractService contractService = ServiceManager.getContractService(providerManager);
        AccountService accountService = ServiceManager.getAccountService(providerManager);
        Account account = accountService.genAccount(Algo.ECRAW);
        //部署合约
        Transaction transaction = new Transaction.HVMBuilder(account.getAddress()).deploy(is).build();
        transaction.sign(account);
        ReceiptResponse receiptResponse = contractService.deploy(transaction).send().polling();
        //获取合约地址
        String contractAddress = receiptResponse.getContractAddress();
        System.out.println("contract address: " + contractAddress);
    }

    /**
     * 调用合约
     * @throws Exception
     */
    public void testSBank() throws Exception {
        ProviderManager providerManager = getProviderManager();
        ContractService contractService = ServiceManager.getContractService(providerManager);
        AccountService accountService = ServiceManager.getAccountService(providerManager);
        Account account = accountService.genAccount(Algo.ECRAW);

        //创建指定invoke bean的交易
        Transaction transaction1 = new Transaction.HVMBuilder(account.getAddress()).invoke(contractAddress, new InvokeBank("AAA", "BBB", 100)).build();
        transaction1.sign(account);
        ReceiptResponse receiptResponse1 = contractService.invoke(transaction1).send().polling();
        //对交易执行结果进行解码
        String decodeHVM = Decoder.decodeHVM(receiptResponse1.getRet(), String.class);
        System.out.println("decode: " + decodeHVM);
    }

    /**
     * 获取节点信息
     * @throws RequestException
     */
    public void getNodeInfo() throws RequestException {
        ProviderManager providerManager = getProviderManager();
        // 将ProviderManager对象作为参数，通过getNodeService()创建NodeService类型的对象
        // NodeService为声明的接口， 实际类型为NodeServiceImpl
        NodeService nodeService = ServiceManager.getNodeService(providerManager);
        // 通过调用NodeService提供的方法创建相应的服务，类型为Request<NodeResponse>
        Request<NodeResponse> request = nodeService.getNodes();
        //发送请求
        NodeResponse send = request.send();
        //打印结果
        System.out.println(send.getResult());
    }

    /**
     * 查询区块
     * @throws RequestException
     */
    public void getBlock() throws RequestException {
        ProviderManager providerManager = getProviderManager();
        BlockService blockService = ServiceManager.getBlockService(providerManager);

        //当前区块 [Block{version='2.0', number='0x4', hash='0xeed0a44065e9fc57b3c02875cdf77d8a27d5dcd72c03d4eea506ee045dbec0ae',
        // parentHash='0x86467a9216d38d38f73c9dff64a30adb4decb964f89379ca933c271050d1d058', writeTime='1678325681166798440',
        // avgTime='0x14', txcounts='0x1', merkleRoot='0x4a2503b1d95bcaf0d4b34c256676567d284561014f0d84bbd466e54726b2fd43',
        // transactions=[Transaction{version='2.0', hash='0x5c223c1f5f9ab95045b560328c68cf7545751c9b8746ead80242c940dfd5dd80',
        // blockNumber='0x4', blockHash='0xeed0a44065e9fc57b3c02875cdf77d8a27d5dcd72c03d4eea506ee045dbec0ae', txIndex='0x0',
        // from='0x552215f37136e4329f5a1b58e1393ac7cb1d05fb', to='0xedf662da7fcbd7a73096b101b0f00859a1b73b8a', amount='0x0',
        // timestamp='1678325679486838264', nonce='1021503377729640', extra='', executeTime='0x14', payload='0x000004a30014caf
        // ebabe0000003400350a000a00240900090025090009002609000900270b000700280a0029002a07002b0a0009002c07002d07002e07002f0100
        // 0466726f6d0100124c6a6176612f6c616e672f537472696e673b010002746f01000576616c7565010001490100063c696e69743e01000328295
        // 6010004436f646501000f4c696e654e756d6265725461626c65010......', signature='00679f03f4737728fbe0ba1e3d1dc10
        // 1943594c172d281f581e8b365f807048e7b0f5b434eecde5466db2ac3ce494bd3ddd638c2dc667baae99bf11f8c22e6f53f00', blockTimesta
        // mp='1678325681146474254', blockWriteTime='1678325681166798440'}]}]
        System.out.println("当前区块:"+blockService.getLatestBlock().send().getResult());

        //查询创世区块
        System.out.println("查询创世区块:"+ blockService.getGenesisBlock().send().getJsonrpc());

        //链高
        System.out.println("链高:"+blockService.getChainHeight().send().getResult());
    }

    /**
     * 根据区块hash查询交易
     */
    public void getTxByBlockHash() throws RequestException {
        ProviderManager providerManager = getProviderManager();
        TxService txService = ServiceManager.getTxService(providerManager);
        //把区块hash换成你要查询的hash
        Request<TxResponse> txByHash = txService.getTxByBlockHashAndIndex("0xeed0a44065e9fc57b3c02875cdf77d8a27d5dcd72c03d4eea506ee045dbec0ae",0x0);
        TxResponse send = txByHash.send();

        //当前交易 [Transaction{version='2.0', hash='0x5c223c1f5f9ab95045b560328c68cf7545751c9b8746ead80242c940dfd5dd80',
        // blockNumber='0x4', blockHash='0xeed0a44065e9fc57b3c02875cdf77d8a27d5dcd72c03d4eea506ee045dbec0ae', txIndex='0x0',
        // from='0x552215f37136e4329f5a1b58e1393ac7cb1d05fb', to='0xedf662da7fcbd7a73096b101b0f00859a1b73b8a', amount='0x0',
        // timestamp='1678325679486838264', nonce='1021503377729640', extra='', executeTime='0x14',
        // payload='0x000004a30014cafebabe0000003400350a000a00240900090025090009002609000900270b000700280a0029002a07002b
        // 0a0009002c07002d07002e07002f01000466726f6d0100124c6a6176612f6c616e672f537472696e673b010002746f01000576616c756
        // 5010001490100063c696e69743e010003282956010004436f646501000f4c696e654e756d6265725461626c650100124c6f63616c56617
        // 26961626c655461626c6501000。。。。, blockTimestamp='1678325681146474254', blockWriteTime='1678325681166798440'}]
        System.out.println(send.getResult());
    }

}

