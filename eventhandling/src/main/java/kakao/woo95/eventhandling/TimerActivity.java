package kakao.woo95.eventhandling;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SimpleTimeZone;

public class TimerActivity extends AppCompatActivity {

    //참조할 변수 선언
    TextView lblTime;
    Button btnStart, btnStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        //뷰의 참조 찾아오기
        lblTime = (TextView)findViewById(R.id.lblTime);
        btnStart = (Button)findViewById(R.id.btnStart);
        btnStop = (Button)findViewById(R.id.btnStop);
        
        //타이머 생성 - 1초마다 수행
        CountDownTimer timer = new CountDownTimer(10000 * 10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Date date = new Date();
                //날짜 및 시간을 문자열로 만들기 위한 포맷 설정
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                // 포맷에 맞춘 문자열을 생성
                String time = sdf.format(date);
                lblTime.setText(time);
            }

            @Override
            public void onFinish() {
                lblTime.setText("타이머 종료");
            }
        };

        //버튼 클릭했을때 처리 - 시작
        btnStart.setOnClickListener(v -> {
            timer.start();
        });

        //버튼 클릭했을때 처리 - 끝
        btnStop.setOnClickListener(v -> {
            timer.cancel();
        });

        //2초마다 주기적으로 텍스트뷰에 텍스트를 출력
        try{
            for(int i =0;i<10;i++){
                Thread.sleep(2000);
                lblTime.setText("i="+i);//한꺼번에 모아 출력하기에 9로 출력됨
                Log.e("i", i + "");
            }
        } catch (Exception e){

        }
    }
}