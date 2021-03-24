package kakao.woo95.eventhandling;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.widget.Button;

import java.net.ContentHandler;

public class SoundActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound);

        Button btnVib = (Button)findViewById(R.id.btnVib);
        btnVib.setOnClickListener(v -> {
            Vibrator vib = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
            vib.vibrate(3000);
        });

        Button btnAlarm = (Button)findViewById(R.id.btnAlarm);
        btnAlarm.setOnClickListener(v -> {
            Uri Noti = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(),Noti);
            ringtone.play();
        });

        Button btnMusic = (Button)findViewById(R.id.btnMusic);
        btnMusic.setOnClickListener(v -> {
            //앱에 넣어진 소리 재생
            MediaPlayer player = MediaPlayer.create(getApplicationContext(),R.raw.song);
            player.start();
        });
    }
}