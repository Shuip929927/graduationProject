package cn.yangcy.pzc_server.controller;

import cn.yangcy.pzc_server.bean.JsonResult;
import cn.yangcy.pzc_server.bean.User;
import cn.yangcy.pzc_server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@Controller
@RestController
public class UserController {

    @Autowired
    private UserService userService;
    /**
     * 根据ID查询学生
     * @param account
     * @return
     */
    //@PathVariable:用于获取url中的数据
    @GetMapping(value = "user/query/{account}")
    public User getUserByAccount(@PathVariable(value = "account") Integer account){
        try {
            User user = userService.getUserByAccount(account);
            return user;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 查询学生列表
     * @return
     */
    @RequestMapping(value = "user/query",method = RequestMethod.GET)
    public List<User> getAllUserList(){

        try {
            List<User> users=userService.getUserList();
            return users;
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 添加用户
     * @param user
     * @return
     */
    @RequestMapping(value = "user/add",method = RequestMethod.POST)
    public ResponseEntity<JsonResult> add(@RequestBody User user){
        JsonResult jsonResult=new JsonResult();

        try {
            int addResult=userService.add(user);

            if (addResult < 0){
                jsonResult.setResult(addResult);
                jsonResult.setStatus("failed");
            } else {
                jsonResult.setResult(addResult);
                jsonResult.setStatus("ok");
            }

        }catch (Exception e){
            jsonResult.setResult(e.getClass().getName()+":"+e.getMessage());
            jsonResult.setStatus("error");
            e.printStackTrace();
        }

        return ResponseEntity.ok(jsonResult);

    }

    /**
     * 根据account删除用户
     * @param account
     * @return
     */
    @RequestMapping(value = "user/delete/{account}",method = RequestMethod.DELETE)
    public ResponseEntity<JsonResult> delete(@PathVariable(value = "account") Integer account){
        JsonResult result = new JsonResult();

        try {
            int deleteResult = userService.delete(account);
            if (deleteResult < 0){
                result.setResult(deleteResult);
                result.setStatus("failed");
            }else {
                result.setResult(deleteResult);
                result.setStatus("ok");
            }
        }catch (Exception e){
            result.setResult(e.getClass().getName()+":"+e.getMessage());
            result.setStatus("error");

            e.printStackTrace();
        }
        return ResponseEntity.ok(result);

    }

    /**
     * 根据id修改用户信息
     * @param user
     * @return
     */
    @RequestMapping(value = "user/update/",method = RequestMethod.PUT)
    public ResponseEntity<JsonResult> update(@RequestBody User user){
        JsonResult result = new JsonResult();

        try {
            int updateResult = userService.update(user);
            if (updateResult < 0){
                result.setResult(updateResult);
                result.setStatus("fail");
            }else {
                result.setResult(updateResult);
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
