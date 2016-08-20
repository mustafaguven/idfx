package tr.com.idefix.android.handler;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import com.microsoft.windowsazure.notifications.NotificationsHandler;
import tr.com.idefix.android.R;
import tr.com.idefix.android.view.activities.MainActivity;

/**
 * Created by mustafaguven on 03.01.2016.
 */
public class MyHandler extends NotificationsHandler {

  Context ctx;

  @Override public void onReceive(Context context, Bundle bundle) {
    ctx = context;
    String nhMessage = bundle.getString("message");

    sendNotification(nhMessage);
  }

  private void sendNotification(String msg) {
    int notifyID = (int) System.currentTimeMillis();

    PendingIntent contentIntent =
        PendingIntent.getActivity(ctx, 0, new Intent(ctx, MainActivity.class), 0);

    int stringId = ctx.getApplicationInfo().labelRes;

    NotificationCompat.Builder mBuilder =
        new NotificationCompat.Builder(ctx).setContentTitle(ctx.getString(stringId))
            .setContentText(msg)
            .setDefaults(Notification.DEFAULT_SOUND)
            .setSmallIcon(R.drawable.ic_bell_bold)
            .setLargeIcon(BitmapFactory.decodeResource(ctx.getResources(), getNotificationIcon()))
            .setStyle(new NotificationCompat.BigTextStyle().bigText(msg));

    mBuilder.setContentIntent(contentIntent);
    mBuilder.setAutoCancel(true);
    NotificationManager mNotificationManager = (NotificationManager) ctx.
        getSystemService(Context.NOTIFICATION_SERVICE);

    mNotificationManager.notify("DR_GCM", notifyID, mBuilder.build());
  }

  public int getNotificationIcon() {
    boolean whiteIcon = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    return whiteIcon ? R.mipmap.ic_launcher : R.drawable.ic_bell_bold;
  }
}
