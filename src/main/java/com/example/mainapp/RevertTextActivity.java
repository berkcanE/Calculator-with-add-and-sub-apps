package com.example.mainapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RevertTextActivity extends AppCompatActivity {

    Button BTback;
    TextView TVresult;
    String getToRevert;

    NotificationCompat notificationCompat;
    Notification notification;
    private Context context;

    SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS");
    Date myDate = new Date();
    String dateForNotification = timeStampFormat.format(myDate);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revert_text);

        BTback = findViewById(R.id.BTback);
        TVresult = findViewById(R.id.TVresult);
        getToRevert = reverted(getIntent().getExtras().getString("toRevert"));
        TVresult.setText(getToRevert);

        notification("RevertActivity", "onCreate:" + dateForNotification);

        BTback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(RevertTextActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        notification("RevertActivity", "onDestroy:" + dateForNotification);
        super.onDestroy();
    }

    public static String reverted(String text)
    {
        char[] txt = new char[text.length()];
        int index = 0;
        for(int i = txt.length - 1 ; i >= 0 ; i--)
        {
            txt[index] = text.charAt(i);
            index++;
        }

        String reverted = "";
        for(int i = 0 ; i < text.length() ; i++)
        {
            reverted = reverted + txt[i];
        }
        return reverted;
    }


    public void notification(String title, String message) {
        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        String CHANNEL_ID="DestroyOperation";
        NotificationChannel notificationChannel=new NotificationChannel(CHANNEL_ID,"nameIt", NotificationManager.IMPORTANCE_DEFAULT);
        PendingIntent pendingIntent=PendingIntent.getActivity(getApplicationContext(),1,intent,0);
        Notification notification=new Notification.Builder(getApplicationContext(),CHANNEL_ID)
                .setContentText(message)
                .setContentTitle(title)
                .setContentIntent(pendingIntent)
                .addAction(android.R.drawable.sym_action_chat,"Title",pendingIntent)
                .setChannelId(CHANNEL_ID)
                .setSmallIcon(android.R.drawable.sym_action_chat)
                .build();

        NotificationManager notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(notificationChannel);
        notificationManager.notify(1,notification);

    }
}