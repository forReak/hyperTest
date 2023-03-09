package org.legalperson;

import cn.hyperchain.contract.BaseContractInterface;

public interface MyContract extends BaseContractInterface {

    String saveData(Person person);

    String getData(String id);
}
