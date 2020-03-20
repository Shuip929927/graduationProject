package cn.yangcy.pzc_server.controller;

import cn.yangcy.pzc_server.bean.JsonResult;
import cn.yangcy.pzc_server.bean.Organization;
import cn.yangcy.pzc_server.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;
    /**
     * 根据ID查询学生组织
     * @param id
     * @return
     */
    //@PathVariable:用于获取url中的数据
    @GetMapping(value = "organization/query/id/{id}")
    public Organization getOrganizationById(@PathVariable(value = "id") Integer id){
        try {
            return organizationService.getOrganizationById(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 查询学生组织列表
     * @return
     */
    @RequestMapping(value = "organization/queryAll",method = RequestMethod.GET)
    public List<Organization> getAllOrganizationList(){

        try {
            return organizationService.getOrganizationList();
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 添加学生组织
     * @param organization
     * @return
     */
    @RequestMapping(value = "organization/add",method = RequestMethod.POST)
    public ResponseEntity<JsonResult> add(@RequestBody Organization organization){
        JsonResult jsonResult=new JsonResult();

        try {
            int addResult=organizationService.add(organization);

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
     * 根据id删除学生组织
     * @param id
     * @return
     */
    @RequestMapping(value = "organization/delete/{id}",method = RequestMethod.DELETE)
    public ResponseEntity<JsonResult> delete(@PathVariable(value = "id") Integer id){
        JsonResult result = new JsonResult();

        try {
            int deleteResult = organizationService.delete(id);
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
     * 修改学生组织信息
     * @param organization
     * @return
     */
    @RequestMapping(value = "organization/update",method = RequestMethod.PUT)
    public ResponseEntity<JsonResult> update(@RequestBody Organization organization){
        JsonResult result = new JsonResult();

        try {
            int updateResult = organizationService.update(organization);
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
