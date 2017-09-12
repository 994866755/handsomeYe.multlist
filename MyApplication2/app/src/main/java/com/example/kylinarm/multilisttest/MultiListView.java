package com.example.kylinarm.multilisttest;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.kylinarm.multilisttest.Entity.BaseMultiEntity;
import com.example.kylinarm.multilisttest.Entity.MockDetailsEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kylinARM on 2017/9/8.
 */

public class MultiListView extends LinearLayout implements OnRecyclerClickListener{

    @InjectView(R.id.ll_content)
    LinearLayout llContent;

    private View baseView;
    private Context context;
    private MultiLastAdapter multiLastAdapter;

    private int count;
    private int divisionCount;
    private List<Integer> weights = new ArrayList<>();
    private List<RecyclerView> recyclerViewList = new ArrayList<>();
    private List<BaseMultiAdapter> mAdapterList = new ArrayList<>();
    private int parentWidth;

    public MultiListView(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public MultiListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inial(context, attrs);
        this.context = context;
        initView();
    }

    public MultiListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inial(context, attrs);
        this.context = context;
        initView();
    }

    private void inial(Context context,AttributeSet attrs)  {
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.multilist_style);
        count = typedArray.getInteger(R.styleable.multilist_style_count,2);
        divisionCount = typedArray.getInteger(R.styleable.multilist_style_division_count,4);
        typedArray.recycle();
    }

    private void initView(){
        // 控制至少为两个
        if (count < 2){
            count = 2;
        }
        setParentWidth();
        this.setBackgroundColor(getResources().getColor(R.color.white));
    }

    public void setAllWeight(int ... weight){
        // 如果传进来的比重小于设置的总个数，则给余下的补权重2
        if (weight.length < count){
            for (int i = 0; i < count; i++) {
                if (i >= weight.length){
                    weights.add(2);
                }else {
                    weights.add(weight[i]);
                }
            }
            return;
        }
        for (int i : weight) {
            weights.add(i);
        }
    }

    private void initRecyclerView(){
        for (int i = 0; i < count; i++) {
            RecyclerView recyclerView = new RecyclerView(context);
            //根据比重来设置每个RecyclerView的宽度
            LinearLayout.LayoutParams layoutParams = new LayoutParams((parentWidth/divisionCount)*weights.get(i), ViewGroup.LayoutParams.WRAP_CONTENT);
            recyclerView.setLayoutParams(layoutParams);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.addItemDecoration(new KylinItemDecoration(1,1,0,1));
            recyclerView.setBackgroundColor(getResources().getColor(R.color.gray));

            recyclerViewList.add(recyclerView);
        }
    }

    private void setParentWidth() {
        WindowManager manager = (WindowManager) AppApplication.getInstance().getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        this.parentWidth = outMetrics.widthPixels;
    }

    public void setLayoutManager(){
        this.setOrientation(HORIZONTAL);
        initRecyclerView();
        addRecyclerToLayout();
    }

    private void addRecyclerToLayout(){
        if (count > 2){
            baseView = LayoutInflater.from(context).inflate(R.layout.layout_multilist_base,null);
            ButterKnife.inject(this,baseView);
            for (int i = 0; i < count; i++) {
                llContent.addView(recyclerViewList.get(i));
            }
            addView(baseView);
        }else {
            this.addView(recyclerViewList.get(0));
            this.addView(recyclerViewList.get(1));
        }
    }

    public void setAdapter(BaseMultiEntity data,MultiLastAdapter multiLastAdapter){
        this.multiLastAdapter = multiLastAdapter;
        BaseMultiEntity p = data;
        // 刚开始默认路径都是0->0->0...->0
        for (int i = 0; i < count; i++) {
            //先判断是否存在next
            if (p!=null && p.getMultiListChildData() != null){
                // 如果指针的下一个的数组中没元素，把它当成是最后一层页面并且无数据
                if (p.getMultiListChildData().size() <= 0){
                    List<MockDetailsEntity> datalist = new ArrayList<>();
                    BaseMultiAdapter multiDetailsAdapter = multiLastAdapter.create(context, datalist);
//                    BaseMultiAdapter multiDetailsAdapter = new MultiDetailsAdapter(context, datalist);
                    recyclerViewList.get(i).setAdapter(multiDetailsAdapter);
                    mAdapterList.add(multiDetailsAdapter);
                    p = null;
                    return;
                }

                if (p.getMultiListChildData().get(0) instanceof BaseMultiEntity){
                    ((BaseMultiEntity) p.getMultiListChildData().get(0)).isClick = true;
                    MultiAdapter multiAdapter = new MultiAdapter(context, p.getMultiListChildData(),i);
                    recyclerViewList.get(i).setAdapter(multiAdapter);
                    multiAdapter.setOnRecyclerClickListener(this);
                    mAdapterList.add(multiAdapter);
                    // 展示该层RecyclerView之后，指针指向链表的下一个节点
                    p = (BaseMultiEntity) p.getMultiListChildData().get(0);
                }else {
                    BaseMultiAdapter multiDetailsAdapter = multiLastAdapter.create(context, p.getMultiListChildData());
//                    BaseMultiAdapter multiDetailsAdapter = new MultiDetailsAdapter(context, p.getMultiListChildData());
                    recyclerViewList.get(i).setAdapter(multiDetailsAdapter);
                    mAdapterList.add(multiDetailsAdapter);
                    // 最后一个节点的时候设置p=null表示该节点为最后一个节点
                    p = null;
                }
            }
        }
    }

    @Override
    public void onItemClick(int position,int k) {
        // k 肯定不会为最后一层
        //把序号为k之后的adapter从列表中删除
        int b = mAdapterList.size();
        for (int i = k+1; i < b; i++) {
            mAdapterList.remove(k+1);
        }
        // TODO 先更新该层的页面
        for (int i = 0; i < mAdapterList.get(k).getDatalist().size(); i++) {
            if (i == position){
                ((BaseMultiEntity) mAdapterList.get(k).getDatalist().get(i)).isClick = true;
            }else {
                ((BaseMultiEntity) mAdapterList.get(k).getDatalist().get(i)).isClick = false;
            }
        }
        mAdapterList.get(k).notifyDataSetChanged();
        // 现在的p是该层的下一层的数据，比如说点击的是第一层，现在的p指的就是第一层的
        // 被点击的那个节点，它是一个对象，包含两个字段，name表示该层被点击的name，getMultiListChildData()
        // 表示指向下一层列表的“指针”
        BaseMultiEntity p = (BaseMultiEntity) mAdapterList.get(k).getDatalist().get(position);

        for (int i = k+1; i < count; i++) {
            if (p!=null && p.getMultiListChildData() != null) {
                // 如果指针的下一个的数组中没元素，把它当成是最后一层页面并且无数据
                if (p.getMultiListChildData().size() <= 0){
                    List<MockDetailsEntity> datalist = new ArrayList<>();
                    BaseMultiAdapter multiDetailsAdapter = multiLastAdapter.create(context, datalist);
//                    BaseMultiAdapter multiDetailsAdapter = new MultiDetailsAdapter(context, datalist);
                    recyclerViewList.get(i).setAdapter(multiDetailsAdapter);
                    mAdapterList.add(multiDetailsAdapter);
                    p = null;
                    return;
                }

                if (p.getMultiListChildData().get(0) instanceof BaseMultiEntity) {
                    // 更新被点击的item的操作,全部变为默认状态，默认为点击第一个
                    for (int j = 0; j < p.getMultiListChildData().size(); j++) {
                        if (j == 0){
                            ((BaseMultiEntity)p.getMultiListChildData().get(j)).isClick = true;
                        }else {
                            ((BaseMultiEntity)p.getMultiListChildData().get(j)).isClick = false;
                        }
                    }

                    MultiAdapter multiAdapter = new MultiAdapter(context, p.getMultiListChildData(), i);
                    recyclerViewList.get(i).setAdapter(multiAdapter);
                    multiAdapter.setOnRecyclerClickListener(this);
                    mAdapterList.add(multiAdapter);
                    // 展示该层RecyclerView之后，指针指向链表的下一个节点
                    p = (BaseMultiEntity) p.getMultiListChildData().get(0);
                } else {
                    BaseMultiAdapter multiDetailsAdapter = multiLastAdapter.create(context, p.getMultiListChildData());
//                    BaseMultiAdapter multiDetailsAdapter = new MultiDetailsAdapter(context, p.getMultiListChildData());
                    recyclerViewList.get(i).setAdapter(multiDetailsAdapter);
                    mAdapterList.add(multiDetailsAdapter);
                    // 最后一个节点的时候设置p=null表示该节点为最后一个节点
                    p = null;
                }
            }
        }
    }


    public interface MultiLastAdapter<T>{

        public BaseMultiAdapter create(Context context, List<T> datalist);

    }


}
