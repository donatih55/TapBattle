package com.example.henriquedonati.tapbattle;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class gameOver extends Fragment {

    public gameOver() {
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

        View inf =  inflater.inflate(R.layout.fragment_game_over, container, false);
        // Set the Text to try this out
        TextView left = (TextView) inf.findViewById(R.id.leftScore);
        left.setText(String.valueOf(MainActivity.score.getLeftScore()));
        TextView right = (TextView) inf.findViewById(R.id.rightScore);
        right.setText(String.valueOf(MainActivity.score.getRightScore()));
        if (MainActivity.score.isLeftWin()){
            if(MainActivity.score.isPictureLeft()){
                inf.setBackground(MainActivity.score.getPictureLeft());
            }else{
                inf.setBackgroundColor(MainActivity.score.getLeftColor());
            }
        }else{
            if(MainActivity.score.isPictureRight()){
                inf.setBackground(MainActivity.score.getPictureRight());
            }else{
                inf.setBackgroundColor(MainActivity.score.getRightColor());
            }
        }


        return inf;
    }

}
