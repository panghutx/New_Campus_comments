package cn.youngsay.food.mapper;

import cn.youngsay.food.model.UserInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    //新增用户
    int insert(UserInfo userInfo);
    //根据loginname查找userinfo
    UserInfo selectByLoginName(String loginName);
    //根据uid查找userinfo
    UserInfo selectByUid(Integer uid);
    //根据uid修改个人信息
    int update(Integer uid,String username,String password,String head);

}
