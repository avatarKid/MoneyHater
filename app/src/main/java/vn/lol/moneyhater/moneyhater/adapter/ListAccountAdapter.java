package vn.lol.moneyhater.moneyhater.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import vn.lol.moneyhater.momeyhater.R;

/**
 * Created by TuanAnh on 7/24/2015.
 */
public class ListAccountAdapter extends ArrayAdapter<String> {
    private final Activity activity;
    private final ArrayList<String> mName;
    private final ArrayList<Double> mMoney;

    public ListAccountAdapter(Activity activity, ArrayList<String> name, ArrayList<Double> money) {
        super(activity, R.layout.item_account, name);
        this.activity = activity;
        this.mName = name;
        this.mMoney = money;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.item_account, null, true);
        TextView tvName = (TextView) rowView.findViewById(R.id.tvAccName);
        TextView tvMoney = (TextView) rowView.findViewById(R.id.tvAccMoney);
        tvName.setText(mName.get(position));
        tvMoney.setText(String.format("%.3f",mMoney.get(position)));
        return rowView;
    }
}
