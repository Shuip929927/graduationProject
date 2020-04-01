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
@RequestMapping(value = "api/")
public class UserController {

    @Autowired
    private UserService userService;
    /**
     * 根据ID查询学生
     * @param account
     * @return
     */
    //@PathVariable:用于获取url中的数据
    @GetMapping(value = "user/query/account/{account}")
    public User getUserByAccount(@PathVariable(value = "account") Integer account){
        System.out.println("查询用户信息");
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
    @RequestMapping(value = "user/queryAll",method = RequestMethod.GET)
    public List<User> getAllUserList(){
        System.out.println("查询所有用户信息");
        try {
            List<User> users=userService.getUserList();
            return users;
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 查询活动报名状态为成功（2）学生列表
     * @return
     */
    @RequestMapping(value = "user/queryActState2/activityId/{activityId}",method = RequestMethod.GET)
    public List<User> getActState2UserList(@PathVariable(value = "activityId") Integer activityId){
        System.out.println("查询活动报名状态为成功（2）学生列表");
        try {
            List<User> users=userService.getActivityEnrollState2UserList(activityId);
            return users;
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 查询活动报名状态为待审核（1）学生列表
     * @return
     */
    @RequestMapping(value = "user/queryActState1/activityId/{activityId}",method = RequestMethod.GET)
    public List<User> getActState1UserList(@PathVariable(value = "activityId") Integer activityId){
        System.out.println("查询活动报名状态为待审核（1）学生列表");
        try {
            List<User> users=userService.getActivityEnrollState1UserList(activityId);
            return users;
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 查询学生组织报名状态为成功（2）学生列表
     * @return
     */
    @RequestMapping(value = "user/queryOrgState2/organizationId/{organizationId}",method = RequestMethod.GET)
    public List<User> getOrgState2UserList(@PathVariable(value = "organizationId") Integer organizationId){
        System.out.println("查询学生组织报名状态为成功（2）学生列表");
        try {
            List<User> users=userService.getOrganizationEnrollState2UserList(organizationId);
            return users;
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 查询学生组织报名状态为待审核（1）学生列表
     * @return
     */
    @RequestMapping(value = "user/queryOrgState1/organizationId/{organizationId}",method = RequestMethod.GET)
    public List<User> getOrgState1UserList(@PathVariable(value = "organizationId") Integer organizationId){
        System.out.println("查询学生组织报名状态为待审核（1）学生列表");
        try {
            List<User> users=userService.getOrganizationEnrollState1UserList(organizationId);
            return users;
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 查询accounts列表里的用户
     * @param map
     * @return
     */
//    @RequestMapping(value = "user/query/accounts",method = RequestMethod.POST)
//    public ResponseEntity<JsonResult> add(@RequestBody Map<String,Integer[]> map){
//        JsonResult jsonResult = new JsonResult();
//        List<Integer> accountsList = new ArrayList<>();
//        if(map == null){
//            System.out.println("error");
//        } else{
//            Integer[] accounts = map.get("accounts");
//            for (int i = 0; i < accounts.length; i++) {
//                accountsList.add(accounts[i]);
//                System.out.println(accounts[i]);
//            }
//        }
//        try {
//            List<User> resultUsersList = userService.getUserListByAccounts(accountsList);
//            if (resultUsersList.size()<0){
//                jsonResult.setResult("失败");
//                jsonResult.setStatus("failed");
//            } else {
//                jsonResult.setResult("成功");
//                jsonResult.setData(resultUsersList);
//                jsonResult.setStatus("ok");
//            }
//
//        }catch (Exception e){
//            jsonResult.setResult(e.getClass().getName()+":"+e.getMessage());
//            jsonResult.setStatus("error");
//            e.printStackTrace();
//        }
//
//        return ResponseEntity.ok(jsonResult);
//    }

    /**
     * 添加用户
     * @param user
     * @return
     */
    @RequestMapping(value = "user/add",method = RequestMethod.POST)
    public ResponseEntity<JsonResult> add(@RequestBody User user){
        JsonResult jsonResult=new JsonResult();
        System.out.println("添加用户");
        System.out.println(user.toString());
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
        System.out.println("删除用户");
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
     * 修改用户信息
     * @param user
     * @return
     */
    @RequestMapping(value = "user/update",method = RequestMethod.PUT)
    public ResponseEntity<JsonResult> update(@RequestBody User user){
        JsonResult result = new JsonResult();
        System.out.println("修改用户");
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
