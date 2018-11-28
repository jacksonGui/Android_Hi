package com.zzg.zcib.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zzg.zcib.hi.R;
import com.zzg.zcib.utils.MyVolley;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.zzg.zcib.utils.MyVolley.IP_;

public class AddOtherActivity extends AppCompatActivity {
    private EditText editText;
    private Button button;
    private String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_other);
        editText=findViewById(R.id.add_other_edit);
        userid=getIntent().getExtras().getString("userid");
        button=findViewById(R.id.btn_add_other);
        button.setOnClickListener(new AddClick());

    }

    private class AddClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            String content=editText.getText().toString();
            try {
                content = URLEncoder.encode(URLEncoder.encode(content, "utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            MyVolley.goVolley("http://" + IP_ + ":8080/AndroidServiceHi/userServlet?action=addOther&id=" + userid + "&content=" + content + "&time=" + getDate(), AddOtherActivity.this,
                    new MyVolley.VolleyCallback() {
                        @Override
                        public void onSuccess(String s) {
                            Toast.makeText(AddOtherActivity.this,"添加成功",Toast.LENGTH_SHORT).show();
                            AddOtherActivity.this.finish();
                        }
                    });
        }
    }

    private String getDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String dateString = formatter.format(currentTime);
        String[] date1 = dateString.split("-");
        return date1[1]+"-"+date1[2]+"/"+date1[3]+":"+date1[4];
    }
}
