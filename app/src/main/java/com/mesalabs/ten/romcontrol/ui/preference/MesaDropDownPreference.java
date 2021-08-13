package com.mesalabs.ten.romcontrol.ui.preference;

import android.content.Context;
import android.util.AttributeSet;

import com.mesalabs.ten.romcontrol.R;
import com.samsung.android.ui.preference.DropDownPreference;

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

public class MesaDropDownPreference extends DropDownPreference {
    public MesaDropDownPreference(Context context) {
        this(context, null);
    }

    public MesaDropDownPreference(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.dropdownPreferenceStyle);
    }

    public MesaDropDownPreference(Context context, AttributeSet attrs, int defStyle) {
        this(context, attrs, defStyle, 0);
    }

    public MesaDropDownPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
