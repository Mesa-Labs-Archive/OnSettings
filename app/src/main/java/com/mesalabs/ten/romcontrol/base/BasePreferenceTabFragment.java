package com.mesalabs.ten.romcontrol.base;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;

import com.mesalabs.cerberus.utils.ViewUtils;
import com.mesalabs.ten.romcontrol.R;
import com.samsung.android.ui.preference.SeslPreference;
import com.samsung.android.ui.preference.SeslPreferenceFragmentCompat;
import com.samsung.android.ui.preference.SeslPreferenceGroup;

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

public abstract class BasePreferenceTabFragment extends SeslPreferenceFragmentCompat {
    protected Context mContext;
    protected View mFragmentView = null;

    public abstract boolean onDispatchKeyEvent(KeyEvent keyEvent, View view);

    public abstract void onTabSelected();

    public abstract void onTabUnselected();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        cleanupResources();
    }

    public void onDestroy() {
        cleanupResources();
        super.onDestroy();
    }

    private void cleanupResources() {
        if (mFragmentView != null) {
            ViewUtils.nullViewDrawablesRecursive(mFragmentView);
        }
        mFragmentView = null;
    }

    protected SeslPreferenceGroup getParent(SeslPreferenceGroup groupToSearchIn, SeslPreference preference) {
        for (int i = 0; i < groupToSearchIn.getPreferenceCount(); i++) {
            SeslPreference child = groupToSearchIn.getPreference(i);

            if (child == preference)
                return groupToSearchIn;

            if (child instanceof SeslPreferenceGroup) {
                SeslPreferenceGroup childGroup = (SeslPreferenceGroup)child;
                SeslPreferenceGroup result = getParent(childGroup, preference);
                if (result != null)
                    return result;
            }
        }

        return null;
    }

    protected ColorStateList getColoredSummaryColor() {
        TypedValue colorPrimaryDark = new TypedValue();
        getContext().getTheme().resolveAttribute(R.attr.colorPrimaryDark, colorPrimaryDark, true);

        int[][] states = new int[][] {
                new int[] { android.R.attr.state_enabled},
                new int[] {-android.R.attr.state_enabled}
        };
        int[] colors = new int[] {
                Color.argb(0xff, Color.red(colorPrimaryDark.data), Color.green(colorPrimaryDark.data), Color.blue(colorPrimaryDark.data)),
                Color.argb(0x4d, Color.red(colorPrimaryDark.data), Color.green(colorPrimaryDark.data), Color.blue(colorPrimaryDark.data))
        };
        return new ColorStateList(states, colors);
    }

    public void onMultiWindowModeChanged(boolean isMultiWindowMode) { }

}
