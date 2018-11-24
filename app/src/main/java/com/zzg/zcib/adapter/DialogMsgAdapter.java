package com.zzg.zcib.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zzg.zcib.entity.Msg;
import com.zzg.zcib.hi.R;
import com.zzg.zcib.utils.MyVolley;

import java.util.List;

import static com.zzg.zcib.utils.MyVolley.IP_;

public class DialogMsgAdapter extends RecyclerView.Adapter {
    private List<Msg> msgList;
    private Context context;


    private static class ViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout leftLayout,rightLayout;
        ImageView leftImg,rightImg;
        TextView leftText,rightText,timeText;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            leftLayout=itemView.findViewById(R.id.left_layout);
            rightLayout=itemView.findViewById(R.id.right_layout);
            leftImg=itemView.findViewById(R.id.msgitem_imgleft);
            rightImg=itemView.findViewById(R.id.msgitem_imgright);
            leftText=itemView.findViewById(R.id.msgitem_textleft);
            rightText=itemView.findViewById(R.id.msgitem_textright);
            timeText=itemView.findViewById(R.id.item_time);
        }
    }

    public DialogMsgAdapter(List<Msg> msgList){
        this.msgList=msgList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dialog_msg_item,viewGroup,false);
        context=viewGroup.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Msg msg=msgList.get(i);
        ViewHolder holder= (ViewHolder) viewHolder;
        if (msg.getType().equals("receive")){
            holder.leftLayout.setVisibility(View.VISIBLE);
            holder.rightLayout.setVisibility(View.GONE);
            holder.timeText.setVisibility(View.GONE);
//            holder.leftImg.setBackgroundResource(msg.getImg());
            final ImageView img=holder.leftImg;
            MyVolley.imageVolley("http://"+IP_+":8080/AndroidServiceHi/"+msg.getImgurl(), context, new MyVolley.ImgCallBack() {
                @Override
                public void onSuccess(Bitmap bitmap) {
                    img.setImageBitmap(bitmap);
                }
            });
            holder.leftText.setText(msg.getContent());
        }else if (msg.getType().equals("send")){
            holder.rightLayout.setVisibility(View.VISIBLE);
            holder.leftLayout.setVisibility(View.GONE);
            holder.timeText.setVisibility(View.GONE);
//            holder.rightImg.setBackgroundResource(msg.getImg());

            final ImageView img=holder.rightImg;
            MyVolley.imageVolley("http://"+IP_+":8080/AndroidServiceHi/"+msg.getImgurl(), context, new MyVolley.ImgCallBack() {
                @Override
                public void onSuccess(Bitmap bitmap) {
                    img.setImageBitmap(bitmap);
                }
            });
            holder.rightText.setText(msg.getContent());
        }else if (msg.getType().equals("time")){
            holder.rightLayout.setVisibility(View.GONE);
            holder.leftLayout.setVisibility(View.GONE);
            holder.timeText.setVisibility(View.VISIBLE);
            holder.timeText.setText(msg.getContent());
        }
    }

    @Override
    public int getItemCount() {
        return msgList.size();
    }
}
