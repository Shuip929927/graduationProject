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
     * 根据ID查询专业
     * @param id
     * @return
     */
    //@PathVariable:用于获取url中的数据
    @GetMapping(value = "major/query/id/{id}")
    public Major getMajorById(@PathVariable(value = "id") Integer id){
        System.out.println(" 根据ID查询专业");
        try {
            return majorService.getMajorById(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping(value = "major/query/departmentId/{department_id}")
    public List<Major> getDepartmentMajorList(@PathVariable(value = "department_id") Integer department_id){
        System.out.println(" 根据部门ID查询专业");
        try {
            return majorService.getDepartmentMajorList(department_id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询所有专业
     * @return
     */
    @RequestMapping(value = "major/queryAll",method = RequestMethod.GET)
    public List<Major> getAllMajorList(){
        System.out.println(" 查询所有专业");
        try {
            return majorService.getMajorList();
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 添加专业
     * @param major
     * @return
     */
    @RequestMapping(value = "major/add",method = RequestMethod.POST)
    public ResponseEntity<JsonResult> add(@RequestBody Major major){
        JsonResult jsonResult=new JsonResult();
        System.out.println(" 添加专业");
        try {
            int addResult=majorService.add(major);

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
     * 根据id删除专业
     * @param id
     * @return
     */
    @RequestMapping(value = "major/delete/{id}",method = RequestMethod.DELETE)
    public ResponseEntity<JsonResult> delete(@PathVariable(value = "id") Integer id){
        JsonResult result = new JsonResult();
        System.out.println(" 根据id删除专业");
        try {
            int deleteResult = majorService.delete(id);
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
     * 修改专业信息
     * @param major
     * @return
     */
    @RequestMapping(value = "major/update",method = RequestMethod.PUT)
    public ResponseEntity<JsonResult> update(@RequestBody Major major){
        JsonResult result = new JsonResult();
        System.out.println(" 修改专业信息");
        try {
            int updateResult = majorService.update(major);
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
