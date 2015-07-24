package vn.lol.moneyhater.moneyhater.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import vn.lol.moneyhater.momeyhater.R;
import vn.lol.moneyhater.moneyhater.adapter.ListAccountAdapter;


public class AccountFragment extends Fragment {
    private ListAccountAdapter mAdapterAccount;
    ListView mlistAccount;
    TextView mTotalMoney;
    private final String[] name = {
            "ABC"
    };
    private final int[] money = {
            1000000
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
        return rootView;
    }

}
