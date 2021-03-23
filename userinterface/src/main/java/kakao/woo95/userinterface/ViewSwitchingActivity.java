package kakao.woo95.userinterface;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class ViewSwitchingActivity extends AppCompatActivity {

    Button BtnPG1, BtnPG2, BtnPG3;
    LinearLayout Page1,Page2,Page3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_switching);

        BtnPG1 = (Button)findViewById(R.id.btnpg1);
        BtnPG2 = (Button)findViewById(R.id.btnpg2);
        BtnPG3 = (Button)findViewById(R.id.btnpg3);

        Page1 = (LinearLayout)findViewById(R.id.page1);
        Page2 = (LinearLayout)findViewById(R.id.page2);
        Page3 = (LinearLayout)findViewById(R.id.page3);

        BtnPG1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Page1.setVisibility(View.VISIBLE);
                Page2.setVisibility(View.INVISIBLE);
                Page3.setVisibility(View.INVISIBLE);

                BtnPG1.setVisibility(View.INVISIBLE);
                BtnPG2.setVisibility(View.VISIBLE);
                BtnPG3.setVisibility(View.VISIBLE);
            }
        });

        BtnPG2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Page1.setVisibility(View.INVISIBLE);
                Page2.setVisibility(View.VISIBLE);
                Page3.setVisibility(View.INVISIBLE);

                BtnPG1.setVisibility(View.VISIBLE);
                BtnPG2.setVisibility(View.INVISIBLE);
                BtnPG3.setVisibility(View.VISIBLE);
            }
        });

        BtnPG3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Page1.setVisibility(View.INVISIBLE);
                Page2.setVisibility(View.INVISIBLE);
                Page3.setVisibility(View.VISIBLE);

                BtnPG1.setVisibility(View.VISIBLE);
                BtnPG2.setVisibility(View.VISIBLE);
                BtnPG3.setVisibility(View.INVISIBLE);
            }
        });
    }
}