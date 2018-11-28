package service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import dao.UserDao;
import entity.User;

public class UserService {
	private UserDao userDao=new UserDao();

	public User login(String username, String password) {
		
		return userDao.findByUsername(username,password);
	}

	public JSONObject getFriendsJson(String id) {
		List<Map<String,Object>> groupList=userDao.getGroup(id);
		List<Map<String,Object>> friendsList=userDao.getFriends(id);
		JSONArray jsonArray=null;
		JSONObject one=null;
		JSONObject friendsJson=new JSONObject();
		JSONArray keys=new JSONArray();
		for(int i=0;i<groupList.size();i++){
			jsonArray=new JSONArray();
			for(int j=0;j<friendsList.size();j++){
				if(groupList.get(i).get("fgroup").equals(friendsList.get(j).get("fgroup"))){
					one=new JSONObject();
					one.put("fid", friendsList.get(j).get("fid"));
					one.put("username", friendsList.get(j).get("username"));
					one.put("isonline", friendsList.get(j).get("isonline"));
					one.put("imgurl", friendsList.get(j).get("imgurl"));
					jsonArray.put(one);
					System.out.println(jsonArray);
				}
			}
			friendsJson.put(groupList.get(i).get("fgroup").toString(), jsonArray);
			keys.put(new JSONObject().put("key", groupList.get(i).get("fgroup").toString()));
		}
		friendsJson.put("keys",keys);
		
		System.out.println(friendsJson);
		
		
		return friendsJson;
	}

	public boolean register(User user) {
		boolean flag=false;
		int count=userDao.insertUser(user);
		if(count!=0){
			flag=true;
			Integer id=userDao.findByUsername(user.getUsername()).getId();
			userDao.insertSelf(id);
		}
		return flag;
	}

	public boolean hasUser(String username) {
		boolean flag=false;
		User user=userDao.findByUsername(username);
		if(user==null){
			flag=true;
		}
		return flag;
	}

	public JSONArray searchFriends(String username) {
		JSONArray jsonArray=new JSONArray();
		List<Map<String,Object>> list=userDao.findByLikeUsername(username);
//		System.out.println(list.get(0).get("username"));
		for(int i=0;i<list.size();i++){
			jsonArray.put(list.get(i));
			
		}
		
		return jsonArray;
	}

	public JSONObject getOneUser(String id) {
		List<Map<String,Object>> list=userDao.findById(id);
		JSONObject jsonObject=new JSONObject(list.get(0));
		
		return jsonObject;
	}

	public void addFriendAsk(String uid, String fid) {
		userDao.addFriendAsk(uid,fid);
		
	}

	public void addFriendReceive(String requestid, String uid, String fid) {
		userDao.addFriendReceive(requestid,uid,fid);
		
	}

	public JSONArray getAddList(String fid) {
		JSONArray jsonArray=new JSONArray();
		List<Map<String,Object>> list=userDao.findAddListById(fid);
//		System.out.println(list.get(0).get("username"));
		for(int i=0;i<list.size();i++){
//			Map map=list.get(i);
//			JSONObject jsonObj=new JSONObject();
//			jsonObj.put("id", map.get("id"));
//			jsonObj.put("fid", map.get("fid"));
//			jsonObj.put("uid", map.get("uid"));
//			jsonObj.put("time", map.get("time"));
//			jsonObj.put("username", map.get("username"));
//			jsonArray.put(jsonObj);
			
			jsonArray.put(list.get(i));
			
		}
		
		
		
		return jsonArray;
	}

	public JSONObject getMyInfo(String id) {
		List<Map<String,Object>> list=userDao.findMyInfoById(id);
		JSONObject jsonObject=new JSONObject(list.get(0));
		
		return jsonObject;
	}

	public boolean updateMyInfo(User user) {
		boolean flag=false;
		int count=userDao.updateInfo(user);
		if(count!=0){
			flag=true;
		}
		return flag;
	}

	public JSONArray getOtherList(String id) {
		JSONArray jsonArray=new JSONArray();
		List<Map<String,Object>> list=userDao.findOtherById(id);
		for(int i=0;i<list.size();i++){
			jsonArray.put(list.get(i));
			
		}
		return jsonArray;
	}

	public void addOther(String id, String content, String time) {
		userDao.insertOther(id,content,time);
		
	}

	public String getGroup(String uid,String fid) {
		List<Map<String,Object>> list=userDao.findGroupById(uid,fid);
		return list.get(0).get("fgroup").toString();
	}

	public void updateGroup(String uid, String fid, String fgroup) {
		userDao.updateGroup(uid,fid,fgroup);
	}

	public String getImgUrl(String username) {
		List<Map<String,Object>> list=userDao.findImgUrlByUsername(username);
		return list.get(0).get("imgurl").toString();
	}

}
