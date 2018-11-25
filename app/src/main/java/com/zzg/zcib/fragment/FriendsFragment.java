package com.zzg.zcib.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.zzg.zcib.adapter.FriendsListAdapter;
import com.zzg.zcib.entity.Friends;
import com.zzg.zcib.entity.FriendsGroup;
import com.zzg.zcib.hi.R;
import com.zzg.zcib.utils.MyVolley;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.zzg.zcib.utils.MyVolley.IP_;


public class FriendsFragment extends Fragment {
    private ExpandableListView friendList;
    private List<FriendsGroup> groupData=new ArrayList<FriendsGroup>();
    private List<List<Friends>> childData=new ArrayList<List<Friends>>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private String id,uImgUrl;
    FriendsListAdapter friendsListAdapter;
    public static FriendsFragment newInstance(String uid,String uImgUrl){
        FriendsFragment friendsFragment=new FriendsFragment();
        Bundle bundle=new Bundle();
        bundle.putString("uid",uid);
        bundle.putString("uImgUrl",uImgUrl);
        friendsFragment.setArguments(bundle);
        return friendsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.friends_fragment,container,false);
        friendList=view.findViewById(R.id.friends_list);
        swipeRefreshLayout=view.findViewById(R.id.friends_swipe_refresh);
        id=getArguments().get("uid").toString();
        uImgUrl=getArguments().getString("uImgUrl");

//        initData();
        friendsListAdapter=new FriendsListAdapter(getContext(),groupData,childData,id,uImgUrl);
        friendList.setAdapter(friendsListAdapter);
        getNetData();
        swipeRefreshLayout.setOnRefreshListener(new FriendsRefresh());
        return view;
    }

    private class FriendsRefresh implements SwipeRefreshLayout.OnRefreshListener{

        @Override
        public void onRefresh() {
            getNetData();
            swipeRefreshLayout.setRefreshing(false);
        }
    }

//    public void onHiddenChanged(boolean hidden) {
//        super.onHiddenChanged(hidden);
//        if(!hidden){
//            getNetData();
//
//        }
//    }

    private void getNetData(){
        groupData.clear();
        childData.clear();
        MyVolley.goVolley("http://"+IP_+":8080/AndroidServiceHi/userServlet?action=getFriends&id="+id,getContext(),
                new MyVolley.VolleyCallback() {
                    @Override
                    public void onSuccess(String s) {
                        JSONObject friendsJSON=JSONObject.fromObject(s);
                        JSONArray keys= (JSONArray) friendsJSON.get("keys");
                        for (int i=0;i<keys.length();i++){
                            JSONObject j= (JSONObject) keys.get(i);
                            groupData.add(new FriendsGroup(j.get("key").toString()));
                            JSONArray item= (JSONArray) friendsJSON.get(j.get("key").toString());
                            List<Friends> list1=new ArrayList<Friends>();
                            for (int j1=0;j1<item.length();j1++){
                                JSONObject jj= (JSONObject) item.get(j1);
                                list1.add(new Friends(jj.getInt("fid"),jj.getString("username"),jj.getInt("isonline"),jj.getString("imgurl")));
                            }
                            childData.add(list1);
                        }
                        friendsListAdapter.refresh(friendList);

                    }
                });

    }

//    private void initData(){
//
////        if (getArguments()!=null){
////            String jsonString=getArguments().getString("jsonString");
////        }
//
//        groupData=new ArrayList<Map<String, Object>>();
//        Map<String,Object> map1=new HashMap<String,Object>();
//        map1.put("groupName","我的好友");
//        groupData.add(map1);
//        Map<String,Object> map2=new HashMap<String,Object>();
//        map2.put("groupName","小学同学");
//        groupData.add(map2);
//        Map<String,Object> map3=new HashMap<String,Object>();
//        map3.put("groupName","大学同学");
//        groupData.add(map3);
//
//        childData=new ArrayList<List<Map<String,Object>>>();
//        List<Map<String,Object>> list1=new ArrayList<Map<String, Object>>();
//        Map<String,Object> map11=new HashMap<String, Object>();
//        map11.put("childImg",R.mipmap.ic_launcher);
//        map11.put("childName","南昌");
//        map11.put("childText","努力做平凡的自己！");
//        list1.add(map11);
//        Map<String,Object> map12=new HashMap<String, Object>();
//        map12.put("childImg",R.mipmap.ic_launcher);
//        map12.put("childName","南昌2");
//        map12.put("childText","努力做平凡的自己！");
//        list1.add(map12);
//        childData.add(list1);
//        List<Map<String,Object>> list2=new ArrayList<Map<String, Object>>();
//        Map<String,Object> map13=new HashMap<String, Object>();
//        map13.put("childImg",R.mipmap.ic_launcher);
//        map13.put("childName","南昌");
//        map13.put("childText","努力做平凡的自己！");
//        list2.add(map13);
//        Map<String,Object> map14=new HashMap<String, Object>();
//        map14.put("childImg",R.mipmap.ic_launcher);
//        map14.put("childName","南昌2");
//        map14.put("childText","努力做平凡的自己！");
//        list2.add(map14);
//        childData.add(list2);
//        List<Map<String,Object>> list3=new ArrayList<Map<String, Object>>();
//        Map<String,Object> map15=new HashMap<String, Object>();
//        map15.put("childImg",R.mipmap.ic_launcher);
//        map15.put("childName","南昌");
//        map15.put("childText","努力做平凡的自己！");
//        list3.add(map15);
//        Map<String,Object> map16=new HashMap<String, Object>();
//        map16.put("childImg",R.mipmap.ic_launcher);
//        map16.put("childName","南昌2");
//        map16.put("childText","努力做平凡的自己！");
//        list3.add(map15);
//        childData.add(list3);
//
//    }
}
