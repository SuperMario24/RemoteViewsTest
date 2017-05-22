package com.example.saber.remoteviewsnotificationtest;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.widget.RemoteViews;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        RemoteViews remoteViews = new RemoteViews(getPackageName(),R.layout.layout_simulated_notification);
        remoteViews.setTextViewText(R.id.msg,"msg from process:"+ Process.myPid());
        remoteViews.setImageViewResource(R.id.icon,R.mipmap.ic_launcher);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0,new Intent(this,ThirdActivity.class),PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent openActivity2PendingIntent = PendingIntent.getActivity(this,
                0,new Intent(this,SecondActivity.class),PendingIntent.FLAG_UPDATE_CURRENT);

        remoteViews.setOnClickPendingIntent(R.id.item_holder,pendingIntent);
        remoteViews.setOnClickPendingIntent(R.id.open_activity2,openActivity2PendingIntent);

        Intent intent = new Intent("REMOTE_ACTION");
        intent.putExtra("remote_views",remoteViews);
        sendBroadcast(intent);
    }
}
