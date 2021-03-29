package kakao.woo95.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class SubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        String msg = getIntent().getStringExtra("data");

        EditText subEditText = (EditText)findViewById(R.id.subEditText);
        subEditText.setText(msg);

        Button btnGoMain = (Button)findViewById(R.id.btnGoMain);
        btnGoMain.setOnClickListener(v -> {
            //subEdit에 입력한 내용을 가져오기
            String callbackstr = subEditText.getText().toString();
            Intent intent = new Intent();
            intent.putExtra("subData",callbackstr);
            setResult(RESULT_OK,intent);
            finish();
        });
    }

    //하위 Activity가 사라질때 호출되는 메소드
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 10:
                switch (resultCode){
                    case RESULT_OK:
                      String msg = data.getStringExtra("subData");
                      EditText editMain = (EditText)findViewById(R.id.mainEdit);
                      editMain.setText(msg);
                }
                break;
        }
    }
}