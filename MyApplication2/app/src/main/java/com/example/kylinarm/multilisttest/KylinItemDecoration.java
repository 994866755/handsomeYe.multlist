package com.example.kylinarm.multilisttest;

import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by Administrator on 2017/9/8.
 */

public class KylinItemDecoration extends RecyclerView.ItemDecoration{

    private static final int HORIZONTAL = LinearLayoutManager.HORIZONTAL;//水平方向
    private static final int VERTICAL = LinearLayoutManager.VERTICAL;//垂直方向
    private int orientation = VERTICAL;
    private int decoration = 0;
    private int left = 0;
    private int top = 0;
    private int right = 0;

    //默认显示分割线在底部
    public KylinItemDecoration(int decoration){
        this(decoration,0);
    }

    public KylinItemDecoration(int decoration, int right){
        this(decoration,right,0);
    }

    public KylinItemDecoration(int decoration, int right, int top){
        this(decoration,right,top,0);
    }

    public KylinItemDecoration(int decoration, int right, int top, int left){
        //dp转px
        this.decoration = dpTopx(decoration);
        this.right = dpTopx(right);
        this.top = dpTopx(top);
        this.left = dpTopx(left);
    }

    private int dpTopx(int k){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,k, AppApplication.getInstance().getResources().getDisplayMetrics());
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(left,top,right,decoration);
    }

}

