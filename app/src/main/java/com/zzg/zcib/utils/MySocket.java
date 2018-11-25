package com.zzg.zcib.utils;

import android.content.Intent;
import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MySocket extends Thread{
    private Socket clientSocket;
    private String jsonS;
    private Mys callback;


    DataOutputStream dos;
    public interface Mys{
        void succ(String s);
    }

    public void setCallback(Mys callback) {
        this.callback = callback;
    }

    public MySocket(String jsonS){
        this.jsonS=jsonS;
    }

    @Override
    public void run() {
        try {
            clientSocket=new Socket(MyVolley.IP_,9751);

        dos=new DataOutputStream(clientSocket.getOutputStream());
        dos.writeUTF(jsonS);
        dos.flush();


        DataInputStream dis=new DataInputStream(clientSocket.getInputStream());
        while (true){
            String jsonString1=dis.readUTF();
//            Log.d("aaaaaaaa",jsonString1);


            if (callback!=null){
                callback.succ(jsonString1);
            }
        }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
