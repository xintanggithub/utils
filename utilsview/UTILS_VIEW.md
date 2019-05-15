
## utilsView

### 0. 如何引入

#### 0.1 根目录下的build.gradle添加如下代码

```
allprojects {
    repositories {
        google()
        jcenter()
        //添加下面这个maven配置
        maven{
            //这是maven仓库地址
            url 'https://raw.githubusercontent.com/xintanggithub/maven/master'
        }
    }
}
```

#### 0.2 需要使用的module下build.gradle添加引用

```
    implementation "com.tson.utils.view:lib:1.0.1"
```

### 1. BaseAdapter的使用

#### 1.1 新建item布局和foot布局

##### 1.1.1 item_layout.xml

```
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">

    <data>

        <variable
            name="vm"
            type="String" />

    </data>

    <android.support.constraint.ConstraintLayout
        android:layout_width="match_parent"
        android:background="@color/colorAccent"
        android:layout_height="wrap_content">

        <TextView
            android:id="@+id/textView"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="@{vm}"
            android:textColor="#000000"
            tools:ignore="MissingConstraints"
            tools:layout_editor_absoluteX="97dp"
            tools:layout_editor_absoluteY="51dp" />
    </android.support.constraint.ConstraintLayout>
</layout>
```

##### 1.1.2 item_faw_footer.xml

```
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">

    <data>

        <variable
            name="vm"
            type="String" />

    </data>

    <LinearLayout
        android:id="@+id/lv_root"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="#336699"
        android:gravity="center"
        android:orientation="horizontal">

        <ProgressBar
            android:id="@+id/progress"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center" />

        <TextView
            android:id="@+id/tv_tips"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginStart="10dp"
            android:layout_marginLeft="10dp"
            tools:text="没有更多" />

    </LinearLayout>
</layout>
```

#### 1.2 创建adapter继承baseAdapter，并且设置footerView

##### 1.2.1 创建adapter

```
// string为item对应的数据类类型、ItemLayoutBinding为item的dataBinding对象
public class MyAdapter extends BaseAdapter<String, ItemLayoutBinding> {

    MyAdapter(List<String> mData, int itemLayoutId, CallBack callBack) {
        super(mData, itemLayoutId, callBack);
    }

    @Override
    public void onBindViewHolders(int position, @NonNull BaseViewHolder holder) {
        ItemLayoutBinding itemLayoutBinding = (ItemLayoutBinding) holder.itemDataBinding;
        itemLayoutBinding.setVm(getData(position));
    }

}
```

##### 1.2.2 配置footerView

- 以下为配置footerView的代码,如果会多次使用并样式一样，建议统一封装回调方法
- footerView的样式、不同状态的显示，baseAdapter均未做处理，给予自定义样式充分的空间

```
val data1 = mutableListOf("a", "b", "c", "a", "b", "c", "a", "b", "c", "a", "b","c", "a", "b", "c", "a", "b", "c", "a", "b", "c", "a", "b", "c", "a", "b", "c","a", "b", "c")

//依次传入 数据、item布局、设置footer的回调
 val layoutManager = GridLayoutManager(this@TestRecyclerViewActivity, 3)

val adapter = MyAdapter(data, R.layout.item_layout, object : CallBack<String, ItemFawFooterBinding> {
    override fun layoutManager(): RecyclerView.LayoutManager {
                return layoutManager
            }
            
    override fun footerHolder(holder: BaseAdapter.FooterViewHolder<*>, mData: List<String>, loadState: Int) {
    //footerView显示逻辑的处理回调
        val item = holder.itemDataBinding as ItemFawFooterBinding
        if (mData.isEmpty()) {
            item.progress.visibility = View.INVISIBLE
            item.tvTips.text = ""
        } else {
            when (loadState) {//footerView的状态
                LOADING -> {//加载更多ing
                    item.progress.visibility = View.VISIBLE
                    item.tvTips.setText(R.string.loading_content_hint)
                }
                LOADING_COMPLETE -> {//加载完成
                    item.progress.visibility = View.INVISIBLE
                    item.tvTips.text = ""
                }
                LOADING_END -> {//没有更多
                    item.progress.visibility = View.GONE
                    item.tvTips.setText(R.string.no_more)
                }
                else -> {
                }
            }
        }
    }

    override fun dataBinding(parent: ViewGroup): ItemFawFooterBinding {
    //传入footerView的dataBinding对象
        return DataBindingUtil
                .inflate(LayoutInflater.from(parent.context),
                        R.layout.item_faw_footer, parent, false)
    }
})
```

##### 1.2.3 绑定到recycleView

```
rv_list.layoutManager = LinearLayoutManager(this@TestRecyclerViewActivity)
rv_list.adapter = adapter
```