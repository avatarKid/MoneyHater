package vn.lol.moneyhater.moneyhater.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;

import vn.lol.moneyhater.momeyhater.R;
import vn.lol.moneyhater.moneyhater.Database.DataManager;
import vn.lol.moneyhater.moneyhater.Util.ConstantValue;
import vn.lol.moneyhater.moneyhater.activity.EditAccountActivity;
import vn.lol.moneyhater.moneyhater.adapter.ListAccountAdapter;
import vn.lol.moneyhater.moneyhater.model.Account;


public class AccountFragment extends Fragment {
    private ListAccountAdapter mAdapterAccount;
    private DataManager mDbHelper;
    private ArrayList<Account> listAccount;
    ListView mlistAccount;
    TextView mTotalMoney;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_account, container,
                false);

        // get global XML helper
        mDbHelper= (DataManager)getActivity().getApplicationContext();


        mlistAccount = (ListView)rootView.findViewById(R.id.lvAccount);
        mTotalMoney = (TextView) rootView.findViewById(R.id.tvAccTotalMoney);
        displayListAccount();
        mlistAccount.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), EditAccountActivity.class);
                intent.putExtra(ConstantValue.ACCOUNT_ID,listAccount.get(i).getAccountID());
//                intent.putExtra(ConstantValue.DB_HELPER,mDbHelper);
                startActivity(intent);
            }
        });

        return rootView;
    }

    public void displayListAccount(){
        listAccount = mDbHelper.getAllAccounts();
        Log.e("Account list at list:", mDbHelper.getAllAccounts().size() + "");
        mAdapterAccount = new ListAccountAdapter(getActivity(),listAccount);

        mAdapterAccount.notifyDataSetChanged();
        mlistAccount.setAdapter(mAdapterAccount);

        double total = 0;
        for(int i=0;i<listAccount.size();i++){
            total += listAccount.get(i).getCash();
        }
        mTotalMoney.setText(NumberFormat.getInstance().format(total) + ConstantValue.SETTING_CURRENCY);
    }
    @Override
    public void onResume() {
        super.onResume();
        displayListAccount();
    }
}
