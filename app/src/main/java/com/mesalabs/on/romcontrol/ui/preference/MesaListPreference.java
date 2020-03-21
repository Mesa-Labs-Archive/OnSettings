package com.mesalabs.on.romcontrol.ui.preference;

import android.content.Context;
import android.util.AttributeSet;

import com.samsung.android.ui.preference.SeslListPreference;

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

public class MesaListPreference extends SeslListPreference {
    public MesaListPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public MesaListPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MesaListPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MesaListPreference(Context context) {
        super(context);
    }
}
