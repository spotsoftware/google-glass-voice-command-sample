package com.spot.glass.voicecommands.services;

import com.google.android.glass.timeline.LiveCard;
import com.google.android.glass.timeline.LiveCard.PublishMode;
import com.spot.glass.voicecommands.activities.MenuActivity;
import com.spot.glass.voicecommands.views.SampleRenderingCallback;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class LiveCardService extends Service {

	private static final String LIVE_CARD_ID = "SAMPLE_VOICE_COMMANDS_LIVE_CARD";

	private SampleRenderingCallback mCallback;
	private LiveCard mLiveCard;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		if (mLiveCard == null) {
			mLiveCard = new LiveCard(this, LIVE_CARD_ID);

			mCallback = new SampleRenderingCallback(this);
			mLiveCard.setDirectRenderingEnabled(true).getSurfaceHolder().addCallback(mCallback);

			Intent menuIntent = new Intent(this, MenuActivity.class);
			mLiveCard.setAction(PendingIntent.getActivity(this, 0, menuIntent, 0));

			mLiveCard.attach(this);
			mLiveCard.publish(PublishMode.REVEAL);
		} else {
			mLiveCard.navigate();
		}

		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		if (mLiveCard != null && mLiveCard.isPublished()) {
			if (mCallback != null) {
				mLiveCard.getSurfaceHolder().removeCallback(mCallback);
			}
			mLiveCard.unpublish();
			mLiveCard = null;
		}

		super.onDestroy();
	}

}
