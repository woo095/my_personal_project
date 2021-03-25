package kakao.woo95.eventhandling;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.LinkedList;

public class AlertActivity extends AppCompatActivity {

    private String mylangs;
    private String db;

    private String[] languages;

    //여러 개를 선택 했을 때 저장할 문자열 List
    private LinkedList<String> myLanguageList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);

        Context thisapp = AlertActivity.this;

        Button btnAlert;
        //final String[] mylangs = new String[1];

        String [] dbs;
        //final String[] db = new String[1];

        btnAlert = (Button)findViewById(R.id.btnAlert);

        btnAlert.setOnClickListener(v -> {
            AlertDialog.Builder dlg = new AlertDialog.Builder(thisapp);
            dlg.setTitle("제목")
                    .setMessage("메시지")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setNegativeButton("취소", (dialog, which) -> {
                        Toast.makeText(AlertActivity.this,
                                "토스트 출력",
                                Toast.LENGTH_LONG).show();
                        Log.e("로그", "대화상자가 닫히고 난 후 출력");
                    })
                    .show();
        });

        //선택 대화상자 출력하기
        Button btnItemDialog = (Button)findViewById(R.id.btnItemDialog);

        //클릭 했을때 처리
        btnItemDialog.setOnClickListener(v -> {
            new AlertDialog.Builder(thisapp)
                    .setTitle("내가 사용할 언어")
                    .setIcon(android.R.drawable.ic_lock_idle_lock)
                    .setItems(R.array.languages, (dialog, which) -> {
                        String [] langs = getResources().getStringArray(R.array.languages);
                        mylangs = langs[which];
                        Log.e("lang", mylangs);
                    }).show();
        });

        dbs = new String[]{
          "oracle","mysql","mariadb","mssql","access","sqlite"
        };

        languages = new String[]{
                "oracle","mysql","mariadb","mssql","access","sqlite","mysql","mariadb","mssql","access","sqlite","mysql","mariadb","mssql","access","sqlite"
        };

        Button btnOneDB = (Button)findViewById(R.id.btnOnedb);
        btnOneDB.setOnClickListener(v -> {
            new AlertDialog.Builder(thisapp)
                    .setTitle("언어를 하나 고르세요!!!")
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setSingleChoiceItems(dbs,0,(dialog, which) -> {
                        db = dbs[which];
                        Log.e("선택한 언어", db);
                    })
                    .show();
        });

        myLanguageList = new LinkedList<>();

        Button btnMultilanguage = (Button)findViewById(R.id.btnMultiLanguage);
        btnMultilanguage.setOnClickListener(v -> {
            new AlertDialog.Builder(thisapp)
                    .setTitle("좋아하는 요리를 모두 선택하세요")
                    .setIcon(android.R.drawable.ic_dialog_map)
                    .setMultiChoiceItems(languages, new boolean[]{
                            false,false,false,false,
                            false,false,false,false,
                            false,false,false,false,
                            false,false,false,false,
                            false,false,false,false,
                            false,false
                    }, (dialog, which, isChecked) -> { //onsetmultichoicelistener
                        if(isChecked == true){
                            myLanguageList.add(languages[which]);
                        } else {
                            myLanguageList.remove(languages[which]);
                        }
                    })
                    .show();
        });

        Button btnLogin = (Button)findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(v -> {
            LinearLayout login = (LinearLayout) View.inflate(thisapp,R.layout.login,null);
            new AlertDialog.Builder(thisapp)
                    .setTitle("로그인")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setView(login)
                    .setNegativeButton("취소",null)
                    .setPositiveButton("로그인", (dialog, which) -> {//DialogInterface.OnClickLinstener
                        EditText tfId = login.findViewById(R.id.tfId);
                        EditText tfPassword = login.findViewById(R.id.tfpwd);

                        Log.e("아이디", tfId.getText().toString());
                        Log.e("비밀번호",tfPassword.getText().toString());
                    })
                    .show();
        });
    }
}