package vn.lol.moneyhater.moneyhater.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import vn.lol.moneyhater.momeyhater.R;
import vn.lol.moneyhater.moneyhater.adapter.ListTransactionAdapter;
import vn.lol.moneyhater.moneyhater.model.SupportTransaction;
import vn.lol.moneyhater.moneyhater.model.Transaction;

public class TransactionFragment extends Fragment {
    private ListTransactionAdapter mAdapterTransaction;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_transaction, container,
                false);
        ArrayList al = new ArrayList();
        al.add(new SupportTransaction("27","500,000"));
        al.add(new Transaction("27/7/2015","Selling","400,000","Sell Tivi"));
        al.add(new Transaction("27/7/2015","Selling","100,000","Sell Laptop"));
        al.add(new SupportTransaction("29","1,000,000"));
        al.add(new Transaction("29/7/2015","Selling","600,000","Sell Telephone"));
        al.add(new Transaction("29/7/2015","Selling","400,000","Sell Fan"));
        mAdapterTransaction = new ListTransactionAdapter(getActivity(),al);
        ListView mlistAccount = (ListView)rootView.findViewById(R.id.lvTransaction);
        mlistAccount.setAdapter(mAdapterTransaction);
        return rootView;
    }
}
