package com.mesalabs.on.romcontrol.utils;

import android.util.Log;

import com.mesalabs.on.romcontrol.OnSettingsApp;

/*
 * On Settings
 *
 * Coded by BlackMesa @2020
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
        if (OnSettingsApp.isDebugBuild())
            Log.v("OnSettings: " + tag, msg);
    }

    // Debug
    public static void d(String tag, String msg) {
        if (OnSettingsApp.isDebugBuild())
            Log.d("OnSettings: " + tag, msg);
    }

    public static void d(String tag, String msg, Exception e) {
        if (OnSettingsApp.isDebugBuild())
            Log.d("OnSettings: " + tag, msg, e);
    }

    public static void d(String tag, String msg, Throwable t) {
        if (OnSettingsApp.isDebugBuild())
            Log.d("OnSettings: " + tag, msg, t);
    }

    // Info
    public static void i(String tag, String msg) {
        if (OnSettingsApp.isDebugBuild())
            Log.i("OnSettings: " + tag, msg);
    }

    public static void i(String tag, String msg, Exception e) {
        if (OnSettingsApp.isDebugBuild())
            Log.i("OnSettings: " + tag, msg, e);
    }

    // Warn
    public static void w(String tag, String msg) {
        if (OnSettingsApp.isDebugBuild())
            Log.w("OnSettings: " + tag, msg);
    }

    public static void w(String tag, String msg, Exception e) {
        if (OnSettingsApp.isDebugBuild())
            Log.w("OnSettings: " + tag, msg, e);
    }

    // Error
    public static void e(String tag, String msg) {
        if (OnSettingsApp.isDebugBuild())
            Log.e("OnSettings: " + tag, msg);
    }
}
