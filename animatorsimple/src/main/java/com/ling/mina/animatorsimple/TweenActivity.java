package com.ling.mina.animatorsimple;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TweenActivity extends AppCompatActivity {
    @BindView(R.id.image_tween)
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tween);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.button_alpha)
    public void setAlpha(){
        //xml方式实现
        /*Animation animation = AnimationUtils
                .loadAnimation(getApplicationContext(),R.anim.alpha_anim);
        imageView.startAnimation(animation);*/

        //java code实现
        alphaAnimation(1.0f,0.0f,1000,imageView);
    }

    @OnClick(R.id.button_scale)
    public void setScale(){
        /*Animation animation = AnimationUtils
                .loadAnimation(getApplicationContext(),R.anim.scale_anim);
        imageView.startAnimation(animation);*/
        scaleAnimation(1.0f,0.0f,1.0f,0.0f,
                0.5f,0.5f,1000,imageView);
    }

    @OnClick(R.id.button_trans)
    public void setTranslate(){
        /*Animation animation = AnimationUtils
                .loadAnimation(getApplicationContext(),R.anim.translate_anim);
        imageView.startAnimation(animation);*/
        translateAnimation(1000,imageView);
    }

    @OnClick(R.id.button_rotate)
    public void setRotate(){
        /*Animation animation = AnimationUtils
                .loadAnimation(getApplicationContext(),R.anim.rotate_anim);
        imageView.startAnimation(animation);*/
        rotateAnimation(1000,imageView);
    }

    @OnClick(R.id.button_set)
    public void setAmin(){
        /*Animation animation = AnimationUtils
                .loadAnimation(getApplicationContext(),R.anim.all_tween);
        imageView.startAnimation(animation);*/
        animationSet(imageView);
    }

    private void alphaAnimation(float from , float to, long time, View view){
        AlphaAnimation animation = new AlphaAnimation(from,to);
        animation.setDuration(time);
        //动画延迟播放
        //animation.setStartOffset(time);
        //动画播放完后，视图是否会停留在动画开始的状态，默认为true
        animation.setFillBefore(true);
        //动画执行完之后的状态 优先级高于before
        animation.setFillAfter(false);
        //重复次数
        animation.setRepeatCount(0);
        //选择重复播放动画模式，restart代表正序重放，reverse代表倒序回放，默认为restart
        animation.setRepeatMode(Animation.RESTART);
        view.startAnimation(animation);

    }

    private void scaleAnimation(float fromX , float toX, float fromY , float toY,
                                float pivotX , float pivotY, long time, View view){
        ScaleAnimation animation = new ScaleAnimation(fromX,toX,fromY,toY,
                Animation.RELATIVE_TO_SELF,pivotX,
                Animation.RELATIVE_TO_SELF,pivotY);
        animation.setDuration(time);
        //动画播放完后，视图是否会停留在动画开始的状态，默认为true
        animation.setFillBefore(true);
        //动画执行完之后的状态 优先级高于before
        animation.setFillAfter(false);
        //重复次数
        animation.setRepeatCount(0);
        //选择重复播放动画模式，restart代表正序重放，reverse代表倒序回放，默认为restart
        animation.setRepeatMode(Animation.RESTART);
        view.startAnimation(animation);
    }

    private void translateAnimation(long time, View view){
        TranslateAnimation animation = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_PARENT,-0.5f,
                TranslateAnimation.RELATIVE_TO_PARENT,0.5f,
                Animation.RELATIVE_TO_SELF,0,
                Animation.RELATIVE_TO_SELF,0);
        animation.setDuration(time);
        //动画播放完后，视图是否会停留在动画开始的状态，默认为true
        animation.setFillBefore(true);
        //动画执行完之后的状态 优先级高于before
        animation.setFillAfter(false);
        //重复次数
        animation.setRepeatCount(0);
        //选择重复播放动画模式，restart代表正序重放，reverse代表倒序回放，默认为restart
        animation.setRepeatMode(Animation.RESTART);
        view.startAnimation(animation);
    }

    private void rotateAnimation(long time, View view){
        RotateAnimation animation = new RotateAnimation(
                0,360,
                Animation.RELATIVE_TO_SELF,0.5f,
                Animation.RELATIVE_TO_SELF,0.5f);
        animation.setDuration(time);
        //动画播放完后，视图是否会停留在动画开始的状态，默认为true
        animation.setFillBefore(true);
        //动画执行完之后的状态 优先级高于before
        animation.setFillAfter(false);
        //重复次数
        animation.setRepeatCount(0);
        //选择重复播放动画模式，restart代表正序重放，reverse代表倒序回放，默认为restart
        animation.setRepeatMode(Animation.RESTART);
        view.startAnimation(animation);
    }

    /**
     * 补间动画的组合
     * @param view
     */
    private void animationSet(View view){
        AnimationSet set = new AnimationSet(true);
        AlphaAnimation alpha = new AlphaAnimation(0.0f,1f);

        ScaleAnimation scale = new ScaleAnimation(0.0f,1.0f,0.0f,1.0f,
                Animation.RELATIVE_TO_SELF,0.5f,
                Animation.RELATIVE_TO_SELF,0.5f);

        RotateAnimation rotate = new RotateAnimation(
                0,360,
                Animation.RELATIVE_TO_SELF,0.5f,
                Animation.RELATIVE_TO_SELF,0.5f);

        TranslateAnimation trans = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_PARENT,0.0f,
                TranslateAnimation.RELATIVE_TO_PARENT,0.0f,
                Animation.RELATIVE_TO_SELF,0.0f,
                Animation.RELATIVE_TO_SELF,0.3f);

        set.setDuration(1000);
        set.setFillAfter(true);
        set.addAnimation(alpha);
        set.addAnimation(scale);
        set.addAnimation(rotate);
        set.addAnimation(trans);
        view.startAnimation(set);
    }
}
