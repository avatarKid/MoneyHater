package vn.lol.moneyhater.moneyhater.Util;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.exception.DropboxException;

import java.util.ArrayList;

/**
 * Created by huy on 8/5/2015.
 */
public class ListFiles extends AsyncTask<Void, Void, ArrayList> {

    private DropboxAPI dropboxApi;
    private String path;
    private Handler handler;

    public ListFiles(DropboxAPI dropboxApi, String path, Handler handler) {
        super();
        this.dropboxApi = dropboxApi;
        this.path = path;
        this.handler = handler;
    }

    @Override
    protected ArrayList doInBackground(Void... params) {

        ArrayList files = new ArrayList();

        try {
            DropboxAPI.Entry directory = dropboxApi.metadata("/", 1000, null, true, null);
            for(DropboxAPI.Entry entry : directory.contents) {
                files.add(entry.fileName());
            }
        } catch (DropboxException e) {
        }

        return files;
    }

    @Override
    protected void onPostExecute(ArrayList result) {
        Message message = handler.obtainMessage();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("data", result);
        message.setData(bundle);
        handler.sendMessage(message);
    }
}