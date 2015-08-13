package vn.lol.moneyhater.moneyhater.Util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import vn.lol.moneyhater.moneyhater.model.SupportTransaction;
import vn.lol.moneyhater.moneyhater.model.Transaction;
import vn.lol.moneyhater.moneyhater.model.TransactionDate;

/**
 * Created by huy on 7/29/2015.
 */
public class CommonFunction {
    public static int safeLongToInt(long l) {
        if (l < Integer.MIN_VALUE || l > Integer.MAX_VALUE) {
            throw new IllegalArgumentException
                    (l + " cannot be cast to int without changing its value.");
        }
        return (int) l;
    }

    /**
     * Creates the specified <code>toFile</code> as a byte for byte copy of the
     * <code>fromFile</code>. If <code>toFile</code> already exists, then it
     * will be replaced with a copy of <code>fromFile</code>. The name and path
     * of <code>toFile</code> will be that of <code>toFile</code>.<br/>
     * <br/>
     * <i> Note: <code>fromFile</code> and <code>toFile</code> will be closed by
     * this function.</i>
     *
     * @param fromFile - FileInputStream for the file to copy from.
     * @param toFile   - FileInputStream for the file to copy to.
     */
    public static void copyFile(FileInputStream fromFile, FileOutputStream toFile) throws IOException {
        FileChannel fromChannel = null;
        FileChannel toChannel = null;
        try {
            fromChannel = fromFile.getChannel();
            toChannel = toFile.getChannel();
            fromChannel.transferTo(0, fromChannel.size(), toChannel);
        } finally {
            try {
                if (fromChannel != null) {
                    fromChannel.close();
                }
            } finally {
                if (toChannel != null) {
                    toChannel.close();
                }
            }
        }
    }

    /**
     * Create list include transaction and Date header display transaction with Date in listview
     *
     * @param listTransaction
     * @return list transaction with Date header
     */
    public static ArrayList<TransactionDate> getListTransactionAndDate(ArrayList<Transaction> listTransaction) {
        ArrayList<TransactionDate> listTransactionAndDate = new ArrayList<TransactionDate>();
        for (Transaction item : listTransaction) {
            ArrayList<String> listDate = new ArrayList<>();
            boolean dateExist = false;
            String date = item.getDate();
            Calendar calendar = item.getCalendar();

            SimpleDateFormat df = new SimpleDateFormat("yyyy:MM:dd:hh:mm:ss");
            Date d1 = null;
            try {
                d1 = df.parse(calendar.get(Calendar.YEAR) + ":" + (calendar.get(Calendar.MONTH) + 1) + ":" + calendar.get(Calendar.DAY_OF_MONTH) + ":23:59:59");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            calendar.setTime(d1);

            if (listTransactionAndDate.size() == 0) {
                listTransactionAndDate.add(new SupportTransaction(item.getDay(), item.getMonth(), item.getYear(), calendar));
                listDate.add(date);
            }

            for (int i = 0; i < listDate.size(); i++) {
                if (date.equals(listDate.get(i) + "")) {
                    dateExist = true;
                }
            }

            if (!dateExist) {
                listTransactionAndDate.add(new SupportTransaction(item.getDay(), item.getMonth(), item.getYear(), calendar));
                listDate.add(date);
            }

            listTransactionAndDate.add(item);
            Collections.sort(listTransactionAndDate);
        }
        return listTransactionAndDate;
    }
}
