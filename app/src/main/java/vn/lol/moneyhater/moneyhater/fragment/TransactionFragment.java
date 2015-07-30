package vn.lol.moneyhater.moneyhater.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
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
        ArrayList al = new ArrayList();
        mDbHelper = (DatabaseHelper) container.getTag(R.id.TAG_DB_HELPER);
        al.add(new SupportTransaction("30","July", "2015", "500,000"));
        al.add(new Transaction("Oc cho",1, 1800000 ,1,1,1 ));
//        al.add(new Transaction("27/7/2015","Selling","100,000","Sell Laptop"));
//        al.add(new SupportTransaction("29","1,000,000"));
//        al.add(new Transaction("29/7/2015","Selling","600,000","Sell Telephone"));
//        al.add(new Transaction("29/7/2015","Selling","400,000","Sell Fan"));
        mAdapterTransaction = new ListTransactionAdapter(getActivity(),al);
        ListView mlistAccount = (ListView)rootView.findViewById(R.id.lvTransaction);
        mlistAccount.setAdapter(mAdapterTransaction);
        return rootView;
    }

    public void addTransaction(){
        /*Hard code*/

//        Transaction transaction1 = new Transaction("Name",1, new Date(),1,1,1 );

    }
}
