package com.mesalabs.on.romcontrol.base;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.mesalabs.cerberus.utils.ViewUtils;
import com.samsung.android.ui.preference.SeslPreference;
import com.samsung.android.ui.preference.SeslPreferenceFragmentCompat;
import com.samsung.android.ui.preference.SeslPreferenceGroup;

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

    public void onMultiWindowModeChanged(boolean isMultiWindowMode) { }

}
