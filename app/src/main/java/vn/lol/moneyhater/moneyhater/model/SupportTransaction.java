package vn.lol.moneyhater.moneyhater.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by huy on 7/24/2015.
 */
public class SupportTransaction implements TransactionDate,Serializable {
    public String day;
    public String month;
    public String year;
    private Calendar mDate;

    public SupportTransaction(String day, String month, String year, Calendar date) {
        this.day = day;
        this.month = month;
        this.year = year;
        mDate = date;
    }

    public Date getTime(){
        return mDate.getTime();
    }
    public String getDay(){
        String day = "";
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.US);
        day = dayFormat.format(mDate.getTime());
        return day;
    }

    public String getMonth(){
        String month = "";
        SimpleDateFormat dayFormat = new SimpleDateFormat("MMMM", Locale.US);
        month = dayFormat.format(mDate.getTime());
        return month;
    }

    public String getYear(){
        String year = "";
        SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy", Locale.US);
        year = dayFormat.format(mDate.getTime());
        return year;
    }

    public String getDate(){
        return getDay() + getMonth() + getYear();
    }

    @Override
    public int compareTo(TransactionDate another) {
        return another.getTime().compareTo(getTime());
    }
}
