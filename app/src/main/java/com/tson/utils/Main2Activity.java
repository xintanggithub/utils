package com.tson.utils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.request.RequestOptions;
import com.tson.utils.lib.iv.GlideUtil;

public class Main2Activity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        findViewById(R.id.button).setOnClickListener(v -> {
            startActivity(new Intent(Main2Activity.this, TestRecyclerViewActivity.class));
        });

        ImageView iv = findViewById(R.id.imageView);
        ImageView iv2 = findViewById(R.id.imageView2);

        String appStoreIconUrl = "https://avatars2.githubusercontent.com/u/27901367?s=460&v=4";
        //圆角
        GlideUtil.Companion.setCornerIcon(this, appStoreIconUrl, iv, 20,
                new RequestOptions().placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_background));
        //圆形
        GlideUtil.Companion.setCornerIcon(this, appStoreIconUrl, iv2, 200,
                new RequestOptions().placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_background));

        findViewById(R.id.button2).setOnClickListener(v -> {
            startActivity(new Intent(this, DemoUtilsActivity.class));
        });
    }
}
