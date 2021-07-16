package com.bytedance.practice5.socket;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientSocketThread extends Thread {
    public ClientSocketThread(SocketActivity.SocketCallback callback) {
        this.callback = callback;
    }

    private SocketActivity.SocketCallback callback;

    //head请求内容
    private static String content = "HEAD / HTTP/1.1\r\nHost:www.zju.edu.cn\r\n\r\n";
    private boolean stopFlag = false;

    @Override
    public void run() {
        try {
            Socket socket = new Socket("www.zju.edu.cn", 80);
            BufferedOutputStream os = new BufferedOutputStream(socket.getOutputStream());
            BufferedInputStream is = new BufferedInputStream(socket.getInputStream());
            byte[] data = new byte[1024 * 5];//
            while (!stopFlag && socket.isConnected()){
                os.write(content.getBytes());
                os.flush();
                int reciveLen = is.read(data);
                if (reciveLen!=-1){
                    String receive = new String(data, 0, reciveLen);
                    Log.d("socket", "Success " + receive);
                    callback.onResponse(receive);

                }else {
                    Log.d("socket", "Fail");
                }
                stopFlag=true;
            }
            os.flush();
            os.close();
            socket.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}