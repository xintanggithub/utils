package com.tson.utils.lib.util.sp;

import android.content.Context;
import com.tson.utils.lib.util.BuildConfig;

/**
 * Created tangxin
 * Time 2018/10/11 下午1:47
 */
public class SharePreferencesFactory extends BaseSharedPreferencesFactory {

    private static final String TAG = "SharePreferencesFactory";

    /**
     * Instantiates a new Setting preferences factory.
     *
     * @param context the context
     */
    public SharePreferencesFactory(Context context) {
        super(context);
    }

    @Override
    protected String getKey() {
        return BuildConfig.APPLICATION_ID + "_utils_lib_sp";
    }
}
