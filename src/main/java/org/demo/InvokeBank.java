package org.demo;

import cn.hyperchain.contract.BaseInvoke;

public class InvokeBank implements BaseInvoke<Boolean, ISBank> {

    public String from;
    public String to;
    public int value;

    // 必须有一个无参默认构造方法
    public InvokeBank() {
    }

    public InvokeBank(String from, String to, int value) {
        this.from = from;
        this.to = to;
        this.value = value;
    }

    @Override
    public Boolean invoke(ISBank obj) {
        boolean a = obj.transfer(from, to, value);
        return a;
    }
}