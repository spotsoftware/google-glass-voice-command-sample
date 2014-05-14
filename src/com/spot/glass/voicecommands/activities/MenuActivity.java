package com.spot.glass.voicecommands.activities;

import com.spot.glass.voicecommands.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MenuActivity extends Activity {

	private boolean mIsAttached = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.menu.menu);
	}

	@Override
	public void onResume() {
		super.onResume();
		if (this.mIsAttached) {
			openOptionsMenu();
		}
	}

	@Override
	public void onAttachedToWindow() {
		super.onAttachedToWindow();
		this.mIsAttached = true;
		openOptionsMenu();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onOptionsMenuClosed(Menu menu) {
		finish();
	}
}
