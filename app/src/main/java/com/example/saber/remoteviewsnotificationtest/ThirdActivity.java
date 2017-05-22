package com.example.saber.remoteviewsnotificationtest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RemoteViews;

public class ThirdActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "ThirdActivity";

    private LinearLayout mRemoteViewsContent;

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
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
        setContentView(R.layout.activity_third);

        initViews();
    }

    private void initViews() {
        mRemoteViewsContent = (LinearLayout) findViewById(R.id.remote_views_content);
        IntentFilter filter = new IntentFilter("REMOTE_ACTION");
        registerReceiver(mBroadcastReceiver,filter);
    }

    private void updateUI(RemoteViews remoteViews) {
       // View view = remoteViews.apply(this,mRemoteViewsContent);
        //不用应用加载
        int layoutId = getResources().getIdentifier("layout_simulated_notification", "layout", getPackageName());
        View view = getLayoutInflater().inflate(layoutId, mRemoteViewsContent, false);
        remoteViews.reapply(this, view);

        mRemoteViewsContent.addView(view);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mBroadcastReceiver);
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button1) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.button2) {
            Intent intent = new Intent(this, SecondActivity.class);
            startActivity(intent);
        }

    }
}
