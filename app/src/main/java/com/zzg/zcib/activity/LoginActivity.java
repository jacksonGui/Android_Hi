package com.zzg.zcib.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.zzg.zcib.hi.R;
import com.zzg.zcib.utils.MyVolley;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static com.zzg.zcib.utils.MyVolley.IP_;

public class LoginActivity extends AppCompatActivity {
    private EditText username,password;
    private Button login,btnToRegister;
    private ImageView userImg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username=findViewById(R.id.login_username);
        password=findViewById(R.id.login_password);

        password.setTypeface(Typeface.DEFAULT);
        password.setTransformationMethod(new PasswordTransformationMethod());

        login=findViewById(R.id.login_btn);
        btnToRegister=findViewById(R.id.btn_toregister);
        userImg=findViewById(R.id.user_img);
        login.setOnClickListener(onClick);
        btnToRegister.setOnClickListener(toRegisterClick);
        username.setOnFocusChangeListener(new GetUserImg());
    }

    private class GetUserImg implements View.OnFocusChangeListener{

        @Override
        public void onFocusChange(View v, boolean hasFocus) {

            if (!hasFocus){
                if("".equals(username.getText().toString())){
                    Toast.makeText(LoginActivity.this,"用户名不能为空！",Toast.LENGTH_SHORT).show();

                }else {
                    String username1=username.getText().toString();
                    try {
                        username1 = URLEncoder.encode(URLEncoder.encode(username1, "utf-8"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    MyVolley.goVolley("http://"+IP_+":8080/AndroidServiceHi/userServlet?action=getImgUrl&username=" + username1,
                            LoginActivity.this, new MyVolley.VolleyCallback() {
                                @Override
                                public void onSuccess(String s) {
                                    MyVolley.imageVolley("http://"+IP_+":8080/AndroidServiceHi/"+s, LoginActivity.this, new MyVolley.ImgCallBack() {
                                        @Override
                                        public void onSuccess(Bitmap bitmap) {
                                            userImg.setImageBitmap(bitmap);
                                        }
                                    });
                                }
                            });

                }

            }
        }
    }

    private View.OnClickListener toRegisterClick=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener onClick=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String username1=username.getText().toString();
            String password1=password.getText().toString();
            if (!"".equals(username1)&&!"".equals(password1)){
                try {
                    username1 = URLEncoder.encode(URLEncoder.encode(username1, "utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                MyVolley.goVolley("http://"+IP_+":8080/AndroidServiceHi/userServlet?action=login&username=" + username1 + "&password=" + password1,
                        LoginActivity.this, new MyVolley.VolleyCallback() {
                    @Override
                    public void onSuccess(String s) {
                        if ("{}".equals(s)){
                            Toast.makeText(LoginActivity.this,"用户名或密码错误！",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(LoginActivity.this,"登录成功！",Toast.LENGTH_SHORT).show();
                            Intent intent =new Intent(LoginActivity.this,MainActivity.class);
                            intent.putExtra("user",s);
                            startActivity(intent);
                        }
                    }
                });




            }
        }
    };


}
