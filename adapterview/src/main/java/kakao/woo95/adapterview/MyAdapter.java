package kakao.woo95.adapterview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MyAdapter extends BaseAdapter {
    //뷰 전개를 위한 Context 프로퍼티
    Context context;
    //출력할 데이터 모임
    List<DataStructure> data;

    //뷰 전개(XML 파일의 내용을 자바 코드로 변경 하는 것)를 위한 프로퍼티
    LayoutInflater inflater;

    //외부로부터 Context 와 List를 주입(Injection)받기 위한 생성자
    public MyAdapter(Context context, List<DataStructure> data){
        this.context = context;
        this.data = data;
        //inflater를 생성
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    //ListView에 출력되는 데이터의 갯수를 설정하는 메소드
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position).getName();
    }

    //postition을 그대로 리턴하는 것이 일반적
    @Override
    public long getItemId(int position) {
        return position;
    }

    //실제 항목 뷰를 생성하는 메소드
    //position - 인덱스
    //convertView -  이전에 출력한 뷰로 재사용을 위해 전달 - null 일 수 있음
    //parent - ListView
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //재사용 위한 뷰가 없으면 생성성
       if(convertView == null){
            convertView = inflater.inflate(R.layout.icontent,parent,false);
        }

       //이미지 출력
        //이미지 뷰를 찾아올 때 부모 뷰를 기준으로 찾아와야 합니다.
        ImageView imageView = (ImageView)convertView.findViewById(R.id.img);

        imageView.setImageResource(data.get(position).getIcon());

        TextView textView = (TextView)convertView.findViewById(R.id.text);
        textView.setText(data.get(position).getName());

        Button button = (Button)convertView.findViewById(R.id.btn);
        button.setOnClickListener(v -> {
            //데이터 찾아오기
            DataStructure ds = data.get(position);
            //출력하기
            Snackbar.make(v, ds.getName() + ":" + ds.getDescription(),
                    Snackbar.LENGTH_LONG).show();
        });
        return convertView;
    }
}
