package com.example.henriquedonati.tapbattle;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceManager;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;



public class Game extends Fragment {

    public Game() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //changeSizes();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_game, container, false);
        Button left = (Button) v.findViewById(R.id.Left);
        Button right = (Button) v.findViewById(R.id.Right);
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
