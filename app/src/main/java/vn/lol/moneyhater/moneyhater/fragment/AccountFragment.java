package vn.lol.moneyhater.moneyhater.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_account, container,
                false);
        mDbHelper= (DatabaseHelper) container.getTag(R.id.TAG_DB_HELPER);
        mlistAccount = (ListView)rootView.findViewById(R.id.lvAccount);
        mTotalMoney = (TextView) rootView.findViewById(R.id.tvAccTotalMoney);
        displayListAccount();

        return rootView;
    }

    public void displayListAccount(){

        ArrayList<String> listAccountName = new ArrayList<String>();
        ArrayList<Double> listCash = new ArrayList<Double>();
        ArrayList<Account> listAccount = mDbHelper.getAllAccounts();
        for (int i=0; i< listAccount.size(); i++)
        {
            String accountName = listAccount.get(i).getAccountName();
            listAccountName.add(accountName);
            Double cash = listAccount.get(i).getCash();
            listCash.add(cash);
        }
        mAdapterAccount = new ListAccountAdapter(getActivity(),listAccountName,listCash);

        mAdapterAccount.notifyDataSetChanged();
        mlistAccount.setAdapter(mAdapterAccount);

        double total = 0;
        for(int i=0;i<listCash.size();i++){
            total += listCash.get(i);
        }
        mTotalMoney.setText(String.format("%.3f",total));
    }
    @Override
    public void onResume() {
        super.onResume();
        displayListAccount();
    }
}
