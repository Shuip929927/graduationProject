package cn.yangcy.pzc_server.controller;

import cn.yangcy.pzc_server.bean.Information;
import cn.yangcy.pzc_server.bean.JsonResult;
import cn.yangcy.pzc_server.service.InformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/")
public class InformationController {

    @Autowired
    private InformationService informationService;

    /**
     * 根据ID查询信息
     * @param id
     * @return
     */
    //@PathVariable:用于获取url中的数据
    @GetMapping(value = "information/query/id/{id}")
    public Information getInformationById(@PathVariable(value = "id") Integer id){
        System.out.println("根据ID查询信息");
        try {
            Information information = informationService.getInformationById(id);
            return information;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 查询所有信息
     * @return
     */
    @RequestMapping(value = "information/queryAll",method = RequestMethod.GET)
    public List<Information> getAllInformationList(){
        System.out.println("查询所有信息");
        try {
            List<Information> informations=informationService.getInformationList();
            return informations;
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 添加信息
     * @param information
     * @return
     */
    @RequestMapping(value = "information/add",method = RequestMethod.POST)
    public ResponseEntity<JsonResult> add(@RequestBody Information information){
        JsonResult jsonResult=new JsonResult();
        System.out.println("添加信息");
        System.out.println(information.toString());
        try {
            int addResult=informationService.add(information);

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
     * 根据id删除信息
     * @param id
     * @return
     */
    @RequestMapping(value = "information/delete/{id}",method = RequestMethod.DELETE)
    public ResponseEntity<JsonResult> delete(@PathVariable(value = "id") Integer id){
        JsonResult result = new JsonResult();
        System.out.println("根据id删除信息");
        try {
            int deleteResult = informationService.delete(id);
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
     * 修改信息
     * @param information
     * @return
     */
    @RequestMapping(value = "information/update",method = RequestMethod.PUT)
    public ResponseEntity<JsonResult> update(@RequestBody Information information){
        JsonResult result = new JsonResult();
        System.out.println("修改信息");
        try {
            int updateResult = informationService.update(information);
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
