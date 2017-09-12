动态单页面多列表
------------------------------

### 一.效果展示

![15051384411381505138249369.gif](http://upload-images.jianshu.io/upload_images/3537898-bd65882193e0ffd5.gif?imageMogr2/auto-orient/strip)

### 二.使用方法：

##### 1.在布局中定义

```
<com.example.kylinarm.multilisttest.MultiListView
        android:layout_height="match_parent"
        android:layout_width="match_parent"
        app:count = "4"
        app:division_count = "4"
        android:id="@+id/mv_content"
        >
    </com.example.kylinarm.multilisttest.MultiListView>
```
目前第一个版本只定义了两个参数，之后的版本会继续扩展功能。
**app:count ：表示你的动态多列表中最多有多少层，例如我的demo，有的有3层，有的有4层，所以我这里定义count为4**
**app:division_count 表示你要把列表划分成多少份，是相对于屏幕宽度来说的。比如我划分成4份，我的demo中的比例就是1:1:1:2（该控件支持左右滑动）**

##### 2.在代码中调用

```
@InjectView(R.id.mv_content)
MultiListView mvContent;

mvContent.setAllWeight(1,1,1);
mvContent.setLayoutManager();
mvContent.setAdapter(mockResult(),this);
```
也就三个方法：
**（1）设置比例，默认的为总比例的一半，我这里设置为1:1:1，实际比例就是1:1:1:2，对着延时结果，可以看出把屏幕宽分成4份，除了最后一列占两份，其它都占一份。**
**（2）让内部初始化操作，我把它和展示数据分开，这样会更灵活。**
**（3）展示数据，第一个参数是传数据，第二个参数下面会说。**

##### 3.创建该View

虽然调用简单，但是创建还是要写些代码，因为你想想。不管我做成什么样的，为了扩展性，我最后一列（也就是demo中灰色的那列）是由情况去决定的，而不是把布局写死。所以就要根据不同的情况去设置不同的布局。比如上面的demo，我的最后的数据是由3个字段构成，所以布局有3个textview。而除了最后一列，其它列都只能是textview。

**因为我封装的是用RecyclerView来实现，所以要求传入Adapter，要就要求使用这个控件的页面实现接口并创建Adapter**

```
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
```
要实现这个接口MultiListView.MultiLastAdapter，所以上边的第三个方法的this是指在当前页面传入最后一列所需的特殊的adapter

```
@Override
    public BaseMultiAdapter create(Context context, List<MockDetailsEntity> datalist) {
        return new MultiDetailsAdapter(context, datalist);
    }
```

然后展示一下我的adapter和viewholder

```
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
```

```
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
```

都不是很难，这里的adapter要继承我的BaseMultiAdapter，至于怎么创建和做什么操作，都和该控件无关。
