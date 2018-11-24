package com.zzg.zcib.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.zzg.zcib.activity.DialogActivity;
import com.zzg.zcib.activity.LoginActivity;
import com.zzg.zcib.activity.MainActivity;
import com.zzg.zcib.activity.PersonActivity;
import com.zzg.zcib.entity.Friends;
import com.zzg.zcib.entity.FriendsGroup;
import com.zzg.zcib.hi.R;
import com.zzg.zcib.utils.MyVolley;

import java.util.List;
import java.util.Map;

import static com.zzg.zcib.utils.MyVolley.IP_;


public class FriendsListAdapter extends BaseExpandableListAdapter implements ExpandableListAdapter  {

    private List<FriendsGroup> groupData;
    private List<List<Friends>> childData;
    private Context context;
    private String uid,uImgUrl;

    public FriendsListAdapter(Context context,List<FriendsGroup> groupData,List<List<Friends>> childData,String uid,String uImgUrl){
        this.context=context;
        this.groupData=groupData;
        this.childData=childData;
        this.uid=uid;
        this.uImgUrl=uImgUrl;
    }


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            notifyDataSetChanged();//更新数据
            super.handleMessage(msg);
        }
    };


    /*供外界更新数据的方法*/
    public void refresh(ExpandableListView expandableListView){
        handler.sendMessage(new Message());
        //必须重新伸缩之后才能更新数据
        for (int i = 0; i < getGroupCount(); i++) {
            expandableListView.collapseGroup(i);
            expandableListView.expandGroup(i);
            expandableListView.collapseGroup(i);
        }
    }


    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getGroupCount() {
        return groupData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childData.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupData.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childData.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupViewHolder;
        if (convertView==null){
            groupViewHolder=new GroupViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.friends_list_group,parent,false);
            groupViewHolder.groupName=convertView.findViewById(R.id.friends_group_name);
            convertView.setTag(groupViewHolder);
        }else {
            groupViewHolder= (GroupViewHolder) convertView.getTag();
        }
        groupViewHolder.groupName.setText(groupData.get(groupPosition).getGroupName());
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder;
        if (convertView==null){
            childViewHolder=new ChildViewHolder();
            convertView=LayoutInflater.from(context).inflate(R.layout.friends_list_item,parent,false);
            childViewHolder.childImg=convertView.findViewById(R.id.friends_item_img);
            childViewHolder.childName=convertView.findViewById(R.id.friends_item_name);
            childViewHolder.childText=convertView.findViewById(R.id.friends_item_text);
            convertView.setTag(childViewHolder);
        }else {
            childViewHolder= (ChildViewHolder) convertView.getTag();
        }

        final ImageView img=childViewHolder.childImg;
        MyVolley.imageVolley("http://"+IP_+":8080/AndroidServiceHi/"+childData.get(groupPosition).get(childPosition).getImgurl(), context, new MyVolley.ImgCallBack() {
            @Override
            public void onSuccess(Bitmap bitmap) {
                img.setImageBitmap(bitmap);
            }
        });
//        childViewHolder.childImg.setBackgroundResource(R.mipmap.ic_launcher);
        childViewHolder.childName.setText(childData.get(groupPosition).get(childPosition).getFusername());
        childViewHolder.childText.setText(childData.get(groupPosition).get(childPosition).getFid().toString());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(context,PersonActivity.class);
                intent.putExtra("fid",childData.get(groupPosition).get(childPosition).getFid());
                intent.putExtra("uImgUrl",uImgUrl);
                intent.putExtra("userid",uid);
                intent.putExtra("personType","friend");
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {

    }

    @Override
    public void onGroupCollapsed(int groupPosition) {

    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return 0;
    }




    private static class GroupViewHolder{
        TextView groupName;
    }
    private static class ChildViewHolder{
        ImageView childImg;
        TextView childName;
        TextView childText;
    }
}
