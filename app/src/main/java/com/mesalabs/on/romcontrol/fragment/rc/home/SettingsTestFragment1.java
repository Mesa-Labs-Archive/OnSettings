package com.mesalabs.on.romcontrol.fragment.rc.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.mesalabs.on.romcontrol.ui.preference.MesaColorPickerPreference;
import com.mesalabs.on.romcontrol.ui.preference.MesaTipsCardViewPreference;
import com.mesalabs.cerberus.utils.CerberusException;
import com.mesalabs.cerberus.utils.SharedPreferencesUtils;
import com.mesalabs.on.romcontrol.R;
import com.mesalabs.on.romcontrol.activity.rc.home.switchbar_testInnerActivity;
import com.mesalabs.on.romcontrol.base.BasePreferenceTabFragment;
import com.mesalabs.on.romcontrol.utils.SettingsUtils;
import com.samsung.android.ui.preference.PreferenceCategory;
import com.samsung.android.ui.preference.SeslPreference;
import com.samsung.android.ui.preference.SeslPreferenceGroup;
import com.samsung.android.ui.preference.SeslSeekBarPreference;
import com.samsung.android.ui.preference.SeslSwitchPreferenceScreen;

public class SettingsTestFragment1 extends BasePreferenceTabFragment implements
        SeslPreference.OnPreferenceChangeListener,
        SeslPreference.OnPreferenceClickListener {
    private long mLastClickTime = 0L;

    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        getListView().seslSetLastItemOutlineStrokeEnabled(true);
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String str) {
        addPreferencesFromResource(R.xml.mesa_testsettingsfragment1);
        seslSetRoundedCornerType(SESL_ROUNDED_CORNER_TYPE_STROKE);
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        final MesaTipsCardViewPreference tip0 = (MesaTipsCardViewPreference) findPreference("tip0");
        final PreferenceCategory padding = (PreferenceCategory) findPreference("padding");
        final SeslPreferenceGroup parent = getParent(getPreferenceScreen(), tip0);
        if (parent == null)
            throw new CerberusException("Couldn't find preference");

        if (SharedPreferencesUtils.getInstance().getBoolean("istipshown", false)) {
            parent.removePreference(tip0);
            parent.removePreference(padding);
        } else {
            tip0.setTipsCardListener(new MesaTipsCardViewPreference.TipsCardListener() {
                @Override
                public void onCancelClicked(View view) {
                    parent.removePreference(tip0);
                    parent.removePreference(padding);

                    SharedPreferencesUtils.getInstance().put("istipshown", true);
                }
            });
        }

        SeslSwitchPreferenceScreen switchbar_test = (SeslSwitchPreferenceScreen) findPreference("switchbar_test");
        switchbar_test.setOnPreferenceClickListener(this);
        switchbar_test.setOnPreferenceChangeListener(this);

        SeslPreference key5 = findPreference("key5");
        key5.seslSetSummaryColor(getResources().getColor(R.color.sesl_primary_dark_color_light, null));
        key5.setSummary(SharedPreferencesUtils.getInstance().getString("key5", "Empty"));
        key5.setOnPreferenceChangeListener(this);

        SeslSeekBarPreference key81 = (SeslSeekBarPreference) findPreference("key81");
        key81.setValue(SharedPreferencesUtils.getInstance().getInt("key81", 0));

        MesaColorPickerPreference color1 = (MesaColorPickerPreference) findPreference("color1");
        color1.setOnPreferenceChangeListener(this);

    }


    @Override
    public void onStart(){
        super.onStart();

        SeslSwitchPreferenceScreen switchbar_test = (SeslSwitchPreferenceScreen) findPreference("switchbar_test");
        switchbar_test.setChecked(SharedPreferencesUtils.getInstance().getBoolean("switchbar_test"));
    }

    @Override
    public boolean onPreferenceChange(SeslPreference preference, Object newValue) {
        if (preference.getKey().equals("switchbar_test")) {
            ((SeslSwitchPreferenceScreen) preference).setChecked((boolean) newValue);
            return true;
        }
        if (preference.getKey().equals("key5")) {
            preference.setSummary(newValue.toString());
            return true;
        }
        if (preference.getKey().equals("color1")) {
            SettingsUtils.putSystemInt(getContext().getContentResolver(), "mesa_test_colorkey", (int) newValue);
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


        if (preference.getKey().equals("switchbar_test")) {
            Toast.makeText(mContext, "switchbar_test pressed", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(mContext, switchbar_testInnerActivity.class));
            return true;
        }

        return false;
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