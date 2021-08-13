package com.mesalabs.ten.romcontrol.fragment.rc.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.View;

import com.mesalabs.cerberus.utils.CerberusException;
import com.mesalabs.ten.romcontrol.ui.preference.MesaSwitchPreferenceScreen;
import com.mesalabs.ten.romcontrol.ui.preference.MesaTipsCardViewPreference;
import com.mesalabs.cerberus.utils.SharedPreferencesUtils;
import com.mesalabs.ten.romcontrol.R;
import com.mesalabs.ten.romcontrol.activity.rc.home.switchbar_testInnerActivity;
import com.mesalabs.ten.romcontrol.base.BasePreferenceTabFragment;
import com.samsung.android.ui.preference.SeslPreference;
import com.samsung.android.ui.preference.SeslPreferenceGroup;

public class SettingsTestFragment1 extends BasePreferenceTabFragment implements
        SeslPreference.OnPreferenceChangeListener,
        SeslPreference.OnPreferenceClickListener {
    private long mLastClickTime = 0L;

    @Override
    public void onCreatePreferences(Bundle bundle, String str) {
        addPreferencesFromResource(R.xml.mesa_testsettingsfragment1);
        seslSetRoundedCornerType(SESL_ROUNDED_CORNER_TYPE_STROKE);
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        final MesaTipsCardViewPreference tip0 = (MesaTipsCardViewPreference) findPreference("tip0");
        final SeslPreferenceGroup parent = getParent(getPreferenceScreen(), tip0);
        if (parent == null)
            throw new CerberusException("Couldn't find preference");

        if (SharedPreferencesUtils.getInstance().getBoolean("istipshown", false)) {
            parent.removePreference(tip0);
        } else {
            tip0.setTipsCardListener(new MesaTipsCardViewPreference.TipsCardListener() {
                @Override
                public void onCancelClicked(View view) {
                    parent.removePreference(tip0);
                    //SharedPreferencesUtils.getInstance().put("istipshown", true);
                }
            });
        }

        MesaSwitchPreferenceScreen switchbar_test = (MesaSwitchPreferenceScreen) findPreference("key1");
        switchbar_test.setOnPreferenceClickListener(this);
        switchbar_test.setOnPreferenceChangeListener(this);

        SeslPreference key5 = findPreference("key7");
        key5.seslSetSummaryColor(getColoredSummaryColor());
        key5.setSummary(SharedPreferencesUtils.getInstance().getString("key7", "Empty"));
        key5.setOnPreferenceChangeListener(this);
    }

    @Override
    public void onStart(){
        super.onStart();

        MesaSwitchPreferenceScreen switchbar_test = (MesaSwitchPreferenceScreen) findPreference("key1");
        switchbar_test.setChecked(SharedPreferencesUtils.getInstance().getBoolean("key1"));
    }

    @Override
    public boolean onPreferenceChange(SeslPreference preference, Object newValue) {
        if (preference.getKey().equals("key1")) {
            ((MesaSwitchPreferenceScreen) preference).setChecked((boolean) newValue);
            return true;
        }
        if (preference.getKey().equals("key7")) {
            preference.setSummary(newValue.toString());
            return true;
        }

        return false;
    }

    @Override
    public boolean onPreferenceClick(SeslPreference preference) {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 600L) {
            return false;
        }
        mLastClickTime = SystemClock.elapsedRealtime();


        if (preference.getKey().equals("key1")) {
            startActivity(new Intent(mContext, switchbar_testInnerActivity.class));
            return true;
        }

        return false;
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        getListView().seslSetLastItemOutlineStrokeEnabled(true);
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
