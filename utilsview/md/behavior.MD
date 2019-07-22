### AppBarLayout.Behavior自定义

#### 简介

- 当AppBarLayout嵌套recycleView的时候，会出现快速滑动列表后的首次点击无效(缓慢滑动、快速滑动后等一段时间，点击不会出现该问题)，自定义该Behavior解决该问题

#### 使用

在xml中加入 "app:layout_behavior="com.jidouauto.market.widget.FixAppBarLayoutBehavior""使用，

```

            <android.support.design.widget.AppBarLayout
                android:id="@+id/app_bar"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:background="@color/transparent"
                app:layout_behavior="com.jidouauto.market.widget.FixAppBarLayoutBehavior"
                android:theme="@style/AppTheme">

```


