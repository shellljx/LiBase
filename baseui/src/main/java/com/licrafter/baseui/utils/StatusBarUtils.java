package com.licrafter.baseui.utils;

import android.os.Build;
import android.view.WindowManager;

import com.licrafter.baseui.BaseAppCompatActivity;

/**
 * author: shell
 * date 2017/3/9 上午11:43
 **/
public class StatusBarUtils {

    public static void initStatusBar(BaseAppCompatActivity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = activity.getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
    }
}
