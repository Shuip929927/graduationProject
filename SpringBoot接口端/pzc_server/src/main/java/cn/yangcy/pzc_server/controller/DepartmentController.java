package cn.yangcy.pzc_server.controller;

import cn.yangcy.pzc_server.bean.Department;
import cn.yangcy.pzc_server.bean.JsonResult;
import cn.yangcy.pzc_server.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    /**
     * 根据ID查询部门
     * @param id
     * @return
     */
    //@PathVariable:用于获取url中的数据
    @GetMapping(value = "dep/query/{id}")
    public Department getDepartmentById(@PathVariable(value = "id") Integer id){
        try {
            Department department = departmentService.getDepartmentById(id);
            return department;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 查询所有部门
     * @return
     */
    @RequestMapping(value = "dep/query",method = RequestMethod.GET)
    public List<Department> getAllInformationList(){

        try {
            List<Department> departments=departmentService.getDepartmentList();
            return departments;
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 添加部门
     * @param department
     * @return
     */
    @RequestMapping(value = "dep/add",method = RequestMethod.POST)
    public ResponseEntity<JsonResult> add(@RequestBody Department department){
        JsonResult jsonResult=new JsonResult();

        try {
            int addResult=departmentService.add(department);

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
     * 根据id删除部门
     * @param id
     * @return
     */
    @RequestMapping(value = "dep/delete/{id}",method = RequestMethod.DELETE)
    public ResponseEntity<JsonResult> delete(@PathVariable(value = "id") Integer id){
        JsonResult result = new JsonResult();

        try {
            int deleteResult = departmentService.delete(id);
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
     * 根据id修改部门信息
     * @param department
     * @return
     */
    @RequestMapping(value = "dep/update/",method = RequestMethod.PUT)
    public ResponseEntity<JsonResult> update(@RequestBody Department department){
        JsonResult result = new JsonResult();

        try {
            int updateResult = departmentService.update(department);
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
