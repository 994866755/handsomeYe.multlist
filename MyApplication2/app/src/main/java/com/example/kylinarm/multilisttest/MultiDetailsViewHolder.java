package com.example.kylinarm.multilisttest;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.kylinarm.multilisttest.Entity.MockDetailsEntity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/9/11.
 */

public class MultiDetailsViewHolder extends RecyclerView.ViewHolder {

    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.tv_money)
    TextView tvMoney;
    @InjectView(R.id.tv_time)
    TextView tvTime;

    private OnRecyclerItemClickListener onRecyclerItemClickListener;

    public MultiDetailsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.inject(this,itemView);
    }

    public void setData(MockDetailsEntity data){
        tvTitle.setText(data.title);
        tvMoney.setText(data.money);
        tvTime.setText(data.time);
    }

    public void setOnRecyclerItemClickListener(OnRecyclerItemClickListener onRecyclerItemClickListener) {
        this.onRecyclerItemClickListener = onRecyclerItemClickListener;
    }

    @OnClick(R.id.tv_title)
    public void itemClick(){
        onRecyclerItemClickListener.onItemClick(getAdapterPosition());
    }

}
