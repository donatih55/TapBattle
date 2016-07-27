package com.example.henriquedonati.tapbattle;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    public static final String SVD_DATA = "SavedData";
    public int subtract = 50; // how much % of the screen would you like to subtract? (in pxs for now)
    public int leftWidth = 0; // current width for left side (automatic)
    public int rightWidth = 0; // current width for right side (automatic)
    public int screenSize = 0; // total screen size that those two should some up to (automatic)
    public static ScoreBoard score;
    public boolean gameover = false; // is it game over right now?
    public SharedPreferences settings;
    private static final int CAPTURE_IMAGE_RIGHT_ACTIVITY_REQUEST_CODE = 200;
    private static final int CAPTURE_IMAGE_LEFT_ACTIVITY_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        setContentView(R.layout.activity_main);

        //get saved data
        settings = getSharedPreferences(SVD_DATA, 0);
        score = new ScoreBoard(settings.getInt("leftScore", 0), Color.WHITE,
                settings.getInt("rightScore", 0), Color.BLACK);
        // init frgament
        mainMenu(null);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);}
    }

    public void startDeveloper (View v){
        // load fragment int
        Fragment dev = new DeveloperNotes();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, dev);
        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();
    }
    public void startSettings (View v){
        // load fragment int
        Fragment settings = new Settings();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, settings);
        if (v != null) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
    }
    public void startGame(View v){

        //we're not in gameover, we are starting
        gameover = false;

        // load fragment int
        Fragment game = new Game();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, game);
        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();
        changeSizes();

    }
    public void clearStack(){
        FragmentManager fm = getFragmentManager();
        for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }
    public void mainMenu(View v){
        Fragment main = new MainMenu();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, main);
        clearStack();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }
    public void gameOver(){
        // now it's game over
        gameover = true;
        //start the fragment
        Fragment game = new gameOver();
        //start the fragment manager
        FragmentManager fragmentManager = getFragmentManager();
        //start transaction
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //Replace the fragments
        fragmentTransaction.replace(R.id.frame, game);
        //Save it to the back button stack
        fragmentTransaction.addToBackStack(null);
        //commit
        fragmentTransaction.commit();
        
        // Load save
        SharedPreferences settings = getSharedPreferences(SVD_DATA, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("rightScore", score.getRightScore());
        editor.putInt("leftScore", score.getLeftScore());

        // save
        editor.commit();


    }

    public void tapMeLeft(View v){
        leftWidth = leftWidth + subtract;
        rightWidth = rightWidth - subtract;
        updateGame();
    }
    public void tapMeRight(View v){
        leftWidth = leftWidth - subtract;
        rightWidth = rightWidth + subtract;
        updateGame();
    }
    public void goToWebsite(View v){
        //Create the Intent
        Uri webpage = Uri.parse("http://www.henriquedonati.com");
        Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
        //Verify it
        PackageManager packageManager = getPackageManager();
        List activities = packageManager.queryIntentActivities(webIntent,
                PackageManager.MATCH_DEFAULT_ONLY);
        boolean isIntentSafe = activities.size() > 0;
        //start it
        if (isIntentSafe) {
            startActivity(webIntent);
        }
    }
    public void resetScore(View v){
        score.resetScore();
    }

    public void updateGame(){
        Button btnL = (Button) findViewById(R.id.Left);
        Button btnR = (Button) findViewById(R.id.Right);
        if (Math.abs(btnL.getWidth()) < screenSize/10){

            if (!gameover) {
                score.setIsLeftWin(false);
                score.rightWon();
                gameOver();

            }
        }else if(btnR.getWidth() < screenSize/10){
            if (!gameover) {
                score.setIsLeftWin(true);
                score.leftWon();
                gameOver();

            }
        }
        else{
            btnL.setWidth(leftWidth);
            btnR.setWidth(rightWidth);
        }
    }

    public void changeSizes(){
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        leftWidth = width/2;
        rightWidth = leftWidth;
        screenSize = width;
    }

    public void picRight(View v){
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // start the image capture Intent
        startActivityForResult(intent, CAPTURE_IMAGE_RIGHT_ACTIVITY_REQUEST_CODE);
    }
    public void picLeft(View v){
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // start the image capture Intent
        startActivityForResult(intent, CAPTURE_IMAGE_LEFT_ACTIVITY_REQUEST_CODE);
    }
    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
        } else {
            getFragmentManager().popBackStack();
            mainMenu(null);
        }
    }
    public void changeColorLeft (View v){
        score.setIsPictureLeft(false);
        switch (v.getId())
        {
            case R.id.leftBlack:
                score.setLeftColor(Color.BLACK);
                break;
            case R.id.leftRed:
                score.setLeftColor(Color.RED);
                break;
            case R.id.leftGreen:
                score.setLeftColor(Color.GREEN);
                break;
            case R.id.leftYellow:
                score.setLeftColor(Color.YELLOW);
                break;
            case R.id.leftBlue:
                score.setLeftColor(Color.BLUE);
                break;
            case R.id.leftWhite:
                score.setLeftColor(Color.WHITE);
                break;
        }
        startSettings(null);
    }
    public void changeColorRight (View v){
        score.setIsPictureRight(false);
        switch (v.getId())
        {
            case R.id.rightBlack:
                score.setRightColor(Color.BLACK);
                break;
            case R.id.rightRed:
                score.setRightColor(Color.RED);
                break;
            case R.id.rightGreen:
                score.setRightColor(Color.GREEN);
                break;
            case R.id.rightYellow:
                score.setRightColor(Color.YELLOW);
                break;
            case R.id.rightBlue:
                score.setRightColor(Color.BLUE);
                break;
            case R.id.rightWhite:
                score.setRightColor(Color.WHITE);
                break;
        }
        startSettings(null);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_RIGHT_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Image captured and saved to fileUri specified in the Intent
                Bitmap picture = (Bitmap) data.getExtras().get("data");
                Drawable drawable = new BitmapDrawable(getResources(), picture);
                score.setPictureRight(drawable);
                score.setIsPictureRight(true);
            } else if (resultCode == RESULT_CANCELED) {
                // User cancelled the image capture
                score.setIsPictureRight(false);
            } else {
                // Image capture failed, advise user
                score.setIsPictureRight(false);
            }
        }

        if (requestCode == CAPTURE_IMAGE_LEFT_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Video captured and saved to fileUri specified in the Intent
                Bitmap picture = (Bitmap) data.getExtras().get("data");
                Drawable drawable = new BitmapDrawable(getResources(), picture);
                score.setPictureLeft(drawable);
                score.setIsPictureLeft(true);
            } else if (resultCode == RESULT_CANCELED) {
                // User cancelled the video capture
                score.setIsPictureLeft(false);
            } else {
                // Video capture failed, advise user
                score.setIsPictureLeft(false);
            }
        }
        startSettings(null);
    }

}
