package com.mesalabs.on.romcontrol.fragment.rc.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.mesalabs.cerberus.ui.preference.ColorPickerPreference;
import com.mesalabs.cerberus.ui.preference.TipsCardViewPreference;
import com.mesalabs.cerberus.utils.CerberusException;
import com.mesalabs.on.romcontrol.utils.LogUtils;
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

    @Override
    public void onCreatePreferences(Bundle bundle, String str) {
        addPreferencesFromResource(R.xml.mesa_testsettingsfragment1);
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        final TipsCardViewPreference tip0 = (TipsCardViewPreference) findPreference("tip0");
        final PreferenceCategory padding = (PreferenceCategory) findPreference("padding");
        final SeslPreferenceGroup parent = getParent(getPreferenceScreen(), tip0);
        if (parent == null)
            throw new CerberusException("Couldn't find preference");

        SharedPreferencesUtils.getInstance().put("istipshown", false); /* temp */

        if (SharedPreferencesUtils.getInstance().getBoolean("istipshown", false)) {
            parent.removePreference(tip0);
            parent.removePreference(padding);
        } else {
            tip0.setTipsCardListener(new TipsCardViewPreference.TipsCardListener() {
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

        ColorPickerPreference color1 = (ColorPickerPreference) findPreference("color1");
        color1.setOnPreferenceChangeListener(this);

        SeslPreference key9 = findPreference("key9");
        key9.setOnPreferenceClickListener(this);

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
            int color = SettingsUtils.getSystemInt(getContext().getContentResolver(), "mesa_test_colorkey");
            //Toast.makeText(mContext, "new value = " + color, Toast.LENGTH_SHORT).show();
            LogUtils.e("STF1", "new value = " + color);
            return true;
        }

        return false;
    }

    @Override
    public boolean onPreferenceClick(SeslPreference preference) {
        if (preference.getKey().equals("switchbar_test")) {
            Toast.makeText(mContext, "switchbar_test pressed", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(mContext, switchbar_testInnerActivity.class));
            return true;
        }
        if (preference.getKey().equals("key9")) {
            Toast.makeText(mContext, "key9 pressed", Toast.LENGTH_SHORT).show();
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