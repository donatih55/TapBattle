package com.example.henriquedonati.tapbattle;


import android.graphics.Color;
import android.graphics.drawable.Drawable;

/**
 * Created by Henrique Donati on 9/22/2015.
 */
public class ScoreBoard {
    protected int leftScore = 0;
    protected int rightScore = 0;
    private static final String SVD_DATA = "SavedData";
    protected int colorLeft;
    protected int colorRight;
    protected Drawable pictureLeft;
    protected Drawable pictureRight;
    protected boolean isPictureLeft;
    protected boolean isPictureRight;
    protected boolean isLeftWin;


    ScoreBoard (int a, int b){
        leftScore = a;
        rightScore = b;
    }
    ScoreBoard (int a, int ac,  int b, int bc){
        leftScore = a;
        rightScore = b;
        colorLeft = ac;
        colorRight = bc;
    }
    public void refresh (int a, int ac,  int b, int bc){
        leftScore = a;
        rightScore = b;
        colorLeft = ac;
        colorRight = bc;
    }

    public void leftWon(){
        leftScore++;
    }

    public void rightWon(){
        rightScore++;
    }

    public int getLeftScore(){
        return leftScore;
    }

    public int getRightScore(){
        return rightScore;
    }

    public int getLeftColor(){
        return colorLeft;
    }

    public int getRightColor(){
        return colorRight;
    }



    public void setLeftColor( int ac){
        colorLeft = ac;

    }
    public void setRightColor ( int bc) {
        colorRight = bc;
    }


    public void resetScore(){
        leftScore = 0;
        rightScore = 0;
    }
    public Drawable getPictureLeft() {
        return pictureLeft;
    }

    public Drawable getPictureRight() {
        return pictureRight;
    }

    public boolean isPictureLeft() {
        return isPictureLeft;
    }

    public boolean isPictureRight() {
        return isPictureRight;
    }

    public void setPictureLeft(Drawable pictureLeft) {
        this.pictureLeft = pictureLeft;
    }

    public void setPictureRight(Drawable pictureRight) {
        this.pictureRight = pictureRight;
    }

    public void setIsPictureLeft(boolean isPictureLeft) {
        this.isPictureLeft = isPictureLeft;
    }

    public void setIsPictureRight(boolean isPictureRight) {
        this.isPictureRight = isPictureRight;
    }

    public boolean isLeftWin() {
        return isLeftWin;
    }

    public void setIsLeftWin(boolean isLeftWin) {
        this.isLeftWin = isLeftWin;
    }
}
