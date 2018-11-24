package com.zzg.zcib.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zzg.zcib.hi.R;
import com.zzg.zcib.utils.MyVolley;

import org.json.JSONObject;

import static com.zzg.zcib.utils.MyVolley.IP_;

public class RegisterActivity extends AppCompatActivity {
    private EditText regiUsername,regiPassword1,regiPassword2,regiEmail,regiPhone;
    private Button btnRegister,btnToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        regiUsername=findViewById(R.id.regi_username);
        regiPassword1=findViewById(R.id.regi_password1);
        regiPassword2=findViewById(R.id.regi_password2);
        regiEmail=findViewById(R.id.regi_email);
        regiPhone=findViewById(R.id.regi_phone);
        btnRegister=findViewById(R.id.btn_regi);
        btnToLogin=findViewById(R.id.btn_tologin);

        regiUsername.setOnFocusChangeListener(new HasUser());
        btnRegister.setOnClickListener(new RegisterClick());
        btnToLogin.setOnClickListener(new ToLoginClick());

    }

    private class RegisterClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            if ("".equals(regiUsername.getText().toString())){
                Toast.makeText(RegisterActivity.this,"用户名不能为空！",Toast.LENGTH_SHORT).show();

            }else if ("".equals(regiPassword1.getText().toString())){
                Toast.makeText(RegisterActivity.this,"密码不能为空！",Toast.LENGTH_SHORT).show();

            }else if ("".equals(regiPassword2.getText().toString())){
                Toast.makeText(RegisterActivity.this,"请再次输入密码！",Toast.LENGTH_SHORT).show();

            }else if (!regiPassword2.getText().toString().equals(regiPassword1.getText().toString())){
                Toast.makeText(RegisterActivity.this,"两次密码输入不相同！",Toast.LENGTH_SHORT).show();

            }else{
                String url="http://"+IP_+":8080/AndroidServiceHi/userServlet?action=register&username="+regiUsername.getText()+"&password="+regiPassword1.getText()+"&email="+regiEmail.getText()+"&phone="+regiPhone.getText();
                MyVolley.goVolley(url, RegisterActivity.this, new MyVolley.VolleyCallback() {
                    @Override
                    public void onSuccess(String s) {
                        if("y".equals(s)){
                            Toast.makeText(RegisterActivity.this,"注册成功！",Toast.LENGTH_SHORT).show();
                            toLogin();
                        }else {
                            Toast.makeText(RegisterActivity.this,"注册失败！",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        }
    }

    private class ToLoginClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            toLogin();
        }
    }

    private class HasUser implements View.OnFocusChangeListener{

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus){
                if("".equals(regiUsername.getText().toString())){
                    Toast.makeText(RegisterActivity.this,"用户名不能为空！",Toast.LENGTH_SHORT).show();

                }else {
                    MyVolley.goVolley("http://" + IP_ + ":8080/AndroidServiceHi/userServlet?action=hasUser&username=" + regiUsername.getText(), RegisterActivity.this,
                            new MyVolley.VolleyCallback() {
                                @Override
                                public void onSuccess(String s) {
                                    if ("y".equals(s)){
                                        Toast.makeText(RegisterActivity.this,"用户名可用！",Toast.LENGTH_SHORT).show();
                                    }else if ("n".equals(s)){
                                        Toast.makeText(RegisterActivity.this,"此用户已被注册！",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }

            }
        }
    }

    private void toLogin(){
        Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
        startActivity(intent);

    }

}
