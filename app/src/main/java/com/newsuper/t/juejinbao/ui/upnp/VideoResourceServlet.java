
package com.newsuper.t.juejinbao.ui.upnp;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.util.resource.FileResource;
import org.eclipse.jetty.util.resource.Resource;

import java.io.File;

public class VideoResourceServlet extends DefaultServlet {

    @Override
    public Resource getResource(String pathInContext) {
        Resource resource = null;

        Log.i(VideoResourceServlet.class.getSimpleName(), "Path:" + pathInContext);
        try {
            String id = Utils.parseResourceId(pathInContext);
            Log.i(VideoResourceServlet.class.getSimpleName(), "Id:" + id);

            Uri uri = ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, Long.parseLong(id));
            Cursor cursor = Application.getApplication().getContentResolver().query(
                    uri, null, null, null, null);
            cursor.moveToFirst();
            String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
            File file = new File(path);
            if (file.exists()) {
                resource = FileResource.newResource(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resource;
    }
}
