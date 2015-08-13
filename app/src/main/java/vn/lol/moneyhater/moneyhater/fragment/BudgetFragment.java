package vn.lol.moneyhater.moneyhater.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import vn.lol.moneyhater.momeyhater.R;
import vn.lol.moneyhater.moneyhater.Database.DatabaseHelper;
import vn.lol.moneyhater.moneyhater.Util.ConstantValue;
import vn.lol.moneyhater.moneyhater.activity.EditAccountActivity;
import vn.lol.moneyhater.moneyhater.activity.EditBudgetActivity;
import vn.lol.moneyhater.moneyhater.adapter.ListBudgetAdapter;
import vn.lol.moneyhater.moneyhater.model.Account;
import vn.lol.moneyhater.moneyhater.model.Budget;

public class BudgetFragment extends Fragment {
    private ListBudgetAdapter mAdapterBudget;
    ListView mlistBudget;
    private DatabaseHelper mDbHelper;
    private ArrayList<Budget> listBudget;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_budget, container,
                false);
        mDbHelper= (DatabaseHelper) container.getTag(R.id.TAG_DB_HELPER);
        mlistBudget = (ListView)rootView.findViewById(R.id.lvBudget);
        displayLisBudget();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        displayLisBudget();
    }

    private void displayLisBudget() {
        listBudget = mDbHelper.getAllBudgets();

        mAdapterBudget = new ListBudgetAdapter(getActivity(),R.layout.item_account,listBudget);
        mlistBudget.setAdapter(mAdapterBudget);
        mAdapterBudget.notifyDataSetChanged();
        mlistBudget.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), EditBudgetActivity.class);
                intent.putExtra(ConstantValue.BUDGET_ID, listBudget.get(position).getBudgetID());
                startActivity(intent);
            }
        });
    }
}
