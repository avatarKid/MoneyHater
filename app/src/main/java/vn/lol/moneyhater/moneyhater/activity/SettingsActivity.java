package vn.lol.moneyhater.moneyhater.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.session.AppKeyPair;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import vn.lol.moneyhater.momeyhater.R;
import vn.lol.moneyhater.moneyhater.Util.ConstantValue;
import vn.lol.moneyhater.moneyhater.Util.ListFiles;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p/>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class SettingsActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener, Preference.OnPreferenceClickListener {

    private final String TAG = this.getClass().getName();
    private final Handler handler = new Handler() {
        public void handleMessage(Message message) {
            try {
                mLstFiles = message.getData().getStringArrayList("data");
                ListPreference pref = (ListPreference) findPreference("list_backup_file");
                CharSequence[] cs = mLstFiles.toArray(new CharSequence[mLstFiles.size()]);
                pref.setEntries(cs);
                pref.setEntryValues(cs);
            } catch (Exception e) {
                Log.e(TAG,"handleMessage");
                e.printStackTrace();
            }
        }
    };
    // In the class declaration section:
    private DropboxAPI<AndroidAuthSession> mDBApi;
    private ArrayList<String> mLstFiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting);
        // And later in some initialization function:
        try {
            AppKeyPair appKeyPair = new AppKeyPair(ConstantValue.DROPBOX_APP_KEY, ConstantValue.DROPBOX_APP_SECRET);

            SharedPreferences prefs = getSharedPreferences(ConstantValue.DROPBOX_SHARE_PREFERENCE, 0);
            String key = prefs.getString(ConstantValue.DROPBOX_APP_SECRET, null);
            AndroidAuthSession session;
            if (key != null) {
                session = new AndroidAuthSession(appKeyPair, key);
            } else {
                session = new AndroidAuthSession(appKeyPair);
            }
            mDBApi = new DropboxAPI(session);
            if (session.isLinked()) {
                ListFiles listFiles = new ListFiles(mDBApi, ConstantValue.DROPBOX_FILE_DIR, handler);
                listFiles.execute();
            }
        } catch (Exception e) {
            Log.e(TAG, "Create dropboxAPI exception");
            e.printStackTrace();
        }
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        try {
            final Preference pref = findPreference(key);
            switch (key) {
                case "pref_language":
                    settingLanguage();
                    break;
                case "pref_currency":
                    Intent i = new Intent();
                    setResult(ConstantValue.RESULT_COODE_CURRENCY, i);
                    finish();
                    break;
                case "pref_backup":
                    if (((CheckBoxPreference) pref).isChecked()) {
                        ((AndroidAuthSession) mDBApi.getSession())
                                .startOAuth2Authentication(SettingsActivity.this);
                    } else {
                        mDBApi.getSession().unlink();
                        SharedPreferences prefs = getSharedPreferences(ConstantValue.DROPBOX_SHARE_PREFERENCE, 0);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.remove(ConstantValue.DROPBOX_APP_KEY);
                        editor.remove(ConstantValue.DROPBOX_APP_SECRET);
                    }
                    break;
                case "list_backup_file":
                    new AlertDialog.Builder(this)
                            .setTitle("Restore database")
                            .setMessage("What do you want?")
                            .setPositiveButton("Restore", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    final String fileName = getApplicationContext().getFilesDir().getPath().toString()+((ListPreference)pref).getValue();
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                File file = new File(fileName);
                                                FileOutputStream outputStream = new FileOutputStream(file);
                                                DropboxAPI.DropboxFileInfo info = mDBApi.getFile(((ListPreference)pref).getValue(), null, outputStream, null);
                                                outputStream.close();
                                                Log.i("DbExampleLog", "The file's rev is: " + info.getMetadata().rev);
                                            } catch (FileNotFoundException e) {
                                                Log.e(TAG,"FileNotFoundException");
                                                e.printStackTrace();
                                            } catch (DropboxException e) {
                                                Log.e(TAG,"DropboxException");
                                                e.printStackTrace();
                                            } catch (IOException e) {
                                                Log.e(TAG,"IOException");
                                                e.printStackTrace();
                                            }
                                            Intent i = new Intent();
                                            i.putExtra(ConstantValue.DROPBOX_FILE,fileName);
                                            setResult(ConstantValue.RESULT_COODE_BACKUP, i);
                                            finish();
                                        }
                                    }).start();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            })
                            .setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                mDBApi.delete(((ListPreference)pref).getValue());
                                                mLstFiles.remove(((ListPreference)pref).getValue());
                                                CharSequence[] cs = mLstFiles.toArray(new CharSequence[mLstFiles.size()]);
                                                ((ListPreference)pref).setEntries(cs);
                                                ((ListPreference)pref).setEntryValues(cs);
                                            } catch (DropboxException e) {
                                                Log.e(TAG,"DELETE FILE ERROR");
                                                e.printStackTrace();
                                            }
                                        }
                                    }).start();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();


                    break;
            }
        } catch (Exception e) {
            Log.e(TAG, "onSharedPreferenceChanged");
            e.printStackTrace();
        }
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {

        switch (preference.getKey()) {
            case "list_backup_file":

                break;
        }

        return false;
    }

    protected void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
        final AndroidAuthSession session = (AndroidAuthSession) mDBApi.getSession();
        if (session.authenticationSuccessful()) {
            try {
                session.finishAuthentication();
                String tokens = session.getOAuth2AccessToken();
//                AccessTokenPair tokenPair = session.getAccessTokenPair();
                SharedPreferences prefs = getSharedPreferences(ConstantValue.DROPBOX_SHARE_PREFERENCE, 0);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(ConstantValue.DROPBOX_APP_KEY, "oauth2:");
                editor.putString(ConstantValue.DROPBOX_APP_SECRET, tokens);
                editor.commit();
            } catch (Exception e) {
                Toast.makeText(this, "Error during Dropbox auth", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void settingLanguage() {
        SharedPreferences sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this);
        int languageCode = Integer.parseInt(sharedPrefs.getString("pref_language", "1"));

        Configuration config = new Configuration();
        switch (languageCode) {
            case 1: //English
                config.locale = Locale.ENGLISH;
                break;
            case 2: // Vietnamese
                config.locale = new Locale("vi");
                break;
            default:
                break;
        }
        getApplicationContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        Intent i = new Intent();
        setResult(ConstantValue.RESULT_COODE_LANGUAGE, i);
        finish();
    }

}
