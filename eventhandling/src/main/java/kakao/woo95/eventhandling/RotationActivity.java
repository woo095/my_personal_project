package kakao.woo95.eventhandling;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.TextView;

public class RotationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotation);

        String key = savedInstanceState.getString("key");

        if(savedInstanceState != null) {

            if (key != null) {
                Log.e("key", key);
            }
        }
    }

    //기기의 환경이 변경 될 때 호출되는 메소드
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        //안드로이드가 제공해주는 기능을 수행하고 내가 원하는 기능을 추가합니다.
        //상위 클래스의 메소드를 호출합니다.
        super.onConfigurationChanged(newConfig);
        TextView lblRotation = (TextView)findViewById(R.id.lblRotation);
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            lblRotation.setText("현재 기기 방향은 가로");
            Log.e("방향", "가로");
        } else {
            lblRotation.setText("현재 기기 방향은 세로");
            Log.e("방향", "세로");
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle bundle) {
        //상위 클래스의 메소드 호출
        super.onSaveInstanceState(bundle);

        bundle.putString("key", "현재 상태 저장");
    }
}