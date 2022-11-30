package cn.youngsay.food.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class AppConfig implements WebMvcConfigurer {
    @Value("${url.img")
    private String URL;

    @Autowired
    private LoginInterceptor loginInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).addPathPatterns("/userinfo.html").
                addPathPatterns("/modify.html");
    }

    //将jar文件下静态资源路径对应到磁盘文件路径，目的是能在web访问图片
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//
//        //System.getProperty("user.dir") 获取到当前jar的所在路径
//        String property ="File:"+ System.getProperty("user.dir")+"/classes/static/images/";
//        //所以当需要访问上传的图片时，url路径为：http://你的服务器IP:8080/images/book/1.jpg
//        registry.addResourceHandler("/images/**").addResourceLocations(property);
//
//    }


}
