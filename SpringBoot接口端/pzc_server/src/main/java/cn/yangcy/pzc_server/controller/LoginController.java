package cn.yangcy.pzc_server.controller;

import cn.yangcy.pzc_server.bean.JsonResult;
import cn.yangcy.pzc_server.bean.User;
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

//    @RequestMapping(value = "/user/login",method = RequestMethod.POST)
    @PostMapping(value = "/admin/login")
    public String login(@RequestParam("userAccount") String account, @RequestParam("userPassword") String password,
                        Map<String,Object> map, HttpSession session){
        if(!StringUtils.isEmpty(account) && !StringUtils.isEmpty(password)){
            User u = userService.getUserByAccount(Integer.valueOf(account));
            if(u.getPassword().equals(MD5Util.digest(password))){
                session.setAttribute("account",account);
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
    }

    @ResponseBody
    @PostMapping(value = "user/login")
    public ResponseEntity<JsonResult> login(@RequestBody Map<String,String> map){
        JsonResult result = new JsonResult();
        System.out.println(map.toString());
        if(map.isEmpty()){
            result.setStatus("error");
            return ResponseEntity.ok(result);
        }
        try {
            User u = userService.getUserByAccount(Integer.valueOf(map.get("account")));
            if (u == null){
                result.setResult("用户不存在");
                result.setStatus("fail");
            }else if(!u.getPassword().equals(map.get("password"))){
                result.setResult("密码错误");
                result.setStatus("fail");
            } else {
                result.setResult("登录成功");
                result.setData(u);
                result.setStatus("ok");
            }
        }catch (Exception e){
            result.setResult(e.getClass().getName()+":"+e.getMessage());
            result.setStatus("error");
            e.printStackTrace();
        }

        return ResponseEntity.ok(result);
    }
}
