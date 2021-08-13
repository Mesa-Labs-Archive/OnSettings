package com.mesalabs.ten.romcontrol.utils;

import android.util.Log;

import com.mesalabs.ten.romcontrol.TenSettingsApp;

/*
 * 십 Settings
 *
 * Coded by BlackMesa123 @2021
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 */

public class LogUtils {
    // Verbose
    public static void v(String tag, String msg) {
        if (TenSettingsApp.isDebugBuild())
            Log.v("십 Settings: " + tag, msg);
    }

    // Debug
    public static void d(String tag, String msg) {
        if (TenSettingsApp.isDebugBuild())
            Log.d("십 Settings: " + tag, msg);
    }

    public static void d(String tag, String msg, Exception e) {
        if (TenSettingsApp.isDebugBuild())
            Log.d("십 Settings: " + tag, msg, e);
    }

    public static void d(String tag, String msg, Throwable t) {
        if (TenSettingsApp.isDebugBuild())
            Log.d("십 Settings: " + tag, msg, t);
    }

    // Info
    public static void i(String tag, String msg) {
        if (TenSettingsApp.isDebugBuild())
            Log.i("십 Settings: " + tag, msg);
    }

    public static void i(String tag, String msg, Exception e) {
        if (TenSettingsApp.isDebugBuild())
            Log.i("십 Settings: " + tag, msg, e);
    }

    // Warn
    public static void w(String tag, String msg) {
        if (TenSettingsApp.isDebugBuild())
            Log.w("십 Settings: " + tag, msg);
    }

    public static void w(String tag, String msg, Exception e) {
        if (TenSettingsApp.isDebugBuild())
            Log.w("십 Settings: " + tag, msg, e);
    }

    // Error
    public static void e(String tag, String msg) {
        if (TenSettingsApp.isDebugBuild())
            Log.e("십 Settings: " + tag, msg);
    }
}
