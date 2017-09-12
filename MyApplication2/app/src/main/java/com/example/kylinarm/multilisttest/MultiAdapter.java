package com.example.kylinarm.multilisttest;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.kylinarm.multilisttest.Entity.BaseMultiEntity;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Administrator on 2017/9/8.
 */

public class MultiAdapter extends BaseMultiAdapter<BaseMultiEntity>  implements OnRecyclerItemClickListener{

    // 定义一个标记，标记用有当前adapter的recyclerview是第几层
    private int level;
    private OnRecyclerClickListener onRecyclerClickListener;

    public MultiAdapter(Context context, List<BaseMultiEntity> datalist, int level) {
        super(context, datalist);
        this.level = level;
    }

    @Override
    protected RecyclerView.ViewHolder setViewHolder(ViewGroup parent, int viewType) {
        return new MultiViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_textview,parent,false),context);
    }

    @Override
    protected void setDataToViewHolder(RecyclerView.ViewHolder holder,int position) {
        ((MultiViewHolder)holder).setData(datalist.get(position));
    }

    @Override
    protected void setClickListener(RecyclerView.ViewHolder mViewHolder) {
        super.setClickListener(mViewHolder);
        ((MultiViewHolder)mViewHolder).setOnRecyclerItemClickListener(this);
    }

    @Override
    public void onItemClick(int position) {
        if (onRecyclerClickListener != null){
            onRecyclerClickListener.onItemClick(position,level);
        }
    }

    public void setOnRecyclerClickListener(OnRecyclerClickListener onRecyclerClickListener) {
        this.onRecyclerClickListener = onRecyclerClickListener;
    }
}
