package com.example.parkinson.features.notification;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ParkinsonApplication;
import com.example.parkinson.R;
import com.example.parkinson.di.ApplicationComponent;
import com.example.parkinson.model.enums.EStatus;
import javax.inject.Inject;


public class NotificationActivity extends AppCompatActivity {
    public ApplicationComponent applicationComponent;
    private NotificationManager notifManger;
    EStatus chosenStatus;

    @Inject
    NotificationViewModel notificationViewModel;

    RadioButton onnBtn, offBtn;
    TextView reportBtn;
    CheckBox isHallucinations, isFalls, isDyskinesia;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        ((ParkinsonApplication) getApplicationContext()).appComponent.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_notification);
        cancelReportNotifaction();
        initUi();
    }

    private void cancelReportNotifaction() {
        notifManger = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notifManger.cancel(11);
    }

    private void initUi() {
        initBtnListeners();
    }

    private void initBtnListeners() {
        reportBtn = findViewById(R.id.notificationReportBtn);
        offBtn = findViewById(R.id.notificationOffBtn);
        onnBtn = findViewById(R.id.notificationOnBtn);

        RadioGroup radioGroup = findViewById(R.id.reportRG);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == onnBtn.getId()) {
                    chosenStatus = EStatus.On;
                } else if (checkedId == offBtn.getId()) {
                    chosenStatus = EStatus.Off;
                }
            }
        });

        reportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reportToServer();
            }
        });

        isHallucinations = findViewById(R.id.notificationHallucinationsBtn);
        isFalls = findViewById(R.id.notificationFallsBtn);
        isDyskinesia = findViewById(R.id.notificationDyskinesiaBtn);
    }

    private void reportToServer() {
        if (chosenStatus == null)
            return;

        switch (chosenStatus) {
            case On:
                notificationViewModel.updateReport(EStatus.On,isDyskinesia.isChecked(), isHallucinations.isChecked(), isFalls.isChecked());
                break;
            case Off:
                notificationViewModel.updateReport(EStatus.Off, isDyskinesia.isChecked(), isHallucinations.isChecked(), isFalls.isChecked());
                break;
        }
        onBackPressed();
    }

    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

}
