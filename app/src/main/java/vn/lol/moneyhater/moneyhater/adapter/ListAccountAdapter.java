package vn.lol.moneyhater.moneyhater.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;

import vn.lol.moneyhater.momeyhater.R;
import vn.lol.moneyhater.moneyhater.Database.DataManager;
import vn.lol.moneyhater.moneyhater.Util.ConstantValue;
import vn.lol.moneyhater.moneyhater.model.Account;
import vn.lol.moneyhater.moneyhater.model.Transaction;

/**
 * Created by TuanAnh on 7/24/2015.
 */
public class ListAccountAdapter extends ArrayAdapter<Account> {
    private final Activity activity;
    private final ArrayList<Account> mAccount;
    private DataManager mDbHelper;

    public ListAccountAdapter(Activity activity, ArrayList<Account> account) {
        super(activity, R.layout.item_account, account);
        this.activity = activity;
        this.mAccount = account;
        mDbHelper= (DataManager)activity.getApplicationContext();
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
        ArrayList<Transaction> transactionsList = mDbHelper.getAllTransactions();
        double total = mAccount.get(position).getCash();
        for (Transaction t: transactionsList) {
            if(t.getAccountID() == mAccount.get(position).getAccountID()){
                if(t.getType() == ConstantValue.TRANSACTION_TYPE_EXPENSE) {
                    total -= t.getCash();
                }else {
                    total += t.getCash();
                }
            }
        }
        tvName.setText(mAccount.get(position).getAccountName());
        tvMoney.setText(NumberFormat.getInstance().format(total) + ConstantValue.SETTING_CURRENCY);
        return rowView;
    }
}
