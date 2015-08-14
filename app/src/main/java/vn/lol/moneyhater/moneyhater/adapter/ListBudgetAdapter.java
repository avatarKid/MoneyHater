package vn.lol.moneyhater.moneyhater.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;

import vn.lol.moneyhater.momeyhater.R;
import vn.lol.moneyhater.moneyhater.model.Budget;

/**
 * Created by TuanAnh on 7/24/2015.
 */
public class ListBudgetAdapter extends ArrayAdapter<Budget> {
    private  Activity mActivity;
    private ArrayList<Budget> mBudgets = new ArrayList<Budget>();

    public ListBudgetAdapter(Activity activity, int resource, ArrayList<Budget> budgets) {
        super(activity, resource, budgets);
        this.mActivity = activity;
        this.mBudgets = budgets;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = mActivity.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.item_bubget, null, true);
        TextView tvName = (TextView) rowView.findViewById(R.id.tvBudgetName);
        TextView tvMoney = (TextView) rowView.findViewById(R.id.tvBudgetMoney);
        tvName.setText(mBudgets.get(position).getBudgetName()+ "");
        tvMoney.setText(NumberFormat.getInstance().format(mBudgets.get(position).getCash()));
        return rowView;
    }
}

