package com.zzg.zcib.activity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.zzg.zcib.hi.R;
import com.zzg.zcib.utils.HttpAssist;
import com.zzg.zcib.utils.MyVolley;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static com.zzg.zcib.utils.MyVolley.IP_;

public class RegisterActivity extends AppCompatActivity {

    private static final int WRITE_PERMISSION = 0x01;
    private EditText regiUsername,regiPassword1,regiPassword2,regiEmail,regiPhone;
    private Button btnRegister,btnToLogin;
    private ImageView img;
    private String imgname;

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
        img=findViewById(R.id.img_register);
        regiUsername.setOnFocusChangeListener(new HasUser());
        btnRegister.setOnClickListener(new RegisterClick());
        btnToLogin.setOnClickListener(new ToLoginClick());
        img.setOnClickListener(new ImgClick());


    }










    private class ImgClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent,1);

//            作者：再忙碌也解不了爱的渴
//            链接：https://www.jianshu.com/p/1f74a469d320
//            來源：简书
//            简书著作权归作者所有，任何形式的转载都请联系作者获得授权并注明出处。
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                if(resultCode == RESULT_OK){
                    final Uri uri = data.getData();

                    //通过uri的方式返回，部分手机uri可能为空
                    if(uri != null){
                        imgname=getFileByUri(uri,RegisterActivity.this).getName();
//                        Log.d("aaaaaaaaaaaaaaasssssss",imgname);
                        try {
                            //通过uri获取到bitmap对象
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            img.setImageBitmap(bitmap);
                            try {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        requestWritePermission();

                                        HttpAssist.uploadFile(getFileByUri(uri,RegisterActivity.this));

                                    }
                                }).start();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }else {
                        //部分手机可能直接存放在bundle中
                        Bundle bundleExtras = data.getExtras();
                        if(bundleExtras != null){
                            Bitmap  bitmaps = bundleExtras.getParcelable("data");
                            img.setImageBitmap(bitmaps);
                        }
                    }

                }
                break;
        }
    }

//    作者：再忙碌也解不了爱的渴
//    链接：https://www.jianshu.com/p/1f74a469d320
//    來源：简书
//    简书著作权归作者所有，任何形式的转载都请联系作者获得授权并注明出处。
public static File getFileByUri(Uri uri,Context context) {
    String path = null;
    if ("file".equals(uri.getScheme())) {
        path = uri.getEncodedPath();
        if (path != null) {
            path = Uri.decode(path);
            ContentResolver cr = context.getContentResolver();
            StringBuffer buff = new StringBuffer();
            buff.append("(").append(MediaStore.Images.ImageColumns.DATA).append("=").append("'" + path + "'").append(")");
            Cursor cur = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[] { MediaStore.Images.ImageColumns._ID, MediaStore.Images.ImageColumns.DATA }, buff.toString(), null, null);
            int index = 0;
            int dataIdx = 0;
            for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
                index = cur.getColumnIndex(MediaStore.Images.ImageColumns._ID);
                index = cur.getInt(index);
                dataIdx = cur.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cur.getString(dataIdx);
            }
            cur.close();
            if (index == 0) {
            } else {
                Uri u = Uri.parse("content://media/external/images/media/" + index);
                System.out.println("temp uri is :" + u);
            }
        }
        if (path != null) {
            return new File(path);
        }
    } else if ("content".equals(uri.getScheme())) {
        // 4.2.2以后
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            path = cursor.getString(columnIndex);
        }
        cursor.close();

        return new File(path);
    } else {
        //Log.i(TAG, "Uri Scheme:" + uri.getScheme());
    }
    return null;
}

    private void requestWritePermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},WRITE_PERMISSION);
            }
        }
        if (ActivityCompat.checkSelfPermission(RegisterActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(RegisterActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }

    }
//---------------------
//    作者：LYL_K
//    来源：CSDN
//    原文：https://blog.csdn.net/LYL_K/article/details/71367465
//    版权声明：本文为博主原创文章，转载请附上博文链接！








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
                String username1=regiUsername.getText().toString();
                try {
                    username1 = URLEncoder.encode(URLEncoder.encode(username1, "utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                String url="http://"+IP_+":8080/AndroidServiceHi/userServlet?action=register&username="+username1+"&password="+regiPassword1.getText()+"&email="+regiEmail.getText()+"&phone="+regiPhone.getText()+"&imgurl=imgs/"+imgname;
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
