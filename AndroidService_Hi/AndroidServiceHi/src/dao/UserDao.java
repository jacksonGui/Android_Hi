package dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import utils.DBUtils;
import entity.User;

public class UserDao {

	public User findByUsername(String username, String password) {
		User user=null;
		String sql="select * from user where username=? and password=?";
		List<Map<String,Object>> list=DBUtils.query(sql, username,password);
		if(!list.isEmpty()){
			user=new User();
			user.setUsername((String)list.get(0).get("username"));
			user.setPassword((String)list.get(0).get("password"));
			user.setId((Integer)list.get(0).get("id"));
			user.setImgurl(list.get(0).get("imgurl").toString());
		}
		return user;
	}

	public List<Map<String, Object>> getGroup(String id) {
		String sql="select distinct fgroup from friends where uid=?";
		return DBUtils.query(sql, id);
	}

	public List<Map<String, Object>> getFriends(String id) {
		String sql="select a.fid,a.fgroup,b.username,b.isonline,b.imgurl from friends as a,user as b where a.uid=? and a.fid=b.id";
		return DBUtils.query(sql, id);
	}

	public int insertUser(User user) {
		String sql="insert into user(username,password,email,phone) value(?,?,?,?)";
		Object[] params={
				user.getUsername(),user.getPassword(),user.getEmail(),user.getPhone()
		};
		return DBUtils.update(sql, params);
	}
	
	public User findByUsername(String username) {
		User user=null;
		String sql="select * from user where username=?";
		List<Map<String,Object>> list=DBUtils.query(sql, username);
		if(!list.isEmpty()){
			user=new User();
			user.setUsername((String)list.get(0).get("username"));
			user.setPassword((String)list.get(0).get("password"));
			user.setId((Integer)list.get(0).get("id"));
		}
		return user;
	}

	public List<Map<String, Object>> findByLikeUsername(String username) {
		String sql="select id,username,isonline,imgurl from user where username like ?";
		return DBUtils.query(sql, "%"+username+"%");
	}

	public List<Map<String, Object>> findById(String id) {
		String sql="select * from user where id = ?";
		return DBUtils.query(sql, id);
	}

	public void addFriendAsk(String uid, String fid) {
		String sql="insert into add_request(uid,fid,isok,time) value(?,?,?,?)";
		Object params[]={
				uid,fid,"0",new Date()
		};
		DBUtils.update(sql, params);
		
	}

	public void addFriendReceive(String requestid, String uid, String fid) {
		String sql1="update add_request set isok=1 where id=?";
		String sql2="insert into friends(uid,fid,fgroup) value(?,?,?)";
		DBUtils.update(sql1, requestid);
		DBUtils.update(sql2, uid,fid,"我的好友");
		DBUtils.update(sql2, fid,uid,"我的好友");
		
	}

	public List<Map<String, Object>> findAddListById(String fid) {
		String sql="select a.id,a.uid,a.fid,a.time,b.username,b.imgurl from add_request as a,user as b where a.fid=? and a.uid=b.id and a.isok=0";
		return DBUtils.query(sql, fid);
	}

}
