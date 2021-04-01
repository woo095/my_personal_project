package kakao.woo95.localdata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class PrefActivity extends AppCompatActivity {

    EditText tfInput;
    CheckBox ckSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pref);

        tfInput = (EditText)findViewById(R.id.tfInput);
        ckSave = (CheckBox)findViewById(R.id.ckSave);

        Button btnSave = (Button)findViewById(R.id.btnSave);
        btnSave.setOnClickListener(v -> {
            SharedPreferences preferences = getSharedPreferences("PrefActivity", Context.MODE_PRIVATE);
            preferences.edit().putString("name", tfInput.getText().toString()).apply();
            preferences.edit().putBoolean("save", ckSave.isChecked()).apply();
        });
        Button btnLoad = (Button)findViewById(R.id.btnLoad);
        btnLoad.setOnClickListener(v -> {
            SharedPreferences preferences = getSharedPreferences("PrefActivity", Context.MODE_PRIVATE);
            String name = preferences.getString("name", "noname");
            boolean save = preferences.getBoolean("save",false);
            tfInput.setText(name);
            ckSave.setChecked(save);
        });
    }
}