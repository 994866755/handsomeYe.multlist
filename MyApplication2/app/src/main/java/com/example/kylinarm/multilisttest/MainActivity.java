package com.example.kylinarm.multilisttest;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.kylinarm.multilisttest.Entity.MockDetailsEntity;
import com.example.kylinarm.multilisttest.Entity.MockEntity;
import com.example.kylinarm.multilisttest.Entity.MockJson;
import com.example.kylinarm.multilisttest.Entity.TestOneEntity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity implements MultiListView.MultiLastAdapter<MockDetailsEntity> {

    @InjectView(R.id.mv_content)
    MultiListView mvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        initView();
        setData();
    }

    private void initView(){
        mvContent.setAllWeight(1,1,1);
        mvContent.setLayoutManager();
    }

    private void setData(){
        mvContent.setAdapter(mockResult(),this);
    }


    public MockEntity mockResult(){
        // 假数据
        Gson gson = new Gson();
        MockEntity mockEntity = gson.fromJson(MockJson.json(),MockEntity.class);
        return mockEntity;
    }

    @Override
    public BaseMultiAdapter create(Context context, List<MockDetailsEntity> datalist) {
        return new MultiDetailsAdapter(context, datalist);
    }
}
