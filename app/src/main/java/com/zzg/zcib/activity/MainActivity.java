package com.zzg.zcib.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.zzg.zcib.fragment.FriendsFragment;
import com.zzg.zcib.fragment.NewsFragment;
import com.zzg.zcib.fragment.OtherFragment;
import com.zzg.zcib.hi.R;
import com.zzg.zcib.utils.MyVolley;
import com.zzg.zcib.view.SlidingMenu;

import net.sf.json.JSONObject;

import java.io.File;

import static com.zzg.zcib.utils.MyVolley.IP_;


public class MainActivity extends AppCompatActivity {
    private TextView titleText,slideUsername,slideEditInfo;
    private Button more,btnAdd,btnReceive;
    private RadioGroup radioGroup;
    private RadioButton newsBtn;
    private NewsFragment newsFragment;
    private FriendsFragment friendsFragment;
    private OtherFragment otherFragment;

    private JSONObject jsonObject;
    private ImageView img,menuImg;
    private SlidingMenu slidingMenu;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        img=findViewById(R.id.user_img);
        menuImg=findViewById(R.id.slidemenu_img);
        radioGroup=findViewById(R.id.bottom_bar);
        titleText=findViewById(R.id.title_text);
        more=findViewById(R.id.more);
        slideUsername=findViewById(R.id.slidemenu_username);
        slideEditInfo=findViewById(R.id.slide_edit_info);
        radioGroup.setOnCheckedChangeListener(new OnChange());
        newsBtn=findViewById(R.id.news_btn);
        slidingMenu=findViewById(R.id.sliding_menu);
        String userJson=getIntent().getExtras().get("user").toString();
        jsonObject=JSONObject.fromObject(userJson);
        slideUsername.setText(jsonObject.getString("username"));
        more.setOnClickListener(new MoreClick());
        MyVolley.imageVolley("http://"+IP_+":8080/AndroidServiceHi/"+jsonObject.getString("imgurl"), MainActivity.this, new MyVolley.ImgCallBack() {
            @Override
            public void onSuccess(Bitmap bitmap) {
                img.setImageBitmap(bitmap);
            }
        });
        MyVolley.imageVolley("http://"+IP_+":8080/AndroidServiceHi/"+jsonObject.getString("imgurl"), MainActivity.this, new MyVolley.ImgCallBack() {
            @Override
            public void onSuccess(Bitmap bitmap) {
                menuImg.setImageBitmap(bitmap);
            }
        });
        img.setOnClickListener(new UserImgClick());
        newsBtn.setChecked(true);

        menuImg.setOnClickListener(new SlideImgClick());
        slideEditInfo.setOnClickListener(new SlideEditInfoClick());


    }


    private class SlideEditInfoClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent =new Intent(MainActivity.this,EditPersonActivity.class);
            intent.putExtra("userid",jsonObject.getString("id"));
            startActivity(intent);
        }
    }

    private class MoreClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            View view= LayoutInflater.from(MainActivity.this).inflate(R.layout.popupwindow_more,null);
            PopupWindow popupWindow=new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
            btnAdd=view.findViewById(R.id.btn_add_pop);
            btnReceive=view.findViewById(R.id.btn_receive_pop);
            btnAdd.setOnClickListener(new AddFriendClick());
            btnReceive.setOnClickListener(new AddFriendAsClick());
            popupWindow.setBackgroundDrawable(new ColorDrawable(Color.BLUE));
            popupWindow.showAsDropDown(v,0, 0);
        }
    }


    private class UserImgClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            slidingMenu.toggle();
        }
    }



    private class SlideImgClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent =new Intent(MainActivity.this,PersonActivity.class);
            intent.putExtra("fid",Integer.parseInt(jsonObject.getString("id")));
            intent.putExtra("userid",jsonObject.getString("id"));
            intent.putExtra("personType","myself");
            startActivity(intent);
        }
    }

    /*
    添加好友点击事件
     */
    private class AddFriendClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent=new Intent(MainActivity.this,AddFriendActivity.class);
            intent.putExtra("userid",jsonObject.getString("id"));
            startActivity(intent);
        }
    }



    /*
    收到的好友请求点击事件
     */
    private class AddFriendAsClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent=new Intent(MainActivity.this,AddFriendReceiveActivity.class);
            intent.putExtra("userid",jsonObject.getString("id"));
            startActivity(intent);
        }
    }


    /*
    底部菜单切换事件
     */
    private class OnChange implements RadioGroup.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            allHide(fragmentTransaction);
            switch (checkedId){
                case R.id.news_btn:
                    if (newsFragment==null){
                        newsFragment=NewsFragment.newInstance(jsonObject.getString("id"),jsonObject.getString("imgurl"));
                        fragmentTransaction.add(R.id.main_content,newsFragment);
                    }else {
                        fragmentTransaction.show(newsFragment);
                    }
                    titleText.setText("消息");
                    break;
                case R.id.friends_btn:
                    if (friendsFragment==null){
                        friendsFragment=FriendsFragment.newInstance(jsonObject.getString("id"),jsonObject.getString("imgurl"));
                        fragmentTransaction.add(R.id.main_content,friendsFragment);
                    }else {
                        fragmentTransaction.show(friendsFragment);
                    }
                    titleText.setText("好友");
                    break;
                case R.id.other_btn:
                    if (otherFragment==null){
                        otherFragment=OtherFragment.newInstance(jsonObject.getString("id"),jsonObject.getString("imgurl"));
                        fragmentTransaction.add(R.id.main_content,otherFragment);
                    }else {
                        fragmentTransaction.show(otherFragment);
                    }
                    titleText.setText("动态");
                    break;
            }
            fragmentTransaction.commit();
        }
    }



    private void allHide(FragmentTransaction fragmentTransaction){
        if (newsFragment!=null){
            fragmentTransaction.hide(newsFragment);
        }
        if (friendsFragment!=null){
            fragmentTransaction.hide(friendsFragment);
        }
        if (otherFragment!=null){
            fragmentTransaction.hide(otherFragment);
        }
    }


}
