package kakao.woo95.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

public class SettingActivity extends AppCompatActivity {

    private void showMessage(String msg){
        EditText edit = (EditText)findViewById(R.id.editText);
        Snackbar.make(edit, msg, Snackbar.LENGTH_LONG).show();
        Log.e("message:",msg);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        EditText editText = (EditText)findViewById(R.id.editText);
        Button btnToggle = (Button)findViewById(R.id.btnToggle);
        btnToggle.setOnClickListener(v -> {
            InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        });
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.activity_setting);
        if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            showMessage("세로");
        } else {
            showMessage("가로");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        showMessage("액티비티 활성화");
    }

    @Override
    protected void onPause() {
        super.onPause();
        showMessage("액티비티 비활성화");
    }
}