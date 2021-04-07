package kakao.woo95.adapterview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ListView;

public class MovieLinkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_link);

        //데이터 가져오기
        Intent intent = getIntent();
        String link = intent.getStringExtra("link");

        //WebView 찾아와서 Link를 출력하기
        WebView webView = (WebView)findViewById(R.id.webview);
        //리다이렉트 되는 URL일 때 크롬을 사용하지 않도록 설정
        webView.setWebViewClient(new WebViewClient());

        webView.loadUrl(link);

    }

}