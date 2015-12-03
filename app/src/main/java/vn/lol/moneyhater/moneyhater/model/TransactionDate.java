package vn.lol.moneyhater.moneyhater.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by HoangTuan on 7/30/15.
 */
public interface TransactionDate extends Comparable<TransactionDate>, Serializable {
    Date getTime();
    String getDay();
    String getMonth();
    String getYear();
    String getDate();
}
