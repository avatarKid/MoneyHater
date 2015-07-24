package vn.lol.moneyhater.moneyhater.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import vn.lol.moneyhater.momeyhater.R;

/**
 * Created by TuanAnh on 7/24/2015.
 */
public class ListBudgetAdapter extends ArrayAdapter<String> {
    private final Activity activity;
    private final String[] mName;
    private final int[] mMoney;

    public ListBudgetAdapter(Activity activity, String[] name, int[] money) {
        super(activity, R.layout.item_account, name);
        this.activity = activity;
        this.mName = name;
        this.mMoney = money;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.item_bubget, null, true);
        TextView tvName = (TextView) rowView.findViewById(R.id.tvBudgetName);
        TextView tvMoney = (TextView) rowView.findViewById(R.id.tvBudgetMoney);
        tvName.setText(mName[position]);
        tvMoney.setText(mMoney[position]+"");
        return rowView;
    }
}

