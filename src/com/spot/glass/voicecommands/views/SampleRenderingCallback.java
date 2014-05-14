package com.spot.glass.voicecommands.views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;

import com.google.android.glass.timeline.DirectRenderingCallback;

public class SampleRenderingCallback implements DirectRenderingCallback {
	private static final String TAG = "GlassUnityLiveCardRenderer";

	// Our MainView
	private final SampleMainView mMainView;

	// interface to the display surface
	private SurfaceHolder mHolder;

	public SampleRenderingCallback(Context context) {
		mMainView = new SampleMainView(context);

		mMainView.setListener(new SampleMainView.ChangeListener() {

			@Override
			public void onChange() {
				Log.w("GlassVoiceSample", "Drawing the LiveCard content.");
				draw(mMainView);
			}
		});

		Log.w("GlassVoiceSample", "LiveCard callback initialized.");
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		// Update your views accordingly.
		// This is called immediately after any structural changes (format or
		// size) have been made to the surface.

		Log.w("GlassVoiceSample", "LiveCard callback is handling a change on the surface.");

		int measuredWidth = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
		int measuredHeight = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);

		mMainView.measure(measuredWidth, measuredHeight);
		mMainView.layout(0, 0, mMainView.getMeasuredWidth(), mMainView.getMeasuredHeight());
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Log.w("GlassVoiceSample", "LiveCard callback notifies that the Surface has been created.");

		mHolder = holder;
		mMainView.start();
		mMainView.setForceStart(true);

		Log.w("GlassVoiceSample", "The SampleMainView started.");
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.d(TAG, "Surface destroyed");

		// Stops the viewThread
		mMainView.stop();
		mHolder = null;
	}

	@Override
	public void renderingPaused(SurfaceHolder holder, boolean paused) {
		if (paused) {
			mMainView.stop();
			Log.w("GlassVoiceSample", "LiveCard callback stops rendering.");
		} else {
			mMainView.start();
			Log.w("GlassVoiceSample", "LiveCard callback starts rendering.");
		}
	}

	/**
	 * Draws the view in the SurfaceHolder's canvas.
	 */
	private void draw(View view) {
		Canvas canvas;
		try {
			canvas = mHolder.lockCanvas();
		} catch (Exception e) {
			return;
		}

		if (canvas != null) {
			view.draw(canvas);
			Log.w("GlassVoiceSample", "Drawing!!!.");
			mHolder.unlockCanvasAndPost(canvas);
		}
	}
}
