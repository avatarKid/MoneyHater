package vn.lol.moneyhater.moneyhater.model;
import android .os.Parcel;
import android .os.Parcelable;

import java.io.Serializable;

/**
 * Created by huy on 7/28/2015.
 */
public class Account implements Serializable {
    private int mAccountID;
    private String mAccountName;
    private Double mCash;
    private int mAccountTypeID;
    private int mIsDeleted;
    public static int DELETED = 1;
    public static int NOT_DELETED = 0;

    public int getIsDeleted() {
        return mIsDeleted;
    }

    public void setIsDeleted(int mIsDeleted) {
        this.mIsDeleted = mIsDeleted;
    }

    public Account(String accountName, Double cash, int accountTypeID) {
        mAccountName = accountName;
        mCash = cash;
        mAccountTypeID = accountTypeID;
        mIsDeleted = NOT_DELETED;
    }

    public Account() {
        mCash=0.0;
    }

    @Override
    public boolean equals(Object o) {
        Account account = (Account)o;
        return (account.getAccountID() == getAccountID());
    }

    public int getAccountID() {
        return mAccountID;
    }

    public void setAccountID(int accountID) {
        mAccountID = accountID;
    }

    public String getAccountName() {
        return mAccountName;
    }

    public void setAccountName(String accountName) {
        mAccountName = accountName;
    }

    public Double getCash() {
        return mCash;
    }

    public void setCash(Double cash) {
        mCash = cash;
    }

    public int getAccountTypeID() {
        return mAccountTypeID;
    }

    public void setAccountTypeID(int accountTypeID) {
        mAccountTypeID = accountTypeID;
    }

    @Override
    public String toString() {
        return mAccountName;
    }

}
