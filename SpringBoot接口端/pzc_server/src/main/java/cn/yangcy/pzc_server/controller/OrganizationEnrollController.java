package cn.yangcy.pzc_server.controller;

import cn.yangcy.pzc_server.bean.JsonResult;
import cn.yangcy.pzc_server.bean.OrganizationEnroll;
import cn.yangcy.pzc_server.service.OrganizationEnrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/")
public class OrganizationEnrollController {

    @Autowired
    private OrganizationEnrollService service;

    /**
     * 根据ID查询某个学生组织的一条报名情况
     * @param id
     * @return
     */
    //@PathVariable:用于获取url中的数据
    @GetMapping(value = "oe/query/id/{id}")
    public OrganizationEnroll getOrganizationEnrollById(@PathVariable(value = "id") Integer id){
        System.out.println("根据ID查询某个学生组织的一条报名情况");
        try {
            return service.getOrganizationEnrollById(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 根据UserID查询某个用户 学生组织的报名情况
     * @param userId
     * @return
     */
    //@PathVariable:用于获取url中的数据
    @GetMapping(value = "oe/query/userId/{userId}")
    public List<OrganizationEnroll> getOrganizationEnrollByUserId(@PathVariable(value = "userId") Integer userId) {
        System.out.println("根据UserID查询某个用户 学生组织的报名情况");
        try {
            return service.getOrganizationEnrollByUserId(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据OrganizationId查询学生组织的所有报名情况
     * @param organizationId
     * @return
     */
    //@PathVariable:用于获取url中的数据
    @GetMapping(value = "oe/query/organizationId/{organizationId}")
    public List<OrganizationEnroll> getOrganizationEnrollByOrganizationId(@PathVariable(value = "organizationId") Integer organizationId) {
        System.out.println("根据OrganizationId查询学生组织的所有报名情况");
        try {
            return service.getOrganizationEnrollByOrganizationId(organizationId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 根据UserId 和学生组织Id查询一条报名情况
     * @param userId organizationId
     * @return
     */
    //@PathVariable:用于获取url中的数据
    @GetMapping(value = "oe/query/userId&organizationId/{userId}&{organizationId}")
    public OrganizationEnroll getOrganizationEnrollByUserIdAndOrganizationId(@PathVariable("userId") Integer userId,
                                                                             @PathVariable("organizationId") Integer organizationId){
        System.out.println("根据UserId 和学生组织Id查询一条报名情况");
        try {
            return service.getOrganizationEnrollByUserIdAndOrganizationId(userId,organizationId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 查询所有学生组织报名信息
     * @return
     */
    @RequestMapping(value = "oe/queryAll",method = RequestMethod.GET)
    public List<OrganizationEnroll> getAllOrganizationEnrollList(){
        System.out.println("查询所有学生组织报名信息 ");
        try {
            return service.getAllOrganizationEnroll();
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 添加学生组织报名信息
     * @param organizationEnroll
     * @return
     */
    @RequestMapping(value = "oe/add",method = RequestMethod.POST)
    public ResponseEntity<JsonResult> add(@RequestBody OrganizationEnroll organizationEnroll){
        JsonResult jsonResult=new JsonResult();
        System.out.println("添加学生组织报名信息 ");
        try {
            int addResult=service.add(organizationEnroll);

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
     * 根据id删除学生组织报名信息
     * @param id
     * @return
     */
    @RequestMapping(value = "oe/delete/{id}",method = RequestMethod.DELETE)
    public ResponseEntity<JsonResult> delete(@PathVariable(value = "id") Integer id){
        JsonResult result = new JsonResult();
        System.out.println("根据id删除学生组织报名信息 ");
        try {
            int deleteResult = service.delete(id);
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
     * 修改学生组织报名信息
     * @param organizationEnroll
     * @return
     */
    @RequestMapping(value = "oe/update",method = RequestMethod.PUT)
    public ResponseEntity<JsonResult> update(@RequestBody OrganizationEnroll organizationEnroll){
        JsonResult result = new JsonResult();
        System.out.println("修改学生组织报名信息 ");
        try {
            int updateResult = service.update(organizationEnroll);
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
