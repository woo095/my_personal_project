package kakao.woo95.adapterview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class ListViewActivity extends AppCompatActivity {
    
    //ListView 출력 관련 프로퍼티
    ListView listview;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        //ListView 출력 관련 초기화 작업
        listview = (ListView)findViewById(R.id.listview);
        list = new ArrayList<>();
        list.add("IoC");
        list.add("DI");
        list.add("AOP");
        
        //android.R 로 시작하면 안드로이드에서 제공하는 리소스
        //R 로 시작하면 사용자가 삽입한 리소스
        //adapter = new ArrayAdapter<>(ListViewActivity.this, android.R.layout.simple_list_item_1,list);

        adapter = new ArrayAdapter<>(ListViewActivity.this, android.R.layout.simple_list_item_single_choice,list);

        //listview에 adapter 설정
        listview.setAdapter(adapter);

        //구분선 설정
        listview.setDivider(new ColorDrawable(Color.CYAN));
        listview.setDividerHeight(3);
        //listview의 선택모드 설정
        listview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = list.get(position);
                Snackbar.make(getWindow().getDecorView().getRootView(),item, Snackbar.LENGTH_LONG).show();
            }
        });

        Button btninsert = (Button)findViewById(R.id.btninsert);
        btninsert.setOnClickListener(v -> {
            //입력한 내용 가져오기
            EditText newitem = (EditText)findViewById(R.id.newitem);
            String item = newitem.getText().toString().trim();
            //전송하기 전에 유효성 검사를 수행 - NULL 체크, 중복 검사 등
            if(item.length() <= 0){
                Snackbar.make(getWindow().getDecorView().getRootView(),"항목이 비어있습니다.",Snackbar.LENGTH_LONG).show();
                return;
            }
            /*if(list.contains(item)){//중복 검사
                Snackbar.make(getWindow().getDecorView().getRootView(),"아이템은 중복 될 수 없습니다.",Snackbar.LENGTH_LONG).show();
                return;
            }*/
            for(String spring:list){
                if(spring.toUpperCase().equals(item.toUpperCase())){
                    Snackbar.make(getWindow().getDecorView().getRootView(),"아이템은 중복 될 수 없습니다.",Snackbar.LENGTH_LONG).show();
                    return;
                }
            }
            //list에 삽입
            list.add(item);
            //listview 업데이트
            adapter.notifyDataSetChanged();
            newitem.setText("");
            Snackbar.make(getWindow().getDecorView().getRootView(),"아이템이 정상적으로 추가되었습니다.",Snackbar.LENGTH_LONG).show();

            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(newitem.getWindowToken(), 0);
        });

        //삭제 버튼
        Button btndelete = (Button)findViewById(R.id.btndelete);
        btndelete.setOnClickListener(v -> {
            //작업을 수행할 데이터 유효성 검사
            //작업을 수행
            //수행 결과를 출력

            int pos = listview.getCheckedItemPosition();
            
            if(pos < 0 || pos >= list.size()){
                Snackbar.make(getWindow().getDecorView().getRootView(),"아이템이 선택되지 않아서 삭제할 수 없습니다.",Snackbar.LENGTH_LONG).show();
                return;
            }

            //작업을 수행
            list.remove(pos);
            adapter.notifyDataSetChanged();;

            listview.clearChoices();
        });
    }
}