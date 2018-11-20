package com.mutahir.quizbox;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by mutahir on 5/11/2017.
 */

public class CustomList extends ArrayAdapter<String> {


    static List<String> score1;
    public CustomList(Context context, List<String> userNameList) {
        super(context, R.layout.custom,userNameList);
        score1=ListOfPeople.score1;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater mutahirinflater=LayoutInflater.from(getContext());
        View customView=mutahirinflater.inflate(R.layout.custom,parent,false);

        String singlefooditem=getItem(position);
        TextView name=(TextView)customView.findViewById(R.id.name);
        name.setText(singlefooditem);

        TextView score=(TextView)customView.findViewById(R.id.score);
        score.setText(score1.get(position)+"/10");
        return customView;

    }
}
