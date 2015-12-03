package vn.lol.moneyhater.moneyhater.model;

import java.io.Serializable;
import java.util.ArrayList;

import vn.lol.moneyhater.moneyhater.Util.ConstantValue;

/**
 * Created by Aoko on 8/13/2015.
 */
public class BudgetGroup implements Serializable {
    ArrayList<Transaction> children;
    Budget budget;

    public BudgetGroup(Budget budget, ArrayList<Transaction> children) {
        this.budget = budget;
        this.children = children;
    }

    public Budget getBudget() {
        return budget;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    public ArrayList<Transaction> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<Transaction> children) {
        this.children = children;
    }

    public Double getCurrentCast(){
       Double cur = budget.getCash();

        if(!children.isEmpty()){
            for (int i = 0; i <children.size(); i++) {
                Transaction val = children.get(i);
                if(val.getType() == ConstantValue.TRANSACTION_TYPE_INCOME){
                    cur += val.getCash();
                }else {
                    cur -= val.getCash();
                }
            }
        }
        return cur;
    }
}
