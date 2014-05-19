package com.spot.glass.voicecommands.listeners;

import android.annotation.SuppressLint;
import android.util.Log;

import com.google.glass.input.VoiceInputHelper;
import com.google.glass.input.VoiceListener;
import com.google.glass.logging.FormattingLogger;
import com.google.glass.logging.FormattingLoggers;
import com.google.glass.voice.VoiceCommand;
import com.google.glass.voice.VoiceConfig;

/**
 * Custom listener for voice recognition service.
 */
public class SampleVoiceListener implements VoiceListener {

	protected final VoiceConfig mVoiceConfig;
	protected final VoiceInputHelper mVoiceHelper;
	protected final OnVoiceCommandReceivedListener mReceiver;

	// { Construction

	protected SampleVoiceListener(final VoiceConfig voiceConfig, final OnVoiceCommandReceivedListener receiver,
			final VoiceInputHelper voiceHelper) {
		this.mVoiceConfig = voiceConfig;
		this.mReceiver = receiver;
		this.mVoiceHelper = voiceHelper;
	}

	public static VoiceListener create(final VoiceConfig voiceConfig, final OnVoiceCommandReceivedListener receiver,
			final VoiceInputHelper voiceHelper) {
		return new SampleVoiceListener(voiceConfig, receiver, voiceHelper);
	}

	// }

	// { VoiceListener implementation

	@Override
	public void onVoiceServiceConnected() {
		mVoiceHelper.setVoiceConfig(mVoiceConfig, false);
		Log.e("VOICE", "Connected");
	}

	@Override
	public void onVoiceServiceDisconnected() {
		Log.e("VOICE", "Disconnected");
	}

	@SuppressLint("DefaultLocale")
	@Override
	public VoiceConfig onVoiceCommand(VoiceCommand vc) {
		String recognizedStr = vc.getLiteral().toLowerCase();
		Log.e("VOICE", "Recognized text: " + recognizedStr);

		mReceiver.onCommandReceived(recognizedStr);

		return null;
	}

	@Override
	public FormattingLogger getLogger() {
		return FormattingLoggers.getContextLogger();
	}

	@Override
	public boolean isRunning() {
		return true;
	}

	@Override
	public boolean onResampledAudioData(byte[] arg0, int arg1, int arg2) {
		Log.e(this.getClass().getName(), "Audio data resampled");
		return false;
	}

	@Override
	public boolean onVoiceAmplitudeChanged(double arg0) {
		Log.e(this.getClass().getName(), "Voice amplitude changed");
		return false;
	}

	@Override
	public void onVoiceConfigChanged(VoiceConfig arg0, boolean arg1) {
		Log.e(this.getClass().getName(), "Voice configuration changed");
	}

	// }

}
