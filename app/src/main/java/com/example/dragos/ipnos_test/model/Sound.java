package com.example.dragos.ipnos_test.model;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.media.MediaPlayer;
import android.widget.ImageView;

public class Sound {

    private MediaPlayer sound;
    private int icon;
    private int iconWhenSelected;
    private ImageView view;
    private String sharedPreferencesKey;
    private AnimatorSet animatorSet;
    public boolean isPaused;


    public Sound(int Icon, int IconWhenSelected, MediaPlayer Sound, ImageView View,boolean IsPaused,String SharedPreferencesKey,AnimatorSet AnimatorSet){

        icon = Icon;
        iconWhenSelected =IconWhenSelected;
        sound = Sound;
        view =  View;
        isPaused = IsPaused;
        sharedPreferencesKey = SharedPreferencesKey;
        animatorSet = AnimatorSet;
    }

    public AnimatorSet getAnimatorSet() {
        return animatorSet;
    }

    public void setAnimatorSet(AnimatorSet animatorSet) {
        this.animatorSet = animatorSet;
    }

    public String getSharedPreferencesKey() {
        return sharedPreferencesKey;
    }

    public void setSharedPreferencesKey(String sharedPreferencesKey) {
        this.sharedPreferencesKey = sharedPreferencesKey;
    }

    public MediaPlayer getSound() {
        return sound;
    }

    public void setSound(MediaPlayer sound) {
        this.sound = sound;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public ImageView getView() {
        return view;
    }

    public void setView(ImageView view) {
        this.view = view;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    public int getIconWhenSelected() {
        return iconWhenSelected;
    }

    public void setIconWhenSelected(int iconWhenSelected) {
        this.iconWhenSelected = iconWhenSelected;
    }
}
