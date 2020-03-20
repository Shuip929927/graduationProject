package cn.yangcy.pzc_server.controller;

import cn.yangcy.pzc_server.bean.JsonResult;
import cn.yangcy.pzc_server.bean.Major;
import cn.yangcy.pzc_server.service.MajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/")
public class MajorController {

    @Autowired
    private MajorService majorService;

    /**
     * 根据ID查询zhuanye
     * @param id
     * @return
     */
    //@PathVariable:用于获取url中的数据
    @GetMapping(value = "major/query/id/{id}")
    public Major getMajorById(@PathVariable(value = "id") Integer id){
        try {
            return majorService.getMajorById(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping(value = "major/query/departmentId/{department_id}")
    public List<Major> getDepartmentMajorList(@PathVariable(value = "department_id") Integer department_id){
        try {
            return majorService.getDepartmentMajorList(department_id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询学生组织列表
     * @return
     */
    @RequestMapping(value = "major/queryAll",method = RequestMethod.GET)
    public List<Major> getAllMajorList(){

        try {
            return majorService.getMajorList();
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 添加学生组织
     * @param major
     * @return
     */
    @RequestMapping(value = "major/add",method = RequestMethod.POST)
    public ResponseEntity<JsonResult> add(@RequestBody Major major){
        JsonResult jsonResult=new JsonResult();

        try {
            int addResult=majorService.add(major);

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
    @RequestMapping(value = "major/delete/{id}",method = RequestMethod.DELETE)
    public ResponseEntity<JsonResult> delete(@PathVariable(value = "id") Integer id){
        JsonResult result = new JsonResult();

        try {
            int deleteResult = majorService.delete(id);
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
     * @param major
     * @return
     */
    @RequestMapping(value = "major/update",method = RequestMethod.PUT)
    public ResponseEntity<JsonResult> update(@RequestBody Major major){
        JsonResult result = new JsonResult();

        try {
            int updateResult = majorService.update(major);
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
