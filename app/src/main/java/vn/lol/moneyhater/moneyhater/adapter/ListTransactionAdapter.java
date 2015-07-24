package vn.lol.moneyhater.moneyhater.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import vn.lol.moneyhater.momeyhater.R;
import vn.lol.moneyhater.moneyhater.model.SupportTransaction;
import vn.lol.moneyhater.moneyhater.model.Transaction;

/**
 * Created by huy on 7/24/2015.
 */
public class ListTransactionAdapter extends BaseAdapter {

    private static final int TYPE_DATE = 0;
    private static final int TYPE_TRANSACTION = 1;
    private static final int TYPE_MAX_COUNT = TYPE_TRANSACTION + 1;

    private ArrayList mData = new ArrayList();
    private LayoutInflater mInflater;

    public ListTransactionAdapter(Activity activity,ArrayList lstTrans) {
        mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mData = lstTrans;
    }

    public void addItem(final Transaction item) {
        mData.add(item);
        notifyDataSetChanged();
    }

    public void addSeparatorItem(final Transaction item) {
        mData.add(item);
        // save separator position
//        mSeparatorsSet.add(mData.size() - 1);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position) instanceof Transaction ? TYPE_TRANSACTION : TYPE_DATE;
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
    public View getView(int position, View convertView, ViewGroup parent) {
//        ViewHolder holder = null;
        int type = getItemViewType(position);
        System.out.println("getView " + position + " " + convertView + " type = " + type);
        if (convertView == null) {
//            holder = new ViewHolder();
            switch (type) {
                case TYPE_DATE:
                    convertView = mInflater.inflate(R.layout.item_date_transaction, null);
                    SupportTransaction support = (SupportTransaction) getItem(position);
                    ((TextView) convertView.findViewById(R.id.tvDateSet)).setText(support.date);
                    ((TextView) convertView.findViewById(R.id.tvDateSetSumMoney)).setText(support.money);
                    break;
                case TYPE_TRANSACTION:
                    convertView = mInflater.inflate(R.layout.item_transaction, null);
                    Transaction transaction = (Transaction) getItem(position);
                    ((TextView) convertView.findViewById(R.id.tvCategoryname)).setText(transaction.category);
                    ((TextView) convertView.findViewById(R.id.tvNote)).setText(transaction.note);
                    ((TextView) convertView.findViewById(R.id.tvTransacsionMoney)).setText(transaction.money);
                    break;
            }

        } else {

        }
        return convertView;
    }

}
