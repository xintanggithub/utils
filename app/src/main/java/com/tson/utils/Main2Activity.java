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

        String appStoreIconUrl = "https://download.cdn.jidouauto.com/old/img/2019-03-08/25E46FCE504011D7A16209C470ABA595.png";
        //圆角
        GlideUtil.Companion.setCornerIcon(this, appStoreIconUrl, iv, 20,
                new RequestOptions().placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_background));
        //圆形
        GlideUtil.Companion.setCornerIcon(this, appStoreIconUrl, iv2, 200,
                new RequestOptions().placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_background));
    }
}
