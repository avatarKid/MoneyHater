package vn.lol.moneyhater.moneyhater.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.lol.moneyhater.momeyhater.R;
import vn.lol.moneyhater.moneyhater.view.PiceChartView;

public class ChartFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chart, container,
                false);
//        float current = 600000;
//        float chi = 400000;
//        View pice = (View)rootView.findViewById(R.id.charPice);
//        ((PiceChartView)pice).setPercentage(chi,current);
        return rootView;
    }

}
