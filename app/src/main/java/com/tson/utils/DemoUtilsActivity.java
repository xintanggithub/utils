package com.tson.utils;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import com.tson.utils.R;
import com.tson.utils.lib.http.RetrofitFactory;
import com.tson.utils.lib.http.base.BaseApiStatus;
import okhttp3.Interceptor;
import okhttp3.Request;

import java.util.List;

public class DemoUtilsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_http);

    }

}
