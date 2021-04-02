package kakao.woo95.network;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageDownloadActivity extends AppCompatActivity {

    //핸들러
    Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            //이미지 뷰 찾아오기
            ImageView imageView = (ImageView)findViewById(R.id.imageView);
            //출력할 이미지를 저장하기 위한 변수
            Bitmap bitmap = null;
            switch (msg.what){
                case 1:
                    //데이터 가져오기
                    bitmap = (Bitmap)msg.obj;
                    imageView.setImageBitmap(bitmap);
                    break;
                case 2:
                    //파일이 존재하는 경우
                    Snackbar.make(imageView,"파일이 이미 존재합니다.",Snackbar.LENGTH_LONG).show();

                    String dirPath = Environment.getDataDirectory().getAbsolutePath();
                    String filePath = dirPath + "/data/kakao.woo95.network/files/web.jpg";
                    imageView.setImageBitmap(BitmapFactory.decodeFile(filePath));
                    break;
                case 3:
                    Snackbar.make(imageView,"파일이 없어 다운로드받아 출력합니다.",Snackbar.LENGTH_LONG).show();
                    String dirPath1 = Environment.getDataDirectory().getAbsolutePath();
                    String filePath1 = dirPath1 + "/data/kakao.woo95.network/files/web.jpg";
                    imageView.setImageBitmap(BitmapFactory.decodeFile(filePath1));
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_download);
        
        //이미지 다운로드 받아서 바로 출력하는 버튼의 이벤트 핸들러 작성
        Button btnDisplay = (Button)findViewById(R.id.btnDisplay);
        //Anonymous Class 이용해서 이벤트 처리 - Java 의 Nested Class, 람다 복습
        btnDisplay.setOnClickListener(v -> {
            new Thread(){
                @Override
                public void run() {
                    super.run();
                    try {
                        //이미지를 다운 받을 스트림 생성
                        InputStream is = new URL("http://img.danawa.com/prod_img/500000/749/051/img/10051749_1.jpg").openStream();
                        //이미지를 가져와서 bitmap에 저장
                        Bitmap bitmap = BitmapFactory.decodeStream(is);
                        //핸들러에게 전송할 메시지를 생성
                        Message message = new Message();
                        message.what = 1;
                        message.obj = bitmap;
                        //핸들러에게 메시지 전송
                        handler.sendMessage(message);
                    } catch (Exception e){
                        Log.e("예외",e.getLocalizedMessage());
                    }
                }
            }.start();
        });

        //두번째 버튼의 클릭 이벤트 핸들러
        Button btnSave = (Button)findViewById(R.id.btnSave);
        btnSave.setOnClickListener(v -> {
            //현재 앱 내에 파일이 존재하는지 확인 - web.jpg
            //파일 경로 생성
            //Data가 저장되는 Directory 경로를 생성
            String dirPath = Environment.getDataDirectory().getAbsolutePath();
            //파일 경로 생성 - 자기 패키지의 경로이름
            //web.jpg 저장할 파일 이름
            String path = dirPath + "/data/kakao.woo95.network/files/web.jpg";

            //파일이 있는 지 확인
            //File 클래스를 다시 공부할 때 존재여부, 마지막 수정 날짜, 생성 날짜, 크기를 가져오는 것이 중요합니다.
            if(new File(path).exists()){
                //핸들러에게 전송할 메시지를 생성해서 출력을 요청
                //이미 파일이 있는 경우 이므로 데이터를 전달할 필요가 없습니다.
                Message message = new Message();
                message.what = 2;
                handler.sendMessage(message);
            }else{
                //이미지 파일이 없는 경우 이미지 파일을 다운로드 받아서 저장하고 핸들러에게 메시지를 전송
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        try {
                            //다운로드 받을 URL 생성
                            URL url = new URL("http://img.danawa.com/prod_img/500000/749/051/img/10051749_1.jpg");
                            //연결
                            HttpURLConnection con = (HttpURLConnection)url.openConnection();
                            //옵션 설정
                            con.setConnectTimeout(300000);
                            con.setUseCaches(false);

                            //텍스트가 아닌 데이터를 가져오기 위한 스트림을 생성
                            InputStream is = con.getInputStream();
                            //기록할 파일 스트림을 생성
                            FileOutputStream fos = openFileOutput("web.jpg",0);

                            //데이터를 임시를 저장할 바이트 배열을 생성
                            byte[] raster =new byte[con.getContentLength()];

                            //다운로드 받아 저장하기
                            while(true){
                                int read = is.read(raster);
                                if(read <=0){
                                    break;
                                }
                                fos.write(raster,0,read);
                            }
                            is.close();
                            fos.close();
                            con.disconnect();

                            Message msg = new Message();
                            msg.what = 3;
                            handler.sendMessage(msg);
                        }catch (Exception e){
                            Log.e("예외 발생 num2", e.getLocalizedMessage());
                        }
                    }
                }.start();
            }
        });
    }
}