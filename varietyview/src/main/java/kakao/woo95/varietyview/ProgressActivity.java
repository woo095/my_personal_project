package kakao.woo95.varietyview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class ProgressActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        ProgressBar rect = (ProgressBar)findViewById(R.id.progressbar);
        ProgressBar circle = (ProgressBar)findViewById(R.id.progressbarindicator);
        Button btnStart = (Button)findViewById(R.id.btnStart);
        Button btnStop = (Button)findViewById(R.id.btnStop);

        btnStart.setOnClickListener(v -> {
            rect.setProgress(50);
            circle.setVisibility(View.VISIBLE);
        });

        btnStop.setOnClickListener(v -> {
            rect.setProgress(0);
            circle.setVisibility(View.INVISIBLE);
        });

        TextView lblSeekValue = (TextView)findViewById(R.id.lblSeekValue);
        SeekBar seekBar = (SeekBar)findViewById(R.id.seekbar);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            //값이 변경 될 때 호출되는 메소드
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //현재 값을 TextView에 출력
                String msg = String.format("%d", progress);
                lblSeekValue.setText(progress+"");
            }

            //값을 변경하기 위해서 thumb을 선택했을 때
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            //값의 변경이 종료되고 thumb에서 손 뗄 때
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.e("시크바", "값 변경 종료");
                Snackbar.make(seekBar,"값 변경 종료", Snackbar.LENGTH_LONG).show();
            }
        });
    }
}