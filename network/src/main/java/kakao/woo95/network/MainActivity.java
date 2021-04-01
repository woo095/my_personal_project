package kakao.woo95.network;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            String data = (String)msg.obj;
            TextView tfOutput = (TextView)findViewById(R.id.tfInput);
            tfOutput.setText(data);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tfOutput = (TextView)findViewById(R.id.tfOutput);
        EditText tfInput = (EditText) findViewById(R.id.tfInput);
        Button btnSend = (Button)findViewById(R.id.btnSend);

        btnSend.setOnClickListener(v -> {
            new Thread(() -> {
                try {
                    //서버와 통신할 소켓 생성
                    //host는 IP주소이고 port는 서버의 포트 번호
                    Socket socket = new Socket("192.168.35.100", 11001);
                    //데이터 전송
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(tfInput.getText().toString());
                    oos.flush();

                    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                    String msg = (String)ois.readObject();
                    //tfInput.setText(msg);

                    Message message = new Message();
                    message.obj = msg;
                    handler.sendMessage(message);

                    oos.close();
                    ois.close();
                    socket.close();

                }catch (Exception e){
                    Log.e("예외 발생", e.getLocalizedMessage());
                }finally {

                }
            }).start();
        });
    }
}