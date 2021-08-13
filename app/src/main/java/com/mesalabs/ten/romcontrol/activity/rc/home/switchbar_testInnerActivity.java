package com.mesalabs.ten.romcontrol.activity.rc.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.mesalabs.cerberus.base.BaseSwitchBarActivity;
import com.mesalabs.cerberus.ui.widget.SwitchBar;
import com.mesalabs.cerberus.utils.CerberusException;
import com.mesalabs.cerberus.utils.SharedPreferencesUtils;
import com.mesalabs.ten.romcontrol.R;
import com.samsung.android.ui.preference.SeslPreferenceGroup;

public class switchbar_testInnerActivity  extends BaseSwitchBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        appBar.setTitleText("Menu Test");
        appBar.setHomeAsUpButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected Fragment getFragment() {
        return new SettingsTestFragment();
    }

    @Override
    protected SwitchBar.SwitchBarPressListener getSwitchBarListener() {
        return new SwitchBar.SwitchBarPressListener() {
            @Override
            public void setChecked(boolean z) {
                SharedPreferencesUtils.getInstance().put("key1", z);
            }
        };
    }

    @Override
    public boolean getSwitchBarDefaultStatus() {
        return SharedPreferencesUtils.getInstance().getBoolean("key1", true);
    }


    public static class SettingsTestFragment extends Fragment {
        private Context mContext;
        private View mRootView;

        @Override
        public void onAttach(Context context) {
            super.onAttach(context);
            mContext = context;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            mRootView = inflater.inflate(R.layout.mesa_rc_fragment_switchbar_testinneractivity, container, false);
            return mRootView;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

            if (mRootView == null) {
                mRootView = getView();
            }
        }

    }
}