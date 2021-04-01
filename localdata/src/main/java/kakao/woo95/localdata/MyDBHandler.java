package kakao.woo95.localdata;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDBHandler extends SQLiteOpenHelper {

    //생성자 - SQLiteOpenHelper가 Default Constructor가 없어서 반드시 생성
    public MyDBHandler(@Nullable Context context) {
        super(context, "item.db", null,1);
    }

    //데이터베이스가 없으면 호출되는 문구
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table item(_id integer primary key," +
                "itemname text, quantity integer)");
    }

    //데이터베이스 버전이 변경도니 경우 호출되는 메소드
    //기존 테이블을 제거 후 새 테이블을 만든다.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //테이블 제거
        db.execSQL("drop table item");
        onCreate(db);
    }
    
    //데이터를 삽입하는 메소드
    public void addItem(Item item){
        //ContentValues를 이용한 삽입
        
        //삽입할 객체를 생성
        ContentValues row = new ContentValues();
        row.put("itemname", item.get_itemname());
        row.put("quantity", item.get_quantity());
        
        //데이터베이스에 접속해서 row 삽입
        SQLiteDatabase db = getWritableDatabase();
        db.insert("item", null,row);
        db.close();
    }

    //검색
    public Item findItem(String itemname){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from item where itemname = " + "\"" + itemname + "\"",null);

        Item item = new Item();
        //1개인 경우엔 if 여러 개인 경우는 while
        if(cursor.moveToFirst()){
            cursor.moveToFirst();
            item.set_id(Integer.parseInt(cursor.getString(0)));
            item.set_itemname(cursor.getString(1));
            item.set_quantity(Integer.parseInt(cursor.getString(2)));
            cursor.close();
        } else { //없을경우 null 리턴
            item = null;
        }
        db.close();
        return item;
    }

    //itemname을 받아서 삭제하는 메소드
    public void deleteItem(String itemname){
        SQLiteDatabase db = getWritableDatabase();
        //?를 이용해서 파라미터를 바인딩 한후 SQL을 실행행
       db.execSQL("delete from item where itemname = ?",
                new String[]{itemname});
    }
}
