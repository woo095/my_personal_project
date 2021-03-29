package kakao.woo95.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnGoSub = (Button)findViewById(R.id.btnGoSub);
        btnGoSub.setOnClickListener(v -> {
            //하위 Activity 출력을 위한 Intent 생성
            //Intent intent = new Intent(MainActivity.this, SubActivity.class);
            //하위 Activity 출력
            //startActivity(intent);

            Intent intent = new Intent();

            EditText mainEdit = (EditText)findViewById(R.id.mainEdit);
            String msg = mainEdit.getText().toString();
            intent.putExtra("data",msg);

            intent.setAction("com.example.ACTION_VIEW");
            //startActivity(intent);

            //SubActivity가 소멸될 때 콜백 메소드를 호출하도록 작성
            //구분을 위해 10을 넣음
            startActivityForResult(intent,10);
        });
    }
}