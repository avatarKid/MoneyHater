package vn.lol.moneyhater.moneyhater.fragment;

import android.animation.Animator;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import vn.lol.moneyhater.momeyhater.R;
import vn.lol.moneyhater.moneyhater.view.Bar;
import vn.lol.moneyhater.moneyhater.view.BarGraph;
import vn.lol.moneyhater.moneyhater.view.Line;
import vn.lol.moneyhater.moneyhater.view.LineGraph;
import vn.lol.moneyhater.moneyhater.view.LinePoint;
import vn.lol.moneyhater.moneyhater.view.PiceChartView;

public class ChartFragment extends Fragment {
    float []MoneySpend = {1000,2000,3000,4000,5000,6000,7000,8000,9000,10000,11000,12000};
    float []MoneyHave = {6000,9000,12000,3000,9000,2000,1000,6000,2000,7000,1000,2000};
    private int []PointMoneyS;
    private int []PointMoneyH;
    private float maxMoney = 0;
    private Line l,l1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chart, container,
                false);
        PointMoneyS = new int[12];
        PointMoneyH = new int[12];
        l = new Line();
        l.setColor(Color.parseColor("#33ad34"));
        l1 = new Line();
        l1.setColor(Color.parseColor("#ff4444"));
        maxMoney = 0;
        SetupBar(rootView);
        SetupPointLine(rootView);
        return rootView;
    }

    private void SetupBar(View rootView){
        ArrayList<Bar> points = new ArrayList<Bar>();
        Bar d = new Bar();
        d.setColor(Color.parseColor("#ff4444"));
        d.setName("Spend");
        d.setValue(10500000);
        Bar d2 = new Bar();
        d2.setColor(Color.parseColor("#33ad34"));
        d2.setName("Have");
        d2.setValue(20000000);
        points.add(d);
        points.add(d2);
        BarGraph g = (BarGraph) rootView.findViewById(R.id.charBar);
        g.setBars(points);
    }

    private void SetupPointLine(View rootView){
        ProcessAnalysLineGraph();
        LineGraph li = (LineGraph)rootView.findViewById(R.id.charLine);
        li.addLine(l);
        li.addLine(l1);
        li.setRangeY(0, 11);
        li.setLineToFill(0);
        li.setOnPointClickedListener(new LineGraph.OnPointClickedListener() {
            @Override
            public void onClick(int lineIndex, int pointIndex) {
                if(lineIndex == 0){
                    Toast.makeText(getActivity(),
                            ""+(int)MoneyHave[pointIndex],
                            Toast.LENGTH_SHORT)
                            .show();
                }else if (lineIndex == 1){
                    Toast.makeText(getActivity(),
                            "- "+(int)MoneySpend[pointIndex], Toast.LENGTH_SHORT)
                            .show();
                }

            }
        });
    }

    private LinePoint createNewPoint(int x,int y){
        LinePoint p = new LinePoint();
        p.setX(x);
        p.setY(y);
        return p;
    }

    private void ProcessAnalysLineGraph(){
        //Find max money
        for(int i=0;i<MoneySpend.length;i++){
            if(MoneySpend[i]>maxMoney){
                maxMoney = MoneySpend[i];
            }else if(MoneyHave[i] > maxMoney){
                maxMoney = MoneyHave[i];
            }
        }
        //Calculate Line Point
        for(int i=0;i<MoneySpend.length;i++){
            PointMoneyS[i] = (int)((MoneySpend[i]/maxMoney) *10);
            l1.addPoint(createNewPoint(i,PointMoneyS[i]));
            PointMoneyH[i] = (int)((MoneyHave[i]/maxMoney) *10);
            l.addPoint(createNewPoint(i,PointMoneyH[i]));
        }
    }


    private void GetDatabase() {

    }



}
