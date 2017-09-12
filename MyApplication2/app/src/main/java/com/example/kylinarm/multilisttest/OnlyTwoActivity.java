package com.example.kylinarm.multilisttest;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.kylinarm.multilisttest.Entity.MockDetailsEntity;
import com.example.kylinarm.multilisttest.Entity.MockEntity;
import com.example.kylinarm.multilisttest.Entity.MockJson;
import com.example.kylinarm.multilisttest.Entity.NewspaperEntity;
import com.example.kylinarm.multilisttest.Entity.OnlyTwoEntity;
import com.google.gson.Gson;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2017/9/11.
 */

public class OnlyTwoActivity extends AppCompatActivity implements MultiListView.MultiLastAdapter<NewspaperEntity> {

    @InjectView(R.id.mv_content)
    MultiListView mvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onlytwo);
        ButterKnife.inject(this);
        initView();
        setData();
    }

    private void initView(){
        mvContent.setAllWeight(2,3);
        mvContent.setLayoutManager();
    }

    private void setData(){
        mvContent.setAdapter(mockResult(),this);
    }


    public OnlyTwoEntity mockResult(){
        Gson gson = new Gson();
        OnlyTwoEntity onlyTwoEntity = gson.fromJson(MockJson.json2(),OnlyTwoEntity.class);
        return onlyTwoEntity;
    }

    @Override
    public BaseMultiAdapter create(Context context, List<NewspaperEntity> datalist) {
        return new OnlyTwoAdapter(context,datalist);
    }
}
