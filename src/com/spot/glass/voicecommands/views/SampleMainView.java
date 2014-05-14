/*
 * Copyright (C) 2013 The Android Open Source Project
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package com.spot.glass.voicecommands.views;

import com.spot.glass.voicecommands.R;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

/**
 * View used to display the Unity Infos.
 * 
 * This code is greatly inspired by the Android's Chronometer widget.
 */
public class SampleMainView extends FrameLayout {

	/**
	 * Interface to listen for changes on the view layout.
	 */
	public interface ChangeListener {
		public void onChange();
	}

	private static final int DELAY_MILLIS = 1000;

	private boolean mStarted;
	private boolean mVisible;
	private boolean mForceStart;
	private boolean mRunning;

	private ChangeListener mChangeListener;

	public SampleMainView(Context context) {
		this(context, null, 0);
	}

	public SampleMainView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SampleMainView(Context context, AttributeSet attrs, int style) {
		super(context, attrs, style);
		LayoutInflater.from(context).inflate(R.layout.main_layout, this);
		Log.w("GlassVoiceSample", "SampleMainView has been initialized.");
	}

	/**
	 * Set a {@link ChangeListener}.
	 */
	public void setListener(ChangeListener listener) {
		mChangeListener = listener;
		Log.w("GlassVoiceSample", "SampleMainView's listener has been set.");
	}

	/**
	 * Set whether or not to force the start of the chronometer when a window
	 * has not been attached to the view.
	 */
	public void setForceStart(boolean forceStart) {
		mForceStart = forceStart;
		updateRunning();
	}

	/**
	 * Start the chronometer.
	 */
	public void start() {
		Log.w("GlassVoiceSample", "SampleMainView started!");
		mStarted = true;
		updateRunning();
	}

	/**
	 * Stop the chronometer.
	 */
	public void stop() {
		Log.w("GlassVoiceSample", "SampleMainView stopped!");
		mStarted = false;
		updateRunning();
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		Log.w("GlassVoiceSample", "SampleMainView has been detached from window.");
		mVisible = false;
		updateRunning();
	}

	@Override
	protected void onWindowVisibilityChanged(int visibility) {
		super.onWindowVisibilityChanged(visibility);
		Log.w("GlassVoiceSample", "SampleMainView visibility has changed.");
		mVisible = (visibility == VISIBLE);
		updateRunning();
	}

	private final Handler mHandler = new Handler();

	private final Runnable mUpdateTextRunnable = new Runnable() {
		@Override
		public void run() {
			if (mRunning) {
				Log.w("GlassVoiceSample", "The Runnable is updating text.");
				updateText();
				mHandler.postDelayed(mUpdateTextRunnable, DELAY_MILLIS);
			}
		}
	};

	/**
	 * Update the running state.
	 */
	private void updateRunning() {
		if (mVisible) {
			Log.e("GlassVoiceSample", "Variable mVisible is TRUE!");
		}
		if (mStarted) {
			Log.e("GlassVoiceSample", "Variable mStarted is TRUE!");
		}

		boolean running = (mVisible || mForceStart) && mStarted;
		if (running != mRunning) {
			if (running) {
				Log.e("GlassVoiceSample", "SampleMainView - Post to the handler");
				mHandler.post(mUpdateTextRunnable);
			} else {
				Log.e("GlassVoiceSample", "SampleMainView - removeCallbacks through the handler");
				mHandler.removeCallbacks(mUpdateTextRunnable);
			}
			mRunning = running;
		}
	}

	/**
	 * Updates the view with the current state of the model.
	 */
	private void updateText() {

		// do stuff
		Log.w("GlassVoiceSample", "Updating text content.");

		if (this.mChangeListener != null) {
			this.mChangeListener.onChange();
		}
	}

}
