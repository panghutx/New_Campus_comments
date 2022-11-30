package cn.youngsay.food.mapper;

import cn.youngsay.food.model.Discuss;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DiscussMapper {
    //提交评论
    int submit(Discuss discuss);
    //评论展示（首页）
    List<Discuss> headList();
    //条件查询
    List<Discuss> queryList(@Param("limit") Integer limit,@Param("offset")Integer offset,@Param("score")Integer score,@Param("title")String title);
    //获得数据量
    int queryCount(@Param("score")Integer score,@Param("title")String title);
    //根据uid获取评论信息
    List<Discuss> getListByUid(Integer uid);
}
