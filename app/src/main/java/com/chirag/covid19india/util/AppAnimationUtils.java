package com.chirag.covid19india.util;

import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

/**
 * Created by Chirag Desai
 */
public class AppAnimationUtils {

    private static final int DEFAULT_ANIMATION_DURATION = 1000;

    public static Animation inFromRightAnimation() {
        return inFromRightAnimation(DEFAULT_ANIMATION_DURATION);
    }

    public static Animation inFromRightAnimation(int duration) {

        Animation inFromRight = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, +1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        inFromRight.setDuration(duration);
        inFromRight.setInterpolator(new AccelerateInterpolator());
        return inFromRight;
    }

    public static Animation outToLeftAnimation() {
        return outToLeftAnimation(DEFAULT_ANIMATION_DURATION);
    }

    public static Animation outToLeftAnimation(int duration) {
        Animation outtoLeft = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, -1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        outtoLeft.setDuration(duration);
        outtoLeft.setInterpolator(new AccelerateInterpolator());
        return outtoLeft;
    }

    public static Animation inFromLeftAnimation() {
        return inFromLeftAnimation(DEFAULT_ANIMATION_DURATION);
    }

    public static Animation inFromLeftAnimation(int duration) {
        Animation inFromLeft = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, -1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        inFromLeft.setDuration(duration);
        inFromLeft.setInterpolator(new AccelerateInterpolator());
        return inFromLeft;
    }

    public static Animation outToRightAnimation() {
        return outToRightAnimation(DEFAULT_ANIMATION_DURATION);
    }

    public static Animation outToRightAnimation(int duration) {
        Animation outtoRight = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, +1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        outtoRight.setDuration(duration);
        outtoRight.setInterpolator(new AccelerateInterpolator());
        return outtoRight;
    }

    public static void fadeInAnimation(final View view, long animationDuration) {
        fadeInAnimation(view, animationDuration, 0);
    }

    public static void fadeInAnimation(final View view, long animationDuration, int startOffset) {
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new AccelerateInterpolator());
        fadeIn.setDuration(animationDuration);
        fadeIn.setStartOffset(startOffset);
        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        view.startAnimation(fadeIn);
    }

    /*public static RotateAnimation setRotationAnimation(){
        return setRotationAnimation(DEFAULT_ANIMATION_DURATION);
    }

    public static RotateAnimation setRotationAnimation(int duration){
        RotateAnimation rotate = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(duration);
        return  rotate.setInterpolator(new LinearInterpolator());
    }*/
}
