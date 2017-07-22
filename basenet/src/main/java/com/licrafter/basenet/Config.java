package com.licrafter.basenet;

import android.os.Build;

/**
 * Created by lijx on 2017/7/22.
 */

public final class Config {
    public static final int TIMEOUT_DEFAULT = 3000;
    private static boolean DEBUG = false;

    public static final String HTTP_UA = "Dalvik/1.6.0 (Linux; U; Android "
            + Build.VERSION.RELEASE
            + "; "
            + Build.MODEL
            + " Build/"
            + Build.ID
            + ")";

    public static void setDebug(boolean debug) {
        DEBUG = debug;
    }

    public static boolean isDebuggable() {
        return DEBUG;
    }
}
