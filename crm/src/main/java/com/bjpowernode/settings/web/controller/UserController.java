package com.bjpowernode.settings.web.controller;

import com.bjpowernode.commons.contants.Contants;
import com.bjpowernode.commons.domain.ReturnObject;
import com.bjpowernode.commons.utils.DateUtils;
import com.bjpowernode.settings.web.domain.User;
import com.bjpowernode.settings.web.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @RequestMapping("/settings/qx/user/toLogin.do")
    public String toLogin(){
        return "settings/qx/user/login";
    }


    @ResponseBody
    @RequestMapping("/settings/qx/user/login.do")
    public Object login(String loginAct, String loginPwd, String isRemPwd, HttpServletRequest request, HttpServletResponse response, HttpSession session){
//        封装参数
        Map<String,Object> map = new HashMap<>();
        map.put("loginAct",loginAct);
        map.put("loginPwd",loginPwd);
//        调用service层方法查询用户
        User user = userService.selectUserByActAndPwd(map);
//        根据查询结果返回响应信息
        ReturnObject returnObject = new ReturnObject();
        if (user==null){
          returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
          returnObject.setMessage("用户名或密码错误");
        }else{
            String expireTime = user.getExpiretime();
            String nowTime = DateUtils.formateDateTime(new Date());
//           用户使用权过期，登录失败
            if (expireTime.compareTo(nowTime)<0){
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("用户过期");

            }else if ("0".equals(user.getLockstate())){
//                状态被锁定
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("用户状态被锁定无法使用");
            }
//            else if (!user.getAllowips().contains(request.getRemoteAddr())){
////                登录ip不存在指定ip之内
//                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
//                returnObject.setMessage("用户不在指定ip范围内");
//            }
            else {
//                登录成功
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
//                将用户存进session中
                session.setAttribute(Contants.SESSION_USER,user);
//                判断是否选择了记住密码，记住了密码则用cookie记录密码
                if(isRemPwd.equals("true")){
                    Cookie c1 = new Cookie("loginAct",user.getLoginact());
                    Cookie c2 = new Cookie("loginPwd",user.getLoginpwd());
                    c1.setMaxAge(24*60*60*10);
                    c2.setMaxAge(24*60*60*10);

                    response.addCookie(c1);
                    response.addCookie(c2);
                }else {
                    Cookie c1 = new Cookie("loginAct","1");
                    c1.setMaxAge(0);
                    response.addCookie(c1);
                    Cookie c2 = new Cookie("loginPwd","1");
                    c2.setMaxAge(0);
                    response.addCookie(c2);
                }
            }
        }
        return returnObject;
    }

//    安全退出，清空cookie和session
    @RequestMapping("/settings/qx/user/logout.do")
    public String logout(HttpServletResponse response,HttpSession session){
        Cookie c1 = new Cookie("loginAct","1");
        Cookie c2 = new Cookie("loginPwd","1");
        c1.setMaxAge(0);
        c2.setMaxAge(0);
        response.addCookie(c1);
        response.addCookie(c2);

//        清空session
        session.invalidate();
        return "redirect:/";
    }
}
