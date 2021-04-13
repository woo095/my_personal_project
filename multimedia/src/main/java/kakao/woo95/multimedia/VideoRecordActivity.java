package kakao.woo95.multimedia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;
import android.widget.FrameLayout;

import com.google.android.material.snackbar.Snackbar;
import com.pedro.library.AutoPermissions;
import com.pedro.library.AutoPermissionsListener;

import java.io.File;

public class VideoRecordActivity extends AppCompatActivity implements AutoPermissionsListener {
    //비디오 녹화와 재생 객체를 저장할 프로퍼티
    MediaPlayer player;
    MediaRecorder recorder;

    //저장할 파일명을 위한 프로퍼티
    String fileName;

    //카메라 뷰를 앱 내에 출력하기 위한 프로퍼티
    SurfaceHolder holder;

    //비디오 녹화를 위한 사용자 정의 메소드
    public void startRecording(){
        //녹화할 객체 생성
        if(recorder == null){
            recorder = new MediaRecorder();
        }
        
        
        //옵션 설정
        
        //소리입력 설정
        recorder.setAudioSource((MediaRecorder.AudioSource.MIC));
        //비디오 입력 설정
        recorder.setVideoSource((MediaRecorder.VideoSource.CAMERA));
        //출력 포맷 설정 (MPEG-4)
        recorder.setOutputFormat((MediaRecorder.OutputFormat.MPEG_4));
        
        //오디오와 비디오 코덱 설정
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
        recorder.setVideoEncoder(MediaRecorder.VideoEncoder.DEFAULT);

        //출력 파일 설정
        recorder.setOutputFile(fileName);

        //카메라 화면을 가져오기 위한 설정
        recorder.setPreviewDisplay(holder.getSurface());

        try{
            recorder.prepare();
            recorder.start();
        } catch (Exception e){
            Log.e("카메라 녹화 예외", e.getLocalizedMessage());
            recorder.release();
            recorder = null;
            
            
            
        }
    }
    
    //녹화 중지를 위한 메소드
    public void stopRecording(){
        if(recorder == null){
            return;
        }
        
        //녹화 중지 후 Recorder 초기화
        recorder.stop();
        recorder.reset();
        recorder.release();
        recorder = null;

        //녹화 중지를 알려주기 위한 설정
        ContentValues values = new ContentValues(10);
        values.put(MediaStore.MediaColumns.TITLE, "RecordVideo");
        values.put(MediaStore.MediaColumns.ALBUM, "Video Album");
        values.put(MediaStore.MediaColumns.ARTIST, "ADAM");
        values.put(MediaStore.MediaColumns.DISPLAY_NAME, "RecordVideo");
        values.put(MediaStore.MediaColumns.DATE_ADDED, System.currentTimeMillis() / 1000);
        //파일의포맷과 파일의 이름 설정
        values.put(MediaStore.MediaColumns.MIME_TYPE, "video/mp4");
        values.put(MediaStore.MediaColumns.DATA, fileName);

        //제대로 생성되었는지 확인
        //비디오를 저장하고 그 결과를 videoUri에 저장 - 실패하면 null 리턴
        Uri videoUri = getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values);
        if(videoUri == null){
            Log.e("비디오 저장 실패", "Video 설정이나 파일 이름 확인");
            return;
        }

        //방송 - 녹화 되었으니 이 파일을 재생할 수 있다고 방송
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, videoUri));
    }

    //비디오 재생을 위한 메소드
    public void startPlay(){
        if(player == null){
            player = new MediaPlayer();
        }

        try {
            player.setDataSource(fileName);
            player.setDisplay(holder);
            player.prepare();
            player.start();
        } catch (Exception e){
            Log.e("비디오 재생 실패", e.getLocalizedMessage());
        }
    }

    //비디오 재생 중지를 위한 메소드
    public void stopPlay(){
        if(player == null){
            return;
        }

        player.stop();
        player.release();
        player = null;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_record);

        //카메라 뷰 설정
        SurfaceView surface = new SurfaceView(this);
        holder = surface.getHolder();
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        FrameLayout container = (FrameLayout)findViewById(R.id.container);
        container.addView(surface);

        //파일이름 결정
        //앱의 도큐먼트 디렉토리 경로 가져오기 -
        String fileDir = getFilesDir().getAbsolutePath();
        fileName = fileDir + File.separator + "video.mp4";

        //동적 권한 요청
        AutoPermissions.Companion.loadAllPermissions(this, 101);

        //비디오 녹화 버튼의 클릭 이벤트 처리
        Button btnvideoRecordStart = (Button)findViewById(R.id.button);
        btnvideoRecordStart.setOnClickListener(v -> {
            startRecording();
        });

        //비디오 녹화 중지 버튼 클릭
        Button btnvideoRecordStop = (Button)findViewById(R.id.button2);
        btnvideoRecordStop.setOnClickListener(v -> {
            stopRecording();
        });

        //비디오 재생 시작 버튼
        Button btnVideoPlayStart = (Button)findViewById(R.id.button3);
        btnVideoPlayStart.setOnClickListener(v -> {
            startPlay();
        });

        Button btnVideoPlayStop = (Button)findViewById(R.id.button4);
        btnVideoPlayStop.setOnClickListener(v -> {
            stopPlay();
        });
    }

    //동적 권한 요청을 위한 메소드 재정의
    //동적으로 권한 요청하고 그 결과를 알려주는 메소드
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //AutoPermissions에 처리를 위임
        AutoPermissions.Companion.parsePermissions(this, requestCode, permissions, this);
    }

    //거부한 권한에 대한 정보를 알려주는 메소드 - AutoPermissions 의 메소드
    @Override
    public void onDenied(int i, String[] strings) {
        Snackbar.make(getWindow().getDecorView().getRootView(), "거부한 권한 개수:" + strings.length, Snackbar.LENGTH_LONG).show();
    }

    //허용한 권한에 대한 정보를 알려주는 메소드 - AutoPermissions의 메소드
    @Override
    public void onGranted(int i, String[] strings) {
        Snackbar.make(getWindow().getDecorView().getRootView(), "필요한 권한 개수:" + strings.length, Snackbar.LENGTH_LONG).show();
    }
}