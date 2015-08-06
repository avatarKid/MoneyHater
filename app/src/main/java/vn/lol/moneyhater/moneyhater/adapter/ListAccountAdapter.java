package vn.lol.moneyhater.moneyhater.adapter;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
    private String current = "";

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
        tvName.setText(mAccount.get(position).getAccountName());
        tvMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i2, int i3) {

                if(!s.toString().equals(""))
                {
                    if(!s.toString().equals(current)){
                        String cleanString = s.toString().replaceAll("[,.]", "");
                        double parsed = Double.parseDouble(cleanString);
                        String formated = NumberFormat.getInstance().format((parsed/10));
                        current = formated;
                        tvMoney.setText(formated);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        tvMoney.setText(mAccount.get(position).getCash() + "");
        return rowView;
    }
}
