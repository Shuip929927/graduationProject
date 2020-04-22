package cn.yangcy.pzc_server.controller;

import cn.yangcy.pzc_server.bean.Department;
import cn.yangcy.pzc_server.bean.JsonResult;
import cn.yangcy.pzc_server.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    /**
     * 根据ID查询部门
     * @param id
     * @return
     */
    //@PathVariable:用于获取url中的数据
    @GetMapping(value = "department/query/id/{id}")
    public Department getDepartmentById(@PathVariable(value = "id") Integer id){
        System.out.println("根据ID查询部门");
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
    @RequestMapping(value = "department/queryAll",method = RequestMethod.GET)
    public List<Department> getAllInformationList(){
        System.out.println("查询所有部门");
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
    @RequestMapping(value = "department/add",method = RequestMethod.POST)
    public ResponseEntity<JsonResult> add(@RequestBody Department department){
        JsonResult jsonResult=new JsonResult();
        System.out.println("添加部门");
        try {
            int addResult=departmentService.add(department);

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
     * 根据id删除部门
     * @param id
     * @return
     */
    @RequestMapping(value = "department/delete/{id}",method = RequestMethod.DELETE)
    public ResponseEntity<JsonResult> delete(@PathVariable(value = "id") Integer id){
        JsonResult result = new JsonResult();
        System.out.println("根据id删除部门");
        try {
            int deleteResult = departmentService.delete(id);
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
     * 修改部门信息
     * @param department
     * @return
     */
    @RequestMapping(value = "department/update",method = RequestMethod.PUT)
    public ResponseEntity<JsonResult> update(@RequestBody Department department){
        JsonResult result = new JsonResult();
        System.out.println("修改部门信息");
        try {
            int updateResult = departmentService.update(department);
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
