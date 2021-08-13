package com.mesalabs.ten.romcontrol.ui.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.mesalabs.ten.romcontrol.R;
import com.mesalabs.ten.romcontrol.utils.LogUtils;
import com.samsung.android.ui.preference.PreferenceViewHolder;
import com.samsung.android.ui.preference.SeslPreference;
import com.samsung.android.ui.widget.SeslSeekBar;

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

public class MesaSeekBarPreference extends SeslPreference {
    private static final String TAG = "MesaSeekBarPreference";
    @SuppressWarnings("WeakerAccess")
            int mSeekBarValue;
    private int mSeekBarMode;
    private boolean mSeekBarSeamless;
    @SuppressWarnings("WeakerAccess")
        String mUnits;
    @SuppressWarnings("WeakerAccess")
            int mMin;
    private int mMax;
    private int mOverlapPoint;
    private int mSeekBarIncrement;
    @SuppressWarnings("WeakerAccess")
            boolean mTrackingTouch;
    @SuppressWarnings("WeakerAccess")
            SeslSeekBar mSeekBar;
    private TextView mSeekBarValueTextView;
    @SuppressWarnings("WeakerAccess")
            boolean mAdjustable;
    private boolean mShowSeekBarValue;
    @SuppressWarnings("WeakerAccess")
            boolean mUpdatesContinuously;
    private SeslSeekBar.OnSeekBarChangeListener mSeekBarChangeListener = new SeslSeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeslSeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser && (mUpdatesContinuously || !mTrackingTouch)) {
                syncValueInternal(seekBar);
            } else {
                updateLabelValue(progress + mMin);
            }
        }

        @Override
        public void onStartTrackingTouch(SeslSeekBar seekBar) {
            mTrackingTouch = true;
        }

        @Override
        public void onStopTrackingTouch(SeslSeekBar seekBar) {
            mTrackingTouch = false;
            if (seekBar.getProgress() + mMin != mSeekBarValue) {
                syncValueInternal(seekBar);
            }
        }
    };
    private View.OnKeyListener mSeekBarKeyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (event.getAction() != KeyEvent.ACTION_DOWN) {
                return false;
            }
            if (!mAdjustable && (keyCode == KeyEvent.KEYCODE_DPAD_LEFT || keyCode == KeyEvent.KEYCODE_DPAD_RIGHT)) {
                return false;
            }
            if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER || keyCode == KeyEvent.KEYCODE_ENTER) {
                return false;
            }
            if (mSeekBar == null) {
                LogUtils.e(TAG, "SeekBar view is null and hence cannot be adjusted.");
                return false;
            }
            return mSeekBar.onKeyDown(keyCode, event);
        }
    };


    public MesaSeekBarPreference(Context context) {
        this(context, null);
    }

    public MesaSeekBarPreference(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.seekBarPreferenceStyle);
    }

    public MesaSeekBarPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public MesaSeekBarPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        setLayoutResource(R.layout.mesa_preference_seekbarpref_layout);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MesaSeekBarPreference, defStyleAttr, defStyleRes);
        mMin = a.getInt(R.styleable.MesaSeekBarPreference_min, 0);
        setMax(a.getInt(R.styleable.MesaSeekBarPreference_max, 100));
        mOverlapPoint = a.getInt(R.styleable.MesaSeekBarPreference_overlap, -1);
        setSeekBarIncrement(a.getInt(R.styleable.MesaSeekBarPreference_seekBarIncrement, 0));
        mAdjustable = a.getBoolean(R.styleable.MesaSeekBarPreference_adjustable, true);
        mShowSeekBarValue = a.getBoolean(R.styleable.MesaSeekBarPreference_showSeekBarValue, false);
        mSeekBarMode = a.getInt(R.styleable.MesaSeekBarPreference_seekBarMode, 0);
        mSeekBarSeamless = a.getBoolean(R.styleable.MesaSeekBarPreference_seekBarSeamless, false);

        mUnits = a.getString(R.styleable.MesaSeekBarPreference_units);
        if (mUnits == null)
            mUnits = "";

        mUpdatesContinuously = a.getBoolean(R.styleable.SeslSeekBarPreference_updatesContinuously, false);
        a.recycle();
    }

    @Override
    public void onBindViewHolder(PreferenceViewHolder view) {
        super.onBindViewHolder(view);
        view.itemView.setOnKeyListener(mSeekBarKeyListener);
        mSeekBar = (SeslSeekBar) view.findViewById(R.id.seekbar);
        mSeekBarValueTextView = (TextView) view.findViewById(R.id.seekbar_value);
        if (mShowSeekBarValue) {
            mSeekBarValueTextView.setVisibility(View.VISIBLE);
        } else {
            mSeekBarValueTextView.setVisibility(View.GONE);
            mSeekBarValueTextView = null;
        }
        if (mSeekBar == null) {
            LogUtils.e(TAG, "SeekBar view is null in onBindViewHolder.");
            return;
        }

        mSeekBar.setOnSeekBarChangeListener(mSeekBarChangeListener);
        mSeekBar.setMode(mSeekBarMode);
        mSeekBar.setMax(mMax - mMin);
        mSeekBar.setOverlapPointForDualColor(mOverlapPoint);
        mSeekBar.setSeamless(mSeekBarSeamless);

        if (mSeekBarIncrement != 0) {
            mSeekBar.setKeyProgressIncrement(mSeekBarIncrement);
        } else {
            mSeekBarIncrement = mSeekBar.getKeyProgressIncrement();
        }
        mSeekBar.setProgress(mSeekBarValue - mMin);
        updateLabelValue(mSeekBarValue);
        mSeekBar.setEnabled(isEnabled());
    }

    @Override
    protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
        if (defaultValue == null) {
            defaultValue = 0;
        }
        setValue(getPersistedInt((Integer) defaultValue));
    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        return a.getInt(index, 0);
    }

    public int getMin() {
        return mMin;
    }

    public void setMin(int min) {
        if (min > mMax) {
            min = mMax;
        }
        if (min != mMin) {
            mMin = min;
            notifyChanged();
        }
    }

    public final int getSeekBarIncrement() {
        return mSeekBarIncrement;
    }

    public final void setSeekBarIncrement(int seekBarIncrement) {
        if (seekBarIncrement != mSeekBarIncrement) {
            mSeekBarIncrement = Math.min(mMax - mMin, Math.abs(seekBarIncrement));
            notifyChanged();
        }
    }

    public int getMax() {
        return mMax;
    }

    public final void setMax(int max) {
        if (max < mMin) {
            max = mMin;
        }
        if (max != mMax) {
            mMax = max;
            notifyChanged();
        }
    }

    public boolean isAdjustable() {
        return mAdjustable;
    }

    public void setAdjustable(boolean adjustable) {
        mAdjustable = adjustable;
    }

    public boolean getUpdatesContinuously() {
        return mUpdatesContinuously;
    }

    public void setUpdatesContinuously(boolean updatesContinuously) {
        mUpdatesContinuously = updatesContinuously;
    }

    public boolean getShowSeekBarValue() {
        return mShowSeekBarValue;
    }

    public void setShowSeekBarValue(boolean showSeekBarValue) {
        mShowSeekBarValue = showSeekBarValue;
        notifyChanged();
    }

    private void setValueInternal(int seekBarValue, boolean notifyChanged) {
        if (seekBarValue < mMin) {
            seekBarValue = mMin;
        }
        if (seekBarValue > mMax) {
            seekBarValue = mMax;
        }
        if (seekBarValue != mSeekBarValue) {
            mSeekBarValue = seekBarValue;
            updateLabelValue(mSeekBarValue);
            persistInt(seekBarValue);
            if (notifyChanged) {
                notifyChanged();
            }
        }
    }

    public int getValue() {
        return mSeekBarValue;
    }

    public void setValue(int seekBarValue) {
        setValueInternal(seekBarValue, true);
    }

    @SuppressWarnings("WeakerAccess")
    void syncValueInternal(SeslSeekBar seekBar) {
        int seekBarValue = mMin + seekBar.getProgress();
        if (seekBarValue != mSeekBarValue) {
            if (callChangeListener(seekBarValue)) {
                setValueInternal(seekBarValue, false);
            } else {
                seekBar.setProgress(mSeekBarValue - mMin);
                updateLabelValue(mSeekBarValue);
            }
        }
    }

    @SuppressWarnings("WeakerAccess")
    void updateLabelValue(int value) {
        if (mSeekBarValueTextView != null) {
            mSeekBarValueTextView.setText(mUnits.isEmpty() ? String.valueOf(value) : value + " " + mUnits);
        }
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        final Parcelable superState = super.onSaveInstanceState();
        if (isPersistent()) {
            return superState;
        }

        final SavedState myState = new SavedState(superState);
        myState.mSeekBarValue = mSeekBarValue;
        myState.mMin = mMin;
        myState.mMax = mMax;
        return myState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (!state.getClass().equals(SavedState.class)) {
            super.onRestoreInstanceState(state);
            return;
        }

        SavedState myState = (SavedState) state;
        super.onRestoreInstanceState(myState.getSuperState());
        mSeekBarValue = myState.mSeekBarValue;
        mMin = myState.mMin;
        mMax = myState.mMax;
        notifyChanged();
    }


    private static class SavedState extends BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR =
                new Parcelable.Creator<SavedState>() {
                    @Override
                    public SavedState createFromParcel(Parcel in) {
                        return new SavedState(in);
                    }
                    @Override
                    public SavedState[] newArray(int size) {
                        return new SavedState[size];
                    }
                };
        int mSeekBarValue;
        int mMin;
        int mMax;
        SavedState(Parcel source) {
            super(source);
            mSeekBarValue = source.readInt();
            mMin = source.readInt();
            mMax = source.readInt();
        }

        SavedState(Parcelable superState) {
            super(superState);
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(mSeekBarValue);
            dest.writeInt(mMin);
            dest.writeInt(mMax);
        }
    }
}