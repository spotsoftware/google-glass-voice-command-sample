package com.spot.glass.voicecommands.activities;

import com.google.glass.input.VoiceInputHelper;
import com.google.glass.input.VoiceListener;
import com.google.glass.input.VoiceInputHelper.VoiceCommandObserver;
import com.google.glass.voice.VoiceConfig;
import com.google.glass.voice.VoiceConfig.LetterToSoundModel;
import com.spot.glass.voicecommands.R;
import com.spot.glass.voicecommands.listeners.OnVoiceCommandReceivedListener;
import com.spot.glass.voicecommands.listeners.SampleVoiceListener;
import com.spot.glass.voicecommands.services.LiveCardService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MenuActivity extends Activity {

	private static final String LOGTAG = "MenuActivity";

	private boolean mIsAttached = false;

	private boolean mVoiceListenerActive = false;
	private VoiceInputHelper mVoiceInputHelper;
	private VoiceConfig mVoiceConfig;

	// { Activity methods overriding

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.menu.menu);

		final String closeMenuCommand = "close menu";
		final String exitAppCommand = "exit sample";

		this.mVoiceConfig = new VoiceConfig("MyVoiceConfig", new String[] { closeMenuCommand, exitAppCommand });
		this.mVoiceConfig.setSensitivity(VoiceConfig.Sensitivity.NORMAL);
		this.mVoiceConfig.setLetterToSoundModel(LetterToSoundModel.GENERIC);

		final VoiceCommandObserver obs = VoiceInputHelper.newUserActivityObserver(this);
		final VoiceListener voiceListener = SampleVoiceListener.create(
			this.mVoiceConfig, new OnVoiceCommandReceivedListener() {

				@Override
				public void onCommandReceived(final String command) {

					Log.e(LOGTAG, "Received command: " + command);

					boolean valid = true;

					if (command.equals(closeMenuCommand)) {
						closeMenu();
					} else if (command.equals(exitAppCommand)) {
						exitSample();
					} else {
						valid = false;
					}

					if (valid) {
						finish();
					}
				}
			}, this.mVoiceInputHelper);
		this.mVoiceInputHelper = new VoiceInputHelper(this, voiceListener, obs);
	}

	@Override
	public void onResume() {
		super.onResume();
		if (this.mIsAttached) {
			this.openOptionsMenu();
		}
		if (!mVoiceListenerActive) {
			Log.e(LOGTAG, "Adding voice service listener");
			mVoiceInputHelper.addVoiceServiceListener();
			mVoiceListenerActive = true;
		}
	}

	@Override
	public void onAttachedToWindow() {
		super.onAttachedToWindow();
		this.mIsAttached = true;
		this.openOptionsMenu();
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		final MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		switch (item.getItemId()) {
			case R.id.close:
				this.closeMenu();
				return true;
			case R.id.exit:
				this.exitSample();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onOptionsMenuClosed(final Menu menu) {
		Log.e(this.getClass().getName(), "Removing voice service listener");
		if (mVoiceListenerActive) {
			mVoiceInputHelper.removeVoiceServiceListener();
		}
		super.onOptionsMenuClosed(menu);
	}

	// }

	// { Private and protected methods

	private void exitSample() {
		this.stopService(new Intent(this, LiveCardService.class));
		this.closeMenu();
	}

	private void closeMenu() {
		this.finish();
	}

	// }

}
