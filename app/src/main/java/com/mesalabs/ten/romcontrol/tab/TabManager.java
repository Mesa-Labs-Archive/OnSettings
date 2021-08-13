package com.mesalabs.ten.romcontrol.tab;

import com.mesalabs.cerberus.utils.SharedPreferencesUtils;

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

public class TabManager {
    private static String KEY = "mesa_current_tab";

    private SharedPreferencesUtils sp;
    private int sSelectedTab = 0;
    private int sPrevTab = sSelectedTab;

    public TabManager(String spName) {
        sp = SharedPreferencesUtils.getInstance(spName);
    }

    public int getCurrentTab() {
        return sSelectedTab;
    }

    public int getPreviousTab() {
        return sPrevTab;
    }

    public void initTabPosition() {
        setTabPosition(getTabFromSharedPreference());
    }

    public void setTabPosition(int position) {
        setTabPositionToSharedPreference(position);
        sPrevTab = sSelectedTab;
        sSelectedTab = position;
    }

    private int getTabFromSharedPreference() {
        int position = sp.getInt(KEY, -1);
        if (position == -1) {
            return 0;
        }
        return position;
    }

    private void setTabPositionToSharedPreference(int position) {
        sp.put(KEY, position);
    }
}
