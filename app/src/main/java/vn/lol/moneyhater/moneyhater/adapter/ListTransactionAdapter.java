package vn.lol.moneyhater.moneyhater.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import vn.lol.moneyhater.momeyhater.R;
import vn.lol.moneyhater.moneyhater.Database.DatabaseHelper;
import vn.lol.moneyhater.moneyhater.model.SupportTransaction;
import vn.lol.moneyhater.moneyhater.model.Transaction;
import vn.lol.moneyhater.moneyhater.model.TransactionDate;

/**
 * Created by HoangTuan on 7/24/2015.
 */
public class ListTransactionAdapter extends BaseAdapter {

    int listPosition = 0;
    private static final int TYPE_DATE = 0;
    private static final int TYPE_TRANSACTION = 1;
    private static final int TYPE_MAX_COUNT = TYPE_TRANSACTION + 1;

    private ArrayList<TransactionDate> mData = new ArrayList<TransactionDate>();
    private LayoutInflater mInflater;

    public ListTransactionAdapter(Activity activity, ArrayList<TransactionDate> lstTrans) {
        mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mData = lstTrans;
    }

    @Override
    public int getItemViewType(int position) {
        Log.e("POS: ",position + "");
        return mData.get(position) instanceof SupportTransaction ? TYPE_DATE : TYPE_TRANSACTION;
    }

    public void clear(){
        mData.clear();
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_MAX_COUNT;
    }

    @Override
    public int getCount() {
        if(mData.size() <= 0){
            return 1;
        }
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
                /* TODO get account name of transaction via Account ID */
                holder.tv2.setText(transaction.getTransactionName());
                holder.tv3.setText(transaction.getCash() + "");
                break;
        }



        return convertView;
    }
    private class ViewHolder {
        public TextView tv1;
        public TextView tv2;
        public TextView tv3;
    }

}
