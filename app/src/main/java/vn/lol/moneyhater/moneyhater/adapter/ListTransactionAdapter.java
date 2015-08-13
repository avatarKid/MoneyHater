package vn.lol.moneyhater.moneyhater.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import vn.lol.moneyhater.momeyhater.R;
import vn.lol.moneyhater.moneyhater.Database.DatabaseHelper;
import vn.lol.moneyhater.moneyhater.model.Account;
import vn.lol.moneyhater.moneyhater.model.SupportTransaction;
import vn.lol.moneyhater.moneyhater.model.Transaction;
import vn.lol.moneyhater.moneyhater.model.TransactionDate;

/**
 * Created by HoangTuan on 7/24/2015.
 */
public class ListTransactionAdapter extends BaseAdapter {

    private final Drawable[] arrImage;
    int listPosition = 0;
    private static final int TYPE_DATE = 0;
    private static final int TYPE_TRANSACTION = 1;
    private static final int TYPE_MAX_COUNT = TYPE_TRANSACTION + 1;

    private ArrayList<TransactionDate> mData = new ArrayList<TransactionDate>();
    private LayoutInflater mInflater;
    private DatabaseHelper databaseHelper;

    public ListTransactionAdapter(Activity activity, ArrayList<TransactionDate> lstTrans, DatabaseHelper mDBHelper) {
        mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mData = lstTrans;
        databaseHelper = mDBHelper;
        arrImage=new Drawable[18];
        arrImage[0]= ResourcesCompat.getDrawable(activity.getResources(), R.drawable.education, null);
        arrImage[1]=ResourcesCompat.getDrawable(activity.getResources(),R.drawable.entertainment,null);
        arrImage[2]=ResourcesCompat.getDrawable(activity.getResources(),R.drawable.family,null);
        arrImage[3]=ResourcesCompat.getDrawable(activity.getResources(),R.drawable.food_drink,null);
        arrImage[4]=ResourcesCompat.getDrawable(activity.getResources(),R.drawable.friend_lover,null);
        arrImage[5]=ResourcesCompat.getDrawable(activity.getResources(),R.drawable.invest,null);
        arrImage[6]=ResourcesCompat.getDrawable(activity.getResources(),R.drawable.loan,null);
        arrImage[7]=ResourcesCompat.getDrawable(activity.getResources(),R.drawable.medical,null);
        arrImage[8]=ResourcesCompat.getDrawable(activity.getResources(),R.drawable.shopping,null);
        arrImage[9]=ResourcesCompat.getDrawable(activity.getResources(),R.drawable.transport,null);
        arrImage[10]=ResourcesCompat.getDrawable(activity.getResources(),R.drawable.travel,null);
        arrImage[11]=ResourcesCompat.getDrawable(activity.getResources(),R.drawable.award,null);
        arrImage[12]=ResourcesCompat.getDrawable(activity.getResources(),R.drawable.debt,null);
        arrImage[13]=ResourcesCompat.getDrawable(activity.getResources(),R.drawable.give,null);
        arrImage[14]=ResourcesCompat.getDrawable(activity.getResources(),R.drawable.interest_money,null);
        arrImage[15]=ResourcesCompat.getDrawable(activity.getResources(),R.drawable.salary,null);
        arrImage[16]=ResourcesCompat.getDrawable(activity.getResources(),R.drawable.selling,null);
        arrImage[17]=ResourcesCompat.getDrawable(activity.getResources(),R.drawable.other,null);
        for (int i=0;i<18;i++) {
            arrImage[i] = new BitmapDrawable(activity.getResources(), Bitmap.createScaledBitmap(((BitmapDrawable) arrImage[i]).getBitmap(), 80, 80, true));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position) instanceof SupportTransaction ? TYPE_DATE : TYPE_TRANSACTION;
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_MAX_COUNT;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean isEnabled(int position) {
        if(mData.get(position) instanceof SupportTransaction){
            return false;
        }
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        int type = getItemViewType(position);
        if (convertView == null) {
            switch (type) {
                case TYPE_DATE:
                    holder = new ViewHolder();
                    convertView = mInflater.inflate(R.layout.item_date_transaction, null);
                    holder.tv1 = (TextView) convertView.findViewById(R.id.tvTransactionDay);
                    holder.tv2 = (TextView) convertView.findViewById(R.id.tvTransacsionMonth);
                    holder.tv3 = (TextView) convertView.findViewById(R.id.tvTransactionYear);
                    convertView.setTag(holder);
                    break;
                case TYPE_TRANSACTION:
                    holder = new ViewHolder();
                    convertView = mInflater.inflate(R.layout.item_transaction, null);
                    holder.tv1 = (TextView) convertView.findViewById(R.id.tvTransactionName);
                    holder.tv2 = (TextView) convertView.findViewById(R.id.tvTransactionAccount);
                    holder.tv3 = (TextView) convertView.findViewById(R.id.tvTransactionMoney);
                    holder.iv = (ImageView) convertView.findViewById(R.id.ivCategoryImage);
                    convertView.setTag(holder);
                    break;
            }

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        switch (type) {
            case TYPE_DATE:
                SupportTransaction support = (SupportTransaction) getItem(position);
                holder.tv1.setText(support.day);
                holder.tv2.setText(support.month);
                holder.tv3.setText(support.year);
                break;
            case TYPE_TRANSACTION:
                Transaction transaction = (Transaction) getItem(position);
                holder.tv1.setText(transaction.getTransactionName());
                Account account = databaseHelper.getAccount(transaction.getAccountID());
                if(account != null && account.getAccountName().isEmpty()) {
                    holder.tv2.setText(databaseHelper.getAccount(transaction.getAccountID()).getAccountName().toUpperCase());
                } else {
                    holder.tv2.setText("");
                }
                try {
                    holder.iv.setImageDrawable(arrImage[transaction.getCategoryID()]);
                } catch (Exception e) {
                    Log.e("ListTransactionAdapter","SET IMAGE");
                    e.printStackTrace();
                } finally {
                }
                holder.tv3.setText(NumberFormat.getInstance().format(transaction.getCash()));
                break;
        }



        return convertView;
    }
    private class ViewHolder {
        public TextView tv1;
        public TextView tv2;
        public TextView tv3;
        public ImageView iv;
    }

}
