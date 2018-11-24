package com.zzg.zcib.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zzg.zcib.activity.PersonActivity;
import com.zzg.zcib.entity.Friends;
import com.zzg.zcib.entity.User;
import com.zzg.zcib.hi.R;
import com.zzg.zcib.utils.MyVolley;

import java.util.List;
import java.util.Map;

import static com.zzg.zcib.utils.MyVolley.IP_;

public class AddFriendAdapter extends BaseAdapter {
    List<Friends> data;
    Context context;
    String userid;

    public AddFriendAdapter(Context context, List<Friends> data,String userid){
        this.data=data;
        this.context=context;
        this.userid=userid;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.item_add_friend,null);
            viewHolder=new ViewHolder();
            viewHolder.userImg=convertView.findViewById(R.id.item_add_img);
            viewHolder.username=convertView.findViewById(R.id.item_add_name);
            viewHolder.userText=convertView.findViewById(R.id.item_add_text);
            viewHolder.btnAdd=convertView.findViewById(R.id.btn_add_item);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        final ImageView img=viewHolder.userImg;
        MyVolley.imageVolley("http://"+IP_+":8080/AndroidServiceHi/"+data.get(position).getImgurl(), context, new MyVolley.ImgCallBack() {
            @Override
            public void onSuccess(Bitmap bitmap) {
                img.setImageBitmap(bitmap);
            }
        });

//        viewHolder.userImg.setBackgroundResource(R.mipmap.ic_launcher);
        viewHolder.username.setText(data.get(position).getFusername());
        viewHolder.userText.setText(data.get(position).getFid().toString());
        viewHolder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, PersonActivity.class);
                intent.putExtra("userid",userid);
                intent.putExtra("fid",data.get(position).getFid());
                intent.putExtra("personType","stranger");
                context.startActivity(intent);
                //20181117 转到信息详情页 添加好友
            }
        });
        return convertView;
    }

    private static class ViewHolder{
        ImageView userImg;
        TextView username;
        TextView userText;
        Button btnAdd;

    }
}
