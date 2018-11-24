package com.zzg.zcib.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zzg.zcib.activity.DialogActivity;
import com.zzg.zcib.activity.PersonActivity;
import com.zzg.zcib.adapter.NewsListAdapter;
import com.zzg.zcib.hi.R;
import com.zzg.zcib.utils.ChatSQLiteUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class NewsFragment extends Fragment {
    private ListView newsList;
    private List<Map<String,Object>> data=new ArrayList<Map<String, Object>>();
    private ChatSQLiteUtil chatSQLiteUtil;
    private String userid;
    private NewsListAdapter newsListAdapter;

    public static NewsFragment newInstance(String uid,String uImgUrl){
        NewsFragment newsFragment=new NewsFragment();
        Bundle bundle=new Bundle();
        bundle.putString("uid",uid);
        bundle.putString("uImgUrl",uImgUrl);
        newsFragment.setArguments(bundle);
        return newsFragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.news_fragment,container,false);
        newsList=view.findViewById(R.id.news_list);
        userid=getArguments().get("uid").toString();
        initData();


        newsListAdapter=new NewsListAdapter(getContext(),data);
        newsList.setAdapter(newsListAdapter);
        newsList.setOnItemClickListener(new OnItemClick());

        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            initData();
            newsListAdapter.notifyDataSetChanged();
//            Log.d("aaaaaaasdddddddddddd",data.toString());
        }
    }

    private void initData(){
        chatSQLiteUtil=new ChatSQLiteUtil(getContext());
        data.clear();
        data.addAll(chatSQLiteUtil.quaryOneMsg(userid));
    }

    private class OnItemClick implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent =new Intent(getContext(),DialogActivity.class);

            intent.putExtra("fname",data.get(position).get("fname").toString());
            intent.putExtra("fid",data.get(position).get("fid").toString());
            intent.putExtra("fImgUrl",data.get(position).get("fimg").toString());
            intent.putExtra("uImgUrl",getArguments().get("uImgUrl").toString());
            intent.putExtra("uid",userid);
            startActivity(intent);
        }
    }
}
