package cn.youngsay.food.controller;

import cn.youngsay.food.model.Discuss;
import cn.youngsay.food.model.UserInfo;
import cn.youngsay.food.service.DiscussService;
import cn.youngsay.food.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class DiscussController {
    @Autowired
    private DiscussService discussService;
    @Autowired
    private UserService userService;

    @RequestMapping("/submit")
    @ResponseBody
    //-1--输入内容为空  0--未登录  1--成功
    public int submit(HttpServletRequest req,String title, String content, Integer score, String status){
        //判空
        if(!StringUtils.hasLength(title)||!StringUtils.hasLength(content)||score==null){
            return 0;
        }
        System.out.println(status);
        //判断发送方式（匿名、非匿名）
        HttpSession session=null;
        UserInfo userinfo = null;
        Integer uid = 0;
        if(status.equals("否")){
            session = req.getSession(false);
            if(session==null){
                return 0;
            }
            userinfo = (UserInfo) session.getAttribute("userinfo");
            if(userinfo==null){
                return 0;
            }
            uid = userinfo.getUid();
        }
        Discuss discuss = new Discuss();
        discuss.setContent(content);
        discuss.setScore(score);
        discuss.setTitle(title);
        discuss.setUid(uid);
        int submit = discussService.submit(discuss);
        return submit;
    }

    //首页展示6条数据
    @ResponseBody
    @RequestMapping("/headList")
    public Map<String,Object> headList(){
        Map<String,Object> result = new HashMap<>();
        List<Discuss> discusses = discussService.headList();
        List<String> usernames = new ArrayList<>();
        //根据uid获取username
        for (Discuss discuss:discusses){
            Integer uid = discuss.getUid();
            if(uid==0){
                //匿名用户
                usernames.add("匿名用户");
            }else{
                UserInfo userInfo = userService.selectByUid(uid);
                String username = userInfo.getUsername();
                usernames.add(username);
            }

        }
        result.put("discusses",discusses);
        result.put("usernames",usernames);
        return result;
    }

    //条件查询显示
    @ResponseBody
    @RequestMapping("/queryList")
    public Map<String,Object> queryList(Integer pindex,Integer psize,Integer score,String position){
        Map<String,Object> result = new HashMap<>();

        //校验
        if (pindex == null || pindex < 1) {
            pindex = 1;
        }
        if (psize == null || psize <= 0) {
            psize = 6;
        }
        if(!StringUtils.hasLength(position)){
            position=null;
        }


        //计算偏移量
        Integer offset = (pindex-1)*psize;
        System.out.println("offset:"+offset);
        List<Discuss> discusses = discussService.queryList(psize, offset, score, position);
        List<String> usernames = new ArrayList<>();
        //根据uid获取username
        for (Discuss discuss:discusses){
            Integer uid = discuss.getUid();
            if(uid==0){
                //匿名用户
                usernames.add("匿名用户");
            }else{
                UserInfo userInfo = userService.selectByUid(uid);
                if(userInfo==null){
                    usernames.add("匿名用户");
                }else{
                    String username = userInfo.getUsername();
                    usernames.add(username);
                }

            }

        }

        //计算总数
        int count = discussService.queryCount(score, position);
        result.put("discusses",discusses);
        result.put("usernames",usernames);
        result.put("count",count);

        return result;
    }

    //根据uid获取评论信息
    @ResponseBody
    @RequestMapping("/getListByUid")
    public List<Discuss> getListByUid(Integer uid){
        return discussService.getListByUid(uid);
    }

}
