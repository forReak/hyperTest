package org.legalperson;

import cn.hyperchain.contract.BaseInvoke;

public class InvokeMyContract implements BaseInvoke<String, MyContract> {

    public int function;

    public Person person;

    public String id;

    public InvokeMyContract() {
    }

    public InvokeMyContract(int function, Person person, String id) {
        this.function = function;
        this.person = person;
        this.id = id;
    }

    @Override
    public String invoke(MyContract myContract) {
        if(function == 1){
            return myContract.saveData(person);
        }
        else{
            return myContract.getData(id);
        }
    }
}
