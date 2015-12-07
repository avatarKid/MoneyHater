package vn.lol.moneyhater.moneyhater.Database;

import android.app.Application;
import android.content.Context;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import vn.lol.moneyhater.moneyhater.Util.ConstantValue;
import vn.lol.moneyhater.moneyhater.model.Account;
import vn.lol.moneyhater.moneyhater.model.Budget;
import vn.lol.moneyhater.moneyhater.model.Transaction;

/**
 * Created by huybu on 11/24/2015.
 */
public class DataManager extends Application {
    private ArrayList<Account> allAccounts;
    private ArrayList<Budget> allBudgets;
    private ArrayList<Transaction> allTransactions;

    public DataManager(){
        this.allAccounts = new ArrayList<>();
        this.allBudgets = new ArrayList<>();
        this.allTransactions = new ArrayList<>();
    }


    public void setAllAccounts(ArrayList<Account> allAccounts) {
        this.allAccounts = allAccounts;
    }

    public void setAllBudgets(ArrayList<Budget> allBudgets) {
        this.allBudgets = allBudgets;
    }

    public void setAllTransactions(ArrayList<Transaction> allTransactions) {
        this.allTransactions = allTransactions;
    }

    public DataManager(ArrayList<Account> allAccounts, ArrayList<Budget> allBudgets, ArrayList<Transaction> allTransactions) {
        try {
            this.allAccounts = allAccounts;
            this.allBudgets = allBudgets;
            this.allTransactions = allTransactions;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Account getAccount(int id) {
        for (Account acc : allAccounts) {
            if (acc.getAccountID() == id)
                return acc;
        }
        return null;
    }

    public boolean insertAccount(Account acc) {
        int id = 0;
        for (Account account : allAccounts ) {
            if (account.getAccountID()>=id) id = account.getAccountID()+1;
        }
        try {
            acc.setAccountID(id);
            allAccounts.add(acc);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean updateAccount(Account acc) {
        try {
            Account accIn = getAccount(acc.getAccountID());
            accIn.setAccountTypeID(acc.getAccountTypeID());
            accIn.setAccountName(acc.getAccountName());
            accIn.setCash(acc.getCash());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean deleteAccount(int id) {
        try {
            Account acc = getAccount(id);
            if (acc != null) {
                allAccounts.remove(acc);
            }
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    public ArrayList<Account> getAllAccounts() {
        return allAccounts;
    }

    public ArrayList<Budget> getAllBudgets() {
        ArrayList<Budget> a = new ArrayList<Budget>();
        a =  (ArrayList<Budget>)this.allBudgets.clone();
        return a;
    }

    public Budget getBudget(int id) {
        for (Budget budget : allBudgets) {
            if (budget.getBudgetID() == id) return budget;
        }
        return null;
    }

    public boolean insertBudget(Budget budget) {
        int id = 0;
        for (Budget budgetCur : allBudgets ) {
            if (budgetCur.getBudgetID()>=id) id = budgetCur.getBudgetID()+1;
        }
        try {
            budget.setBudgetID(id);
            allBudgets.add(budget);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean updateBudget(Budget budget) {
        try {
            Budget budgetIn = getBudget(budget.getBudgetID());
            budgetIn.setBudgetName(budget.getBudgetName());
            budgetIn.setCash(budget.getCash());

        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean deleteBudget(int id) {
        try {
            Budget budget = getBudget(id);
            if (budget != null) {
                allBudgets.remove(budget);
            }
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    public ArrayList<Transaction> getAllTransactions() {
        return allTransactions;
    }

    public Transaction getTransaction(int id) {
        for (Transaction transaction : allTransactions) {
            if (transaction.getTransactionID() == id) return transaction;
        }
        return null;
    }

    public boolean insertTransaction(Transaction transaction) {
        int id = 0;
        for (Transaction tran : allTransactions ) {
            if (tran.getTransactionID()>=id) id = tran.getTransactionID()+1;
        }
        try {
            transaction.setTransactionID(id);
            allTransactions.add(transaction);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean updateTransaction(Transaction transaction) {
        try {
            Transaction accIn = getTransaction(transaction.getTransactionID());
            accIn.setCash(transaction.getCash());
            accIn.setDate(transaction.getDate());
            accIn.setAccountID(transaction.getAccountID());
            accIn.setBudgetID(transaction.getBudgetID());
            accIn.setCategoryID(transaction.getCategoryID());
            accIn.setTransactionName(transaction.getTransactionName());
            accIn.setType(transaction.getType());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean deleteTransaction(int id) {
        try {
            Transaction transaction = getTransaction(id);
            if (transaction != null) {
                allTransactions.remove(transaction);
            }
        } catch (Exception ex) {
            return false;
        }
        return true;
    }



    public ArrayList<Transaction> getTransactionByAccountID(int accountID) {
        ArrayList<Transaction> al = new ArrayList<>();
        for (Transaction tran :
                allTransactions) {
            if (tran.getAccountID()==accountID) al.add(tran);
        }
        return al;
    }

    public void importDatabase(String fileName) {

    }

    public ArrayList<Transaction> getTransactionByBudgetID(int budgetID) {
        ArrayList<Transaction> al = new ArrayList<>();
        for (Transaction tran :
                allTransactions) {
            if (tran.getBudgetID()==budgetID) al.add(tran);
        }
        return al;
    }

    public float[][] getTransactionInYear(int year){
        float[][] data = new float[2][12];
        SimpleDateFormat dayFormat = new SimpleDateFormat("MM", Locale.US);
        for (Transaction tran :
                allTransactions) {
            if (tran.getYear().equalsIgnoreCase(year+"")){
                int in = Integer.parseInt(dayFormat.format(tran.getTime()));
                if(tran.getType() == ConstantValue.TRANSACTION_TYPE_EXPENSE){
                    data[ConstantValue.TRANSACTION_TYPE_EXPENSE][in-1]+= (float)tran.getCash();
                }else{
                    data[ConstantValue.TRANSACTION_TYPE_INCOME][in-1]+= (float)tran.getCash();
                }
            }
        }
        return data;
    }

    public float[] getTransactionInMonth(int month,int year){
        float[] data = new float[2];
        SimpleDateFormat dayFormat = new SimpleDateFormat("MM", Locale.US);
        for (Transaction tran :
                allTransactions) {
            if (tran.getYear().equalsIgnoreCase(year+"") && dayFormat.format(tran.getTime()).equalsIgnoreCase(month+"")){
                if(tran.getType() == ConstantValue.TRANSACTION_TYPE_EXPENSE){
                    data[ConstantValue.TRANSACTION_TYPE_EXPENSE]+=(float)tran.getCash();
                }else{
                    data[ConstantValue.TRANSACTION_TYPE_INCOME]+=(float)tran.getCash();
                }
            }
        }
        return data;
    }
}
