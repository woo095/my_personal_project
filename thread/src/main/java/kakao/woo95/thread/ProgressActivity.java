package kakao.woo95.thread;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ProgressActivity extends AppCompatActivity {

    //진행율 표시를 위한 대화상자 (Deprecated)
    ProgressDialog progressDialog;
    //진행율 표시를 위한 뷰
    ProgressBar progressBar;

    private TextView tfResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        //Progressbar 찾아오기
        progressBar = (ProgressBar)findViewById(R.id.progressbar);

        //UI를 갱신 - 다른 작업이 없을때 수행
        //Handler handler = new Handler(Looper.getMainLooper());

        //메시지를 전송해서 바로 작업을 수행시키는 핸들러
        Handler handler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                //전송받은 코드를 저장
                int what = msg.what;
                //데이터를 가져옵니다
                int i = (Integer)msg.obj;
                tfResult.setText("i="+i);
                if(i < 100){
                    progressDialog.setProgress(i);
                    progressBar.setProgress(i);
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    progressDialog.dismiss();
                    progressBar.setVisibility(View.GONE);
                }
            }
        };

        //텍스트 뷰 찾아오기
        tfResult = (TextView)findViewById(R.id.tfResult);

        //버튼의 클릭 이벤트 처리코드드
        Button btnStart = (Button)findViewById(R.id.btnStart);
        btnStart.setOnClickListener(v -> {
            /*
            try {
                for(int i=1; i<=100; i++){
                    //tfResult.setText("i="+i);
                    //전송할 메시지를 생성
                    Message msg = new Message();
                    //데이터 저장
                    msg.obj = i;
                    //다른 곳과 구분하고자 할 때는 What을 이용
                    msg.what = 1;
                    handler.sendMessage(msg);
                    Thread.sleep(50);
                }
            }catch (Exception e){
                Log.e("예외 발생", e.getLocalizedMessage());
            }
            */

            //스레드를 활용해 핸들러를 호출
            new Thread(){
                @Override
                public void run() {
                    super.run();
                    try {
                        for(int i=1; i<=100; i++){
                            Message msg = new Message();
                            //데이터 저장
                            msg.obj = i;
                            //다른 곳과 구분하고자 할 때는 What을 이용
                            msg.what = 1;
                            handler.sendMessage(msg);
                            Thread.sleep(50);
                        }
                    }catch (Exception e){
                        Log.e("예외 발생", e.getMessage());
                    }
                }
            }.start();

            progressDialog = new ProgressDialog(ProgressActivity.this);
            progressDialog.setProgress(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setTitle("다운로드 중");
            progressDialog.setMessage("대기");
            progressDialog.setCancelable(false);
            //progressDialog.show();
        });

    }
}