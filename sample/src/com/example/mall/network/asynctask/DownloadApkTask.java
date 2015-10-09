package com.example.mall.network.asynctask;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;

import com.example.mall.R;
import com.example.mall.constant.ConstantSettings;
import com.example.mall.notification.NotificationView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 下载apk
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: com.example.mall.network.asynctask.DownloadApkTask.java
 * @author: liqiongwei
 * @date: 2015-10-09 15:48
 */
public class DownloadApkTask extends android.os.AsyncTask<String, Integer, File> {
    private static final String TAG = DownloadApkTask.class.getSimpleName();
    private int IO_BUFFER_SIZE = 4 * 1024;

    private static Context mContext;

    public DownloadApkTask(Context context) {
        this.mContext = context;

        NotificationView.getInstance(context).show();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        showNotify(values[0], mContext.getString(R.string.download_apk_tip));
    }

    private void showNotify(int progress, String msg) {
        Notification notification = NotificationView.getInstance(mContext).mNotification;
        notification.contentView.setTextColor(R.id.tv_download_tip,
                Color.BLACK);

        notification.contentView.setTextViewText(R.id.progress_tv, progress + "%");
        notification.contentView.setProgressBar(R.id.update_progressbar,
                100, progress, false);
        if (progress >= 100) {
            notification.contentView.setTextViewText(R.id.tv_download_tip,
                    mContext.getResources().getString(R.string.download_apk_ok_tip));
        } else {
            notification.contentView
                    .setTextViewText(R.id.tv_download_tip, msg);
        }
        NotificationView.getInstance(mContext).mNotificationManager
                .notify(NotificationView.VERSION_NOTIFICATION_ID, notification);
    }

    @Override
    protected void onPostExecute(final File result) {
        super.onPostExecute(result);
        if (result == null) {
        } else { // 下载完安装
            install(mContext, result);
        }
    }

    @Override
    protected File doInBackground(String... params) {
        String url = params[0];
        String path = ConstantSettings.FILE_PATH
                + url.substring(url.lastIndexOf("/") + 1, url.length());
        return doBackground(url, path);
    }

    public File doBackground(String url, String path) {
        final HttpClient client = new DefaultHttpClient();
        final HttpGet getRequest = new HttpGet(url);
        try {
            HttpResponse response = client.execute(getRequest);
            final int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                return null;
            }

            final HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream inputStream = null;
                OutputStream outputStream = null;
                try {
                    File file = new File(
                            Environment.getExternalStorageDirectory(), path);
                    if (file.exists()) {
                        file.delete();
                    }
                    if (!file.exists()) {
                        file.createNewFile();
                    }

                    inputStream = entity.getContent();
                    long length = entity.getContentLength();
                    outputStream = new FileOutputStream(file);

                    long progress = 0;
                    int currentProgress = -1;

                    byte[] b = new byte[IO_BUFFER_SIZE];
                    int read;
                    while ((read = inputStream.read(b)) != -1) {
                        outputStream.write(b, 0, read);

                        progress += read;
                        int current = (int) (progress * 100 / length);
                        if (currentProgress != current
                                && (current % 2) == 0) {
                            publishProgress(current);
                            currentProgress = current;
                        }
                    }

                    outputStream.flush();
                    return file;

                } finally {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    if (outputStream != null) {
                        outputStream.close();
                    }
                    entity.consumeContent();
                }
            }
        } catch (Exception e) {
            getRequest.abort();
            NotificationView.getInstance(mContext).mNotificationManager
                    .cancel(NotificationView.VERSION_NOTIFICATION_ID);

            //Notification notification = NotificationView.getInstance(mContext).mNotification;
            //notification.contentView.setTextViewText(R.id.tv_download_tip,
            //       mContext.getResources().getString(R.string.download_apk_failure_tip));
            // TODO: 显示对话框即可，或者通过广播做其他操作
        } finally {
            if (client != null) {
                client.getConnectionManager().shutdown();
            }
        }
        return null;
    }

    public static String getApkVersionByPath(Context ctx, File file) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo info = pm.getPackageArchiveInfo(file.getAbsolutePath(),
                    PackageManager.GET_ACTIVITIES);
            if (info != null)
                return info.versionName;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static void install(Context context, File file) {
        NotificationView.getInstance(mContext).mNotificationManager
                .cancel(NotificationView.VERSION_NOTIFICATION_ID);

        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        context.startActivity(intent);
    }
}