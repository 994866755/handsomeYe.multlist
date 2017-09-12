package com.example.kylinarm.multilisttest;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/9/11.
 */

public class MenuActivity extends AppCompatActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.inject(this);
    }

    @OnClick({R.id.btn_two,R.id.btn_more})
    public void click(View view){
        switch (view.getId()){
            case R.id.btn_two:
                startToActivity(OnlyTwoActivity.class);
                break;
            case R.id.btn_more:
                startToActivity(MainActivity.class);
                break;
        }
    }

    private void startToActivity(Class cls){
        Intent intent = new Intent(MenuActivity.this,cls);
        startActivity(intent);
    }

}
