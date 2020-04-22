package cn.yangcy.pzc_server.controller;

import cn.yangcy.pzc_server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

//@ResponseBody
@Controller
//以上两个涉及到的类可以用@RestController替换
//@RestController
public class MyController {

    @Autowired
    private UserService userService;

    @RequestMapping({"/login","/"})
    public String Login(){
        return "login";
    }

    @RequestMapping("/index")
    public String Index(){
        return "index";
    }

    @GetMapping(value = "/to/{path}")
    public String to(@PathVariable(value = "path")String path){

        return path+"/index";
    }

}
