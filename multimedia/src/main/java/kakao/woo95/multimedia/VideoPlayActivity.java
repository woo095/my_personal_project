package kakao.woo95.multimedia;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoPlayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);

        //VideoView 가져오기
        VideoView videoView = (VideoView)findViewById(R.id.videoView);

        //재생할 비디오 설정
        MediaController mc = new MediaController(this);
        videoView.setMediaController(mc);

        //비디오 파일 경로
        String path = "android.resource://" + getPackageName() + "/" + R.raw.novasonic;
        videoView.setVideoURI(Uri.parse(path));
        videoView.requestFocus();

        Button btnstart = (Button)findViewById(R.id.startBtn);
        btnstart.setOnClickListener(v -> {
            videoView.seekTo(0);
            videoView.start();
        });
    }
}