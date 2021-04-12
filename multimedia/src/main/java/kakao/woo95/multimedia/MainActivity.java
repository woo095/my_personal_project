package kakao.woo95.multimedia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    //노래 목옥을 저장할 리스트
    ArrayList<String> list;
    //현재 재생 중인 노래의 인덱스를 저장할 프로퍼티
    int idx;

    //노래와 영상 재생기
    MediaPlayer player;

    //버튼
    Button btnplay, btnstop, btnprev, btnnext;

    //텍스트 뷰
    TextView fileName;

    //시크 바
    SeekBar progress;

    //현재 음원 재생 여부를 저장할 프로퍼티
    boolean isPlaying;

    //노래 제목을 출력하고 Seekbar 초기화를 위한 핸들러
    Handler messageHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            boolean result = (Boolean)msg.obj;
            if(result==true){
                //노래 제목을 출려하고 Seekbar의 범위를 duration으로 설정
                String resultMSG = "재생 준비 완료";
                fileName.setText(list.get(idx));
                progress.setMax(player.getDuration());
                Snackbar.make(getWindow().getDecorView().getRootView(),resultMSG,Snackbar.LENGTH_LONG).show();
            } else {
                Snackbar.make(getWindow().getDecorView().getRootView(),"재생 준비 실패",Snackbar.LENGTH_LONG).show();
            }
        }
    };

    //사용자 정의 메소드

    //노래를 재생할 수 있도록 준비해주는 메소드
    private boolean prepare(){
        try {
            //재생 준비
            player.prepare();
        }catch (Exception e){
            Log.e("노래 재생 준비 실패",e.getLocalizedMessage());
            return false;
        }
        return true;
    }
    
    //재생할 위치의 노래의 인덱스를 받아서 재생기에 노래를 설정메소드
    private void loadMedia(int idx){
        Message message = new Message();
        //재생할 노래의 인덱스를 핸들러에게 전달하기 위해서 메시지에 저장
        message.what = idx;
        
        try{
            player.setDataSource(MainActivity.this, Uri.parse(list.get(idx)));
        } catch (Exception e){
            Log.e("노래 재생기 예외발생",e.getLocalizedMessage());
        }
        
        //노래 재생 준비
        if(prepare() == false){
            message.obj = false;
        } else {//true 여야 노래 재생
            message.obj = true;
        }

        //핸들러에게 메시지 전송
        messageHandler.sendMessage(message);
    }

    //Seekbar를 업데이트 해주는 핸들러
    Handler progressHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            //super.handleMessage(msg);
            if(player == null){//노래가 재생 중이 아니면 리턴
                return;
            }
            if(player.isPlaying()){//노래가 재생 중인 경우 노래의 현재 위치를 Seekbar에 표시
                progress.setProgress(player.getCurrentPosition());
            }
            //0.2초 후에 핸들러 다시 호출
            progressHandler.sendEmptyMessageDelayed(0,200);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //텍스트 뷰 찾아오기
        fileName = (TextView)findViewById(R.id.filename);
        //노래 목록을 저장할 List 객체 생성
        list = new ArrayList<String>();

        //노래 목록을 가져올 스레드 생성
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    //URL 생성
                    URL url = new URL("http://cyberadam.cafe24.com/song/song.txt");
                    //연결 객체 생성
                    HttpURLConnection con = (HttpURLConnection)url.openConnection();
                    //옵션 설정 - 파라미터 전송 방법, 시간, 캐시 사용 여부
                    con.setConnectTimeout(30000);
                    con.setUseCaches(false);

                    BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    //문자열 전체 읽기
                    while(true){
                        String line = br.readLine();
                        if(line == null){
                            break;
                        }
                        sb.append(line + "\n");
                    }

                    //다운받은 문자열 확인
                    //Snackbar.make(getWindow().getDecorView().getRootView(),sb.toString(), Snackbar.LENGTH_LONG).show();

                    //다운로드 받은 데이터 사용
                    //,로 구분된 문자열을 사용하가
                    String [] songList = sb.toString().split(",");
                    //배열의 내용을 리스트에 저장
                    for(String song : songList){
                        list.add("http://cyberadam.cafe24.com/song/"+song+".mp3");
                    }

                    Log.e("배열", Arrays.toString(songList));
                    Log.e("리스트", list.toString());
                    
                    //재생기 생성
                    player = new MediaPlayer();
                    //노래의 인덱스 초기화
                    idx = 0;
                    //노래 로드
                    loadMedia(idx);

                    //시크바 찾아오기
                    progress = (SeekBar)findViewById(R.id.progress);

                    //노래 재생이 끝나면 호출되는 이벤트 핸들러 - 다음 노래를 작성
                    player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            //마지막 노래일경우 목록 첫번째 노래로 돌아감
                            idx = (idx == list.size() -1) ? 0 : idx + 1;
                            //재생기를 다시 시작
                            player.reset();
                            loadMedia(idx);
                            player.start();
                        }
                    });

                    //player가 노래를 재생하다가 에러가 발생한 경우(MediaPlayer 의 OnErrorListener가 처리)
                    player.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                        @Override
                        public boolean onError(MediaPlayer mp, int what, int extra) {
                            Snackbar.make(getWindow().getDecorView().getRootView(),"재생 중 에러 발생",Snackbar.LENGTH_LONG).show();
                            return false;
                        }
                    });

                    //노래의 재생 위치를 옮겼을 때 호출되는 메소드
                    player.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
                        @Override
                        public void onSeekComplete(MediaPlayer mp) {
                            //재생 중이면 노래 재생
                            if(isPlaying){
                                player.start();
                            }
                        }
                    });
                    
                    //Seekbar의 thumb을 사용자가 움직였을 때 처리하는 이벤트
                    progress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        //Seekbar 의 thumb을 움직이고 난 후 호출되는 메소드
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                            if(fromUser == true){
                                //노래의 재생 위치를 progress 값으로 변경
                                player.seekTo(progress);
                            }
                        }

                        //thumb을 눌렀을때 호출되는 메소드
                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {
                            isPlaying = player.isPlaying();
                            if(isPlaying){
                                player.pause();
                            }
                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {

                        }
                    });

                    //배열을 이용해서 List를 생성
                   //스트림 생성 - byte 단위, char 단위에 따라 다르게 생성
                    progressHandler.sendEmptyMessageDelayed(0, 200);
                }catch (Exception e){
                    Log.e("음원 목록 가져오기 실패", e.getLocalizedMessage());
                }
            }
        }.start();

        //재생 버튼 눌렀을 때 처리
        btnplay = (Button)findViewById(R.id.play);
        btnplay.setOnClickListener(v -> {
            if(player.isPlaying() == false){
                player.start();
                btnplay.setText("Pause");
            } else {
                player.pause();
                btnplay.setText("Play");
            }
        });

        //중지 버튼을 눌렀을때 처리
        btnstop = (Button)findViewById(R.id.stop);
        btnstop.setOnClickListener(v -> {
            //재생 중지
            btnplay.setText("Play");
            player.stop();
            progress.setProgress(0);
            //노래 준비
            prepare();
        });

        //이전 버튼의 이벤트 핸들러
        btnprev = (Button)findViewById(R.id.prev);
        btnprev.setOnClickListener(v -> {
            boolean isPlaying = player.isPlaying();
            idx = (idx == 0) ? list.size() -1 :  idx-1; //맨 처음에서 뒤로가기를 눌렀다면 다시 맨 끝으로 보냄
            player.reset();
            loadMedia(idx);

            if(isPlaying){
                player.start();
                btnplay.setText("Pause");
            }
        });
    }
}