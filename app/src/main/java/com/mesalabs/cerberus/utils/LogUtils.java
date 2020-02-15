package com.mesalabs.cerberus.utils;

import android.util.Log;

import com.mesalabs.on.romcontrol.OnSettingsApp;

/*
 * Cerberus Core App
 *
 * Coded by BlackMesa @2020
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * ULTRA-MEGA-PRIVATE SOURCE CODE. SHARING TO DEVKINGS TEAM
 * EXTERNALS IS PROHIBITED AND WILL BE PUNISHED WITH ANAL ABUSE.
 */

public class LogUtils {
    // Verbose
    public static void v(String tag, String msg) {
        if (OnSettingsApp.isDebugBuild())
            Log.v("CerberusCore: " + tag, msg);
    }

    // Debug
    public static void d(String tag, String msg) {
        if (OnSettingsApp.isDebugBuild())
            Log.d("CerberusCore: " + tag, msg);
    }

    public static void d(String tag, String msg, Exception e) {
        if (OnSettingsApp.isDebugBuild())
            Log.d("CerberusCore: " + tag, msg, e);
    }

    public static void d(String tag, String msg, Throwable t) {
        if (OnSettingsApp.isDebugBuild())
            Log.d("CerberusCore: " + tag, msg, t);
    }

    // Info
    public static void i(String tag, String msg) {
        if (OnSettingsApp.isDebugBuild())
            Log.i("CerberusCore: " + tag, msg);
    }

    public static void i(String tag, String msg, Exception e) {
        if (OnSettingsApp.isDebugBuild())
            Log.i("CerberusCore: " + tag, msg, e);
    }

    // Warn
    public static void w(String tag, String msg) {
        if (OnSettingsApp.isDebugBuild())
            Log.w("CerberusCore: " + tag, msg);
    }

    public static void w(String tag, String msg, Exception e) {
        if (OnSettingsApp.isDebugBuild())
            Log.w("CerberusCore: " + tag, msg, e);
    }

    // Error
    public static void e(String tag, String msg) {
        if (OnSettingsApp.isDebugBuild())
            Log.e("CerberusCore: " + tag, msg);
    }
}
