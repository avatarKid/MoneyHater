package vn.lol.moneyhater.moneyhater.model;

import java.util.Date;

/**
 * Created by huy on 7/24/2015.
 */
public class Transaction {
    public String date;
    public String category;
    public String money;
    public String note;

    public Transaction(String date, String category, String money, String note) {
        this.date = date;
        this.category = category;
        this.money = money;
        this.note = note;
    }
}
