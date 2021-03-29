package kakao.woo95.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class BasicAppActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_app);

        TextView resultText = (TextView)findViewById(R.id.resultText);
        Button btn_contacts = (Button)findViewById(R.id.btn_contacts);
        Button btn_camera = (Button)findViewById(R.id.btn_camera);
        Button btn_call = (Button)findViewById(R.id.btn_call);
        Button btn_browser = (Button)findViewById(R.id.btn_browser);
        Button btn_map = (Button)findViewById(R.id.btn_map);
        Button btn_speech = (Button)findViewById(R.id.btn_speech);
        ImageView resultImage = (ImageView)findViewById(R.id.resultImage);

        btn_map.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:37.5662952,126.977945"));
            startActivity(intent);
        });

        btn_browser.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://cyberadam.cafe24.com"));
            startActivity(intent);
        });

        btn_call.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:010-5537-1743"));
            startActivity(intent);
        });

        btn_speech.setOnClickListener(v -> {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "음성 인식 테스트");
            startActivityForResult(intent,50);
        });

        btn_camera.setOnClickListener(v -> {
            //카메라 앱 Activity 생성
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 30);
        });

        btn_contacts.setOnClickListener(v -> {
            //디바이스의 저장된 데이터 Activity
            Intent intent = new Intent(Intent.ACTION_PICK);
            //주소록 설정
            intent.setData(ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
            startActivityForResult(intent, 10);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        //주소록 앱을 실해앟고 확인
        if(requestCode == 10 && resultCode == RESULT_OK){
            String result = data.getDataString();
            TextView resultText = (TextView)findViewById(R.id.resultText);
            resultText.setText(result);
        }

        //카메라를 실행하고확인을 눌러서 돌아온 경우 처리
        if(requestCode == 30 && resultCode == RESULT_OK){
            Bitmap bitmap = (Bitmap)data.getExtras().get("data");
            ImageView resultImage = (ImageView)findViewById(R.id.resultImage);
            resultImage.setImageBitmap(bitmap);
        }
        //50번 코드와 함께 호출한 Activity 가 종료되고 확인을 눌렀다면면
       if(requestCode == 50 && resultCode == RESULT_OK){
           ArrayList<String> list = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
           String result = list.get(0);
           TextView resultText = (TextView)findViewById(R.id.resultText);
           resultText.setText(result);
        }
    }
}