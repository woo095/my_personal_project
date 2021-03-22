package kakao.woo95.userinterface;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

public class keyboardManageActivity extends AppCompatActivity {

    EditText tfText;
    Button BtnKeyboardShow, BtnKeyboardHide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyboard_manage);

        tfText = (EditText)findViewById(R.id.tftext);
        BtnKeyboardShow = (Button)findViewById(R.id.btnkeyboardshow);
        BtnKeyboardHide = (Button)findViewById(R.id.btnkeyboardhide);

        BtnKeyboardShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager iMM = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                iMM.showSoftInput(tfText, 0);
            }
        });

        BtnKeyboardHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager iMM = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                iMM.hideSoftInputFromWindow(tfText.getWindowToken(), 0);
            }
        });
    }
}