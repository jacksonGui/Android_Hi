package com.zzg.zcib.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import com.zzg.zcib.entity.Msg;
import com.zzg.zcib.hi.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatSQLiteUtil {
    private  ChatSQLiteHelper chatSQLiteHelper;
    private Context context;

    public ChatSQLiteUtil(Context context) {
        chatSQLiteHelper=new ChatSQLiteHelper(context,"userinfo.db",null,1);
        this.context=context;
    }

    public void insert(Msg msg){
        String sql="insert into chat(uid,fid,msgtype,msgcontent,time,fname,imgurl,fimg) values(?,?,?,?,?,?,?,?)";
        SQLiteDatabase database=chatSQLiteHelper.getWritableDatabase();
        database.execSQL(sql,new String[]{msg.getUid(),msg.getFid(),msg.getType(),msg.getContent(),getDate().toString(),msg.getFname(),msg.getImgurl(),msg.getfImg()});
    }

    public List<Map<String,Object>> quaryOneMsg(String uid){
        List<Map<String,Object>> list=new ArrayList<Map<String, Object>>();
        String sql="select fid,msgcontent,time,fname,imgurl,fimg from (select fid,msgcontent,time,fname,imgurl,fimg from chat where uid=? order by time) group by fid";
        SQLiteDatabase database=chatSQLiteHelper.getWritableDatabase();
        Cursor cursor=database.rawQuery(sql,new String[]{uid});
        if (cursor.moveToFirst()){
            do {
                final Map<String,Object> map=new HashMap<String, Object>();

                map.put("fid",cursor.getString(cursor.getColumnIndex("fid")));
                map.put("msgcontent",cursor.getString(cursor.getColumnIndex("msgcontent")));
                map.put("time",cursor.getString(cursor.getColumnIndex("time")));
                map.put("fname",cursor.getString(cursor.getColumnIndex("fname")));
                map.put("imgurl",cursor.getString(cursor.getColumnIndex("imgurl")));
                map.put("fimg",cursor.getString(cursor.getColumnIndex("fimg")));
                list.add(map);
            }while (cursor.moveToNext());
        }
        return list;
    }

    public List<Msg> quaryMsg(String uid,String fid){
        List<Msg> list=new ArrayList<Msg>();
        String sql="select msgtype,msgcontent,time,imgurl from chat where uid=? and fid=? order by time";
        SQLiteDatabase database=chatSQLiteHelper.getWritableDatabase();
        Cursor cursor=database.rawQuery(sql,new String[]{uid,fid});
        if (cursor.moveToFirst()){
            do {
                list.add(new Msg(R.mipmap.ic_launcher,cursor.getString(cursor.getColumnIndex("msgcontent")),
                        null,null,cursor.getString(cursor.getColumnIndex("time")),
                        cursor.getString(cursor.getColumnIndex("msgtype")),null,
                        cursor.getString(cursor.getColumnIndex("imgurl")),null));
            }while (cursor.moveToNext());
        }
        return list;
    }

    private String getDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String dateString = formatter.format(currentTime);
        String[] date1 = dateString.split("-");
        return date1[1]+"-"+date1[2]+" "+date1[3]+":"+date1[4];
    }
}
