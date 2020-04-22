package cn.yangcy.pzc_server.controller;

import cn.yangcy.pzc_server.bean.ActivityEnroll;
import cn.yangcy.pzc_server.bean.JsonResult;
import cn.yangcy.pzc_server.service.ActivityEnrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/")
public class ActivityEnrollController {

    @Autowired
    private ActivityEnrollService service;

    /**
     * 根据ID查询某个学生活动的一条报名情况
     * @param id
     * @return
     */
    //@PathVariable:用于获取url中的数据
    @GetMapping(value = "ae/query/id/{id}")
    public ActivityEnroll getActivityEnrollById(@PathVariable(value = "id") Integer id){
        System.out.println("根据ID查询某个学生活动的一条报名情况");
        try {
            return service.getActivityEnrollById(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 根据userID查询某个学生的所有学生活动报名情况
     * @param userId
     * @return
     */
    //@PathVariable:用于获取url中的数据
    @GetMapping(value = "ae/query/userId/{userId}")
    public List<ActivityEnroll> getActivityEnrollByUserId(@PathVariable(value = "userId") Integer userId) {
        System.out.println("根据userID查询某个学生的所有学生活动报名情况");
        try {
            return service.getActivityEnrollByUserId(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据活动ID查询某个学生活动的报名成功情况
     * @param activityId
     * @return
     */
    //@PathVariable:用于获取url中的数据
    @GetMapping(value = "ae/query/activityId&state2/{activityId}")
    public List<ActivityEnroll> getActivityEnrollState2ByActivityId(@PathVariable(value = "activityId") Integer activityId) {
        System.out.println("根据活动ID查询某个学生活动的报名成功情况");
        try {
            return service.getActivityEnrollState2ByActivityId(activityId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据ID查询某个学生活动的所有报名情况
     * @param activityId
     * @return
     */
    //@PathVariable:用于获取url中的数据
    @GetMapping(value = "ae/query/activityId&state1/{activityId}")
    public List<ActivityEnroll> getActivityEnrollState1ByActivityId(@PathVariable(value = "activityId") Integer activityId) {
        System.out.println("根据ID查询某个学生活动的所有报名情况");
        try {
            return service.getActivityEnrollState1ByActivityId(activityId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
    /**
     * 根据UserId 和 活动Id 查询一条报名情况
     * @param userId organizationId
     * @return
     */
    //@PathVariable:用于获取url中的数据
    @GetMapping(value = "ae/query/userId&activityId/{userId}&{activityId}")
    public ActivityEnroll getActivityEnrollByUserIdAndOrganizationId(@PathVariable("userId") Integer userId,
                                                                             @PathVariable("activityId") Integer activityId){
        System.out.println("根据UserId 和 活动Id 查询一条报名情况");
        try {
            return service.getActivityEnrollByUserIdAndActivityId(userId,activityId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 查询所有学生活动报名信息
     * @return
     */
    @RequestMapping(value = "ae/queryAll",method = RequestMethod.GET)
    public List<ActivityEnroll> getAllActivityEnrollList(){
        System.out.println("查询所有学生活动报名信息");
        try {
            return service.getAllActivityEnroll();
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 添加学生活动报名信息
     * @param activityEnroll
     * @return
     */
    @RequestMapping(value = "ae/add",method = RequestMethod.POST)
    public ResponseEntity<JsonResult> add(@RequestBody ActivityEnroll activityEnroll){
        JsonResult jsonResult=new JsonResult();
        System.out.println("添加学生活动报名信息");
        try {
            int addResult=service.add(activityEnroll);

            if (addResult <= 0){
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
     * 根据id删除学生活动报名信息
     * @param id
     * @return
     */
    @RequestMapping(value = "ae/delete/{id}",method = RequestMethod.DELETE)
    public ResponseEntity<JsonResult> delete(@PathVariable(value = "id") Integer id){
        JsonResult result = new JsonResult();
        System.out.println("根据id删除学生活动报名信息");
        try {
            int deleteResult = service.delete(id);
            if (deleteResult <= 0){
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
     * 修改学生活动报名信息
     * @param activityEnroll
     * @return
     */
    @RequestMapping(value = "ae/update",method = RequestMethod.PUT)
    public ResponseEntity<JsonResult> update(@RequestBody ActivityEnroll activityEnroll){
        JsonResult result = new JsonResult();
        System.out.println("修改学生活动报名信息");
        try {
            int updateResult = service.update(activityEnroll);
            if (updateResult <= 0){
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
