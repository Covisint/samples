package com.covisint.papi.sample.android.openregistration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.covisint.papi.sample.android.openregistration.util.SystemUiHider;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        ImageView imageView = (ImageView) findViewById(R.id.fullscreen_content);
        AlphaAnimation glowAnimation = new AlphaAnimation(0.0f, 1.0f);
        glowAnimation.setDuration(2000);
        glowAnimation.setStartOffset(0);
//        glowAnimation.setFillEnabled(true);
//        glowAnimation.setFillBefore(true);
//        glowAnimation.setFillAfter(true);
        glowAnimation.setInterpolator(new LinearInterpolator()); // do not alter animation rate
        glowAnimation.setRepeatCount(2); // Repeat animation infinitely
        glowAnimation.setRepeatMode(Animation.REVERSE);
        glowAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // Do nothing
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(SplashActivity.this, SearchOrganization.class);
                SplashActivity.this.startActivity(intent);
                SplashActivity.this.finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        imageView.startAnimation(glowAnimation);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }
}