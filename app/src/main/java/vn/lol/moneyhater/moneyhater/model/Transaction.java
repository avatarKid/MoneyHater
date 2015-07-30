package vn.lol.moneyhater.moneyhater.model;

import android.text.format.Time;

import java.security.Principal;

import java.util.Date;

/**
 * Created by huy on 7/24/2015.
 */
public class Transaction {
    private int mTransactionID;
    private String mTransactionName;
    private int mType;
    private double mCash;
    private int mCategoryID;
    private int mBudgetID;
    private int mAccountID;
//    private Time mDate;

    public Transaction() {
    }

    public Transaction(String transactionName, int type, double cash, int categoryID, int budgetID, int accountID) {
        mTransactionName = transactionName;
        mType = type;
        mCash = cash;
        mCategoryID = categoryID;
        mBudgetID = budgetID;
        mAccountID = accountID;
//        mDate= new Time(Time.getCurrentTimezone());
//        today.setToNow();
    }

    public int getTransactionID() {
        return mTransactionID;
    }

    public void setTransactionID(int transactionID) {
        mTransactionID = transactionID;
    }

    public String getTransactionName() {
        return mTransactionName;
    }

    public void setTransactionName(String transactionName) {
        mTransactionName = transactionName;
    }

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        mType = type;
    }

    public double getCash() {
        return mCash;
    }

    public void setCash(double cash) {
        mCash = cash;
    }

    public int getCategoryID() {
        return mCategoryID;
    }

    public void setCategoryID(int categoryID) {
        mCategoryID = categoryID;
    }

    public int getBudgetID() {
        return mBudgetID;
    }

    public void setBudgetID(int budgetID) {
        mBudgetID = budgetID;
    }

    public int getAccountID() {
        return mAccountID;
    }

    public void setAccountID(int accountID) {
        mAccountID = accountID;
    }

}
