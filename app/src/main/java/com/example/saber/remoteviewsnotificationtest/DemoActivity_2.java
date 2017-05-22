package com.example.saber.remoteviewsnotificationtest;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.Toast;

public class DemoActivity_2 extends AppCompatActivity {

    private Button btnSendMsg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_2);

        btnSendMsg = (Button) findViewById(R.id.btn_send_message);

        //传递RemoteViews给需要的界面
        btnSendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RemoteViews remoteViews = new RemoteViews(getPackageName(),R.layout.layout_simulated_notification);
                remoteViews.setTextViewText(R.id.msg,"msg from process:"+ Process.myPid());

                remoteViews.setImageViewResource(R.id.icon,R.mipmap.ic_launcher);

                PendingIntent pendingIntent = PendingIntent.getActivity(
                        DemoActivity_2.this,0,new Intent(DemoActivity_2.this,DemoActivity_1.class),PendingIntent.FLAG_UPDATE_CURRENT);
                PendingIntent openActivity2PendingIntent = PendingIntent.getActivity(
                        DemoActivity_2.this,0,new Intent(DemoActivity_2.this,DemoActivity_2.class),PendingIntent.FLAG_UPDATE_CURRENT);

                remoteViews.setOnClickPendingIntent(R.id.item_holder,pendingIntent);
                remoteViews.setOnClickPendingIntent(R.id.open_activity2,openActivity2PendingIntent);

                //发送广播，传递RemoteViews对象，更新远程界面
                Intent intent = new Intent("REMOTE_ACTION");
                intent.putExtra("remote_views",remoteViews);
                sendBroadcast(intent);

            }
        });



        Toast.makeText(this, "This is DemoActivity_2", Toast.LENGTH_SHORT).show();
    }
}
