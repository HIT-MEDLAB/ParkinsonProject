package com.example.parkinson.features.notification;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

import com.example.parkinson.R;
import com.example.parkinson.features.brodacsts.NotifService;
import com.example.parkinson.features.brodacsts.ReportService;
import com.example.parkinson.model.general_models.Time;

import java.util.Calendar;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class NotifServiceForground extends Service {
    NotifManager notifManager;
    SharedPreferences sharedPreferences;

    @Inject
    public NotifServiceForground(NotifManager notifManager){
        this.notifManager = notifManager;
    }
    public IBinder onBind(Intent intent) {
        return null;
    }
    public NotifServiceForground() {
        super();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sharedPreferences = getSharedPreferences("details",MODE_PRIVATE);
        if(sharedPreferences.getBoolean("is first lunch",true) == true)
        {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("is first lunch",false);
            editor.commit();
            createNotifacations();
            cReateReportNotif();
            cReateReportMedicineNotif();
        }

        createNotifacations();

        return super.onStartCommand(intent, flags, startId);
    }

    private void cReateReportMedicineNotif() {
        Calendar rightNow = Calendar.getInstance();
        int currentHourIn24Format = rightNow.get(Calendar.HOUR_OF_DAY);
        int minutes = rightNow.get(Calendar.MINUTE);
        minutes = minutes < 30 ? 0 : 30;

        Intent medicineIntent = new Intent(this, NotifService.class);
        medicineIntent.putExtra("command", "start Notifaction");
        medicineIntent.putExtra("notifHour", "2100");
        PendingIntent medicineIntentPintent = PendingIntent.getService(this, 0, medicineIntent, 0);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(System.currentTimeMillis());
        calendar2.set(Calendar.MINUTE,minutes);

        AlarmManager alarm2 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarm2.setRepeating(AlarmManager.RTC_WAKEUP, calendar2.getTimeInMillis(),
                1000 * 60 *30, medicineIntentPintent);
    }

    private void cReateReportNotif() {
        Intent intent = new Intent(this, ReportService.class);
        PendingIntent pintent = PendingIntent.getService(this, 0, intent, 0);
        Time time = getCurrnetHour();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, time.getHour());
        calendar.set(Calendar.MINUTE,0);

        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                1000 * 60 * 60, pintent);
    }

    private void createNotifacations() {
        createNotificationChannel();
        NotificationCompat.Builder builder;
        NotificationManager notificationManager;
        Intent reportActivity = new Intent(this, NotificationActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 100, reportActivity, PendingIntent.FLAG_UPDATE_CURRENT);

        builder = new NotificationCompat.Builder(this, "CHANNEL_ID")
                .setSmallIcon(R.drawable.icon_medical)
                .setContentTitle("איך אתה מרגיש ?")
                .setContentText("לחץ לדיווח נוסף")
                .setContentIntent(pendingIntent)
                .setFullScreenIntent(pendingIntent, false)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setColor(Color.BLACK)
                .setOngoing(true);


        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        startForeground(10,builder.build() );
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "CHANNEL";
            String description = "CHANNEL_DESC";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("CHANNEL_ID", name, importance);
            channel.setDescription(description);
            channel.enableVibration(true);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private Time getCurrnetHour() {
        Calendar rightNow = Calendar.getInstance();
        int currentHourIn24Format = rightNow.get(Calendar.HOUR_OF_DAY);
        int minutes = rightNow.get(Calendar.MINUTE);
        minutes = minutes < 30 ? 0 : 30;
        Time time = new Time(minutes,currentHourIn24Format);
        return time;
    }

    @Override
    public void onDestroy() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("is first lunch",true);
        editor.commit();
        super.onDestroy();
    }
}
