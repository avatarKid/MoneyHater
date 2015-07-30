package vn.lol.moneyhater.moneyhater.model;

/**
 * Created by huy on 7/24/2015.
 */
public class SupportTransaction {
    public String day;
    public String month;
    public String year;
    public String money;

    public SupportTransaction(String day, String month, String year, String money) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.money = money;
    }
}
