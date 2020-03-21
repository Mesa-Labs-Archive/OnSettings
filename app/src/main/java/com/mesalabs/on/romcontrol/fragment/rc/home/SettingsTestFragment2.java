package com.mesalabs.on.romcontrol.fragment.rc.home;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.mesalabs.on.romcontrol.R;
import com.mesalabs.on.romcontrol.base.BasePreferenceTabFragment;

public class SettingsTestFragment2 extends BasePreferenceTabFragment {
    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        getListView().seslSetLastItemOutlineStrokeEnabled(true);
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String str) {
        addPreferencesFromResource(R.xml.mesa_testsettingsfragment2);
        seslSetRoundedCornerType(SESL_ROUNDED_CORNER_TYPE_STROKE);
    }

    @Override
    public boolean onDispatchKeyEvent(KeyEvent keyEvent, View view) {
        return false;
    }

    @Override
    public void onTabSelected() { }

    @Override
    public void onTabUnselected() { }

}