package vn.lol.moneyhater.moneyhater.Database;

import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.session.AccessTokenPair;
import com.dropbox.client2.session.AppKeyPair;
import com.dropbox.client2.session.Session;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;

import vn.lol.moneyhater.moneyhater.Util.ConstantValue;
import vn.lol.moneyhater.moneyhater.activity.MainActivity;

/**
 * Created by huy on 8/2/2015.
 */
public class DropboxBackup {

    final static private String APP_KEY = "6xarw436ospag2t";
    final static private String APP_SECRET = "zo7stxt03zmw9my";
    final static private String DROPBOX_DIR = "moneyhater";
    final static private String DROPBOX_INFO_SHAREPREFERCE = "moneydropbox";
    private final String TAG = this.getClass().getName();
    private final Session.AccessType ACCESS_TYPE = Session.AccessType.DROPBOX;

//    public static boolean mIsAuthen=false;

    private MainActivity mMainActivity;
    // In the class declaration section:
    private DropboxAPI<AndroidAuthSession> mDBApi;

    public DropboxBackup(MainActivity mainActivity) {
        mMainActivity = mainActivity;
        try {
            AppKeyPair appKeyPair = new AppKeyPair(APP_KEY, APP_SECRET);

            SharedPreferences prefs = mMainActivity.getSharedPreferences(ConstantValue.DROPBOX_SHARE_PREFERENCE, 0);
            String secret = prefs.getString(ConstantValue.DROPBOX_APP_SECRET, null);
            AndroidAuthSession session;
            if (secret != null) {
                session = new AndroidAuthSession(appKeyPair, secret);
                session.isLinked();
            } else {
                session = new AndroidAuthSession(appKeyPair);
            }

            mDBApi = new DropboxAPI(session);
        } catch (Exception e) {
            Log.e(TAG, "Create dropboxAPI exception");
            e.printStackTrace();
        }
    }

    public void backupFile(DatabaseHelper dbHelper) {
        final AndroidAuthSession session = (AndroidAuthSession) mDBApi.getSession();
        if (session.isLinked()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        File file = new File(DatabaseHelper.DB_FILEPATH);
                        FileInputStream inputStream = new FileInputStream(file);
                        DropboxAPI.Entry response = mDBApi.putFile("/Backup " + new SimpleDateFormat("dd-MM-yyyy HHmmss").format(new Date()), inputStream,
                                file.length(), null, null);
                        Log.i(TAG, "The uploaded file's rev is: " + response.rev);

                        Log.i(TAG, session.authenticationSuccessful() + "");
                    } catch (FileNotFoundException e) {
                        Log.e(TAG, "backupFile FileNotFoundException");
                        e.printStackTrace();
                    } catch (DropboxException e) {
                        Log.e(TAG, "backupFile DropboxException");
                        e.printStackTrace();
                    }
                }
            }).start();
        } else {
            session.startOAuth2Authentication(mMainActivity);
        }
        Log.i(TAG, session.authenticationSuccessful() + "");
    }

    public void resumeOK() {
        try {
            final AndroidAuthSession session = (AndroidAuthSession) mDBApi.getSession();
            if (session.authenticationSuccessful()) {
                session.finishAuthentication();
                String tokenPair = session.getOAuth2AccessToken();
                SharedPreferences prefs = mMainActivity.getSharedPreferences(ConstantValue.DROPBOX_SHARE_PREFERENCE, 0);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(ConstantValue.DROPBOX_APP_SECRET, tokenPair);
                editor.commit();
            }
        } catch (Exception e) {
            Toast.makeText(mMainActivity, "mMainActivity Error during Dropbox auth", Toast.LENGTH_SHORT).show();
        }
    }
}
