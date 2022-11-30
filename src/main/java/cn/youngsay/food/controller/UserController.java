package cn.youngsay.food.controller;

import cn.youngsay.food.common.PasswordUtil;
import cn.youngsay.food.common.TencentCOS;
import cn.youngsay.food.model.UserInfo;
import cn.youngsay.food.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Value("${url.img}")
    private String URL;


    @RequestMapping("/sign")
    @ResponseBody
    public int insert(HttpServletRequest req,UserInfo userInfo){
        //判断输入数据是否为空
        if(!StringUtils.hasLength(userInfo.getUsername())||
                !StringUtils.hasLength(userInfo.getLoginName())||
                !StringUtils.hasLength(userInfo.getPassword())){
            return 0;
        }
        //判断loginname是否唯一
        UserInfo userInfo1 = userService.selectByLoginName(userInfo.getLoginName());
        if(userInfo1!=null){
            return 0;
        }

        //添加用户
        userInfo.setPassword(PasswordUtil.encrypt(userInfo.getPassword()));

        int insert = userService.insert(userInfo);
        UserInfo dbUserInfo = userService.selectByLoginName(userInfo.getLoginName());
        //存入session
        HttpSession session = req.getSession(true);
        session.setAttribute("userinfo",dbUserInfo);

        return 1;
    }

    @ResponseBody
    @RequestMapping("/user")
    public UserInfo getByUid(HttpServletRequest req,Integer uid){
        //判断uid是否合法
        System.out.println(uid);
        if(uid == null){
            return null;
        }
        UserInfo userInfo = userService.selectByUid(uid);
        //判断用户与登录用户是否一致 TODO
        HttpSession session = req.getSession(false);
        UserInfo userinfo = (UserInfo) session.getAttribute("userinfo");
        //登录用户与要查看的不一致
        if(!userinfo.getUid().equals(uid)){
            return null;
        }
        //将密码置空
        userInfo.setPassword("");
        return userInfo;
    }

    @ResponseBody
    @RequestMapping("/login")
    public boolean login(HttpServletRequest req,String loginName, String password){
        if(!StringUtils.hasLength(loginName)||!StringUtils.hasLength(password)){
            return false;
        }
        UserInfo userInfo = userService.selectByLoginName(loginName);
        //查找数据库中的密码，与用户输入密码比对
        if(userInfo==null){
            return false;
        }
        String dbPassword = userInfo.getPassword();
        if(!PasswordUtil.decrypt(password,dbPassword)){
            return false;
        }
        //存入session
        HttpSession session = req.getSession(true);
        session.setAttribute("userinfo",userInfo);
        return true;
    }

    @ResponseBody
    @RequestMapping("logout")
    public void logout(HttpServletRequest req){
        HttpSession session = req.getSession(false);
        session.removeAttribute("userinfo");
    }

    @ResponseBody
    @RequestMapping("person")
    //根据session获取个人uid
    public Integer getLoginName(HttpServletRequest req){
        HttpSession session = req.getSession(false);
        if(session==null){
            return null;
        }
        UserInfo userinfo = (UserInfo) session.getAttribute("userinfo");
        System.out.println(userinfo);
        //注册后从数据库取uid
        if(userinfo!=null&&userinfo.getUid()!=null){
            return userinfo.getUid();
        }
        return null;

    }

    //根据uid获取个人信息
    @ResponseBody
    @RequestMapping("/show")
    public UserInfo show(Integer uid){
        UserInfo userInfo = userService.selectByUid(uid);
        userInfo.setPassword("");
        return userInfo;
    }

    @RequestMapping("update")
    @ResponseBody
    //保存修改后的信息，包括上传图片
    public int update(HttpServletRequest req,Integer uid,String username,String password,@RequestPart(value = "myfile",required = false)MultipartFile file) throws IOException {

        //判断登录用户与当前uid是否一致
        HttpSession session = req.getSession(false);
        if(session==null){
            return 0;
        }
        UserInfo userinfo = (UserInfo) session.getAttribute("userinfo");
        if(userinfo==null||!userinfo.getUid().equals(uid)){
            return 0;
        }
        if(!StringUtils.hasLength(username)){
            username=null;
        }
        if(!StringUtils.hasLength(password)){
            password=null;
        }else{
            password = PasswordUtil.encrypt(password);
        }

        //调用上传图片方法（本地或腾讯云）
        String imageName = tencentCos(file);

        userService.update(uid,username,password,imageName);
        return 1;
    }
    /**
     * 删除临时文件
     *
     * @param files 临时文件，可变参数
     */
    private void deleteFile(File... files) {
        for (File file:files) {
            if (file.exists()) {
                file.delete();
            }
        }
    }

    //以下针对腾讯云图床
    private String tencentCos(MultipartFile file) throws IOException {
        String imageName = null;
        if(file!=null) {
            String oldName = file.getOriginalFilename(); //得到上传的文件名  例如123.png
            assert oldName != null;
            String format = oldName.substring(oldName.lastIndexOf(".")); //得到后缀名
            //使用uuid作为文件名，防止生成的临时文件重复
            File tempFile = File.createTempFile(UUID.randomUUID().toString(), format);
            //转成File
            file.transferTo(tempFile);
            //调用腾讯云工具上传文件
            imageName = TencentCOS.uploadfile(tempFile, "avatar");
            //程序结束时，删除临时文件
            deleteFile(tempFile);

        }
        return URL+imageName;
    }

    //本地上传图片方法
    private String local(MultipartFile file){

        String dirPath = null;
        if(file!=null){
            String oldName = file.getOriginalFilename(); //得到上传的文件名  例如123.png
            assert oldName != null;
            String format = oldName.substring(oldName.lastIndexOf(".")); //得到后缀名
            String finalName = UUID.randomUUID()+format; //例如 dde95b5b-7a28-42f1-9afc-063b353b4ec1.png
            //存储路径
            String path = URL;
            //路径+文件名 /D:/download/food/target/classes/static/images/dde95b5b-7a28-42f1-9afc-063b353b4ec1.png
            //获取jar包所在目录
            ApplicationHome h = new ApplicationHome(getClass());
            File jarF = h.getSource();
            String workPath = path+finalName;
            System.out.println(workPath);
//            //创建目录
            File filePath=new File(path);
            if(!filePath.exists()){
                filePath.mkdirs();
            }

            try{
                //将文件写入磁盘
                file.transferTo(new File(workPath));
            }catch (Exception e){
                e.printStackTrace();
            }
            dirPath = "/images/"+finalName;

        }
        return dirPath;
    }
}
