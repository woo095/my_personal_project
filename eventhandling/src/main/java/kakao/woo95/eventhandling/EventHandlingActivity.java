package kakao.woo95.eventhandling;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

public class EventHandlingActivity extends AppCompatActivity {

    LinearLayout container;
    EditText tfEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_handling);

        container = (LinearLayout)findViewById(R.id.container);
        tfEdit = (EditText)findViewById(R.id.tfEdit);

        //container 터치 이벤트 정리
        container.setOnTouchListener((v, event) -> {
            //키보드 관리 객체를 가져옴
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(tfEdit.getWindowToken(), 0);
            return false;
        });
    }
}