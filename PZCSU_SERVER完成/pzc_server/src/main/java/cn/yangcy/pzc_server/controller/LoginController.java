package cn.yangcy.pzc_server.controller;

import cn.yangcy.pzc_server.bean.Admin;
import cn.yangcy.pzc_server.bean.JsonResult;
import cn.yangcy.pzc_server.bean.User;
import cn.yangcy.pzc_server.service.AdminService;
import cn.yangcy.pzc_server.service.UserService;
import cn.yangcy.pzc_server.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

//    @RequestMapping(value = "/user/login",method = RequestMethod.POST)
    @PostMapping(value = "admin/login")
    public String login(@RequestParam("account") String account, @RequestParam("password") String password,
                        Map<String,Object> map, HttpSession session){
        if(!StringUtils.isEmpty(account) && !StringUtils.isEmpty(password)){
            Admin admin = adminService.getAdminByAccount(account);
            if(admin != null){
                if(admin.getPassword().equals(password)){
                    session.setAttribute("name",admin.getName());
                    map.put("name",admin.getName());
//            return "redirect:index";
                    return "index";
                } else {
                    session.invalidate();
                    map.put("errMsg","用户名或密码错误！");
                    return "login";
                }
            } else {
                session.invalidate();
                map.put("errMsg","用户名或密码错误！");
                return "login";
            }
        } else {
            session.invalidate();
            map.put("errMsg","用户不存在！");
            return "login";
        }
    }

}
