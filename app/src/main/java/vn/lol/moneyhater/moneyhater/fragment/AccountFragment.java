package vn.lol.moneyhater.moneyhater.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import vn.lol.moneyhater.momeyhater.R;
import vn.lol.moneyhater.moneyhater.Database.DatabaseHelper;
import vn.lol.moneyhater.moneyhater.adapter.ListAccountAdapter;
import vn.lol.moneyhater.moneyhater.model.Account;


public class AccountFragment extends Fragment {
    private ListAccountAdapter mAdapterAccount;
    private DatabaseHelper mDbHelper;
    ListView mlistAccount;
    TextView mTotalMoney;
    private final String[] name = {
            "ABC",
            "VINA"
    };
    private final int[] money = {
            1000000,
            500000
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_account, container,
                false);
        mAdapterAccount = new ListAccountAdapter(getActivity(),name,money);
        mlistAccount = (ListView)rootView.findViewById(R.id.lvAccount);
        mlistAccount.setAdapter(mAdapterAccount);
        mTotalMoney = (TextView) rootView.findViewById(R.id.tvAccTotalMoney);
        int total = 0;
        for(int i=0;i<money.length;i++){
            total += money[i];
        }
        mTotalMoney.setText(total+"");

        mDbHelper= (DatabaseHelper) container.getTag(R.id.TAG_DB_HELPER);

        Account acc1 = new Account("Vi9",1235.0,0);
        Account acc2 = new Account("Vi8",425.1,0);
        Account acc3 = new Account("Vi7",234.5,0);
        mDbHelper.insertAccount(acc1);
        mDbHelper.insertAccount(acc2);
        mDbHelper.insertAccount(acc3);

        ArrayList<Account> al = mDbHelper.getAllAccounts();

        return rootView;
    }

}
