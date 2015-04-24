package com.plineo.cordova;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

/**
 * This class gets the actual file path and name from native side to JavaScript.
 */
public class GetFilename extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("get")) {
            String uri = args.getString(0);
            this.getRealPathFromURI(uri, callbackContext);
            return true;
        }
        return false;
    }

    private void getRealPathFromURI(String uri, CallbackContext callbackContext) {
        if (uri != null && uri.length() > 0) {
            callbackContext.success(this.getFile(uri));
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }

    private String getFile(String uri) {

         /*Uri contentUri = Uri.parse(uri);
         String[] proj = { MediaStore.Images.Media.DATA };
         CursorLoader loader = new CursorLoader(cordova.getActivity(), contentUri, proj, null, null, null);
         Cursor cursor = loader.loadInBackground();
         int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
         cursor.moveToFirst();
         String fileName = cursor.getString(column_index);
         return fileName;*/
        Cursor cursor = getActivity().getContentResolver()
            .query(uri, null, null, null, null, null);
        String displayName = "";
        try {
            if (cursor != null && cursor.moveToFirst()) {
                displayName = cursor.getString(
                        cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
            }
        } finally {
            cursor.close();
        }
        return displayName;
     }
}