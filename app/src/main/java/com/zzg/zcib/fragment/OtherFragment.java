package com.zzg.zcib.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.zzg.zcib.activity.AddOtherActivity;
import com.zzg.zcib.activity.EditPersonActivity;
import com.zzg.zcib.activity.MainActivity;
import com.zzg.zcib.adapter.OtherAdapter;
import com.zzg.zcib.hi.R;
import com.zzg.zcib.utils.MyVolley;

import net.sf.json.JSONObject;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.zzg.zcib.utils.MyVolley.IP_;


public class OtherFragment extends Fragment {
    private Button addOther;
    private ListView listOther;
    private OtherAdapter otherAdapter;
    private List<Map<String,Object>> list=new ArrayList<Map<String, Object>>();
    private String userid;
    private SwipeRefreshLayout swipeRefreshLayout;


    public static OtherFragment newInstance(String uid,String uImgUrl){
        OtherFragment otherFragment=new OtherFragment();
        Bundle bundle=new Bundle();
        bundle.putString("uid",uid);
        bundle.putString("uImgUrl",uImgUrl);
        otherFragment.setArguments(bundle);
        return otherFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.other_fragment,container,false);

        addOther=view.findViewById(R.id.btn_add_other);
        listOther=view.findViewById(R.id.list_other);
        userid=getArguments().getString("uid");

        otherAdapter=new OtherAdapter(getContext(),list);
        listOther.setAdapter(otherAdapter);
        swipeRefreshLayout=view.findViewById(R.id.swipe_refresh_other);

        swipeRefreshLayout.setOnRefreshListener(new OtherRefresh());
        initData();
        addOther.setOnClickListener(new AddClick());
        return view;
    }

    private class AddClick implements View.OnClickListener{


        @Override
        public void onClick(View v) {
            Intent intent =new Intent(getContext(),AddOtherActivity.class);
            intent.putExtra("userid",userid);
            startActivity(intent);
        }
    }
    private class OtherRefresh implements SwipeRefreshLayout.OnRefreshListener{

        @Override
        public void onRefresh() {

            initData();

            swipeRefreshLayout.setRefreshing(false);
        }
    }

    private void initData(){
        list.clear();
        MyVolley.goVolley("http://" + IP_ + ":8080/AndroidServiceHi/userServlet?action=getOther&id=" + userid, getContext(),
                new MyVolley.VolleyCallback() {
                    @Override
                    public void onSuccess(String s) {
                        net.sf.json.JSONArray jsonArray=new net.sf.json.JSONArray(s);
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject=jsonArray.getJSONObject(i);
                            Map<String ,Object> map=new HashMap<String, Object>();
                            map.put("username",jsonObject.getString("username"));
                            map.put("content",jsonObject.getString("content"));
                            map.put("time",jsonObject.getString("time"));
                            map.put("imgurl",jsonObject.getString("imgurl"));
                            list.add(map);
                        }
                        otherAdapter.notifyDataSetChanged();
                    }
                });

    }

}
