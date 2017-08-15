package com.example.dragos.ipnos_test;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.example.dragos.ipnos_test.model.Sound;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private Sound birdsSound;
    private Sound windSound;
    private Sound pianoSound;
    private Sound rainSound;
    private Sound orchestralSound;
    private Sound oceanSound;
    private Sound musicboxSound;
    private Sound fluteSound;
    private Sound loungeSound;

    private SharedPreferences preferences;
    private boolean playButtonPressed;
    private ArrayList<Sound>playingSounds;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView birdsIcon = (ImageView) findViewById(R.id.birds_sound);
        ImageView windIcon = (ImageView) findViewById(R.id.wind_sound);
        ImageView pianoIcon = (ImageView) findViewById(R.id.piano_sound);
        ImageView rainIcon = (ImageView) findViewById(R.id.rain_sound);
        ImageView orchestralIcon = (ImageView) findViewById(R.id.orchestral_sound);
        ImageView oceanIcon = (ImageView) findViewById(R.id.ocean_sound);
        ImageView musicboxIcon = (ImageView) findViewById(R.id.musicbox_sound);
        ImageView fluteIcon = (ImageView) findViewById(R.id.flute_sound);
        ImageView loungeIcon = (ImageView) findViewById(R.id.lounge_sound);

        ImageView playButton = (ImageView) findViewById(R.id.play_button);
        ImageView pauseButton = (ImageView) findViewById(R.id.pause_button);
        ImageView clearButton = (ImageView) findViewById(R.id.clear_button);

        AnimatorSet birdsAnim = new AnimatorSet();
        AnimatorSet windAnim = new AnimatorSet();
        AnimatorSet pianoAnim = new AnimatorSet();
        AnimatorSet rainAnim = new AnimatorSet();
        AnimatorSet orchestralAnim = new AnimatorSet();
        AnimatorSet oceanAnim = new AnimatorSet();
        AnimatorSet musicBoxAnim = new AnimatorSet();
        AnimatorSet fluteAnim = new AnimatorSet();
        AnimatorSet loungeAnim = new AnimatorSet();

        birdsSound = new Sound(R.drawable.sound_birds_normal,R.drawable.sound_birds_selected,MediaPlayer.create(this,R.raw.birds), birdsIcon,false,"birds", birdsAnim);
        setOnCostumClicklistener(birdsSound);
        windSound= new Sound(R.drawable.sound_wind_normal,R.drawable.sound_wind_selected,MediaPlayer.create(this,R.raw.wind), windIcon,false,"wind", windAnim);
        setOnCostumClicklistener(windSound);
        pianoSound = new Sound(R.drawable.sound_piano_normal,R.drawable.sound_piano_selected,MediaPlayer.create(this,R.raw.piano), pianoIcon,false,"piano", pianoAnim);
        setOnCostumClicklistener(pianoSound);
        rainSound = new Sound(R.drawable.sound_rain_normal,R.drawable.sound_rain_selected,MediaPlayer.create(this,R.raw.rain), rainIcon,false,"rain", rainAnim);
        setOnCostumClicklistener(rainSound);
        orchestralSound = new Sound(R.drawable.sound_orchestral_normal,R.drawable.sound_orchestral_selected,MediaPlayer.create(this,R.raw.orchestral), orchestralIcon,false,"orchestral", orchestralAnim);
        setOnCostumClicklistener(orchestralSound);
        oceanSound = new Sound(R.drawable.sound_ocean_normal,R.drawable.sound_ocean_selected,MediaPlayer.create(this,R.raw.ocean), oceanIcon,false,"ocean", oceanAnim);
        setOnCostumClicklistener(oceanSound);
        musicboxSound = new Sound(R.drawable.sound_musicbox_normal,R.drawable.sound_musicbox_selected,MediaPlayer.create(this,R.raw.musicbox), musicboxIcon,false,"musicBox", musicBoxAnim);
        setOnCostumClicklistener(musicboxSound);
        fluteSound = new Sound(R.drawable.sound_flute_normal,R.drawable.sound_flute_selected,MediaPlayer.create(this,R.raw.flute), fluteIcon,false,"flute", fluteAnim);
        setOnCostumClicklistener(fluteSound);
        loungeSound = new Sound(R.drawable.sound_lounge_normal,R.drawable.sound_lounge_selected,MediaPlayer.create(this,R.raw.lounge), loungeIcon,false,"lounge", loungeAnim);
        setOnCostumClicklistener(loungeSound);

        playingSounds = new ArrayList<>();
        preferences = getSharedPreferences("mypref",0);
        settingUpsharedPreferences();


        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!playButtonPressed) {

                    playButtonPressed = true;

                    if (!playingSounds.isEmpty()) {

                        for (int i = 0; i < playingSounds.size(); i++) {

                            playingSounds.get(i).getSound().start();
                            playingSounds.get(i).setPaused(false);
                            startAnimation(playingSounds.get(i).getView(), playingSounds.get(i).getAnimatorSet());
                        }
                    }
                }
            }
        });


        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!playingSounds.isEmpty()) {

                    playButtonPressed = false;

                    for (int i = 0; i < playingSounds.size() ; i++) {

                        playingSounds.get(i).getSound().pause();
                        playingSounds.get(i).setPaused(true);
                        playingSounds.get(i).getAnimatorSet().cancel();
                        endAnimation(playingSounds.get(i).getView());

                    }
                }
            }
        });


        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!playingSounds.isEmpty()) {

                    playButtonPressed = false;
                    int numOfSounds = playingSounds.size();

                    for (int i = 0; i < numOfSounds ; i++) {

                        stoppingSound(playingSounds.get(i));
                    }
                    playingSounds = new ArrayList<>();
                }
            }
        });
    }

    private void setOnCostumClicklistener(final Sound sound){

        sound.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (playingSounds.size() == 3 && !sound.getSound().isPlaying() && !sound.isPaused){

                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                    alertDialog.setTitle(R.string.warning_title);
                    alertDialog.setMessage("A maximum of 3 sounds can be selected at a time");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                    alertDialog.show();

                }else if (sound.getSound().isPlaying() || sound.isPaused() ) {

                        stoppingSound(sound);
                        playingSounds.remove(sound);

                        for (int i = 0; i < playingSounds.size() ; i++) {

                            if (playingSounds.get(i).isPaused()) {

                                playingSounds.get(i).setPaused(false);
                                playingSounds.get(i).getSound().start();
                                startAnimation(playingSounds.get(i).getView(), playingSounds.get(i).getAnimatorSet());
                            }
                        }

                    } else {

                        sound.getSound().setLooping(true);
                        sound.getSound().start();
                        sound.getView().setImageResource(sound.getIconWhenSelected());
                        playingSounds.add(sound);

                        for (int i = 0; i < playingSounds.size() ; i++) {

                                playingSounds.get(i).setPaused(false);
                                playingSounds.get(i).getSound().start();

                            if(!playingSounds.get(i).getAnimatorSet().isStarted()) {

                                startAnimation(playingSounds.get(i).getView(), playingSounds.get(i).getAnimatorSet());
                            }
                        }
                    }
            }
        });
    }

    @Override
    protected void onStop(){
        super.onStop();

        SharedPreferences preferences = getSharedPreferences("mypref",0);
        editor = preferences.edit();
        resettingSharedPreferencesValues();

        for (int i = 0; i < playingSounds.size() ; i++) {

            editor.putBoolean(playingSounds.get(i).getSharedPreferencesKey(), true);
        }
        editor.apply();
    }

    private void stoppingSound (Sound sound){
        sound.getView().setImageResource(sound.getIcon());
        sound.getSound().stop();
        sound.getSound().prepareAsync();
        sound.getAnimatorSet().cancel();
        endAnimation(sound.getView());
        sound.setPaused(false);
    }

    private void settingUpsharedPreferences(){
        gettingPreviousSelectedSounds(birdsSound);
        gettingPreviousSelectedSounds(windSound);
        gettingPreviousSelectedSounds(pianoSound);
        gettingPreviousSelectedSounds(rainSound);
        gettingPreviousSelectedSounds(orchestralSound);
        gettingPreviousSelectedSounds(oceanSound);
        gettingPreviousSelectedSounds(musicboxSound);
        gettingPreviousSelectedSounds(fluteSound);
        gettingPreviousSelectedSounds(loungeSound);
    }

    private void resettingSharedPreferencesValues(){

        editor.putBoolean(birdsSound.getSharedPreferencesKey(), false);
        editor.putBoolean(windSound.getSharedPreferencesKey(), false);
        editor.putBoolean(pianoSound.getSharedPreferencesKey(), false);
        editor.putBoolean(rainSound.getSharedPreferencesKey(), false);
        editor.putBoolean(orchestralSound.getSharedPreferencesKey(), false);
        editor.putBoolean(oceanSound.getSharedPreferencesKey(), false);
        editor.putBoolean(musicboxSound.getSharedPreferencesKey(), false);
        editor.putBoolean(loungeSound.getSharedPreferencesKey(), false);
        editor.putBoolean(fluteSound.getSharedPreferencesKey(), false);
    }

    private void gettingPreviousSelectedSounds(Sound sound){

        if(preferences.getBoolean(sound.getSharedPreferencesKey(),false)){

            sound.getView().setImageResource(sound.getIconWhenSelected());
            sound.getSound().setLooping(true);
            sound.setPaused(true);
            playingSounds.add(sound);
        }

    }

    private void startAnimation(final ImageView view, AnimatorSet animatorSet){

       ValueAnimator animator1 = ValueAnimator.ofFloat(0, 10);
        ValueAnimator animator2 = ValueAnimator.ofFloat(10, -10);

        animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                // 2
                view.setRotation(value);
                view.setPivotX(200);
                view.setPivotY(110);
            }
        });


        animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                // 2
                view.setRotation(value);
                view.setPivotX(200);
                view.setPivotY(110);
            }
        });

        animator2.setRepeatCount(ValueAnimator.INFINITE);
        animator2.setRepeatMode(ValueAnimator.REVERSE);
        animatorSet.play(animator1).before(animator2);
        animatorSet.setDuration(2000);
        animatorSet.start();
    }

    private void endAnimation(final ImageView view){

        ValueAnimator animator = ValueAnimator.ofFloat(view.getRotation(), 0);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();

                view.setRotation(value);
                view.setPivotX(200);
                view.setPivotY(110);
            }
        });

        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(1000);
        animator.start();
    }



}
