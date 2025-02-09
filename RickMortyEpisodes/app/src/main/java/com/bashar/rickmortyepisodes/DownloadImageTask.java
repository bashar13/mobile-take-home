package com.bashar.rickmortyepisodes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

    ImageView imageView;
    public DownloadImageTask(ImageView image) {
        imageView = image;
    }

    protected Bitmap doInBackground(String... urls) {
        String urlStr = urls[0];
        Bitmap bmp = null;
        try {
            InputStream in = new java.net.URL(urlStr).openStream();
            bmp = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return bmp;
    }
    protected void onPostExecute(Bitmap result) {
        imageView.setImageBitmap(result);
    }
}
