package com.tson.utils.lib.util.sp;

import android.content.Context;

/**
 * Created tangxin
 * Time 2018/10/11 下午1:47
 *
 * @author tangxin
 */
public class SharePreferencesFactory extends BaseSharedPreferencesFactory {

    private static final String TAG = "SharePreferencesFactory";

    private String key = "tson_utils_lib_sp";

    /**
     * Instantiates a new Setting preferences factory.
     *
     * @param context the context
     */
    public SharePreferencesFactory(Context context) {
        super(context);
    }

    public SharePreferencesFactory(Context context, String key) {
        super(context);
        this.key = key;
    }

    @Override
    protected String getKey() {
        return key;
    }
}
