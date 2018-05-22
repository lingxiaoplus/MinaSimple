package com.ling.mina.animatorsimple;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_frame_anim)
    public void showFrameAnim(){
        Intent intent = new Intent(getApplicationContext(),FrameActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.button_tween_anim)
    public void showTweenAnim(){
        Intent intent = new Intent(getApplicationContext(),TweenActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.button_value_anim)
    public void showValueAnim(){
        Intent intent = new Intent(getApplicationContext(),ValueAnimActivity.class);
        startActivity(intent);
    }
}
