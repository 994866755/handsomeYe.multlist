package com.example.kylinarm.multilisttest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.kylinarm.multilisttest.Entity.BaseMultiEntity;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2017/9/8.
 */

public class MultiViewHolder extends RecyclerView.ViewHolder{

    private TextView tvContent;
    private RelativeLayout rlItem;

    private Context context;
    private OnRecyclerItemClickListener onRecyclerItemClickListener;

    public MultiViewHolder(View itemView,Context mContext) {
        super(itemView);
        this.context = mContext;

        tvContent = itemView.findViewById(R.id.tv_content);
        rlItem = itemView.findViewById(R.id.rl_item);

        tvContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onRecyclerItemClickListener != null) {
                    onRecyclerItemClickListener.onItemClick(getAdapterPosition());
                }
            }
        });
    }

    public void setData(BaseMultiEntity str){
        tvContent.setText(str.getData());
        if (str.isClick){
            Log.v("mmp","vhture" + getAdapterPosition());
            rlItem.setBackgroundColor(context.getResources().getColor(R.color.gray));
        }else {
            Log.v("mmp","vhfalse");
            rlItem.setBackgroundColor(context.getResources().getColor(R.color.white));
        }
    }

    public void setStringData(String str){
        tvContent.setText(str);
    }

    public void setOnRecyclerItemClickListener(OnRecyclerItemClickListener onRecyclerItemClickListener) {
        this.onRecyclerItemClickListener = onRecyclerItemClickListener;
    }
}
