package com.zzg.zcib.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.zzg.zcib.adapter.AddFriendReceiveAdapter;
import com.zzg.zcib.entity.AddRequest;
import com.zzg.zcib.hi.R;
import com.zzg.zcib.utils.MyVolley;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import static com.zzg.zcib.utils.MyVolley.IP_;

public class AddFriendReceiveActivity extends AppCompatActivity {
    private String userid;
    private ListView receiveList;
    private List<AddRequest> data=new ArrayList<AddRequest>();
    private AddFriendReceiveAdapter addFriendReceiveAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend_receive);
        userid=getIntent().getExtras().getString("userid");

        receiveList=findViewById(R.id.list_add_receive);
        addFriendReceiveAdapter=new AddFriendReceiveAdapter(data,AddFriendReceiveActivity.this);
        receiveList.setAdapter(addFriendReceiveAdapter);
        initData();
    }

    private void initData(){
        MyVolley.goVolley("http://" + IP_ + ":8080/AndroidServiceHi/userServlet?action=getFriendAddList&fid=" + userid, AddFriendReceiveActivity.this,
                new MyVolley.VolleyCallback() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            JSONArray jsonArray=new JSONArray(s);
                            for (int i = 0; i <jsonArray.length() ; i++) {
                                JSONObject jsonObject=jsonArray.getJSONObject(i);
                                data.add(new AddRequest(jsonObject.getString("id"),
                                        jsonObject.getString("fid"),
                                        jsonObject.getString("uid"),
                                        "",
                                        jsonObject.getString("time"),
                                        jsonObject.getString("username"),
                                        jsonObject.getString("imgurl")));
                            }
                            addFriendReceiveAdapter.notifyDataSetChanged();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
