package kakao.woo95.localdata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class ItemActivity extends AppCompatActivity {

    EditText itemid, itemname, quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        itemid = (EditText)findViewById(R.id.itemid);
        itemname = (EditText)findViewById(R.id.itemname);
        quantity = (EditText)findViewById(R.id.quantity);

        Button btnInsert = (Button)findViewById(R.id.btnInsert);
        btnInsert.setOnClickListener(v -> {
            //데이터베이스 연동 클래스 객체를 생성
            MyDBHandler handler = new MyDBHandler(ItemActivity.this);
            
            //삽입할 데이터 만들기
            Item item = new Item();
            item.set_itemname(itemname.getText().toString());
            item.set_quantity(Integer.parseInt(quantity.getText().toString()));

            handler.addItem(item);
            
            //입력 도구들을 초기화
            itemname.setText("");
            quantity.setText("");
            itemid.setText("");

            //키보드 숨기기
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(itemname.getWindowToken(), 0);
            imm.hideSoftInputFromWindow(quantity.getWindowToken(), 0);
            imm.hideSoftInputFromWindow(itemid.getWindowToken(), 0);

            Toast.makeText(getApplicationContext(),"삽입 실행 완료",Toast.LENGTH_LONG).show();
            //Snackbar.make(v, "삽입 실행 완료",Snackbar.LENGTH_LONG).show();
        });

        Button btnSelect = (Button)findViewById(R.id.btnSelect);
        btnSelect.setOnClickListener(v -> {
            String query = itemname.getText().toString();
            //조회하기전 유효성 검사
            if(query == null && query.trim().length() == 0){
                return;
            }

            MyDBHandler handler = new MyDBHandler(ItemActivity.this);
            Item item = handler.findItem(query);
            if(item == null){
                itemid.setText("일치하는 데이터가 없습니다.");
            }else {
                itemid.setText(item.get_id() + "");
                quantity.setText(item.get_quantity() + "");
            }
        });

        Button btnDelete = (Button)findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(v -> {
            String query = itemname.getText().toString();
            //유효성 검사
            if(query == null && query.trim().length() == 0){
                itemname.setText("삭제할 이름을 입력하세요");
            } else {
                MyDBHandler handler = new MyDBHandler(ItemActivity.this);
                handler.deleteItem(query);
                //itemid.setText("삭제 성공");
                Toast.makeText(getApplicationContext(),"삭제 실행 완료",Toast.LENGTH_LONG).show();
            }
        });
    }
}