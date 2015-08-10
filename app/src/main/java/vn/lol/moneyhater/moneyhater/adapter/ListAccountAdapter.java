package vn.lol.moneyhater.moneyhater.adapter;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;

import vn.lol.moneyhater.momeyhater.R;
import vn.lol.moneyhater.moneyhater.model.Account;

/**
 * Created by TuanAnh on 7/24/2015.
 */
public class ListAccountAdapter extends ArrayAdapter<Account> {
    private final Activity activity;
    private final ArrayList<Account> mAccount;

    public ListAccountAdapter(Activity activity, ArrayList<Account> account) {
        super(activity, R.layout.item_account, account);
        this.activity = activity;
        this.mAccount = account;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.item_account, null, true);
        TextView tvName = (TextView) rowView.findViewById(R.id.tvAccName);
        final TextView tvMoney = (TextView) rowView.findViewById(R.id.tvAccMoney);
        ImageView ivType = (ImageView) rowView.findViewById(R.id.ivAccType);
        if(mAccount.get(position).getAccountTypeID() == 0){
            ivType.setBackgroundResource(R.drawable.card);
        } else {
            ivType.setBackgroundResource(R.drawable.cash);
        }
        tvName.setText(mAccount.get(position).getAccountName());
        tvMoney.setText(NumberFormat.getInstance().format(mAccount.get(position).getCash()));
        return rowView;
    }
}
