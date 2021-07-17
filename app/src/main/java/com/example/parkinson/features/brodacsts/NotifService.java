package com.example.parkinson.features.brodacsts;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.parkinson.R;
import com.example.parkinson.features.notification.NotifManager;
import com.example.parkinson.model.general_models.Medicine;
import com.example.parkinson.model.general_models.Time;
import com.google.firebase.auth.FirebaseAuth;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

public class NotifService extends Service implements NotifManager.NotifMangerInteface {

    NotificationManager manager;

    private NotifManager notifManger = NotifManager.getNotifManager();

    public void postNotifaction(List<Medicine> medicines, int id, String notifHour) {
        if (medicines.isEmpty()) {
            return;
        }

        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        String channelId = null;
        if (Build.VERSION.SDK_INT >= 26) {
            channelId = "Notifaction";
            CharSequence channelName = "Medicine Report Chanel";
            NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(notificationChannel);
        }


        Intent replyIntent = new Intent(this, NotifService.class);
        replyIntent.putExtra("command", "medicationReport");
        replyIntent.putExtra("notifHour", notifHour);
        PendingIntent replyPendingIntent = PendingIntent.getService(this,
                5, replyIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle()
                .setBigContentTitle("עליך לקחת את התרופות הבאות")
                .setSummaryText("תרופות לקחת");

        for (Medicine med : medicines) {
            inboxStyle.addLine(med.getName());
        }

        Notification notification = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_medical)
                .setContentTitle("תזכורת על לקיחת תרופות")
                .setContentText("עליך לדווח על התרופות של השעה : " + notifHour)
                .setStyle(inboxStyle)
                .addAction(R.mipmap.ic_launcher, "דיווח על לקיחת התרופות", replyPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();

        manager.notify(id, notification);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void setNotification(Intent intent, int notifId, String notifHour) {

        notifManger.getMedication(notifId, notifHour);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            return super.onStartCommand(intent, flags, startId);
        }

        notifManger.setListner(this);
        String command = intent.getStringExtra("command");
        int notifId = intent.getIntExtra("notifId", 1);
        String notifHour = getCurrnetHour();
        if (command == null)
            return super.onStartCommand(intent, flags, startId);


        switch (command) {
            case "medicationReport":
                notifManger.report(notifId, notifHour);
                break;
            case "start Notifaction":
                setNotification(intent, notifId, notifHour);
                break;
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private String getCurrnetHour() {
        Calendar rightNow = Calendar.getInstance();
        int currentHourIn24Format = rightNow.get(Calendar.HOUR_OF_DAY);
        int minutes = rightNow.get(Calendar.MINUTE);
        minutes = minutes < 30 ? 0 : 30;
        Time time = new Time(minutes, currentHourIn24Format);
        return time.toString();
    }

    @Override
    public void closeNotif(int id) {
        manager.cancel(id);
    }

}