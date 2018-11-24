package com.zzg.zcib.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.zzg.zcib.activity.MainActivity;
import com.zzg.zcib.hi.R;
import com.zzg.zcib.utils.MyVolley;

import java.util.List;
import java.util.Map;

import static com.zzg.zcib.utils.MyVolley.IP_;

public class NewsListAdapter extends BaseAdapter {
    List<Map<String,Object>> data;
    Context context;

    public NewsListAdapter(Context context,List<Map<String,Object>> data){
        this.data=data;
        this.context=context;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.news_list_item,null);
            viewHolder=new ViewHolder();
            viewHolder.userImg=convertView.findViewById(R.id.item_user_img);
            viewHolder.username=convertView.findViewById(R.id.item_username);
            viewHolder.newText=convertView.findViewById(R.id.item_new);
            viewHolder.timeText=convertView.findViewById(R.id.item_time);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        final ImageView img=viewHolder.userImg;
        if (data.get(position).get("fimg")!=null){
            MyVolley.imageVolley("http://"+IP_+":8080/AndroidServiceHi/"+data.get(position).get("fimg").toString(), context, new MyVolley.ImgCallBack() {
                @Override
                public void onSuccess(Bitmap bitmap) {
                    img.setImageBitmap(bitmap);
                }
            });
        }


//        viewHolder.userImg.setImageBitmap((Bitmap) data.get(position).get("img"));
        viewHolder.username.setText(data.get(position).get("fname").toString());
        viewHolder.newText.setText(data.get(position).get("msgcontent").toString());
        viewHolder.timeText.setText(data.get(position).get("time").toString());
        return convertView;
    }

    private static class ViewHolder{
        ImageView userImg;
        TextView username;
        TextView newText;
        TextView timeText;
    }
}
