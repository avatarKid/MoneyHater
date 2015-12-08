package vn.lol.moneyhater.moneyhater.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import vn.lol.moneyhater.momeyhater.R;
import vn.lol.moneyhater.moneyhater.Database.DataManager;
import vn.lol.moneyhater.moneyhater.Util.ConstantValue;
import vn.lol.moneyhater.moneyhater.activity.EditTransaction;
import vn.lol.moneyhater.moneyhater.adapter.ListTransactionAdapter;
import vn.lol.moneyhater.moneyhater.model.Account;
import vn.lol.moneyhater.moneyhater.model.SupportTransaction;
import vn.lol.moneyhater.moneyhater.model.Transaction;
import vn.lol.moneyhater.moneyhater.model.TransactionDate;

public class TransactionFragment extends Fragment {
    private ListTransactionAdapter mAdapterTransaction;
    DataManager mDbHelper;
    ListView mlistTransaction;
    TextView tvIncome, tvExpense, tvSumaryTransaction;
    ArrayList<TransactionDate> listTransaction;
    private ArrayList<String> listDate;
    int selectedTransaction = 0;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Init
        View rootView = inflater.inflate(R.layout.fragment_transaction, container,
                false);

        // get global XML helper
        mDbHelper= (DataManager)getActivity().getApplicationContext();

        listTransaction = new ArrayList<TransactionDate>();
        listDate = new ArrayList<>();
        mlistTransaction = (ListView) rootView.findViewById(R.id.lvTransaction);
        tvIncome = (TextView) rootView.findViewById(R.id.tvIncome);
        tvExpense = (TextView) rootView.findViewById(R.id.tvExpense);
        tvSumaryTransaction = (TextView) rootView.findViewById(R.id.tvSumTransaction);
        loadData();

        mlistTransaction.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedTransaction = position;
                int transactionID = ((Transaction) listTransaction.get(position)).getTransactionID();
                Intent intent = new Intent(getActivity(), EditTransaction.class);
                intent.putExtra(ConstantValue.EDIT_TRANSACTION, mDbHelper.getTransaction(transactionID));
//                intent.putExtra(ConstantValue.DB_HELPER,mDbHelper);
                getActivity().startActivityForResult(intent, ConstantValue.REQUEST_CODE_EDIT_TRANSACTION);
            }
        });
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    /*
    * Add transaction to list
    * */
    public void addTransaction(Transaction item) {
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
            if (date.equals(listDate.get(i) + "")) {
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ConstantValue.REQUEST_CODE_ADD_TRANSACTION) {
            if (resultCode == Activity.RESULT_OK) {
                Transaction transaction = (Transaction) data.getSerializableExtra(ConstantValue.NEW_TRANSACTION);
                addTransaction(transaction);
                mAdapterTransaction = new ListTransactionAdapter(getActivity(), listTransaction, mDbHelper);
                mlistTransaction.setAdapter(mAdapterTransaction);
                mAdapterTransaction.notifyDataSetChanged();
                mDbHelper.insertTransaction(transaction);
            }
        }
        if (requestCode == ConstantValue.REQUEST_CODE_EDIT_TRANSACTION) {
            if (resultCode == ConstantValue.RESULT_CODE_DELETE_TRANSACTION) {
                int transactionID = data.getIntExtra(ConstantValue.TRANSACTION_ID, 0);
                mDbHelper.deleteTransaction(transactionID);
                removeTransaction(transactionID);
            }
        }
        if (requestCode == ConstantValue.REQUEST_CODE_EDIT_TRANSACTION) {
            if (resultCode == ConstantValue.RESULT_CODE_SAVE_TRANSACTION) {
                Transaction transaction = (Transaction) data.getSerializableExtra(ConstantValue.SAVE_TRANSACTION);
                updateTransaction(transaction);
            }
        }
        calculateIncomeAndExpense();
    }

    public void updateTransaction(Transaction transaction) {
        removeTransaction(transaction.getTransactionID());
        addTransaction(transaction);
        mAdapterTransaction.notifyDataSetChanged();
        mDbHelper.updateTransaction(transaction);
    }

    public void removeTransaction(int transactionID) {
        // Transaction will be delete
        Transaction deleteTransaction = (Transaction) listTransaction.get(selectedTransaction);
        String dateOfDeleteTransaction = deleteTransaction.getDate();

        // get count of transaction have the same date with deleteTransaction
        int numberOfSameDate = 0;
        for (int i = 0; i < listTransaction.size(); i++) {
            if (listTransaction.get(i).getDate().equals(dateOfDeleteTransaction) &&
                    listTransaction.get(i) instanceof Transaction) {
                numberOfSameDate++;
            }
        }
        listTransaction.remove(selectedTransaction);
        // check if there is only 1 transaction have xxx date in listTransaction -> remove that date in listDate
        for (int i = 0; i < listDate.size(); i++) {
            if (listDate.get(i).equals(dateOfDeleteTransaction) && numberOfSameDate == 1) {
                listDate.remove(i);
                listTransaction.remove(selectedTransaction - 1);
            }
        }

        mAdapterTransaction.notifyDataSetChanged();
        if (mDbHelper.getAllTransactions().isEmpty()) {
            listTransaction.clear();
            mAdapterTransaction.notifyDataSetChanged();
        }
    }

    /*
    * Load data onCreate
    * */
    public void loadData() {
        listTransaction.clear();

        for (Transaction transaction : mDbHelper.getAllTransactions()) {
            ArrayList<Account> listAccount = mDbHelper.getAllAccounts();
            for(int i=0;i<listAccount.size();i++) {
                if(listAccount.get(i).getIsDeleted() != 1){
                    if(transaction.getAccountID() == listAccount.get(i).getAccountID()){
                        addTransaction(transaction);
                    }
                }
            }
        }

        if (!mDbHelper.getAllTransactions().isEmpty()) {
            mAdapterTransaction = new ListTransactionAdapter(getActivity(), listTransaction, mDbHelper);
            mlistTransaction.setAdapter(mAdapterTransaction);
            mAdapterTransaction.notifyDataSetChanged();
        }
        calculateIncomeAndExpense();
    }

    /*
    * Calculate income and expense
    * */
    public void calculateIncomeAndExpense() {
        double income = 0;
        double expense = 0;

        for (int i = 0; i < listTransaction.size(); i++) {
            if (listTransaction.get(i) instanceof Transaction) {
                Transaction transaction = (Transaction) listTransaction.get(i);
                if (transaction.getType() == ConstantValue.TRANSACTION_TYPE_EXPENSE) {
                    expense += transaction.getCash();
                } else {
                    income += transaction.getCash();
                }
            }
        }

        tvIncome.setText(NumberFormat.getInstance().format(income) + ConstantValue.SETTING_CURRENCY);
        tvExpense.setText(NumberFormat.getInstance().format(expense) + ConstantValue.SETTING_CURRENCY);
        tvSumaryTransaction.setText(NumberFormat.getInstance().format(income - expense) + ConstantValue.SETTING_CURRENCY);
    }
}
