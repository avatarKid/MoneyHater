package vn.lol.moneyhater.moneyhater.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import vn.lol.moneyhater.momeyhater.R;
import vn.lol.moneyhater.moneyhater.Database.DatabaseHelper;
import vn.lol.moneyhater.moneyhater.Util.ConstantValue;
import vn.lol.moneyhater.moneyhater.activity.EditTransaction;
import vn.lol.moneyhater.moneyhater.adapter.ListTransactionAdapter;
import vn.lol.moneyhater.moneyhater.model.SupportTransaction;
import vn.lol.moneyhater.moneyhater.model.Transaction;
import vn.lol.moneyhater.moneyhater.model.TransactionDate;

public class TransactionFragment extends Fragment {
    private ListTransactionAdapter mAdapterTransaction;
    DatabaseHelper mDbHelper;
    ListView mlistTransaction;
    ArrayList<TransactionDate> listTransaction;
    private ArrayList<String> listDate;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_transaction, container,
                false);
        mDbHelper = (DatabaseHelper) container.getTag(R.id.TAG_DB_HELPER);
        listTransaction = new ArrayList<TransactionDate>();
        listDate = new ArrayList<>();
        mlistTransaction = (ListView) rootView.findViewById(R.id.lvTransaction);


        mlistTransaction.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int transactionID = ((Transaction) listTransaction.get(position)).getTransactionID();
                Intent intent = new Intent(getActivity(), EditTransaction.class);
                intent.putExtra("transaction", mDbHelper.getTransaction(transactionID));
                getActivity().startActivityForResult(intent, ConstantValue.REQUEST_CODE_EDIT_TRANSACTION);
            }
        });
        return rootView;
    }



    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    public void addItem(Transaction item) {
        boolean dateExist = false;
        String date = item.getDate();
        Calendar calendar = item.getCalendar();

        SimpleDateFormat df = new SimpleDateFormat("yyyy:MM:dd:hh:mm:ss");
        Date d1 = null;
        try {
            d1 = df.parse(calendar.get(Calendar.YEAR) + ":" + (calendar.get(Calendar.MONTH) + 1) + ":" + calendar.get(Calendar.DAY_OF_MONTH) + ":23:59:59");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.setTime(d1);

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
    }

    /**
     * Created by expert Huy
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ConstantValue.REQUEST_CODE_ADD_TRANSACTION) {
            if (resultCode == Activity.RESULT_OK) {
                Transaction transaction = (Transaction) data.getSerializableExtra(ConstantValue.NEW_TRANSACTION);
                addItem(transaction);
                Log.e("Transaction type: ", transaction.getType() + "");
                mAdapterTransaction = new ListTransactionAdapter(getActivity(), listTransaction);
                mlistTransaction.setAdapter(mAdapterTransaction);
                mAdapterTransaction.notifyDataSetChanged();
                mDbHelper.insertTransaction(transaction);
            }
        }
        if(requestCode == ConstantValue.REQUEST_CODE_EDIT_TRANSACTION){
            if (resultCode == ConstantValue.RESULT_CODE_DELETE_TRANSACTION){
                mDbHelper.deleteTransaction(data.getIntExtra(ConstantValue.TRANSACTION_ID, 0));
                loadData();
                if(mDbHelper.getAllTransactions().isEmpty()){
                    listTransaction.clear();
                    mAdapterTransaction.notifyDataSetChanged();
                }
            }
        }
    }

    public void loadData() {
        listTransaction.clear();
        for (Transaction transaction : mDbHelper.getAllTransactions()) {
            addItem(transaction);
            Log.e("IDDD: ", transaction.getTransactionID() + "");
        }
        if(!mDbHelper.getAllTransactions().isEmpty()){
            Log.e("NULL", "Llsllslsls");
            mAdapterTransaction = new ListTransactionAdapter(getActivity(), listTransaction);
            mlistTransaction.setAdapter(mAdapterTransaction);
            mAdapterTransaction.notifyDataSetChanged();
        }
    }

}
