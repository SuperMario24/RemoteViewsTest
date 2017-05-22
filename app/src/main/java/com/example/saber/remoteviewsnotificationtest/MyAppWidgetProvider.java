package com.example.saber.remoteviewsnotificationtest;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.SystemClock;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * Created by saber on 2017/5/17.
 */

public class MyAppWidgetProvider extends AppWidgetProvider {

    private static final String TAG = "MyAppWidgetProvider";
    private static final String CLICK_ACTION = "com.example.saber.remoteviewsnotificationtest.action.CLICK";

    public MyAppWidgetProvider() {
        super();
    }

    /**
     * 基于广播机制
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(final Context context, final Intent intent) {
        super.onReceive(context,intent);
        Log.i(TAG, "onReceive: action = "+intent.getAction());
        if(CLICK_ACTION.equals(intent.getAction())){
            Toast.makeText(context, "clicked it", Toast.LENGTH_SHORT).show();

            new Thread(new Runnable() {
                @Override
                public void run() {

                        Bitmap srcBitmap = BitmapFactory.decodeResource(context.getResources(),R.mipmap.ic_launcher);
                        //获取AppWidgetManager对象
                        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                        for(int i=0;i<37;i++){
                            float degree = (i*10)%360;
                            //创建RemoteViews
                            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.widget);
                            remoteViews.setImageViewBitmap(R.id.image_view,rotateBitmap(context,srcBitmap,degree));

                            //每次点击执行的事件
                            Intent intentClick = new Intent();
                            intentClick .setAction(CLICK_ACTION);
                            //PendingIntent 第一个参数context，第二个参数requestCode，一般为0，第三个参数intent，第四个参数Flags
                            PendingIntent pendindIntent = PendingIntent.getBroadcast(context,0,intentClick,0);
                            remoteViews.setOnClickPendingIntent(R.id.image_view,pendindIntent);//点击事件

                            Intent intent = new Intent(context,MainActivity.class);
                            PendingIntent pendingIntent1 = PendingIntent.getActivity(context,0,intent,0);

                            //调用此方法更新remoteviews
                            appWidgetManager.updateAppWidget(new ComponentName(context,MyAppWidgetProvider.class),remoteViews);
                            SystemClock.sleep(30);

                    }
                }
            }).start();

        }
    }


    /**
     * 每次桌面小部件更新时都会调用一次该方法，被添加时或自动更新时调用
     */
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        Log.i(TAG, "onUpdate");

        final int counter = appWidgetIds.length;
        Log.i(TAG, "counter = "+counter);
        for(int i=0;i<counter;i++){
            int appWidgetId = appWidgetIds[i];
            onWidgetUpdate(context,appWidgetManager,appWidgetId);
        }
    }


    /**
     * 桌面小部件更新
     */
    private void onWidgetUpdate(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        Log.i(TAG, "appWidgetId = "+appWidgetId);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.widget);

        //桌面小部件单机事件发送的intent广播，第一次添加时，为小部件添加了点击事件
        Intent intentClick = new Intent();
        intentClick.setAction(CLICK_ACTION);
        //点击时发广播
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,intentClick,PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.ll,pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId,remoteViews);
    }

    private Bitmap rotateBitmap(Context context, Bitmap srcBitmap, float degree) {
        Matrix matrix = new Matrix();
        matrix.reset();
        matrix.setRotate(degree);
        Bitmap tmpBitmap = Bitmap.createBitmap(srcBitmap,0,0,srcBitmap.getWidth(),srcBitmap.getHeight(),matrix,true);
        return tmpBitmap;
    }

}
