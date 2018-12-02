package com.zzg.zcib.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.zzg.zcib.adapter.AddFriendAdapter;
import com.zzg.zcib.entity.Friends;
import com.zzg.zcib.entity.User;
import com.zzg.zcib.hi.R;
import com.zzg.zcib.utils.MyVolley;

import org.apache.commons.collections.ArrayStack;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.zzg.zcib.utils.MyVolley.IP_;

public class AddFriendActivity extends AppCompatActivity {
    private EditText searchText;
    private Button searchBtn;
    private ListView searchResultList;
    private List<Friends> listData=new ArrayList<Friends>();
    private AddFriendAdapter addFriendAdapter;
    private String userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        searchText=findViewById(R.id.add_search_text);
        searchBtn=findViewById(R.id.btn_add_search);
        searchResultList=findViewById(R.id.list_add_friends);
        userid=getIntent().getExtras().getString("userid");
        addFriendAdapter=new AddFriendAdapter(AddFriendActivity.this,listData,userid);
        searchResultList.setAdapter(addFriendAdapter);

        searchBtn.setOnClickListener(new SearchClick());

    }

    private class SearchClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {

            String username1=searchText.getText().toString();

            if (!"".equals(username1)){
                try {
                    username1 = java.net.URLEncoder.encode(java.net.URLEncoder.encode(username1, "utf-8"));
                } catch (java.io.UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                MyVolley.goVolley("http://" + IP_ + ":8080/AndroidServiceHi/userServlet?action=searchFriends&username=" + username1, AddFriendActivity.this,
                        new MyVolley.VolleyCallback() {
                            @Override
                            public void onSuccess(String s) {
                                listData.clear();
                                net.sf.json.JSONArray jsonArray=new net.sf.json.JSONArray(s);
                                for (int i=0;i<jsonArray.length();i++){
                                    net.sf.json.JSONObject jsonObject=jsonArray.getJSONObject(i);
                                    listData.add(new Friends(jsonObject.getInt("id"),jsonObject.getString("username"),jsonObject.getInt("isonline"),jsonObject.getString("imgurl")));
                                }
                                addFriendAdapter.notifyDataSetChanged();
                            }

                        });
            }
        }
    }




}
