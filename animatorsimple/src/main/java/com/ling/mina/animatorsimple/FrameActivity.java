package com.ling.mina.animatorsimple;

import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class FrameActivity extends AppCompatActivity {
    private ImageView imgPdg,imgQvip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame);
        initView();
    }

    private void initView() {
        imgPdg = findViewById(R.id.image_pdg);
        imgQvip = findViewById(R.id.image_qvip);
        imgPdg.setImageResource(R.drawable.jd_pdg_anim);
        AnimationDrawable animationDrawable1 = (AnimationDrawable) imgPdg.getDrawable();
        animationDrawable1.start();

        imgQvip.setImageResource(R.drawable.qq_refresh_anim);
        AnimationDrawable animationDrawable2 = (AnimationDrawable) imgQvip.getDrawable();
        animationDrawable2.start();
    }
}
