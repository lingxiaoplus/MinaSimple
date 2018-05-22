package com.ling.mina.animatorsimple;

import android.animation.IntEvaluator;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;

import com.ling.mina.animatorsimple.interpolar.DecelerateAccInterpolar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ValueAnimActivity extends AppCompatActivity {

    @BindView(R.id.image_value)
    ImageView imageValue;
    private int checkedItem;
    private String[] items;
    /**
     * 属性动画就TimeInterpolator  补间动画Interpolator
     */
    TimeInterpolator inter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_value_anim);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_rotate)
    public void startRotate(){
        rotateAnim(imageValue);
    }

    @OnClick(R.id.button_select)
    public void selectInterpolator(){
        items = getResources().getStringArray(R.array.interpolator);
        selectScreen(items);
    }
    private void rotateAnim(View view){
        ObjectAnimator animator = ObjectAnimator.ofFloat(view,"rotation",0,360);
        animator.setDuration(1000);
        if (null != inter){
            animator.setInterpolator(inter);
        }
        animator.start();
    }

    @OnClick(R.id.button_evaluator)
    public void onEvaluator(){
        Intent intent = new Intent(getApplicationContext(),PointActivity.class);
        startActivity(intent);
    }

    private void selectScreen(String[] items){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                checkedItem = i;
                if (i == 0){
                    //动画加速进行
                    inter = new AccelerateInterpolator();
                }else if (i == 1){
                    //系统的插值器 快速完成动画，超出再回到结束样式
                    inter = new OvershootInterpolator();
                }else if (i == 2){
                    //先加速再减速
                    inter = new AccelerateDecelerateInterpolator();
                }else if (i == 3){
                    //先退后再加速前进
                    inter = new AnticipateInterpolator();
                }else if (i == 4){
                    //先退后再加速前进，超出终点后再回终点
                    inter = new AnticipateOvershootInterpolator();
                }else if (i == 5){
                    //最后阶段弹球效果
                    inter = new BounceInterpolator();
                }else if (i == 6){
                    //周期运动
                    inter = new CycleInterpolator(0.5f);
                }else if (i == 7){
                    //减速
                    inter = new DecelerateInterpolator();
                }else if (i == 8){
                    //匀速
                    inter = new LinearInterpolator();
                }else if (i == 9){
                    //自定义插值器 先减速再加速
                    inter = new DecelerateAccInterpolar();
                }
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }
}
