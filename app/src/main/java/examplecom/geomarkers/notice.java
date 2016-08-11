package examplecom.geomarkers;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.View;

import java.util.concurrent.TimeUnit;

/**
 * Created by nikita on 11.08.16.
 */
public class notice {

    int NOTIFY_ID = 10;

    public void notice(Context context, double longti, double latiti) {


        Notification.Builder builder = new Notification.Builder(context);
// оставим только самое необходимое
        Intent notificationIntent = new Intent(context, MainLayoutActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context,
                0, notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(contentIntent)
                .setSmallIcon(R.drawable.add)
                .setContentTitle("Вы находитесь близко к метке")
                .setContentText("Метка на координатах: "+ longti + latiti); // Текст уведомления

        Notification notification = builder.build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(NOTIFY_ID, notification);
    }
}
