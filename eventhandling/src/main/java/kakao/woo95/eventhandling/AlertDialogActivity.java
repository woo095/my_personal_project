package kakao.woo95.eventhandling;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

public class AlertDialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_dialog);

        Button btnAlert = (Button)findViewById(R.id.btnAlert);
        btnAlert.setOnClickListener(v -> {
            AlertDialog.Builder dlg = new AlertDialog.Builder(AlertDialogActivity.this);
            dlg.setTitle("대화상자 만들기");
            dlg.setMessage("대화상자를 만들었습니다.")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setNegativeButton("취소", (dialog, which) -> {
                        Log.e("로그","대화상자에서 취소를 누르셨습니다.");
                    })
                    .setPositiveButton("확인", (dialog, which) -> {
                        Log.e("로그","대화상자에서 확인을 누르셨습니다.");
                    })
                    .setCancelable(false)
                    .show();
        });
    }
}