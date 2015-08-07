package vn.lol.moneyhater.moneyhater.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import vn.lol.moneyhater.momeyhater.R;
import vn.lol.moneyhater.moneyhater.Database.DatabaseHelper;
import vn.lol.moneyhater.moneyhater.adapter.ListBudgetAdapter;
import vn.lol.moneyhater.moneyhater.model.Account;
import vn.lol.moneyhater.moneyhater.model.Budget;

public class BudgetFragment extends Fragment {
    private ListBudgetAdapter mAdapterBudget;
    ListView mlistBudget;
    private DatabaseHelper mDbHelper;

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
        ArrayList<Budget> listBudget = mDbHelper.getAllBudgets();

        mAdapterBudget = new ListBudgetAdapter(getActivity(),R.layout.item_account,listBudget);
        mlistBudget.setAdapter(mAdapterBudget);
        mAdapterBudget.notifyDataSetChanged();
    }
}
