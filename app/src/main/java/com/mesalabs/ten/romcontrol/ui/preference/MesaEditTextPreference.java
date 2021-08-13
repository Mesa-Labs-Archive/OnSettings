package com.mesalabs.ten.romcontrol.ui.preference;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;

import androidx.core.content.res.TypedArrayUtils;

import com.mesalabs.ten.romcontrol.R;
import com.samsung.android.ui.preference.EditTextPreference;

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

public class MesaEditTextPreference extends EditTextPreference {
    public MesaEditTextPreference(Context context) {
        this(context, null);
    }

    @SuppressLint("RestrictedApi")
    public MesaEditTextPreference(Context context, AttributeSet attrs) {
        this(context, attrs, TypedArrayUtils.getAttr(context, R.attr.editTextPreferenceStyle, android.R.attr.editTextPreferenceStyle));
        setNegativeButtonText(R.string.mesa_cancel);
        setPositiveButtonText(R.string.mesa_done);
    }

    public MesaEditTextPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public MesaEditTextPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
