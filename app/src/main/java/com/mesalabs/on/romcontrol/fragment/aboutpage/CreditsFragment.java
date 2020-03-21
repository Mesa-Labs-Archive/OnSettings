package com.mesalabs.on.romcontrol.fragment.aboutpage;

import android.os.Bundle;

import com.mesalabs.on.romcontrol.R;
import com.samsung.android.ui.preference.SeslPreferenceFragmentCompat;

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

public class CreditsFragment extends SeslPreferenceFragmentCompat {
    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        getListView().seslSetLastItemOutlineStrokeEnabled(true);
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String str) {
        addPreferencesFromResource(R.xml.mesa_creditspref_creditsactivity);
        seslSetRoundedCornerType(SESL_ROUNDED_CORNER_TYPE_STROKE);
    }
}
