package com.spot.glass.voicecommands.views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;

import com.google.android.glass.timeline.DirectRenderingCallback;

;

public class SampleRenderingCallback implements DirectRenderingCallback {
	private static final String TAG = "GlassUnityLiveCardRenderer";

	// Our MainView
	private final SampleMainView mMainView;

	// interface to the display surface
	private SurfaceHolder mHolder;

	public SampleRenderingCallback(Context context) {
		mMainView = new SampleMainView(context);

		// Subscribing to view update
		mMainView.setListener(new SampleMainView.ChangeListener() {

			@Override
			public void onChange() {
				draw(mMainView);
			}
		});
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		// Update your views accordingly.
		// This is called immediately after any structural changes (format or
		// size) have been made to the surface.

		int measuredWidth = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
		int measuredHeight = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);

		mMainView.measure(measuredWidth, measuredHeight);
		mMainView.layout(0, 0, mMainView.getMeasuredWidth(), mMainView.getMeasuredHeight());
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		mHolder = holder;
		mMainView.start();
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
		} else {
			mMainView.start();
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
			mHolder.unlockCanvasAndPost(canvas);
		}
	}
}
