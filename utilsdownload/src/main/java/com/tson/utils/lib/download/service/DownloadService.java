package com.tson.utils.lib.download.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import com.liulishuo.filedownloader.FileDownloader;
import com.tson.utils.lib.download.DownloadViewModel;
import com.tson.utils.lib.download.Downloader;
import com.tson.utils.lib.download.R;

/**
 * Date 2019/5/27 12:11 PM
 *
 * @author tangxin
 */
public class DownloadService extends Service {

    private DownloadViewModel viewModel;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setForegroundService();
        }

        viewModel = Downloader.Companion.of().get(DownloadViewModel.class);
        FileDownloader.getImpl().bindService(new Runnable() {
            @Override
            public void run() {
                viewModel.getConnectService().connect();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setForegroundService() {
        //设定的通知渠道名称
        String channelName = "download service";
        //设置通知的重要程度
        int importance = NotificationManager.IMPORTANCE_LOW;
        //构建通知渠道
        String channelId = "download_notification_id";
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        channel.setDescription("download service");
        //在创建的通知渠道上发送通知
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId);
        //设置通知图标
        builder.setSmallIcon(R.drawable.ic_autorenew_black_24dp)
                .setContentTitle("")
                //设置通知内容
                .setContentText("")
                //用户触摸时，自动关闭
                .setAutoCancel(true)
                //设置处于运行状态
                .setOngoing(false);
        //向系统注册通知渠道，注册后不能改变重要性以及其他通知行为
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
        //将服务置于启动状态 NOTIFICATION_ID指的是创建的通知的ID
        startForeground(1001, builder.build());
        notificationManager.cancelAll();
    }

}