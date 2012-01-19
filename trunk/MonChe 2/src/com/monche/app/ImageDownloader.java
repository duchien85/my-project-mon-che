/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.monche.app;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.monche.cache.ExternalCache;
import com.monche.logic.PhotoLibs;
import com.monche.logic.Util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;

public class ImageDownloader {
	
	public class ImageId{
		String id;
		String url;
		public ImageId(String x, String y){
			id = x;
			url = y;
		}
	}
	
	
    private static final String LOG_TAG = "ImageDownloader";
    public enum Mode { NO_ASYNC_TASK, NO_DOWNLOADED_DRAWABLE, CORRECT }
    private Mode mode = Mode.CORRECT;
    public com.monche.app.ViewPhotoActivity view;
    
    public ExternalCache cache = new ExternalCache();
    
    
    public void download(String id, ImageView imageView) {
        //resetPurgeTimer();
    	String url = PhotoLibs.getInstances().getImageUrl(id);
    	ImageId photo = new ImageId(id, url);
    	
        Bitmap bitmap = getBitmapFromCache(id);
        view.onBeginLoad();
        if (bitmap == null) {
            forceDownload(photo, imageView);
        } else {
            cancelPotentialDownload(url, imageView);
            imageView.setImageBitmap(bitmap);
            view.onLoaded();
            imageView.setAdjustViewBounds(false);
        }
    }

    private void forceDownload(ImageId photo, ImageView imageView) {
    	String url = photo.url;
        if (url == null) {
            imageView.setImageDrawable(null);
            return;
        }
        if (cancelPotentialDownload(url, imageView)) {
            switch (mode) {
                case NO_ASYNC_TASK:
                    Bitmap bitmap = downloadBitmap(url);
                    addBitmapToCache(url, bitmap);
                    imageView.setImageBitmap(bitmap);
                    break;

                case NO_DOWNLOADED_DRAWABLE:
                    imageView.setMinimumHeight(156);
                    BitmapDownloaderTask task = new BitmapDownloaderTask(imageView);
                    task.execute(photo);
                    break;

                case CORRECT:
                    task = new BitmapDownloaderTask(imageView);
                    DownloadedDrawable downloadedDrawable = new DownloadedDrawable(task);
                    imageView.setImageDrawable(downloadedDrawable);
                    imageView.setMinimumHeight(156);
                    task.execute(photo);
                    break;
            }
        }
    }

    private static boolean cancelPotentialDownload(String url, ImageView imageView) {
        BitmapDownloaderTask bitmapDownloaderTask = getBitmapDownloaderTask(imageView);

        if (bitmapDownloaderTask != null) {
            String bitmapUrl = bitmapDownloaderTask.url;
            if ((bitmapUrl == null) || (!bitmapUrl.equals(url))) {
                bitmapDownloaderTask.cancel(true);
            } else {
                // The same URL is already being downloaded.
                return false;
            }
        }
        return true;
    }

    private static BitmapDownloaderTask getBitmapDownloaderTask(ImageView imageView) {
        if (imageView != null) {
            Drawable drawable = imageView.getDrawable();
            if (drawable instanceof DownloadedDrawable) {
                DownloadedDrawable downloadedDrawable = (DownloadedDrawable)drawable;
                return downloadedDrawable.getBitmapDownloaderTask();
            }
        }
        return null;
    }

    Bitmap downloadBitmap(String url) {
        final int IO_BUFFER_SIZE = 4 * 1024;

        // AndroidHttpClient is not allowed to be used from the main thread
        final HttpClient client = (mode == Mode.NO_ASYNC_TASK) ? new DefaultHttpClient() :
            AndroidHttpClient.newInstance("Android");
        final HttpGet getRequest = new HttpGet(url);

        try {
            HttpResponse response = client.execute(getRequest);
            final int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                Log.w("ImageDownloader", "Error " + statusCode +
                        " while retrieving bitmap from " + url);
                return null;
            }

            final HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream inputStream = null;
                try {
                    inputStream = entity.getContent();
                    // return BitmapFactory.decodeStream(inputStream);
                    // Bug on slow connections, fixed in future release.
                    return BitmapFactory.decodeStream(new FlushedInputStream(inputStream));
                } finally {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    entity.consumeContent();
                }
            }
        } catch (IOException e) {
            getRequest.abort();
            Log.w(LOG_TAG, "I/O error while retrieving bitmap from " + url, e);
        } catch (IllegalStateException e) {
            getRequest.abort();
            Log.w(LOG_TAG, "Incorrect URL: " + url);
        } catch (Exception e) {
            getRequest.abort();
            Log.w(LOG_TAG, "Error while retrieving bitmap from " + url, e);
        } finally {
            if ((client instanceof AndroidHttpClient)) {
                ((AndroidHttpClient) client).close();
            }
        }
        return null;
    }

    /*
     * An InputStream that skips the exact number of bytes provided, unless it reaches EOF.
     */
    static class FlushedInputStream extends FilterInputStream {
        public FlushedInputStream(InputStream inputStream) {
            super(inputStream);
        }

        @Override
        public long skip(long n) throws IOException {
            long totalBytesSkipped = 0L;
            while (totalBytesSkipped < n) {
                long bytesSkipped = in.skip(n - totalBytesSkipped);
                if (bytesSkipped == 0L) {
                    int b = read();
                    if (b < 0) {
                        break;  // we reached EOF
                    } else {
                        bytesSkipped = 1; // we read one byte
                    }
                }
                totalBytesSkipped += bytesSkipped;
            }
            return totalBytesSkipped;
        }
    }

    /**
     * The actual AsyncTask that will asynchronously download the image.
     */
    class BitmapDownloaderTask extends AsyncTask<ImageId, Void, Bitmap> {
        private String url;
        private String id;
        private final WeakReference<ImageView> imageViewReference;

        public BitmapDownloaderTask(ImageView imageView) {
            imageViewReference = new WeakReference<ImageView>(imageView);
        }

        @Override
        protected Bitmap doInBackground(ImageId ... params) {
            url = params[0].url;
            id  = params[0].id;
            return downloadBitmap(url);
        }
        
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (isCancelled()) {
                bitmap = null;
            }

            addBitmapToCache(id, bitmap);

            if (imageViewReference != null) {
                ImageView imageView = imageViewReference.get();
                BitmapDownloaderTask bitmapDownloaderTask = getBitmapDownloaderTask(imageView);
                // Change bitmap only if this process is still associated with it
                // Or if we don't use any bitmap to task association (NO_DOWNLOADED_DRAWABLE mode)
                if ((this == bitmapDownloaderTask) || (mode != Mode.CORRECT)) {
                    imageView.setImageBitmap(bitmap);
                    view.onLoaded();
                }
            }
        }
    }


    class LoadToCacheTask extends AsyncTask<ImageId, Void, Bitmap> {
        private String url;
        private String id;

        public LoadToCacheTask() {
        }

        @Override
        protected Bitmap doInBackground(ImageId ... params) {
            url = params[0].url;
            id  = params[0].id;
            return downloadBitmap(url);
        }
        
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            addBitmapToCache(id, bitmap);
        }
    }
   
    static class DownloadedDrawable extends ColorDrawable {
        private final WeakReference<BitmapDownloaderTask> bitmapDownloaderTaskReference;

        public DownloadedDrawable(BitmapDownloaderTask bitmapDownloaderTask) {
            super(Color.BLACK);
            bitmapDownloaderTaskReference =
                new WeakReference<BitmapDownloaderTask>(bitmapDownloaderTask);
        }

        public BitmapDownloaderTask getBitmapDownloaderTask() {
            return bitmapDownloaderTaskReference.get();
        }
    }

    public void setMode(Mode mode) {
        this.mode = mode;
        //clearCache();
    }
   
    public void loadFutureCache(String id){
    	if (cache.havePhoto(id)) return;
    	ImageId p = new ImageId(id, PhotoLibs.getInstances().getImageUrl(id));
    	LoadToCacheTask task = new LoadToCacheTask();
        task.execute(p);
    }
    
    private void addBitmapToCache(String id, Bitmap bitmap) {
       cache.saveToCache(id, bitmap);
    }

   
    private Bitmap getBitmapFromCache(String id) {
        return cache.getFromCache(id);
    }

	public void setView(com.monche.app.ViewPhotoActivity v) {
		view = v;
		cache.context = v.getApplicationContext();
	}
 
}
