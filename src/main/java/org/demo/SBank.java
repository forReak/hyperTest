//package org.demo;
//
//import cn.hyperchain.annotations.StoreField;
//import cn.hyperchain.contract.BaseContract;
//import cn.hyperchain.core.HyperMap;
//
////合约主体类SBank.java
//public class SBank extends BaseContract implements ISBank {
//    //注解标记数据持久化
//    @StoreField
//    public HyperMap<String, Integer> accounts = new HyperMap<String, Integer>();
//
//    public SBank() {
//    }
//
//    @Override
//    public void onInit() {
//        this.accounts.put("AAA", 1000000000);
//        this.accounts.put("BBB", 1000000000);
//        this.accounts.put("CCC", 1000000000);
//        this.accounts.put("DDD", 1000000000);
//    }
//
//    @Override
//    public boolean transfer(String from, String to, int value) {
//        int fromBalance = this.accounts.get(from);
//        int toBalance;
//        if (!this.accounts.containsKey(from) || fromBalance < value) {
//            return false;
//        }
//        // if to account not exist, create
//        if (!this.accounts.containsKey(to)) {
//            toBalance = 0;
//        } else {
//            toBalance = this.accounts.get(to);
//        }
//
//        // do transaction
//        this.accounts.put(from, fromBalance - value);
//        this.accounts.put(to, toBalance + value);
//        return true;
//    }
//
//    @Override
//    public boolean deposit(String from, int value) {
//        // if to account not exist, create
//        if (!this.accounts.containsKey(from)) {
//            this.accounts.put(from, 0);
//        }
//        this.accounts.put(from, this.accounts.get(from) + value);
//        return true;
//    }
//}
//
////ISBank.java
//
////invoke bean: InvokeBank.java
