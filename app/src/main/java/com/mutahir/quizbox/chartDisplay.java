package com.mutahir.quizbox;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import static android.graphics.Color.YELLOW;

/**
 * Created by mutahir on 5/12/2017.
 */

public class chartDisplay extends Activity {
    AnimationDrawable animationDrawable;
    RelativeLayout relativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart);

        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(5000);
        animationDrawable.setExitFadeDuration(2000);
        Intent intent=getIntent();
        ArrayList<String> userNameList=intent.getStringArrayListExtra("userNameList");
        ArrayList<String> score1=intent.getStringArrayListExtra("score1");

        List<PieEntry> pieEntryList=new ArrayList<PieEntry>();
        for(int i=0;i<score1.size();i++){
            pieEntryList.add(new PieEntry(Float.parseFloat(score1.get(i)),userNameList.get(i)));
        }
        PieDataSet pie=new PieDataSet(pieEntryList,"Who know you better");
        pie.setColors(ColorTemplate.COLORFUL_COLORS);
        PieData data=new PieData(pie);
        data.setValueTextColor(Color.BLACK);
        PieChart chart=(PieChart)findViewById(R.id.chart);
        chart.setData(data);
        chart.invalidate();
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (animationDrawable != null && !animationDrawable.isRunning())
            animationDrawable.start();
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (animationDrawable != null && animationDrawable.isRunning())
            animationDrawable.stop();
    }
}
