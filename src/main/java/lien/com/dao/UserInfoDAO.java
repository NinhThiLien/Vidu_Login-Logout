package lien.com.dao;

import java.util.List;

import lien.com.model.UserInfo;

public interface UserInfoDAO {

	public UserInfo findUserInfo(String userName);
    
    // [USER,AMIN,..]
    public List<String> getUserRoles(String userName);
}
