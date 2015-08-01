package vn.lol.moneyhater.moneyhater.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import vn.lol.moneyhater.momeyhater.R;
import vn.lol.moneyhater.moneyhater.Database.DatabaseHelper;
import vn.lol.moneyhater.moneyhater.Util.ConstantValue;
import vn.lol.moneyhater.moneyhater.activity.NewTransactionActivity;
import vn.lol.moneyhater.moneyhater.adapter.ListTransactionAdapter;
import vn.lol.moneyhater.moneyhater.model.SupportTransaction;
import vn.lol.moneyhater.moneyhater.model.Transaction;
import vn.lol.moneyhater.moneyhater.model.TransactionDate;

public class TransactionFragment extends Fragment {
    private ListTransactionAdapter mAdapterTransaction;
    DatabaseHelper mDbHelper;
    ListView mlistAccount;
    ArrayList<TransactionDate> listTransaction;
    private ArrayList<String> listDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_transaction, container,
                false);
        mDbHelper = (DatabaseHelper) container.getTag(R.id.TAG_DB_HELPER);
        listTransaction = new ArrayList();
        listDate = new ArrayList<>();
        mlistAccount = (ListView) rootView.findViewById(R.id.lvTransaction);

        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    public void addItem(Transaction item) {
        boolean dateExist = false;
        String date = item.getDate();
        Calendar calendar = item.getCalendar();
        Log.e("after : ", calendar.toString());
        if (listTransaction.size() == 0) {
            listTransaction.add(new SupportTransaction(item.getDay(), item.getMonth(), item.getYear(), calendar));
            listDate.add(date);
        }


        for (int i = 0; i < listDate.size(); i++) {
            if (date.equals(listDate.get(i))) {
                dateExist = true;
            }
        }

        if (!dateExist) {
            listTransaction.add(new SupportTransaction(item.getDay(), item.getMonth(), item.getYear(), calendar));
            listDate.add(date);
        }

        listTransaction.add(item);
        Collections.sort(listTransaction);

        for (int i = 0; i< listTransaction.size(); i++){
            if(listTransaction.get(i) instanceof SupportTransaction){
                Log.e("Supp", listTransaction.get(i).getDate());
            } else {
                Log.e("trans", listTransaction.get(i).getDate());
            }
        }

    }

    /**
     * Created by expert Huy
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ConstantValue.REQUEST_CODE_ADD_TRANSACTION) {
            if(resultCode == Activity.RESULT_OK){
                Transaction transaction= (Transaction) data.getSerializableExtra(ConstantValue.NEW_TRANSACTION);
                addItem(transaction);
                mAdapterTransaction = new ListTransactionAdapter(getActivity(), listTransaction);
                mlistAccount.setAdapter(mAdapterTransaction);
                mAdapterTransaction.notifyDataSetChanged();
                mDbHelper.insertTransaction(transaction);
            }
        }
    }

}
