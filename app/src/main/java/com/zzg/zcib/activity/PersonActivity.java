package com.zzg.zcib.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zzg.zcib.hi.R;
import com.zzg.zcib.utils.MyVolley;

import org.json.JSONException;
import org.json.JSONObject;

import static com.zzg.zcib.utils.MyVolley.IP_;

public class PersonActivity extends AppCompatActivity {
    private ImageView personImg;
    private TextView personName,personEmail,personPhone;
    private Button btnAdd,btnChat,btnEdit;
    private String userid,fImgUrl,uImgUrl;
    private Integer fid;
    private String personType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        personImg=findViewById(R.id.person_img);
        personName=findViewById(R.id.person_name);
        personEmail=findViewById(R.id.person_email);
        personPhone=findViewById(R.id.person_phone);
        btnAdd=findViewById(R.id.btn_person_add);
        btnChat=findViewById(R.id.btn_person_chat);
        btnEdit=findViewById(R.id.btn_person_edit);
        userid=getIntent().getExtras().getString("userid");
        fid=getIntent().getExtras().getInt("fid");
        personType=getIntent().getExtras().getString("personType");
        uImgUrl=getIntent().getExtras().getString("uImgUrl");
        initViewData();
        setBtnVisibility();

        btnAdd.setOnClickListener(new AddClick());
        btnChat.setOnClickListener(new ChatClick());
        btnEdit.setOnClickListener(new EditClick());
    }

    private void setBtnVisibility(){
        if(personType.equals("myself")){
            btnEdit.setVisibility(View.VISIBLE);
            btnChat.setVisibility(View.GONE);
            btnAdd.setVisibility(View.GONE);
        }else if (personType.equals("friend")){
            btnEdit.setVisibility(View.GONE);
            btnAdd.setVisibility(View.GONE);
            btnChat.setVisibility(View.VISIBLE);
        }else if (personType.equals("stranger")){
            btnEdit.setVisibility(View.GONE);
            btnChat.setVisibility(View.GONE);
            btnAdd.setVisibility(View.VISIBLE);
        }
    }

    private void initViewData(){
        if (fid!=null){
            MyVolley.goVolley("http://" + IP_ + ":8080/AndroidServiceHi/userServlet?action=getOneUser&id=" + fid, this,
                    new MyVolley.VolleyCallback() {
                        @Override
                        public void onSuccess(String s) {
                            try {
                                JSONObject jsonObject=new JSONObject(s);
                                personName.setText(jsonObject.getString("username"));
                                personEmail.setText(jsonObject.getString("email"));
                                personPhone.setText(jsonObject.getString("phone"));
                                fImgUrl=jsonObject.getString("imgurl");
                                MyVolley.imageVolley("http://"+IP_+":8080/AndroidServiceHi/"+fImgUrl, PersonActivity.this, new MyVolley.ImgCallBack() {
                                    @Override
                                    public void onSuccess(Bitmap bitmap) {
                                        personImg.setImageBitmap(bitmap);
                                    }
                                });

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });
        }
    }


    private class ChatClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent =new Intent(PersonActivity.this,DialogActivity.class);

            intent.putExtra("fname",personName.getText().toString());
            intent.putExtra("fid",fid.toString());
            intent.putExtra("uid",userid);

            intent.putExtra("fImgUrl",fImgUrl);
            intent.putExtra("uImgUrl",uImgUrl);
            startActivity(intent);
        }
    }

    private class EditClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent =new Intent(PersonActivity.this,EditPersonActivity.class);
            intent.putExtra("uid",userid);
            startActivity(intent);
        }
    }


    private class AddClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            MyVolley.goVolley("http://" + IP_ + ":8080/AndroidServiceHi/userServlet?action=addFriendAsk&uid="+userid+"&fid=" + fid,PersonActivity.this,
                    new MyVolley.VolleyCallback() {
                        @Override
                        public void onSuccess(String s) {
                            Toast.makeText(PersonActivity.this,"已发送好友请求",Toast.LENGTH_SHORT).show();
                        }
                    });


        }
    }
}
