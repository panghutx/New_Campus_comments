package cn.youngsay.food.service;

import cn.youngsay.food.mapper.UserMapper;
import cn.youngsay.food.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public int insert(UserInfo userInfo){
        return userMapper.insert(userInfo);
    }

    //根据loginname查找userinfo
    public UserInfo selectByLoginName(String loginName){
        return userMapper.selectByLoginName(loginName);
    }

    //根据uid查找userinfo
    public UserInfo selectByUid(Integer uid){
        return userMapper.selectByUid(uid);
    }

    //根据uid修改信息
    public int update(Integer uid,String username,String password,String head){
        return userMapper.update(uid, username, password, head);
    }
}
