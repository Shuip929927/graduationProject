package cn.yangcy.pzc_server.controller;

import cn.yangcy.pzc_server.bean.JsonResult;
import cn.yangcy.pzc_server.bean.Activity;
import cn.yangcy.pzc_server.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    /**
     * 根据ID查询学生组织活动
     * @param id
     * @return
     */
    //@PathVariable:用于获取url中的数据
    @GetMapping(value = "activity/query/id/{id}")
    public Activity getActivityById(@PathVariable(value = "id") Integer id){
        System.out.println("根据ID查询学生组织活动");
        try {
            return activityService.getActivityById(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 查询学生组织活动列表
     * @return
     */
    @RequestMapping(value = "activity/queryAll",method = RequestMethod.GET)
    public List<Activity> getAllStudentActivitiesList(){
        System.out.println("请求活动信息列表");
        try {
            return activityService.getActivitiesList();
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 通过UserId查询该用户报名成功的所有活动
     *  @param userId
     * @return
     */
    @RequestMapping(value = "activity/queryActState2/userId/{userId}",method = RequestMethod.GET)
    public List<Activity> getUserEnrollState2ActivityListByUserId(@PathVariable(value = "userId")Integer userId){
        System.out.println("通过UserId查询该用户报名成功的所有活动");
        try {
            List<Activity> activities = activityService.getUserEnrollState2ActivityListByUserId(userId);
            return activities;
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 通过UserId查询该用户为部门负责人时举办的活动
     *  @param userId
     * @return
     */
    @RequestMapping(value = "activity/queryOrgHold/userId/{userId}",method = RequestMethod.GET)
    public List<Activity> getOrganizationHoldActivityByUserId(@PathVariable(value = "userId")Integer userId){
        System.out.println("通过UserId查询该用户为部门负责人时举办的活动");
        try {
            return activityService.getOrganizationHoldActivityByUserId(userId);
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 添加学生组织活动
     * @param activity
     * @return
     */
    @RequestMapping(value = "activity/add",method = RequestMethod.POST)
    public ResponseEntity<JsonResult> add(@RequestBody Activity activity){
        JsonResult jsonResult = new JsonResult();
        System.out.println("添加学生组织活动");
        System.out.println(activity.toString());
        try {
            int addResult= activityService.add(activity);

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
     * 根据id删除学生组织活动
     * @param id
     * @return
     */
    @RequestMapping(value = "activity/delete/{id}",method = RequestMethod.DELETE)
    public ResponseEntity<JsonResult> delete(@PathVariable(value = "id") Integer id){
        JsonResult result = new JsonResult();
        System.out.println("根据id删除学生组织活动");
        try {
            int deleteResult = activityService.delete(id);
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
     * 修改学生组织活动信息
     * @param activity
     * @return
     */
    @RequestMapping(value = "activity/update",method = RequestMethod.PUT)
    public ResponseEntity<JsonResult> update(@RequestBody Activity activity){
        JsonResult result = new JsonResult();
        System.out.println("修改学生组织活动信息");
        try {
            int updateResult = activityService.update(activity);
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
