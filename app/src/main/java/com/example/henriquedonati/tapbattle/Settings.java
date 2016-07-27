package com.example.henriquedonati.tapbattle;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


public class Settings extends Fragment {

    public Settings() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_settings, container, false);
        RelativeLayout left = (RelativeLayout) v.findViewById(R.id.leftLayout);
        RelativeLayout right = (RelativeLayout) v.findViewById(R.id.rightLayout);
        if (MainActivity.score.isPictureLeft){
            left.setBackground(MainActivity.score.getPictureLeft());
        }else{
            left.setBackgroundColor(MainActivity.score.getLeftColor());
        }
        if (MainActivity.score.isPictureRight){
            right.setBackground(MainActivity.score.getPictureRight());
        }else{
            right.setBackgroundColor(MainActivity.score.getRightColor());
        }
        return v;
    }

}
