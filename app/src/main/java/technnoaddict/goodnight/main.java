package technnoaddict.goodnight;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.provider.Settings;
import android.service.notification.StatusBarNotification;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static java.lang.System.exit;

public class main extends Service {
//int inter = Set.interval;
    Handler handler = new Handler();
    Runnable refresh;
     int SPLASH_TIME_OUT = Set.interval ;
    NotificationManager nm;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Toast.makeText(getApplicationContext(),"Interval is "+SPLASH_TIME_OUT,Toast.LENGTH_LONG).show();
        super.onCreate();
    }


    @Override
    public int onStartCommand(Intent intent, final int flags, int startId) {


        refresh = new Runnable() {
            @Override
            public void run() {



                final AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
                final int volumeget = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

                Toast.makeText(getApplicationContext(),"VOLUME is "+volumeget,Toast.LENGTH_LONG).show();
                if(volumeget!=0)

                {
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volumeget - 1, 1);

                }
                else {



                    Intent intent1 = new Intent(getApplicationContext(), main.class);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0 /* Request code */, intent1,
                            PendingIntent.FLAG_ONE_SHOT);

                    Intent i = new Intent(getApplicationContext(), Set.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    PendingIntent pendingIntent2 = PendingIntent.getActivity(getApplicationContext(), 0 /* Request code */, i,
                            PendingIntent.FLAG_ONE_SHOT);



                   // Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    android.support.v4.app.NotificationCompat.Builder notificationBuilder = new android.support.v4.app.NotificationCompat.Builder(getApplicationContext())
                            .setContentTitle("GOOD NIGHT")
                            .setSmallIcon(android.R.drawable.checkbox_on_background)
                            .setContentText("Its Time to Sleep ")
                            .setAutoCancel(false)
                            .addAction(android.R.drawable.ic_menu_revert,"Reset",pendingIntent2)
                            .setContentIntent(pendingIntent);


                    NotificationManager notificationManager =
                            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                    notificationManager.notify(0
                            /* ID of notification */, notificationBuilder.build());

                    ////
                    ///
                    stopSelf();
                    exit(0);
                }

            handler.postDelayed(refresh,SPLASH_TIME_OUT*1000);
        }
    };
    handler.post(refresh);

        return START_STICKY;
    };

    @Override
    public void onDestroy() {
        exit(0);
        super.onDestroy();
    }
}
