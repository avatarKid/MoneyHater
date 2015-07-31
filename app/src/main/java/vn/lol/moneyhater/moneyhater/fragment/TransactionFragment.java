package vn.lol.moneyhater.moneyhater.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import vn.lol.moneyhater.momeyhater.R;
import vn.lol.moneyhater.moneyhater.Database.DatabaseHelper;
import vn.lol.moneyhater.moneyhater.adapter.ListTransactionAdapter;
import vn.lol.moneyhater.moneyhater.model.SupportTransaction;
import vn.lol.moneyhater.moneyhater.model.Transaction;

public class TransactionFragment extends Fragment {
    private ListTransactionAdapter mAdapterTransaction;
    DatabaseHelper mDbHelper;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_transaction, container,
                false);
        mDbHelper = (DatabaseHelper) container.getTag(R.id.TAG_DB_HELPER);
        mAdapterTransaction = new ListTransactionAdapter(getActivity(),new ArrayList());
        addTransaction();
        ListView mlistAccount = (ListView)rootView.findViewById(R.id.lvTransaction);
        mlistAccount.setAdapter(mAdapterTransaction);
        return rootView;
    }

    public void addTransaction(){
        /*Hard code*/
        Calendar calendar = Calendar.getInstance();
        calendar.set(2015, 07, 19);
        mAdapterTransaction.addItem(new Transaction("Oc Lon 1", 1, 1800000, 1, 1, 1, calendar));
        mAdapterTransaction.addItem(new Transaction("Oc cho 1", 1, 1800000, 1, 1, 1, Calendar.getInstance()));
        mAdapterTransaction.addItem(new Transaction("Oc cho 2", 1, 1800000, 1, 1, 1, Calendar.getInstance()));
        mAdapterTransaction.addItem(new Transaction("Oc cho 3", 1, 1800000, 1, 1, 1, Calendar.getInstance()));
        mAdapterTransaction.addItem(new Transaction("Oc Lon 2", 1, 1800000, 1, 1, 1, calendar));
        mAdapterTransaction.addItem(new Transaction("Oc Lon 3",1, 1800000 ,1,1,1, calendar));
        mAdapterTransaction.addItem(new Transaction("Oc Lon 4",1, 1800000 ,1,1,1, calendar));
    }
}
