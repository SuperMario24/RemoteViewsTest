package com.example.saber.remoteviewsnotificationtest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RemoteViews;

/**
 * sdk必须23及以下
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Button btn1;
    private Button btn2;

    private LinearLayout mRemoteViewsContent;

    private BroadcastReceiver mRemoteViewsReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            RemoteViews remoteViews = intent.getParcelableExtra("remote_views");
            if(remoteViews != null){
                updateUI(remoteViews);
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1 = (Button) findViewById(R.id.button1);
        btn2 = (Button) findViewById(R.id.button2);

        mRemoteViewsContent = (LinearLayout) findViewById(R.id.remote_views_content);

        //注册接收广播
        IntentFilter filter = new IntentFilter();
        filter.addAction("REMOTE_ACTION");
        registerReceiver(mRemoteViewsReceiver,filter);



        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,TestActivity.class));
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,DemoActivity_2.class));
            }
        });

    }


    private void updateUI(RemoteViews remoteViews) {
        //将RemoteViews添加到该界面，apply--加载并更新界面
       // View view = remoteViews.apply(this,mRemoteViewsContent);
        int layoutId = getResources().getIdentifier("layout_simulated_notification", "layout", getPackageName());
        View view = getLayoutInflater().inflate(layoutId, mRemoteViewsContent, false);
        remoteViews.reapply(this, view);
        mRemoteViewsContent.addView(view);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mRemoteViewsReceiver);
        super.onDestroy();
    }
}
