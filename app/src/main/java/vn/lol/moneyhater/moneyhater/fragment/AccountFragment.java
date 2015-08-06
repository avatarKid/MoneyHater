package vn.lol.moneyhater.moneyhater.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;

import vn.lol.moneyhater.momeyhater.R;
import vn.lol.moneyhater.moneyhater.Database.DatabaseHelper;
import vn.lol.moneyhater.moneyhater.Util.ConstantValue;
import vn.lol.moneyhater.moneyhater.activity.EditAccountActivity;
import vn.lol.moneyhater.moneyhater.adapter.ListAccountAdapter;
import vn.lol.moneyhater.moneyhater.model.Account;


public class AccountFragment extends Fragment {
    private ListAccountAdapter mAdapterAccount;
    private DatabaseHelper mDbHelper;
    private ArrayList<Account> listAccount;
    private String current = "";
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
        mlistAccount.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), EditAccountActivity.class);
                intent.putExtra(ConstantValue.ACCOUNT_ID,listAccount.get(i).getAccountID());
                startActivity(intent);
            }
        });

        return rootView;
    }

    public void displayListAccount(){
        listAccount = mDbHelper.getAllAccounts();
        mAdapterAccount = new ListAccountAdapter(getActivity(),listAccount);

        mAdapterAccount.notifyDataSetChanged();
        mlistAccount.setAdapter(mAdapterAccount);

        double total = 0;
        for(int i=0;i<listAccount.size();i++){
            total += listAccount.get(i).getCash();
        }
        mTotalMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i2, int i3) {
                if(!s.toString().equals(""))
                {
                    if(!s.toString().equals(current)){
                        String cleanString = s.toString().replaceAll("[,.]", "");
                        double parsed = Double.parseDouble(cleanString);
                        String formated = NumberFormat.getInstance().format((parsed/10));
                        current = formated;
                        mTotalMoney.setText(formated);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mTotalMoney.setText(total + "");
    }
    @Override
    public void onResume() {
        super.onResume();
        displayListAccount();
    }
}
