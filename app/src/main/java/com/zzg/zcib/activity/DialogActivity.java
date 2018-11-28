package com.zzg.zcib.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zzg.zcib.adapter.DialogMsgAdapter;
import com.zzg.zcib.entity.Msg;
import com.zzg.zcib.hi.R;
import com.zzg.zcib.utils.ChatSQLiteUtil;
import com.zzg.zcib.utils.MySocket;

import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DialogActivity extends AppCompatActivity {
    private TextView name,position;
    private  EditText editText;
    private Button send,back;
    private RecyclerView recyclerView;
    private DialogMsgAdapter dialogMsgAdapter;
    private List<Msg> msgList =new ArrayList<Msg>();
    private String uid,fid,fname,uImgUrl,fImgUrl;
    private ChatSQLiteUtil chatSQLiteUtil;
    private String editString;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        editText=findViewById(R.id.msg_edittext);
        send=findViewById(R.id.send_btn);
        recyclerView=findViewById(R.id.content_relv);
        name=findViewById(R.id.name_text);
        back=findViewById(R.id.back_btn);
        uid=getIntent().getExtras().getString("uid");
        fid=getIntent().getExtras().getString("fid");
        fname=getIntent().getExtras().getString("fname");
        uImgUrl=getIntent().getExtras().getString("uImgUrl");
        fImgUrl=getIntent().getExtras().getString("fImgUrl");
        chatSQLiteUtil=new ChatSQLiteUtil(this);

        initData();
        dialogMsgAdapter=new DialogMsgAdapter(msgList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(dialogMsgAdapter);
        send.setOnClickListener(new mOnClick());
        firstCon();
        name.setText(fname);

        recyclerView.scrollToPosition(msgList.size()-1);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private class mOnClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if (!"".equals(editText.getText().toString())){
                editString=editText.getText().toString();

            mySend();
//            myReceive("aa");
            new Thread(new Runnable(){
                @Override
                public void run() {
                    connectSocket();
                }
            }).start();
            editText.setText("");
            }
        }
    }

    private void initData(){
//        Msg msg=new Msg(R.mipmap.ic_launcher,"吃饭了吗",Msg.TYPE_RECEIVED);
//        msgList.add(msg);
//        Msg msg2=new Msg(R.mipmap.ic_launcher,"没呢，你呢",Msg.TYPE_SEND);
//        msgList.add(msg2);
//        Msg msg3=new Msg(R.mipmap.ic_launcher,"走吧，西吧",Msg.TYPE_RECEIVED);
//        msgList.add(msg3);
//        Msg msg4=new Msg(null,"2018-11-3 11:12",Msg.TYPE_TIME);
//        msgList.add(msg4);

        msgList= chatSQLiteUtil.quaryMsg(uid,fid);

    }

    private void connectSocket(){
        MySocket mySocket;
        JSONObject jsonObject1=new JSONObject();
        jsonObject1.put("first",false);
        jsonObject1.put("uid",uid);
        Log.d("ssssssssssss",uid);
        jsonObject1.put("fid",fid);
        jsonObject1.put("msg",editString);
        mySocket=new MySocket(jsonObject1.toString());

        mySocket.start();


    }

    public void mySend(){
            Msg msg=new Msg(R.mipmap.ic_launcher,editString,uid,fid,null,"send",fname,uImgUrl,fImgUrl);
            chatSQLiteUtil.insert(msg);
            msgList.add(msg);
            dialogMsgAdapter.notifyItemInserted(msgList.size()-1);
            recyclerView.scrollToPosition(msgList.size()-1);
    }

    public void myReceive(String content){
//        content="aaaaaaaaaa";
        if (!"".equals(content)){
            Msg msg=new Msg(R.mipmap.ic_launcher,content,uid,fid,null,"receive",fname,fImgUrl,fImgUrl);
            chatSQLiteUtil.insert(msg);
            msgList.add(msg);
            dialogMsgAdapter.notifyItemInserted(msgList.size()-1);
            recyclerView.scrollToPosition(msgList.size()-1);
        }
    }

    private void firstCon(){

        JSONObject jsonObject1=new JSONObject();
        jsonObject1.put("first",true);
        jsonObject1.put("uid",uid);
        MySocket mySocket=new MySocket(jsonObject1.toString());
        mySocket.setCallback(new MySocket.Mys() {
            @Override
            public void succ(final String s) {
                runOnUiThread(new Runnable(){

                    @Override
                    public void run() {
                        //更新UI
                        JSONObject jsonObject=new JSONObject(s);
                        Log.d("aaaaaaa",s);
                        if (jsonObject.getString("from").equals(fid)){
                            myReceive(jsonObject.getString("msg"));
                        }

                    }

                });


            }
        });
        mySocket.start();
    }


}
