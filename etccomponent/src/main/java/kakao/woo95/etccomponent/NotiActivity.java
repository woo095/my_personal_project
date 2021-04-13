package kakao.woo95.etccomponent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;

public class NotiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noti);

        Button btnNoti = (Button)findViewById(R.id.btnNoti);
        btnNoti.setOnClickListener(v -> {
            //알림 제어 객체 생성
            NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            //알림을 다양한 방법으로 생성해주는 객체 생성
            //Oreo 버전을 기준으로 다르게 생성
            NotificationCompat.Builder builder = null;
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){//Oreo 버전 이상
                String channelId = "one-channel";
                String channelName = "My Channel One";
                String channelDescription = "My Channel One Description";

                NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
                channel.setDescription(channelDescription);

                manager.createNotificationChannel(channel);
                builder = new NotificationCompat.Builder(NotiActivity.this, channelId);
            }else {//Oreo 버전 이하
                builder = new NotificationCompat.Builder(NotiActivity.this);
            }

            //알림 설정
            builder.setSmallIcon(android.R.drawable.ic_notification_overlay);
            builder.setContentTitle("알림 제목");
            builder.setContentText("알림 내용");
            builder.setAutoCancel(true);
            builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.noti_large));

            //눌렀을 때 동작을 설정
            //다른 Activity 출력
            Intent intent = new Intent(NotiActivity.this, MainActivity.class);//알림을 누르게 될경우 MainActivity로 넘어간다
            PendingIntent activityIntent = PendingIntent.getActivity(NotiActivity.this, 10, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            builder.setContentIntent(activityIntent);
            PendingIntent receiverIntent = PendingIntent.getBroadcast(NotiActivity.this, 20, new Intent(NotiActivity.this, NotiReceiver.class),PendingIntent.FLAG_UPDATE_CURRENT);
            builder.addAction(new NotificationCompat.Action.Builder(android.R.drawable.ic_menu_share, "ACTION",receiverIntent).build());

            //노티피케이션 호출
            manager.notify(111, builder.build());
        });
    }
}