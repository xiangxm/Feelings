package cz.destil.feelings.ui;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import cz.destil.feelings.R;
import cz.destil.feelings.notify.AlarmSetter;

public class SettingsActivity extends PreferenceActivity {

	protected static final String TAG = "SettingsActivity";
	private static String KEY_NOTIFICATION_TIME = "notification_time";

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		addPreferencesFromResource(R.xml.activity_preferences);
		final Preference notificationPreference = findPreference(KEY_NOTIFICATION_TIME);
		notificationPreference.setDefaultValue(getDefaultNotificationTime());
		notificationPreference.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {

			@Override
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				sendBroadcast(new Intent(SettingsActivity.this, AlarmSetter.class));
				return true;
			}
		});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// app icon in action bar clicked; go home
			Intent intent = new Intent(this, MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * Returns notification hour from preferences
	 */
	public static int getNotificationHour(Context context) {
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
		Calendar calendar = new GregorianCalendar();
		calendar.setTimeInMillis(sp.getLong(KEY_NOTIFICATION_TIME, getDefaultNotificationTime()));
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * Returns notification minute from preferences
	 */
	public static int getNotificationMinute(Context context) {
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
		Calendar calendar = new GregorianCalendar();
		calendar.setTimeInMillis(sp.getLong(KEY_NOTIFICATION_TIME, getDefaultNotificationTime()));
		return calendar.get(Calendar.MINUTE);
	}

	/**
	 * @return Default notification time in millis
	 */
	private static long getDefaultNotificationTime() {
		Calendar calendar = new GregorianCalendar();
		// 06:00
		calendar.set(Calendar.HOUR_OF_DAY, 6);
		calendar.set(Calendar.MINUTE, 0);
		return calendar.getTimeInMillis();
	}

}
