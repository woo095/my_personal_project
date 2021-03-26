package kakao.woo95.eventhandling;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class AnimationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        //뷰 찾아오기
        Button btnScale = (Button)findViewById(R.id.btnScale);

        //클릭 이벤트 처리
        btnScale.setOnClickListener(v -> {
            Animation anim = AnimationUtils.loadAnimation(AnimationActivity.this, R.anim.scale);
            v.startAnimation(anim);
        });
    }
}