package vn.lol.moneyhater.moneyhater.adapter;

import android.app.Activity;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

import java.text.NumberFormat;

import vn.lol.moneyhater.momeyhater.R;
import vn.lol.moneyhater.moneyhater.Util.ConstantValue;
import vn.lol.moneyhater.moneyhater.model.BudgetGroup;
import vn.lol.moneyhater.moneyhater.model.Transaction;

import static vn.lol.moneyhater.momeyhater.R.color.red_light;

/**
 * Created by Aoko on 8/13/2015.
 */
public class ListBudgetExpandableAdapter  extends BaseExpandableListAdapter {

    private final SparseArray<BudgetGroup> groups;
    public LayoutInflater inflater;
    public Activity activity;

    public ListBudgetExpandableAdapter(Activity activity, SparseArray<BudgetGroup> groups) {
        this.groups = groups;
        this.activity = activity;
        inflater = activity.getLayoutInflater();
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return groups.get(groupPosition).getChildren().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return groups.get(groupPosition).getChildren().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_budget_list_row_group , null);
        }
        BudgetGroup group = (BudgetGroup) getGroup(groupPosition);
        CheckedTextView name = (CheckedTextView) convertView.findViewById(R.id.tv_budget_groupname);
        TextView cash = (TextView) convertView.findViewById(R.id.tv_budget_total);
        TextView current = (TextView) convertView.findViewById(R.id.tv_budget_subtrack);
        name.setText(group.getBudget().getBudgetName());
        name.setChecked(isExpanded);
        cash.setText(NumberFormat.getInstance().format(group.getBudget().getCash()) + ConstantValue.SETTING_CURRENCY);
        current.setText(NumberFormat.getInstance().format(group.getCurrentCast()) + ConstantValue.SETTING_CURRENCY);
        if(group.getCurrentCast() < 0){
            current.setTextColor(activity.getResources().getColor(R.color.red_light));
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final Transaction children = (Transaction)getChild(groupPosition, childPosition);
        TextView name,date,cash;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_budget_list_row_detail, null);
        }
        name = (TextView) convertView.findViewById(R.id.tv_transaction_name);
        date = (TextView) convertView.findViewById(R.id.tv_transaction_cash);
        cash = (TextView) convertView.findViewById(R.id.tv_transaction_date);

        name.setText(children.getTransactionName());
        if(children.getType() == ConstantValue.TRANSACTION_TYPE_INCOME){
            cash.setText(NumberFormat.getInstance().format(children.getCash()) + ConstantValue.SETTING_CURRENCY);
            name.setTextColor(activity.getResources().getColor(R.color.green));
            cash.setTextColor(activity.getResources().getColor(R.color.green));
        }else {
            cash.setText(NumberFormat.getInstance().format(children.getCash()) + ConstantValue.SETTING_CURRENCY);
            name.setTextColor(activity.getResources().getColor(R.color.red_light));
            cash.setTextColor(activity.getResources().getColor(R.color.red_light));
        }

        date.setText(children.getDate());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

}
