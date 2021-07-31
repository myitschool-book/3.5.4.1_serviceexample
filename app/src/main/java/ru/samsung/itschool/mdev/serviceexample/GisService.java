package ru.samsung.itschool.mdev.serviceexample;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;
import androidx.annotation.NonNull;


public class GisService extends Service {
    public static final String CHANNEL = "GIS_SERVICE";
    public static final String INFO = "INFO";
    private Handler h;
    @Override
    public void onCreate() {
        h = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                Intent i = new Intent(CHANNEL);
                i.putExtra(INFO, msg.obj.toString());
                sendBroadcast(i);
            }
        };
        Toast.makeText(this, "Служба создана",Toast.LENGTH_SHORT).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Служба запущена",Toast.LENGTH_SHORT).show();
        Thread weather = new Thread(new HTTPRequest(h));
        weather.start();
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Служба остановлена", Toast.LENGTH_SHORT).show();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}