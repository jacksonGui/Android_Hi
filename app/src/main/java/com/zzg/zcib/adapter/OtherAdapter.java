package com.zzg.zcib.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.zzg.zcib.hi.R;
import com.zzg.zcib.utils.MyVolley;

import java.util.List;
import java.util.Map;

import static com.zzg.zcib.utils.MyVolley.IP_;

public class OtherAdapter extends BaseAdapter{
    private List<Map<String,Object>> list;
    private Context context;

    public OtherAdapter(Context context,List<Map<String,Object>> list) {
        this.context=context;
        this.list=list;
    }

    @Override
    public int getCount() {
        return list.size();
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
            viewHolder=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.item_list_other,null);
            viewHolder.username=convertView.findViewById(R.id.item_list_other_username);
            viewHolder.content=convertView.findViewById(R.id.item_list_other_content);
            viewHolder.time=convertView.findViewById(R.id.item_list_other_time);
            viewHolder.imageView=convertView.findViewById(R.id.item_list_other_img);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.username.setText(list.get(position).get("username").toString());
        viewHolder.content.setText(list.get(position).get("content").toString());
        viewHolder.time.setText(list.get(position).get("time").toString());
        final ImageView img=viewHolder.imageView;
        if (list.get(position).get("imgurl")!=null){
            MyVolley.imageVolley("http://"+IP_+":8080/AndroidServiceHi/"+list.get(position).get("imgurl").toString(), context, new MyVolley.ImgCallBack() {
                @Override
                public void onSuccess(Bitmap bitmap) {
                    img.setImageBitmap(bitmap);
                }
            });
        }
        return convertView;
    }

    private class ViewHolder{
        ImageView imageView;
        TextView username;
        TextView content;
        TextView time;
    }
}
