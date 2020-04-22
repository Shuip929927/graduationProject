package cn.yangcy.pzc_server.controller;

import cn.yangcy.pzc_server.bean.*;
import cn.yangcy.pzc_server.service.*;
import cn.yangcy.pzc_server.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController()
@RequestMapping(value = "bg/")
public class BackGroundController {

    @Autowired
    private UserService userService;

    @Autowired
    private InformationService informationService;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private OrganizationEnrollService organizationEnrollService;

    @Autowired
    private ActivityEnrollService activityEnrollService;

    @Autowired
    private BackGroundService backGroundService;

    /**
     * 查询条数
     *
     * @return backGroundJson
     */
    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public BackGroundJson queryCount() {
        BackGroundJson backGroundJson = new BackGroundJson();
        Map<String,Integer> map = new HashMap<>();
        System.out.println("添加用户");
        try {
            List<Information> infos = informationService.getInformationList();
            List<User> users = userService.getUserList();
            List<Organization> orgs = organizationService.getOrganizationList();
            List<Activity> acts = activityService.getActivitiesList();
            map.put("info", infos.size());
            map.put("user", users.size());
            map.put("org", orgs.size());
            map.put("act", acts.size());
            backGroundJson.code(0);
            backGroundJson.setMsg("查询成功");
            backGroundJson.setData(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return backGroundJson;
    }

    /**
     * 查询学生列表
     *
     * @return backGroundJson
     * @RequestParam(value = "page") Integer page,@RequestParam(value = "limit") Integer limit
     */
    @RequestMapping(value = "user/queryAll", method = RequestMethod.GET)
    public BackGroundJson getAllUserList(Integer page, Integer limit, User user) {
        BackGroundJson backGroundJson = new BackGroundJson();
        System.out.println("查询所有用户信息");
        try {
            String account = "%";
            String gender = "%";
            String departmentId = "%";
            int start = (page - 1) * limit;
            int end = page * limit;
            List<User> res = new ArrayList<>();
            System.out.println(user.toString());
//----------条件查询--------
            if (user.getAccount() != null) {
                account = String.valueOf(user.getAccount());
            }
            if(user.getGender() != null) {
                gender = String.valueOf(user.getGender());
            }
            if(user.getDepartmentId() != null) {
                departmentId = String.valueOf(user.getDepartmentId());
            }
            List<User> users = backGroundService.getAllUserBG(account,gender,departmentId);
//----------正常查询所有--------
            if (start < users.size() && end < users.size()) {
                for (int i = start; i < end; i++) {
                    res.add(users.get(i));
                }
                backGroundJson.code(0);
                backGroundJson.setMsg("查询成功");
                backGroundJson.setCount(users.size());
                backGroundJson.setData(res);
                System.out.println(backGroundJson);
            } else if (start < users.size() && end > users.size()) {
                for (int i = start; i < users.size(); i++) {
                    res.add(users.get(i));
                }
                backGroundJson.code(0);
                backGroundJson.setMsg("查询成功");
                backGroundJson.setCount(users.size());
                backGroundJson.setData(res);
                System.out.println(backGroundJson);
            }
            if(res.size() == 0){
                backGroundJson.code(1);
                backGroundJson.setMsg("查询用户不存在");
                backGroundJson.setCount(res.size());
            }
            return backGroundJson;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 添加用户
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "user/add", method = RequestMethod.POST)
    public BackGroundJson addUser(@RequestBody User user) {
        BackGroundJson backGroundJson = new BackGroundJson();
        user.setPassword(MD5Util.digest(user.getPassword()));
        user.setPhoneNumber(MD5Util.digest(user.getPhoneNumber()));
        System.out.println("添加用户");
        System.out.println(user.toString());
        try {
            int addResult = userService.add(user);

            if (addResult < 0) {
                backGroundJson.code(1);
                backGroundJson.setMsg("添加失败");

            } else {
                backGroundJson.code(0);
                backGroundJson.setMsg("添加成功");
            }

        } catch (Exception e) {
            backGroundJson.code(1);
            backGroundJson.setMsg("添加失败");
            e.printStackTrace();
        }

        return backGroundJson;
    }

    /**
     * 修改用户信息
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "user/update", method = RequestMethod.PUT)
    public BackGroundJson updateUser(@RequestBody Map<String,String> map) {
        BackGroundJson backGroundJson = new BackGroundJson();
        System.out.println("修改用户");
        Integer account = Integer.valueOf(map.get("account"));
        String password = map.get("password");
        Integer gender = Integer.valueOf(map.get("gender"));
        String name = map.get("name");
        String phoneNumber = map.get("phoneNumber");
        Integer departmentId = Integer.valueOf(map.get("departmentId"));
        Integer majorId = Integer.valueOf(map.get("majorId"));
        String classes = map.get("classes");
        Integer power = Integer.valueOf(map.get("power"));
        String passwordInput = map.get("passwordInput");
        String phoneNumberInput = map.get("phoneNumberInput");

        User user = new User(account,password,gender,name,
                phoneNumber,departmentId,majorId,classes,power);

        System.out.println("user = " + user.toString());
        System.out.println("passwordInput = " + passwordInput);
        System.out.println("phoneInput = " + phoneNumberInput);
        try {
            if (passwordInput != null) {
                user.setPassword(MD5Util.digest(passwordInput));
            }
            if (phoneNumberInput != null) {
                user.setPhoneNumber(MD5Util.digest(phoneNumberInput));
            }
            int updateResult = userService.update(user);
            if (updateResult < 0) {
                backGroundJson.code(1);
                backGroundJson.setMsg("修改失败");
            } else {
                backGroundJson.code(0);
                backGroundJson.setMsg("修改成功");
            }
        } catch (Exception e) {
            backGroundJson.code(1);
            backGroundJson.setMsg("修改失败");
            e.printStackTrace();
        }

        return backGroundJson;
    }

    /**
     * 查询所有通知信息
     *
     * @return backGroundJson
     */
    @RequestMapping(value = "information/queryAll", method = RequestMethod.GET)
    public BackGroundJson getAllInformationList(Integer page, Integer limit,String id, String type, String isDelete) {
        BackGroundJson backGroundJson = new BackGroundJson();
        System.out.println("查询所有信息");
        String infoId = "%";
        String infoType = "%";
        String infoIsDelete = "%";
        try {
            if(id != null && !"".equals(id)){
                infoId = id;
            }
            if(type != null && !"".equals(type)){
                infoType = type;
            }
            if(isDelete != null && !"".equals(isDelete)){
                infoIsDelete = isDelete;
            }
            List<Information> informations = backGroundService.getAllInformationListBG(infoId,infoType,infoIsDelete);
            int start = (page - 1) * limit;
            int end = page * limit;
            List<Information> res = new ArrayList<>();
            if (start < informations.size() && end < informations.size()) {
                for (int i = start; i < end; i++) {
                    res.add(informations.get(i));
                }
                backGroundJson.code(0);
                backGroundJson.setMsg("查询成功");
                backGroundJson.setCount(informations.size());
                backGroundJson.setData(res);
                System.out.println(backGroundJson);
            } else if (start < informations.size() && end > informations.size()) {
                for (int i = start; i < informations.size(); i++) {
                    res.add(informations.get(i));
                }
                backGroundJson.code(0);
                backGroundJson.setMsg("查询成功");
                backGroundJson.setCount(informations.size());
                backGroundJson.setData(res);
                System.out.println(backGroundJson);
            }
            if(res.size() == 0){
                backGroundJson.code(1);
                backGroundJson.setMsg("查询结果不存在");
                backGroundJson.setCount(res.size());
            }
            return backGroundJson;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 查询学生组织列表
     *
     * @return backGroundJson
     */
    @RequestMapping(value = "organization/queryAll", method = RequestMethod.GET)
    public BackGroundJson getAllOrganizationList(Integer page, Integer limit) {
        BackGroundJson backGroundJson = new BackGroundJson();
        System.out.println("查询学生组织列表");
        try {
            List<Organization> organizationList = organizationService.getOrganizationList();
            int start = (page - 1) * limit;
            int end = page * limit;
            List<Organization> res = new ArrayList<>();
            if (start < organizationList.size() && end < organizationList.size()) {
                for (int i = start; i < end; i++) {
                    res.add(organizationList.get(i));
                }
                backGroundJson.code(0);
                backGroundJson.setMsg("查询成功");
                backGroundJson.setCount(organizationList.size());
                backGroundJson.setData(res);
                System.out.println(backGroundJson);
            } else if (start < organizationList.size() && end > organizationList.size()) {
                for (int i = start; i < organizationList.size(); i++) {
                    res.add(organizationList.get(i));
                }
                backGroundJson.code(0);
                backGroundJson.setMsg("查询成功");
                backGroundJson.setCount(organizationList.size());
                backGroundJson.setData(res);
                System.out.println(backGroundJson);
            }
            if(res.size() == 0){
                backGroundJson.code(1);
                backGroundJson.setMsg("查询结果不存在");
                backGroundJson.setCount(res.size());
            }
            return backGroundJson;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 添加学生组织
     *
     * @param organization
     * @return
     */
    @RequestMapping(value = "organization/add", method = RequestMethod.POST)
    public BackGroundJson addOrganization(@RequestBody Organization organization) {
        BackGroundJson backGroundJson = new BackGroundJson();
        System.out.println("添加学生组织");
        System.out.println(organization.toString());
        try {
            User u = userService.getUserByAccount(Integer.valueOf(organization.getPerson_id()));
            if(u == null){
                backGroundJson.code(1);
                backGroundJson.setMsg("添加失败,负责人不存在");
                return backGroundJson;
            }

            int addResult = organizationService.add(organization);

            if (addResult < 0) {
                backGroundJson.code(1);
                backGroundJson.setMsg("添加失败");

            } else {
                backGroundJson.code(0);
                backGroundJson.setMsg("添加成功");
            }

        } catch (Exception e) {
            backGroundJson.code(1);
            backGroundJson.setMsg("添加失败");
            e.printStackTrace();
        }

        return backGroundJson;
    }

    /**
     * 修改学生组织信息
     *
     * @param organization
     * @return
     */
    @RequestMapping(value = "organization/update", method = RequestMethod.PUT)
    public BackGroundJson updateOrganization (@RequestBody Organization organization) {
        BackGroundJson backGroundJson = new BackGroundJson();
        System.out.println("修改学生组织");
        try {
            int updateResult = organizationService.update(organization);
            if (updateResult < 0) {
                backGroundJson.code(1);
                backGroundJson.setMsg("修改失败");
            } else {
                backGroundJson.code(0);
                backGroundJson.setMsg("修改成功");
            }
        } catch (Exception e) {
            backGroundJson.code(1);
            backGroundJson.setMsg("修改失败");
            e.printStackTrace();
        }

        return backGroundJson;
    }

    /**
     * 查询学生组织活动列表
     *
     * @return backGroundJson
     */
    @RequestMapping(value = "activity/queryAll", method = RequestMethod.GET)
    public BackGroundJson getAllStudentActivitiesList(Integer page, Integer limit,String organization_id, String activity_state) {
        BackGroundJson backGroundJson = new BackGroundJson();
        System.out.println("请求活动信息列表");
        try {

            String act_state = "%";
            String org_id = "%";
            if(activity_state != null && !"".equals(activity_state)){
                act_state = activity_state;
            }
            if(organization_id != null && !"".equals(organization_id)){
                org_id = organization_id;
            }
            List<Activity> activities = backGroundService.getAllActivityBG(org_id, act_state);
            int start = (page - 1) * limit;
            int end = page * limit;
            List<Activity> res = new ArrayList<>();
            if (start < activities.size() && end < activities.size()) {
                for (int i = start; i < end; i++) {
                    res.add(activities.get(i));
                }
                backGroundJson.code(0);
                backGroundJson.setMsg("查询成功");
                backGroundJson.setCount(activities.size());
                backGroundJson.setData(res);
                System.out.println(backGroundJson);
            } else if (start < activities.size() && end > activities.size()) {
                for (int i = start; i < activities.size(); i++) {
                    res.add(activities.get(i));
                }
                backGroundJson.code(0);
                backGroundJson.setMsg("查询成功");
                backGroundJson.setCount(activities.size());
                backGroundJson.setData(res);
                System.out.println(backGroundJson);
            }
            if(res.size() == 0){
                backGroundJson.code(1);
                backGroundJson.setMsg("查询结果不存在");
                backGroundJson.setCount(res.size());
            }
            return backGroundJson;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询学生组织活动列表
     *
     * @return backGroundJson
     */
    @RequestMapping(value = "activity/queryByOrgId", method = RequestMethod.GET)
    public BackGroundJson getActivitiesListByOrgId(String organization_id) {
        BackGroundJson backGroundJson = new BackGroundJson();
        System.out.println("请求活动信息列表");
        try {
            String act_state = "%";
            String org_id = "%";
            if(organization_id != null && !"".equals(organization_id)){
                org_id = organization_id;
            }
            List<Activity> activities = backGroundService.getAllActivityBG(org_id, act_state);
            backGroundJson.code(0);
            backGroundJson.setMsg("查询成功");
            backGroundJson.setCount(activities.size());
            backGroundJson.setData(activities);
            System.out.println(backGroundJson);
            if(activities.size() == 0){
                backGroundJson.code(1);
                backGroundJson.setMsg("查询结果不存在");
                backGroundJson.setCount(activities.size());
            }
            return backGroundJson;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询学生组织成员列表
     *
     * @return backGroundJson
     */
    @RequestMapping(value = "user/orgMember", method = RequestMethod.GET)
    public BackGroundJson getOrgMemberList(Integer page, Integer limit, String organization_enroll_state, String account, String organization_id) {
        BackGroundJson backGroundJson = new BackGroundJson();
        System.out.println("查询所有组织用户信息");

        try {
            String oe_state = "%";
            String ac = "%";
            String org_id = "%";
            if(organization_enroll_state != null && !"".equals(organization_enroll_state)){
                oe_state = organization_enroll_state;
            }
            if(account != null && !"".equals(account)){
                ac = account;
            }
            if(organization_id != null && !"".equals(organization_id)){
                org_id = organization_id;
            }
            List<BackGroundOrgMember> members = backGroundService.getOrgMemberListBG(oe_state,ac,org_id);
            int start = (page - 1) * limit;
            int end = page * limit;
            List<BackGroundOrgMember> res = new ArrayList<>();
            if (start < members.size() && end < members.size()) {
                for (int i = start; i < end; i++) {
                    res.add(members.get(i));
                }
                backGroundJson.code(0);
                backGroundJson.setMsg("查询成功");
                backGroundJson.setCount(members.size());
                backGroundJson.setData(res);
                System.out.println(backGroundJson);
            } else if (start < members.size() && end > members.size()) {
                for (int i = start; i < members.size(); i++) {
                    res.add(members.get(i));
                }
                backGroundJson.code(0);
                backGroundJson.setMsg("查询成功");
                backGroundJson.setCount(members.size());
                backGroundJson.setData(res);
                System.out.println(backGroundJson);
            }
            if(res.size() == 0){
                backGroundJson.code(1);
                backGroundJson.setMsg("查询结果不存在");
                backGroundJson.setCount(res.size());
            }
            return backGroundJson;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 添加学生组织成员列表
     *
     * @param organizationEnroll;
     * @return backGroundJson
     */
    @RequestMapping(value = "oe/add", method = RequestMethod.POST)
    public BackGroundJson addOE(@RequestBody OrganizationEnroll organizationEnroll) {
        BackGroundJson backGroundJson = new BackGroundJson();
        System.out.println("添加学生组织报名信息");
        System.out.println(organizationEnroll.toString());
        int addResult = -1;
        try {
            User u = userService.getUserByAccount(organizationEnroll.getUser_id());
            if(u == null){
                backGroundJson.code(1);
                backGroundJson.setMsg("添加失败,用户不存在");
                return backGroundJson;
            }
            OrganizationEnroll oe = organizationEnrollService.getOrganizationEnrollByUserIdAndOrganizationId
                    (organizationEnroll.getUser_id(),organizationEnroll.getOrganization_id());
            if(oe!=null && oe.getOrganization_enroll_state()==0){
                oe.setOrganization_enroll_state(organizationEnroll.getOrganization_enroll_state());
                addResult = organizationEnrollService.update(oe);
            } else {
                addResult = backGroundService.addOeBG(organizationEnroll);
            }

            if (addResult < 0) {
                backGroundJson.code(1);
                backGroundJson.setMsg("添加失败");

            } else {
                Organization organization = organizationService.getOrganizationById(organizationEnroll.getOrganization_id());
                organization.setNumber_of_people(organization.getNumber_of_people()+1);
                organizationService.update(organization);
                User user = userService.getUserByAccount(organizationEnroll.getUser_id());
                if(user.getPower()==1){
                    user.setPower(2);
                    userService.update(user);
                }
                backGroundJson.code(0);
                backGroundJson.setMsg("添加成功");
            }

        } catch (Exception e) {
            backGroundJson.code(1);
            backGroundJson.setMsg("添加失败");
            e.printStackTrace();
        }

        return backGroundJson;
    }

    /**
     * 修改学生组织信息
     *
     * @param
     * @return
     */
    @RequestMapping(value = "oe/update", method = RequestMethod.PUT)
//    public BackGroundJson updateOE (Integer id, Integer user_id, Integer organization_id, Integer organization_enroll_state, Integer newOE) {
    public BackGroundJson updateOE (@RequestBody Map<String,Integer> map) {
        BackGroundJson backGroundJson = new BackGroundJson();
        Integer id = map.get("id");
        Integer user_id = map.get("user_id");
        Integer organization_id = map.get("organization_id");
        Integer organization_enroll_state = map.get("organization_enroll_state");
        Integer newOE = map.get("newOE");
        OrganizationEnroll organizationEnroll = new OrganizationEnroll(id,user_id,organization_id,organization_enroll_state);
        System.out.println("修改学生组织");
        System.out.println(map.toString());
        System.out.println(organizationEnroll.toString());
        System.out.println(newOE);
        try {
            if(!organizationEnroll.getOrganization_enroll_state().equals(newOE) && newOE != null){
                if(organizationEnroll.getOrganization_enroll_state() == 2){
                    if(newOE == 1){
                        System.out.println("2变1");
                        organizationEnroll.setOrganization_enroll_state(newOE);

                    } else if(newOE == 0){
                        System.out.println("2变0");
                        organizationEnroll.setOrganization_enroll_state(newOE);
                    }
                    Organization organization = organizationService.getOrganizationById(organizationEnroll.getOrganization_id());
                    organization.setNumber_of_people(organization.getNumber_of_people()-1);
                    organizationService.update(organization);
                    List<OrganizationEnroll> oeList = organizationEnrollService.getOrganizationEnrollByUserId(organizationEnroll.getOrganization_id());
                    User user = userService.getUserByAccount(organizationEnroll.getUser_id());
                    if(oeList.size() == 0 && user.getPower() == 2){
                        user.setPower(1);
                        userService.update(user);
                    }
                }
                if(organizationEnroll.getOrganization_enroll_state() == 1){
                    if(newOE == 2 ){
                        System.out.println("1变2");
                        organizationEnroll.setOrganization_enroll_state(newOE);
                        Organization organization = organizationService.getOrganizationById(organizationEnroll.getOrganization_id());
                        organization.setNumber_of_people(organization.getNumber_of_people()+1);
                        organizationService.update(organization);
                        User user = userService.getUserByAccount(organizationEnroll.getUser_id());
                        if(user.getPower()==1){
                            user.setPower(2);
                            userService.update(user);
                        }
                    } else if(newOE == 0){
                        System.out.println("1变0");
                        organizationEnroll.setOrganization_enroll_state(newOE);
                    }
                }
            }
            int updateResult = organizationEnrollService.update(organizationEnroll);
            if (updateResult < 0) {
                backGroundJson.code(1);
                backGroundJson.setMsg("修改失败");
            } else {
                backGroundJson.code(0);
                backGroundJson.setMsg("修改成功");
            }
        } catch (Exception e) {
            backGroundJson.code(1);
            backGroundJson.setMsg("修改失败");
            e.printStackTrace();
        }

        return backGroundJson;
    }
    /**
     * 查询学生活动成员列表
     *
     * @return backGroundJson
     */
    @RequestMapping(value = "user/actMember", method = RequestMethod.GET)
    public BackGroundJson getActMemberList(Integer page, Integer limit, String activity_enroll_state, String user_id, String activity_id, String gender) {
        BackGroundJson backGroundJson = new BackGroundJson();
        System.out.println("查询所有用户信息");
        try {
            String ae_state = "%";
            String ac = "%";
            String act_id = "%";
            if(activity_enroll_state != null && !"".equals(activity_enroll_state)){
                ae_state = activity_enroll_state;
            }
            if(user_id != null && !"".equals(user_id)){
                ac = user_id;
            }
            if(activity_id != null && !"".equals(activity_id)){
                act_id = activity_id;
            }
            List<BackGroundActMember> members = backGroundService.getActMemberListBG(ae_state,ac,act_id);
            int start = (page - 1) * limit;
            int end = page * limit;
            List<BackGroundActMember> res = new ArrayList<>();
            if (start < members.size() && end < members.size()) {
                for (int i = start; i < end; i++) {
                    res.add(members.get(i));
                }
                backGroundJson.code(0);
                backGroundJson.setMsg("查询成功");
                backGroundJson.setCount(members.size());
                backGroundJson.setData(res);
                System.out.println(backGroundJson);
            } else if (start < members.size() && end > members.size()) {
                for (int i = start; i < members.size(); i++) {
                    res.add(members.get(i));
                }
                backGroundJson.code(0);
                backGroundJson.setMsg("查询成功");
                backGroundJson.setCount(members.size());
                backGroundJson.setData(res);
                System.out.println(backGroundJson);
            }
            if(res.size() == 0){
                backGroundJson.code(1);
                backGroundJson.setMsg("查询结果不存在");
                backGroundJson.setCount(res.size());
            }
            return backGroundJson;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 添加学生活动报名信息
     * @param activityEnroll;
     * @return backGroundJson
     */
    @RequestMapping(value = "ae/add",method = RequestMethod.POST)
    public BackGroundJson addAE(@RequestBody ActivityEnroll activityEnroll){
        BackGroundJson backGroundJson = new BackGroundJson();
        System.out.println("添加学生活动报名信息");
        System.out.println(activityEnroll.toString());
        try {
            User u = userService.getUserByAccount(activityEnroll.getUser_id());
            if(u == null){
                backGroundJson.setCode(1);
                backGroundJson.setMsg("添加失败，用户不存在");
                return backGroundJson;
            }
            ActivityEnroll ae = activityEnrollService.getActivityEnrollByUserIdAndActivityId(activityEnroll.getUser_id(),activityEnroll.getActivity_id());
            if(ae !=null && ae.getActivity_enroll_state()!= 0){
                backGroundJson.setCode(1);
                backGroundJson.setMsg("添加失败");
                return backGroundJson;
            }
            int addResult=backGroundService.addAeBG(activityEnroll);
            if (addResult < 0){
                backGroundJson.setCode(1);
                backGroundJson.setMsg("添加失败");
            } else {
                backGroundJson.setCode(0);
                backGroundJson.setMsg("添加成功");
            }

        }catch (Exception e){
            backGroundJson.setCode(1);
            backGroundJson.setMsg("添加失败");
            e.printStackTrace();
        }

        return backGroundJson;

    }


    /**
     * 查询所有部门
     *
     * @return
     */
    @RequestMapping(value = "department/queryAll", method = RequestMethod.GET)
    public BackGroundJson getAllDepartmentist(Integer page, Integer limit) {
        BackGroundJson backGroundJson = new BackGroundJson();
        System.out.println("查询所有部门");
        try {
            List<Department> departments = departmentService.getDepartmentList();
            int start = (page - 1) * limit;
            int end = page * limit;
            List<Department> res = new ArrayList<>();
            if (start < departments.size() && end < departments.size()) {
                for (int i = start; i < end; i++) {
                    res.add(departments.get(i));
                }
                backGroundJson.code(0);
                backGroundJson.setMsg("查询成功");
                backGroundJson.setCount(departments.size());
                backGroundJson.setData(res);
                System.out.println(backGroundJson);
            } else if (start < departments.size() && end > departments.size()) {
                for (int i = start; i < departments.size(); i++) {
                    res.add(departments.get(i));
                }
                backGroundJson.code(0);
                backGroundJson.setMsg("查询成功");
                backGroundJson.setCount(departments.size());
                backGroundJson.setData(res);
                System.out.println(backGroundJson);
            }
            if(res.size() == 0){
                backGroundJson.code(1);
                backGroundJson.setMsg("查询结果不存在");
                backGroundJson.setCount(res.size());
            }
            return backGroundJson;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询所有专业
     *
     * @return
     */
    @RequestMapping(value = "major/queryAll", method = RequestMethod.GET)
    public BackGroundJson getMajorListBG(Integer page, Integer limit, String department_id) {
        BackGroundJson backGroundJson = new BackGroundJson();
        System.out.println("查询所有部门");
        try {
            String depId = "%";
            if(department_id != null && !"".equals(department_id)){
                depId = department_id;
            }
            List<Major> majors = backGroundService.getMajorListBG(depId);
            int start = (page - 1) * limit;
            int end = page * limit;
            List<Major> res = new ArrayList<>();
            if (start < majors.size() && end < majors.size()) {
                for (int i = start; i < end; i++) {
                    res.add(majors.get(i));
                }
                backGroundJson.code(0);
                backGroundJson.setMsg("查询成功");
                backGroundJson.setCount(majors.size());
                backGroundJson.setData(res);
                System.out.println(backGroundJson);
            } else if (start < majors.size() && end > majors.size()) {
                for (int i = start; i < majors.size(); i++) {
                    res.add(majors.get(i));
                }
                backGroundJson.code(0);
                backGroundJson.setMsg("查询成功");
                backGroundJson.setCount(majors.size());
                backGroundJson.setData(res);
                System.out.println(backGroundJson);
            }
            if(res.size() == 0){
                backGroundJson.code(1);
                backGroundJson.setMsg("查询结果不存在");
                backGroundJson.setCount(res.size());
            }
        } catch (Exception e) {
            backGroundJson.code(1);
            backGroundJson.setMsg("查询失败");
            e.printStackTrace();
        }
        return backGroundJson;
    }
}
