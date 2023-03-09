package org.legalperson;

import cn.hyperchain.annotations.StoreField;
import cn.hyperchain.core.HyperMap;
import cn.hyperchain.contract.BaseContract;

public class MyContractImpl extends BaseContract implements MyContract{

    @StoreField
    public HyperMap<String, Person> personMap = new HyperMap<String, Person>();

    public MyContractImpl() {
    }

    @Override
    public String saveData(Person person) {
        personMap.put(person.getId(),person);
        return "true";
    }

    @Override
    public String getData(String id) {
        return personMap.get(id).toString();
    }
}
