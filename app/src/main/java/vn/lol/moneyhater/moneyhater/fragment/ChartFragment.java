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
        d.setColor(Color.parseColor("#ff4444"));
        d.setName("Spend");
        d.setValue(10000000);
        Bar d2 = new Bar();
        d2.setColor(Color.parseColor("#33ad34"));
        d2.setName("Have");
        d2.setValue(20000000);
        points.add(d);
        points.add(d2);
        BarGraph g = (BarGraph) rootView.findViewById(R.id.charBar);
        g.setBars(points);

        SetupPointLine(rootView);
        return rootView;
    }

    private void SetupPointLine(View rootView){
        Line l = new Line();
        l.addPoint(createNewPoint(0,1));
        l.addPoint(createNewPoint(1,2));
        l.addPoint(createNewPoint(2,3));
        l.addPoint(createNewPoint(3,4));
        l.addPoint(createNewPoint(4,5));
        l.addPoint(createNewPoint(5,6));
        l.addPoint(createNewPoint(6,7));
        l.addPoint(createNewPoint(7,8));
        l.addPoint(createNewPoint(8,9));
        l.addPoint(createNewPoint(9,10));
        l.addPoint(createNewPoint(10,1));
        l.addPoint(createNewPoint(11, 2));
        l.setColor(Color.parseColor("#33ad34"));
        Line l1 = new Line();
        l1.addPoint(createNewPoint(0,4));
        l1.addPoint(createNewPoint(1,5));
        l1.addPoint(createNewPoint(2,2));
        l1.addPoint(createNewPoint(3, 5));
        l1.addPoint(createNewPoint(4,3));
        l1.addPoint(createNewPoint(5,5));
        l1.addPoint(createNewPoint(6,1));
        l1.addPoint(createNewPoint(7,6));
        l1.addPoint(createNewPoint(8,2));
        l1.addPoint(createNewPoint(9,5));
        l1.addPoint(createNewPoint(10,4));
        l1.addPoint(createNewPoint(11,5));
        l1.setColor(Color.parseColor("#ff4444"));
        LineGraph li = (LineGraph)rootView.findViewById(R.id.charLine);

        li.addLine(l);
        li.addLine(l1);
        li.setRangeY(0, 11);
        li.setLineToFill(0);
    }

    private LinePoint createNewPoint(int x,int y){
        LinePoint p = new LinePoint();
        p.setX(x);
        p.setY(y);
        return p;
    }

    private void GetDatabase() {

    }



}
