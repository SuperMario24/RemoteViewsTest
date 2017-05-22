# RemoteViewsTest
often used in Notification or Appwidget

1.RemoteViews在Android中使用场景主要为：通知栏和桌面小部件

2.桌面小部件是通过APPWidgetProvider来实现的，本质上是一个广播。

3.notification通过contentIntent来确定点击通知栏整体的事件，内部控件的点击事件，则需要通过remoteViews的setOnClickPendingIntent来确定。

4.桌面小部件的开发步骤：
（1）定义自定的xml界面，也就是layout
（2）定义配置信息，res/xml/文件名称，名称：appwidget-provider。定义几个属性。1.initialLayout，初始化布局文件。minWidth,minHeight,设置宽高，
updatePeriodMillis每次系统自动更新小部件的时间。
（3）重写小部件的实现类APPWidgetProvider。
（4）菜单文件中声明小部件。本质上是一个广播，所以需要注册。

5.APPWidgetProvider中常用的几个方法调用：
（1）onEnable:当小部件第一次添加到桌面时调用该方法
（2）onUpdate：当小部件被添加或者每次小部件更新时调用
（3）onDisabled：当最后一个该类型的小部件被删除时，调用该方法
（4）onReceive：广播的内置方法，分发具体事件给其他方法

6.PendingIntent支持三种意图：
（1）启动Activity
（2）启动Service
（3）发送广播

7.PendingIntent的匹配规则：
（1）内部Intent相同
（2）requestCode相同，第二个通常为0的参数相同。则认为他们是同一个PendingIntent

intent的匹配规则：（1）ComponentName相同（2）Intent-filter相同



8.RemoteViews没有提供findviewbyid方法，因为处于不同的进程，所有一般用反射完成。

9.RemoteViews内部机制：
（1）RemoteViews通过Binder传递到SystemServer进程
（2）系统根据包名查找资源
（3）通过LayoutInflater加载RemoteViews布局文件
（4）系统对View执行一系列的更新。

10：RemoteViews如何更新View
（1）RemoteViews每调用一次set方法，RemoteViews中就会添加一个Action对象
（2）通过Binder将Action的集合传递到远程进程
（3）远程进程通过RemoteViews的apply方法去遍历所有的Action对象并调用他们的apply方法进行更新。


11.apply方法和reApply方法的区别：
apply会加载并更新界面，reApply则只会更新界面。

12.RemoteViews可跨进程更新特定的View（不支持自定义的View）。




















