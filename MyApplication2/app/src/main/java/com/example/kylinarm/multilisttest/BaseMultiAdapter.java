package com.example.kylinarm.multilisttest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2017/9/11.
 */

public abstract class BaseMultiAdapter<T> extends RecyclerView.Adapter{

    protected Context context;
    protected List<T> datalist;

    public BaseMultiAdapter(Context context,List<T> datalist){
        this.context = context;
        this.datalist = datalist;
    }

    public List<T> getDatalist() {
        return datalist;
    }

    public void refresh(List<T> datalist){
        this.datalist = datalist;
        notifyDataSetChanged();
    }

    public void add(T t){
        this.datalist.add(t);
        notifyDataSetChanged();
    }

    public void addAll(List<T> datalist){
        this.datalist.addAll(datalist);
        notifyDataSetChanged();
    }

    public void restart(){
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder mViewHolder = setViewHolder(parent,viewType);
        setClickListener(mViewHolder);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        setDataToViewHolder(holder,position);
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    protected void setClickListener(RecyclerView.ViewHolder mViewHolder){}

    protected abstract RecyclerView.ViewHolder setViewHolder(ViewGroup parent, int viewType);
    protected abstract void setDataToViewHolder(RecyclerView.ViewHolder holder,int position);


}
