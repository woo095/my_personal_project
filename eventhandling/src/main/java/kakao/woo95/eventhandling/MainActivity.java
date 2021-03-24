package kakao.woo95.eventhandling;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    
    //버튼 2개의 참조를 저장하기 위한 변수
    Button btn1stseat, btn2ndseat;

    //클릭 이벤트 처리를 위한 클래스
    class ClickClass implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Snackbar.make(v,"버튼의 클릭 이벤트 처리",Snackbar.LENGTH_LONG).show();
        }
    }

    //버튼을 참조할 변수
    Button btnToast, btnSnackbar, btnDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //버튼 찾아오기
        btnToast = (Button)findViewById(R.id.btnToast);
        btnSnackbar = (Button)findViewById(R.id.btnSnackbar);
        btnDelegate = (Button)findViewById(R.id.btnDelegate);
        
        //버튼 클릭 이벤트 처리
        btnToast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //토스트 출력
                Toast.makeText(getApplicationContext(),"토스트 메시지",Toast.LENGTH_LONG).show();
            }
        });

        btnSnackbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,"스낵바 출력",Snackbar.LENGTH_LONG).setAction("확인", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("태그", "로그를 출력합니다.");
                    }
                }).show();
            }
        });

        //btnDelegate.setOnClickListener(new ClickClass());
        /*btnDelegate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"익명 객체를 이용한 토스트 메시지",Toast.LENGTH_LONG).show();
            }
        });*/

        btnDelegate.setOnClickListener((View v) -> {
            Toast.makeText(getApplicationContext(),"람다식을 이용한 토스트", Toast.LENGTH_LONG).show();
        });

        btn1stseat = (Button)findViewById(R.id.btn1stseat);
        btn2ndseat = (Button)findViewById(R.id.btn2ndseat);

        View.OnClickListener router = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*switch (v.getId()){
                    case R.id.btn1stseat:
                        Toast.makeText(getApplicationContext(),"1번 좌석 예약",Toast.LENGTH_SHORT).show();
                        Log.e("예약", "1번 좌석 예약");
                        break;
                    case R.id.btn2ndseat:
                        Snackbar.make(v,"2번 좌석 예약",Snackbar.LENGTH_LONG).show();
                        Log.e("예약", "2번 좌석 예약");
                        break;

                }*/
                //이벤트가 발생한 뷰의 텍스트 출력
                Button btn = (Button)v;
                Log.e("예약",btn.getText().toString());
            }
        };

        btn1stseat.setOnClickListener(router);
        btn2ndseat.setOnClickListener(router);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Toast.makeText(getApplicationContext(),"터치 이벤트 처리 - Hierarchy Model", Toast.LENGTH_LONG).show();

        return true;
    }

    //백 버튼을 누르면 Activity 종료
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        
        //눌려진 키 값 확인
        if(keyCode == KeyEvent.KEYCODE_BACK){
            finish();//현재 액티비티 종료
        }
        return true;
    }
}