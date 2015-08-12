package vn.lol.moneyhater.moneyhater.model;

/**
 * Created by huy on 7/28/2015.
 */
public class Budget {
    private int mBudgetID;
    private String mBudgetName;
    private int mImageID;
    private double mCash;

    public Budget() {
    }

    public Budget(String budgetName, int imageID, double cash) {
        mBudgetName = budgetName;
        mImageID = imageID;
        mCash = cash;
    }

    @Override
    public boolean equals(Object o) {
        Budget budget = (Budget)o;
        return (budget.getBudgetID() == getBudgetID());
    }

    public int getBudgetID() {
        return mBudgetID;
    }

    public void setBudgetID(int budgetID) {
        mBudgetID = budgetID;
    }

    public String getBudgetName() {
        return mBudgetName;
    }

    public void setBudgetName(String budgetName) {
        mBudgetName = budgetName;
    }

    public int getImageID() {
        return mImageID;
    }

    public void setImageID(int imageID) {
        mImageID = imageID;
    }

    public double getCash() {
        return mCash;
    }

    public void setCash(double cash) {
        mCash = cash;
    }

    @Override
    public String toString() {
        return mBudgetName;
    }
}
