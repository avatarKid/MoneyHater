package vn.lol.moneyhater.moneyhater.fragment;

import android.animation.Animator;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import vn.lol.moneyhater.momeyhater.R;
import vn.lol.moneyhater.moneyhater.view.Bar;
import vn.lol.moneyhater.moneyhater.view.BarGraph;
import vn.lol.moneyhater.moneyhater.view.HoloGraphAnimate;
import vn.lol.moneyhater.moneyhater.view.Line;
import vn.lol.moneyhater.moneyhater.view.LineGraph;
import vn.lol.moneyhater.moneyhater.view.LinePoint;
import vn.lol.moneyhater.moneyhater.view.PiceChartView;

public class ChartFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chart, container,
                false);
        float current = 600000;
        float chi = 400000;
//        View pice = (View)rootView.findViewById(R.id.charPice);
//        ((PiceChartView)pice).setPercentage(chi,current);
        ArrayList<Bar> points = new ArrayList<Bar>();
        Bar d = new Bar();
        d.setColor(Color.parseColor("#99CC00"));
        d.setName("Test1");
        d.setValue(10);
        Bar d2 = new Bar();
        d2.setColor(Color.parseColor("#FFBB33"));
        d2.setName("Test2");
        d2.setValue(20000000);
        Bar d3 = new Bar();
        d3.setColor(Color.parseColor("#FFEB33"));
        d3.setName("Test3");
        d3.setValue(40000000);
        points.add(d);
        points.add(d2);
        points.add(d3);
         BarGraph g = (BarGraph) rootView.findViewById(R.id.charPice);
        g.setBars(points);
        return rootView;
    }

    private void GetDatabase() {

    }



}
