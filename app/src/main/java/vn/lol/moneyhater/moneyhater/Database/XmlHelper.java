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
import java.util.ArrayList;

import vn.lol.moneyhater.moneyhater.model.Account;
import vn.lol.moneyhater.moneyhater.model.Budget;
import vn.lol.moneyhater.moneyhater.model.Transaction;

/**
 * Created by huybu on 11/24/2015.
 */
public class XmlHelper extends Application {
    private ArrayList<Account> allAccounts;
    private ArrayList<Budget> allBudgets;
    private ArrayList<Transaction> allTransactions;

    public XmlHelper(){
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

    public XmlHelper(ArrayList<Account> allAccounts,ArrayList<Budget> allBudgets,ArrayList<Transaction> allTransactions) {
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
        try {
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
        return allBudgets;
    }

    public Budget getBudget(int id) {
        for (Budget budget : allBudgets) {
            if (budget.getBudgetID() == id) return budget;
        }
        return null;
    }

    public boolean insertBudget(Budget budget) {
        try {
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
        try {
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
}
