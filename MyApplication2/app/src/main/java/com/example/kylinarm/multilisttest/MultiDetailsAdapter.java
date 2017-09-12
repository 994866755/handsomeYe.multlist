package com.example.kylinarm.multilisttest;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.kylinarm.multilisttest.Entity.BaseMultiEntity;
import com.example.kylinarm.multilisttest.Entity.MockDetailsEntity;

import java.util.List;

/**
 * Created by Administrator on 2017/9/11.
 */

public class MultiDetailsAdapter extends BaseMultiAdapter<MockDetailsEntity> implements OnRecyclerItemClickListener {

    public MultiDetailsAdapter(Context context, List<MockDetailsEntity> datalist) {
        super(context, datalist);
    }

    @Override
    protected RecyclerView.ViewHolder setViewHolder(ViewGroup parent, int viewType) {
        return new MultiDetailsViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_multilist_details,parent,false));
    }

    @Override
    protected void setDataToViewHolder(RecyclerView.ViewHolder holder,int position) {
        if (datalist.size() > 0) {
            ((MultiDetailsViewHolder) holder).setData(datalist.get(position));
        }
    }

    @Override
    protected void setClickListener(RecyclerView.ViewHolder mViewHolder) {
        super.setClickListener(mViewHolder);
        ((MultiDetailsViewHolder)mViewHolder).setOnRecyclerItemClickListener(this);
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(context,"选择"+datalist.get(position).title,Toast.LENGTH_SHORT).show();
    }

}
