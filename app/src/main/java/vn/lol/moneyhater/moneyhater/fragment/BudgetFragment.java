package vn.lol.moneyhater.moneyhater.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import vn.lol.moneyhater.momeyhater.R;
import vn.lol.moneyhater.moneyhater.adapter.ListBudgetAdapter;

public class BudgetFragment extends Fragment {
    private ListBudgetAdapter mAdapterBudget;
    ListView mlistBudget;
    private final String[] name = {
            "Project",
            "Party"
    };
    private final int[] money = {
            500000,
            1000000
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_budget, container,
                false);
        mAdapterBudget = new ListBudgetAdapter(getActivity(),name,money);
        mlistBudget = (ListView)rootView.findViewById(R.id.lvBudget);
        mlistBudget.setAdapter(mAdapterBudget);
        return rootView;
    }

}
