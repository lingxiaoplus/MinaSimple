package com.ling.mina.animatorsimple.evaluator;

import android.animation.TypeEvaluator;

import com.ling.mina.animatorsimple.Point;

/**
 * Created by 任梦林 on 2018/5/22.
 * 自定义估值器实现复杂的动画效果
 */

public class PointEvaluator implements TypeEvaluator {
    /**
     * 在evaluate（）里写入对象动画过渡的逻辑
     * @param fraction 插值器getInterpolation（）的返回值
     * @param startValue 动画的初始值
     * @param endValue 动画的结束值
     * @return
     */
    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        // 将动画初始值startValue 和 动画结束值endValue 强制类型转换成Point对象
        Point startPoint = (Point) startValue;
        Point endPoint = (Point) endValue;

        // 根据fraction来计算当前动画的x和y的值
        float x = startPoint.getX() + fraction * (endPoint.getX() - startPoint.getX());
        //float y = startPoint.getY() + fraction * (endPoint.getY() - startPoint.getY());
        float y = (float) (Math.sin(x * Math.PI / 180) * 100) + endPoint.getY() / 2;
        // 将计算后的坐标封装到一个新的Point对象中并返回
        Point point = new Point(x, y);

        return point;
    }
}
