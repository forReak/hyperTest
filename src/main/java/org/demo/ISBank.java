package org.demo;

import cn.hyperchain.contract.BaseContractInterface;

public interface ISBank extends BaseContractInterface {
    boolean transfer(String from, String to, int val);

    boolean deposit(String from, int val);
}
