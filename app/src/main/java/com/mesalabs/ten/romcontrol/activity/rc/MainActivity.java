package com.mesalabs.ten.romcontrol.activity.rc;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.LinkedHashMap;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.mesalabs.cerberus.base.BaseAppBarActivity;
import com.mesalabs.cerberus.ui.utils.ActionBarUtils;
import com.mesalabs.cerberus.ui.widget.TabLayout;
import com.mesalabs.cerberus.update.utils.AppUpdateUtils;
import com.mesalabs.cerberus.utils.ViewUtils;
import com.mesalabs.ten.romcontrol.TenSettingsApp;
import com.mesalabs.ten.romcontrol.R;
import com.mesalabs.ten.romcontrol.activity.aboutpage.AboutActivity;
import com.mesalabs.ten.romcontrol.base.BasePreferenceTabFragment;
import com.mesalabs.ten.romcontrol.tab.TabManager;
import com.samsung.android.ui.tabs.SeslTabLayout;

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

public class MainActivity extends BaseAppBarActivity {
    private static String SP_DB = TenSettingsApp.getAppPackageName() + "tabs";

    private BasePreferenceTabFragment mFragment;
    private FragmentManager mFragmentManager;
    private TabManager mTabManager;
    private TabLayout mTabLayout;
    private String[] mTabName;

    private boolean mUpdateAvailable = false;

    private AppUpdateUtils mAppUpdate;
    private AppUpdateUtils.StubListener mStubListener = new AppUpdateUtils.StubListener() {
        public void onUpdateCheckCompleted(int status) {
            mUpdateAvailable = status == AppUpdateUtils.STATE_NEW_VERSION_AVAILABLE;
            initMoreMenuButton();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //  init AppUpdateUtils
        mAppUpdate = new AppUpdateUtils(this, TenSettingsApp.getAppPackageName(), mStubListener);
        mAppUpdate.checkUpdates();

        // init Tab Tags
        mTabName = getResources().getStringArray(R.array.mesa_tab_tag_mainactivity);

        // init TabManager
        mTabManager = new TabManager(SP_DB);
        mTabManager.initTabPosition();

        // init UX
        removeViewRoundedCorners();

        setBaseContentView(R.layout.mesa_rc_activity_mainactivity_layout);

        appBar = new ActionBarUtils(this);
        appBar.initAppBar(false);
        appBar.setTitleText("십 Settings");

        ViewGroup preferenceContainer = findViewById(R.id.mesa_container_mainactivity);
        ViewUtils.updateListBothSideMargin(this, preferenceContainer);

        initMoreMenuButton();

        createTabs();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        View focusedTab = mTabLayout.getFocusedChild();
        int keyCode = event.getKeyCode();

        if (mFragment == null || !mFragment.isResumed()) {
            return super.dispatchKeyEvent(event);
        } else if (focusedTab == null) {
            if (mFragment.onDispatchKeyEvent(event, null) || super.dispatchKeyEvent(event)) {
                return true;
            }
            return false;
        } else if (keyCode != KeyEvent.KEYCODE_N && keyCode != KeyEvent.KEYCODE_A && keyCode != KeyEvent.KEYCODE_D && keyCode != KeyEvent.KEYCODE_FORWARD_DEL) {
            return super.dispatchKeyEvent(event);
        } else {
            if (mFragment.onDispatchKeyEvent(event, focusedTab) || super.dispatchKeyEvent(event)) {
                return true;
            }
            return false;
        }
    }

    @Override
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);

        ViewGroup preferenceContainer = findViewById(R.id.mesa_container_mainactivity);
        ViewUtils.updateListBothSideMargin(this, preferenceContainer);
    }

    @Override
    public void onMultiWindowModeChanged(boolean isMultiWindowMode) {
        super.onMultiWindowModeChanged(isMultiWindowMode);
        for (String tabName : mTabName) {
            BasePreferenceTabFragment fragment = (BasePreferenceTabFragment) mFragmentManager.findFragmentByTag(tabName);
            if (fragment != null) fragment.onMultiWindowModeChanged(isMultiWindowMode);
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if (mTabLayout != null) {
            mTabLayout.setResumeStatus(false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mTabLayout != null) {
            mTabLayout.setResumeStatus(true);
        }
    }

    private void createTabs() {
        mFragmentManager = getSupportFragmentManager();

        mTabLayout = findViewById(R.id.mesa_tablayout_mainactivity);

        String[] tabs = getResources().getStringArray(R.array.mesa_tab_name_mainactivity);
        for (String s: tabs) {
            mTabLayout.addTab(mTabLayout.newTab().setText(s));
        }

        mTabLayout.addOnTabSelectedListener(new SeslTabLayout.OnTabSelectedListener() {
            public void onTabSelected(SeslTabLayout.Tab tab) {
                int tabPosition = tab.getPosition();
                mTabManager.setTabPosition(tabPosition);
                setCurrentItem();
                BasePreferenceTabFragment fragment = (BasePreferenceTabFragment) mFragmentManager.findFragmentByTag(mTabName[tabPosition]);
                if (fragment != null) fragment.onTabSelected();
            }

            public void onTabUnselected(SeslTabLayout.Tab tab) {
                int tabPosition = tab.getPosition();
                if (appBar != null) appBar.dismissMoreMenuPopupWindow();
                closeContextMenu();
                BasePreferenceTabFragment fragment = (BasePreferenceTabFragment) mFragmentManager.findFragmentByTag(mTabName[tabPosition]);
                if (fragment != null) fragment.onTabUnselected();
            }

            public void onTabReselected(SeslTabLayout.Tab tab) { }
        });

        mTabLayout.setup(this);

        setCurrentItem();
    }

    private void setCurrentItem() {
        if (mTabLayout.isEnabled()) {
            int tabPosition = mTabManager.getCurrentTab();
            SeslTabLayout.Tab tab = mTabLayout.getTabAt(tabPosition);
            if (tab != null) {
                tab.select();
                setFragment(tabPosition);
            }
        }
    }

    private void setFragment(int tabPosition) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        String tabName = mTabName[tabPosition];
        Fragment fragment = mFragmentManager.findFragmentByTag(tabName);
        if (mFragment != null) {
            transaction.hide(mFragment);
        }
        if (fragment != null) {
            mFragment = (BasePreferenceTabFragment) fragment;
            transaction.show(fragment);
        } else {
            String [] clazz = getResources().getStringArray(R.array.mesa_tab_class_mainactivity);
            try {
                mFragment = (BasePreferenceTabFragment) Class.forName(clazz[tabPosition]).newInstance();
            } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            transaction.add(R.id.mesa_fragmentcontainer_mainactivity, mFragment, tabName);
        }
        transaction.commit();
    }

    private LinkedHashMap<String, Integer> initMoreMenuButtonList() {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put(getString(R.string.mesa_about_app), mUpdateAvailable ? ActionBarUtils.NEW_UPDATE_AVAILABLE : 0);
        return linkedHashMap;
    }

    private void initMoreMenuButton() {
        appBar.setMoreMenuButton(initMoreMenuButtonList(),
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                        appBar.dismissMoreMenuPopupWindow();

                        String obj = adapterView.getAdapter().getItem(i).toString();
                        if (obj.equals(getString(R.string.mesa_about_app))) {
                            startActivity(new Intent(mContext, AboutActivity.class));
                        }
                    }
                });
    }
}
