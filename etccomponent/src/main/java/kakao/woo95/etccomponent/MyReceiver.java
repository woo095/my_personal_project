package kakao.woo95.etccomponent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        //throw new UnsupportedOperationException("Not yet implemented");
        //
        if(intent.getAction() == Intent.ACTION_BOOT_COMPLETED){
            Log.e("메시지","부팅 완료됨");
        }
    }
}