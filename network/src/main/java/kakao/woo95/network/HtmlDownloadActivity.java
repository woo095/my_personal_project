package kakao.woo95.network;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HtmlDownloadActivity extends AppCompatActivity {

    Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            String html = (String)msg.obj;
            TextView tfResult = (TextView)findViewById(R.id.tfResult);
            tfResult.setText(html);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_html_download);

        //EditText 와 Button을 찾아오기
        EditText tfUrl = (EditText)findViewById(R.id.tfUrl);
        Button btnDownload = (Button)findViewById(R.id.btnDownload);

        //버튼 클릭 이벤트 처리
        btnDownload.setOnClickListener(v -> {
            new Thread(){
                @Override
                public void run() {
                    super.run();
                    try {
                        //다운로드 받을 URL 생성
                        URL url = new URL(tfUrl.getText().toString());
                        //연결 객체 생성
                        HttpURLConnection con = (HttpURLConnection)url.openConnection();
                        
                        //필요한 옵션 설정
                        con.setConnectTimeout(30000);//연결 제한 시간 설정 (30초)
                        con.setUseCaches(false); //캐시 사용 안함
                        //자주 변경되는 데이터는 false
                        //잘 변경 되지 않는 데이터는 true
                        con.setRequestMethod("GET");//GET 생략 가능
                        //GET 생략 가능
                        
                        //응답이 제대로 오면
                        if(con.getResponseCode() == HttpURLConnection.HTTP_OK){
                            //전송된 문자열 읽기 - 무조건 UTF-8로 읽어옵니다.
                            //BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                            BufferedReader br;
                            //연결된 곳의 헤더 정보를 읽어옵니다.
                            String headerType = con.getContentType();
                            //문자열 비교 - EUC-KR이 포함되어 있는지 확인
                            if(headerType.toUpperCase().indexOf("EUC-KR") >= 0){
                                br = new BufferedReader(new InputStreamReader(con.getInputStream(),"EUC-KR"));
                            }else {//EUC-KR이 없을경우
                                br = new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));
                            }
                            

                            //문자열을 추가할 StringBuilder 생성
                            StringBuilder sb = new StringBuilder();
                            //문자열을 줄단위로 읽어서 sb에 추가
                            while(true){
                                String line = br.readLine();// 한 줄 읽기
                                //읽은 게 없으면 읽기 종료
                                if(line == null){
                                    break;
                                }
                                sb.append(line + "\n");
                            }
                            //연결 해제
                            br.close();
                            con.disconnect();

                            //핸들러에게 메시지를 전송
                            Message msg = new Message();
                            msg.obj = sb.toString();
                            handler.sendMessage(msg);
                        }
                    }catch (Exception e){
                        Log.e("예외",e.getLocalizedMessage());
                    }
                }
            }.start();
        });
    }
}