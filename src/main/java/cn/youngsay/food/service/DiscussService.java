package cn.youngsay.food.service;

import cn.youngsay.food.mapper.DiscussMapper;
import cn.youngsay.food.model.Discuss;
import cn.youngsay.food.model.UserInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DiscussService {

    @Autowired
    private DiscussMapper discussMapper;

    public int submit(Discuss discuss){
        return discussMapper.submit(discuss);
    }
    public List<Discuss> headList(){
        return discussMapper.headList();
    }

    public List<Discuss> queryList(Integer limit, Integer offset,Integer score, String title){
        return discussMapper.queryList(limit, offset, score, title);
    }

    public int queryCount(Integer score,String title){
        return discussMapper.queryCount(score, title);
    }

    public List<Discuss> getListByUid(Integer uid){
        return  discussMapper.getListByUid(uid);
    }
}
