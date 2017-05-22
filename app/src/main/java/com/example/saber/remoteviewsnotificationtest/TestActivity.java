package com.example.saber.remoteviewsnotificationtest;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

public class TestActivity extends AppCompatActivity {

    private Button btnSendnormal;
    private Button btnSendCustom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        btnSendnormal = (Button) findViewById(R.id.btn_normal);
        btnSendCustom = (Button) findViewById(R.id.btn_custom);

        //发送普通通知
        btnSendnormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //通知由NotificationManager管理
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                PendingIntent pendingIntent = PendingIntent.getActivity
                        (TestActivity.this,0,new Intent(TestActivity.this,DemoActivity_1.class),PendingIntent.FLAG_UPDATE_CURRENT);

                NotificationCompat.Builder builder = new NotificationCompat.Builder(TestActivity.this);
                builder.setContentTitle("This is noraml notification title");
                builder.setContentText("This is content text");
                builder.setSmallIcon(R.mipmap.ic_launcher);
                builder.setWhen(System.currentTimeMillis());
                builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher));
                builder.setAutoCancel(true);//点击后自动消失
                builder.setContentIntent(pendingIntent);

                Notification notification = builder.build();

                notificationManager.notify(1,notification);
            }
        });


        //发送自定义通知,通过RemoteViews加载布局
        btnSendCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NotificationCompat.Builder builder = new NotificationCompat.Builder(TestActivity.this);
                builder.setSmallIcon(R.mipmap.ic_launcher);
                builder.setTicker("hello world");
                builder.setWhen(System.currentTimeMillis());
                builder.setAutoCancel(true);

                //点整体还是回到DemoActivity_1
                PendingIntent pendingIntent = PendingIntent.getActivity
                        (TestActivity.this,0,new Intent(TestActivity.this,DemoActivity_1.class),PendingIntent.FLAG_UPDATE_CURRENT);

                RemoteViews remoteViews = new RemoteViews(getPackageName(),R.layout.layout_notification);
                remoteViews.setTextViewText(R.id.msg,"chapter_5");
                remoteViews.setImageViewResource(R.id.icon,R.mipmap.ic_launcher);

                PendingIntent openActivity2PendingIntent = PendingIntent.getActivity(
                        TestActivity.this,0,new Intent(TestActivity.this,DemoActivity_2.class),PendingIntent.FLAG_UPDATE_CURRENT);
                //RemoteViews里的控件设置点击事件后回到DemoActivity_2
                remoteViews.setOnClickPendingIntent(R.id.open_activity2,openActivity2PendingIntent);

                //设置RemoteViews，点整体还是回到DemoActivity_1
                builder.setContentIntent(pendingIntent);
                //sdk23以下设置RemoteViews的方法
                builder.setContent(remoteViews);

                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                notificationManager.notify(2,builder.build());

            }
        });

    }
}
