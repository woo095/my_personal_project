package kakao.woo95.supportlibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //ViewPager의 Adapter 클래스
    class MyPagerAdapter extends FragmentPagerAdapter{

        //Fragment 목록을 저장할 List 생성
        ArrayList<Fragment> fragments;

        //생성자
        public MyPagerAdapter(FragmentManager manager){
            super(manager);

            //프래그먼트 목록 생성
            fragments = new ArrayList<>();
            fragments.add(new OneFragment());
            fragments.add(new ThreeFragment());
        }

        //인덱스에 따라 출력할 Fragment를 설정해주는 메소드
        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn1 = (Button)findViewById(R.id.main_btn1);
        Button btn2 = (Button)findViewById(R.id.main_btn2);
        Button btn3 = (Button)findViewById(R.id.main_btn3);

        OneFragment oneFragment = new OneFragment();
        TwoFragment twoFragment = new TwoFragment();
        ThreeFragment threeFragment = new ThreeFragment();

        //첫번째 화면 출력
        FragmentManager manager = getSupportFragmentManager();

        FragmentTransaction tf = manager.beginTransaction();
        tf.addToBackStack(null);
        tf.add(R.id.main_container, oneFragment);
        tf.commit();

        btn1.setOnClickListener(v -> {
            if(!oneFragment.isVisible()){
                FragmentTransaction tfs = manager.beginTransaction();
                tfs.addToBackStack(null);
                tfs.replace(R.id.main_container, oneFragment);
                tfs.commit();
            }
        });

        btn2.setOnClickListener(v -> {
            if(!twoFragment.isVisible()){
                //DialogFragment는 show 라는 메소드를 호출해서 출력
                twoFragment.show(manager, null);
            }
        });

        btn3.setOnClickListener(v -> {
            if(!threeFragment.isVisible()){
                FragmentTransaction tfs = manager.beginTransaction();
                tfs.addToBackStack(null);
                tfs.replace(R.id.main_container, threeFragment);
                tfs.commit();
            }
        });

        //ViewPager 에 Adapter를 연결
        ViewPager pager = (ViewPager)findViewById(R.id.page);
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
    }
}