package kakao.woo95.localdata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText)findViewById(R.id.editText);
        Button btnResource = (Button)findViewById(R.id.btnResource);
        btnResource.setOnClickListener(v -> {
            try {
                InputStream fis = getResources().openRawResource(R.raw.creed);
                //데이터 읽기
                byte[] data = new byte[fis.available()];
                while(fis.read(data)!=-1){

                }
                //스트림닫기
                fis.close();
                //출력
                editText.setText(new String(data));
            } catch (Exception e){
                Log.e("예외",e.getLocalizedMessage());
            }
        });

        Button btnWrite = (Button)findViewById(R.id.btnWrite);
        btnWrite.setOnClickListener(v -> {
            try {
                FileOutputStream fos = openFileOutput("test.txt", Context.MODE_PRIVATE);
                fos.write("the 조은 컴퓨터 학원".getBytes());
                fos.close();
                //editText.setText("기록 성공!");
                Toast.makeText(getApplicationContext(),"기록 성공",Toast.LENGTH_LONG).show();
            } catch (Exception e){
                Log.e("예외", e.getLocalizedMessage());
            }
        });
        Button btnRead = (Button)findViewById(R.id.btnRead);
        btnRead.setOnClickListener(v -> {
            try {
                FileInputStream fis = openFileInput("test.txt");
                byte [] data = new byte[fis.available()];
                while(fis.read(data) != -1){

                }
                fis.close();
                //editText.setText("기록 성공!");
                editText.setText(new String(data));
                Toast.makeText(getApplicationContext(),"읽기 성공",Toast.LENGTH_LONG).show();
            } catch (Exception e){
                Log.e("예외", e.getLocalizedMessage());
            }
        });
    }
}