package kakao.woo95.thread;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    //Handler 객체 생성
    Handler handler = new Handler(Looper.getMainLooper()){
        //핸드러가 메시지를 받으면 호출되는 메소드
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            //데이터를 받아서 UI를 갱신
            //전송받은 데이터 가져오기 - 원래의 자료형으로 변환
            //Object 자료형은 참조형으로만 형변환이 가능한데 1.8 버전 부터는 기본형으로도
            //변환이 가능
            int i = (Integer)msg.obj;
            //TextView에 i를 출력
            TextView resultView = (TextView)findViewById(R.id.resultView);
            resultView.setText(i + "");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*new Thread(){
            int value=0;
            @Override
            public void run() {
                super.run();
                try {
                    for(int i=0; i<10;i++){
                        Thread.sleep(1000);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                TextView resultView = (TextView)findViewById(R.id.resultView);
                                resultView.setText(value + "");
                                value++;
                            }
                        });
                    }
                }catch (Exception e){

                }
            }
        }.start();*/


        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    for(int i=0;i<10;i++){
                        Thread.sleep(1000);
                        Message msg = new Message();
                        msg.obj = i;
                        handler.sendMessage(msg);
                    }
                }catch (Exception e){
                    Log.e("예외", e.getLocalizedMessage());
                }
            }
        }.start();
/*
        //Thread.sleep을 사용하기 위한 예외 처리
        try {
            *//*for(int i=0;i<10;i=i+1){
                //1초 대기
                Thread.sleep(1000);
                Log.e("내용","메시지1");
            }
            for(int i=0;i<10;i=i+1){
                //1초 대기
                Thread.sleep(1000);
                Log.e("내용","메시지2");
            }*//*
            *//*new Thread(){
                public void run(){
                    try {
                        for(int i = 0;i<10;i=i+1){
                            Thread.sleep(1000);
                            Log.e("내용","스레드1");
                        }
                    }
                    catch (Exception e){

                    }
                }
            }.start();

            new Thread(){
                public void run(){
                    try {
                        for(int i = 0;i<10;i=i+1){
                            Thread.sleep(1000);
                            Log.e("내용","스레드2");
                        }
                    }
                    catch (Exception e){

                    }
                }
            }.start();*//*



            for(int i =0;i<10;i=i+1){
                Thread.sleep(1000);
                Log.e("메시지", "i=" + i);
                resultView.setText("i="+i);
            }
        }catch (Exception e){
            Log.e("예외",e.getLocalizedMessage());
        }*/
    }
}