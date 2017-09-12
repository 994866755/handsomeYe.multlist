package com.example.kylinarm.multilisttest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.kylinarm.multilisttest.Entity.MockDetailsEntity;
import com.example.kylinarm.multilisttest.Entity.NewspaperEntity;
import com.example.kylinarm.multilisttest.Entity.OnlyTwoEntity;

import java.util.List;

/**
 * Created by Administrator on 2017/9/11.
 */

public class OnlyTwoAdapter extends BaseMultiAdapter<NewspaperEntity> implements OnRecyclerItemClickListener  {

    public OnlyTwoAdapter(Context context, List<NewspaperEntity> datalist) {
        super(context, datalist);
    }

    @Override
    protected RecyclerView.ViewHolder setViewHolder(ViewGroup parent, int viewType) {
        return new OnlyTwoViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_onlytwo_details,parent,false));
    }

    @Override
    protected void setDataToViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (datalist.size() > 0) {
            ((OnlyTwoViewHolder) holder).setData(datalist.get(position));
        }
    }

    @Override
    protected void setClickListener(RecyclerView.ViewHolder mViewHolder) {
        super.setClickListener(mViewHolder);
        ((OnlyTwoViewHolder)mViewHolder).setOnRecyclerItemClickListener(this);
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(context,"选择"+datalist.get(position).title,Toast.LENGTH_SHORT).show();
    }

}
