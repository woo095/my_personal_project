package kakao.woo95.adapterview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MovieListActivity extends AppCompatActivity {

    //다운로드 진행 상황을 표시할 뷰
    private ProgressBar downloadview;

    //파싱한 결과 데이터를 저장할 프로퍼티를 생성
    private int count;
    private ArrayList<Movie> movieList;

    ListView listView;
    //ArrayAdapter<Movie> adapter;
    //사용자 항목 뷰를 사용하기 위한 adapter
    MovieAdapter adapter;
    
    
    //ListView의 데이터를 다시 출력할 핸들러
    Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            //listView 재출력
            adapter.notifyDataSetChanged();

            downloadview.setVisibility(View.INVISIBLE);

            //스레드 변수를 초기화
            th = null;
        }
    };

    //마지막 항목 뷰가 스크롤에 의해서 보진 것인지를 판별할 프로퍼티티
   boolean lastItemVisibleFlag = false;

    int page =1;//페이지 번호를 저장할 프로퍼티

    //다운로드 받고 JSON파싱을 수행한 후 재출력을 위해 핸들러를 호출하는 스레드를 생성하고 실행
    class ThreadEx extends Thread{
        @Override
        public void run() {
            super.run();
            try {
                URL url = new URL("http://cyberadam.cafe24.com/movie/list?page="+page);
                //HttpURLConnection 설정
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                //옵션 설정
                con.setRequestMethod("GET");
                con.setConnectTimeout(300000);
                con.setUseCaches(false);

                StringBuilder sb = new StringBuilder();
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

                //문자열을 줄 단위로 읽어서 sb에 추가
                while (true){
                    //한 줄을 가져오기기
                    String line = br.readLine();
                    if(line == null){
                        break;
                    }
                    //읽은 내용이 있을 때는 line에 추가
                    sb.append(line + "\n");
                }

                //연결 해제
                br.close();
                con.disconnect();

                //데이터 확인
                //Log.e("다운로드 받은 문자열",sb.toString());

                //전체 문자열을 JSONObject로 변환
                JSONObject root = new JSONObject(sb.toString());

                //데이터 갯수 가져오기
                count = root.getInt("count");

                //영화 목록 가져오기
                JSONArray movies = root.getJSONArray("list");
                //영화목록 순회하기
                for(int i=0;i<movies.length();i++){
                    //하나의 데이터 가져오기
                    JSONObject item = movies.getJSONObject(i);
                    //하나의 행을 저장할 객체 생성
                    Movie movie = new Movie();

                    //각 속성을 가져와서 movie에 대입
                    movie.setTitle(item.getString("title"));
                    movie.setSubtitle(item.getString("subtitle"));
                    movie.setLink(item.getString("link"));
                    movie.setRating(item.getDouble("rating"));
                    movie.setThumbnail(item.getString("thumbnail"));

                    //파싱한 데이터를 List에 대입
                    //movieList.add(movie); // 마지막에 추가하는 방식

                    //첫번째에 추가
                   movieList.add(0,movie);
                }

                //Log.e("파싱한 데이터", movieList.toString());

                //핸들러에게 메시지 전송 (Thread 에선 ListView 변환이 안되기 때문에)
                Message msg = new Message();
                handler.sendMessage(msg);

                //보낼 데이터가 없을 시 아래처럼 작성해도 됨
                //handler.sendEmptyMessage(1);

            } catch (Exception e){
                Log.e("다운로드 또는 파싱 예외",e.getLocalizedMessage());
            }
        }
    }

    //스레드 클래스의 객체를 참조할 프로퍼티
    //여기에 만든 이유는 스레드가 동작 중이면 다른 스레드를 생성하지 못하도록 하기 위해서서
   ThreadEx th;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        //영화 목록을 저장한 List 생성
        movieList = new ArrayList<>();

        listView = (ListView)findViewById(R.id.listview);
        //adapter = new ArrayAdapter<>(MovieListActivity.this, android.R.layout.simple_list_item_1, movieList);
        adapter = new MovieAdapter(MovieListActivity.this, movieList);

        listView.setAdapter(adapter);
        listView.setDivider(new ColorDrawable(Color.CYAN));
        listView.setDividerHeight(3);

        //진행 상황 표시할 뷰를 찾아오기
        downloadview = (ProgressBar)findViewById(R.id.downloadview);
        //화면에 출력
        downloadview.setVisibility(View.VISIBLE);

        //이전 스레드 동작이 없다면 중지
        if(th != null){
            return;
        }

        //이전 스레드가 엇다면 스레드를 생성해서 데이터 가져오기 수행행
        th = new ThreadEx();
        th.start();


        listView.setOnItemClickListener((parent, view, position, id) -> {
            String link = movieList.get(position).getLink();
            Intent intent = new Intent(MovieListActivity.this, MovieLinkActivity.class);
            //전달할 데이터를 저장
            intent.putExtra("link",link);
            startActivity(intent);
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE 
                        && lastItemVisibleFlag == true){
                    //페이지 번호 증가
                    page = page + 1;
                    //전체 데이터 출력했는지 확인
                    //10은 페이지 당 데이터 갯수
                    if(page * 10 >= count){
                        Snackbar.make(view, "더 이상 출력할 데이터가 없습니다.", Snackbar.LENGTH_LONG).show();
                        return;
                    }
                    //스레드가 동작 중이면 중지
                    if(th != null){
                        return;
                    }
                    
                    //스레드가 동작 중이 아닐 때만 작업 수행
                    downloadview.setVisibility(View.VISIBLE);
                    th = new ThreadEx();
                    th.start();
                }
            }

            //스크롤 하는 도중 발생
            //firstVisibleItem 은 현재 보여지고 있는 항목 중에서 첫번째 항목의 인덱스
            //visibleItemCount 은 보여지고 있는 데이터의 갯수
            //totalItemCount 은 전체 데이터 갯수
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                //마지막에서 스크롤 한 것인지 판단 여부를 저장하는 프로퍼티의 값을 설정
                //데이터가 있고 첫번째 인덱스와 보여진 데이터 갯수를 더한 값이 전체 데이터 갯수보다 크거나 같다면 lastItemVisibleFlag는 true
                lastItemVisibleFlag = totalItemCount > 0 && firstVisibleItem + visibleItemCount >= totalItemCount;
            }

            //스와이프 레이아웃의 리프레시 이벤트 처리


        });

        SwipeRefreshLayout swipe_layout = (SwipeRefreshLayout)findViewById(R.id.swipe_layout);
        swipe_layout.setOnRefreshListener(() -> {
            page = page + 1;
            if(page * 10 >= count){
                Snackbar.make(getWindow().getDecorView().getRootView(),"업데이트할 데이터가 없습니다.",Snackbar.LENGTH_LONG).show();
                return;
            }
            
            //다운로드 중이면 다운로드 안함
            if(th != null){
                return;
            }

            downloadview.setVisibility(View.VISIBLE);
            th = new ThreadEx();
            th.start();
            swipe_layout.setRefreshing(false);
        });

    }
}