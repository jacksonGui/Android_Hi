package com.zzg.zcib.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zzg.zcib.entity.AddRequest;
import com.zzg.zcib.hi.R;
import com.zzg.zcib.utils.MyVolley;

import java.util.List;

import static com.zzg.zcib.utils.MyVolley.IP_;

public class AddFriendReceiveAdapter extends BaseAdapter {
    private List<AddRequest> data;
    private Context context;

    public AddFriendReceiveAdapter(List<AddRequest> data, Context context) {
        this.data = data;
        this.context = context;
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
            convertView= LayoutInflater.from(context).inflate(R.layout.item_add_receive,null);
            viewHolder=new ViewHolder();
            viewHolder.userImg=convertView.findViewById(R.id.img_item_add_receive);
            viewHolder.name=convertView.findViewById(R.id.name_item_add_receive);
            viewHolder.time=convertView.findViewById(R.id.time_item_add_receive);
            viewHolder.btnyes=convertView.findViewById(R.id.btn_item_add_receive);
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
        viewHolder.name.setText(data.get(position).getFname());
        viewHolder.time.setText(data.get(position).getTime());
        viewHolder.btnyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyVolley.goVolley("http://" + IP_ + ":8080/AndroidServiceHi/userServlet?action=addFriendReceive&id=" + data.get(position).getRequestid() + "&uid=" + data.get(position).getUid() + "&fid=" + data.get(position).getFid(),
                        context, new MyVolley.VolleyCallback() {
                            @Override
                            public void onSuccess(String s) {
                                Toast.makeText(context,"已接受好友请求",Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        return convertView;
    }


    private static class ViewHolder{
        ImageView userImg;
        TextView name;
        TextView time;
        Button btnyes;
    }
}
