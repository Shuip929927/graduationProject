package cn.yangcy.pzc.viewmodel;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import cn.yangcy.pzc.R;
import cn.yangcy.pzc.model.activities.Activities;
import cn.yangcy.pzc.model.activities.ActivitiesRepository;
import cn.yangcy.pzc.model.department.DepartmentRepository;
import cn.yangcy.pzc.model.enroll.EnrollRepository;
import cn.yangcy.pzc.model.organization.Organization;
import cn.yangcy.pzc.model.organization.OrganizationRepository;
import cn.yangcy.pzc.model.user.User;
import cn.yangcy.pzc.model.user.UserRepository;

public class MyPageViewModel extends AndroidViewModel {

    private UserRepository userRepository;
    private DepartmentRepository departmentRepository;
    private OrganizationRepository organizationRepository;
    private ActivitiesRepository activitiesRepository;
    private EnrollRepository enrollRepository;

    private List<Integer> myEnrollOrganizationIdList;
    private LiveData<List<Organization>> myEnrollOrganizationLiveData;

    private List<Integer> myEnrollActivitiesIdList;
    private LiveData<List<Activities>> myEnrollActivitiesLiveData;

    private int myChargeOrganizationId;
    private LiveData<List<Activities>> myHoldActivitiesLiveData;

    public MyPageViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
        departmentRepository = new DepartmentRepository(application);
        organizationRepository = new OrganizationRepository(application);
        activitiesRepository = new ActivitiesRepository(application);
        enrollRepository = new EnrollRepository(application);
    }

    //PersonInfoPage
    public boolean updateUser(User user){
        if(departmentRepository.queryDepartmentByName(user.getDepartment()) == null){
            Toast.makeText(getApplication(), R.string.err_department,Toast.LENGTH_SHORT).show();
            return false;
        } else {
            userRepository.updateUser(user);
            return true;
        }
    }


    //MyEnrollOrganizationPage


    public List<Integer> getMyEnrollOrganizationIdList() {
        return myEnrollOrganizationIdList;
    }

    public void setMyEnrollOrganizationIdList(List<Integer> myEnrollOrganizationIdList) {
        this.myEnrollOrganizationIdList = myEnrollOrganizationIdList;
    }

    public LiveData<List<Organization>> getMyEnrollOrganizationLiveData() {
        return myEnrollOrganizationLiveData;
    }

    public void setMyEnrollOrganizationLiveData(LiveData<List<Organization>> myEnrollOrganizationLiveData) {
        this.myEnrollOrganizationLiveData = myEnrollOrganizationLiveData;
    }

    public LiveData<List<Organization>> queryMyEnrollOrganization(int userAccount){
        setMyEnrollOrganizationIdList(enrollRepository.getUserEnrollOrganizationList(userAccount));
        int isPersonOfCharge = organizationRepository.queryPersonInChargeOrgid(userAccount);
        if (isPersonOfCharge > 0 && isPersonOfCharge < 14){
            getMyEnrollOrganizationIdList().add(isPersonOfCharge);
        }
        setMyEnrollOrganizationLiveData(
                organizationRepository.getUserEnrollOrganizationLiveData(getMyEnrollOrganizationIdList()));
        return getMyEnrollOrganizationLiveData();
    }

    //MyEnrollActivitiesPage


    public List<Integer> getMyEnrollActivitiesIdList() {
        return myEnrollActivitiesIdList;
    }

    public void setMyEnrollActivitiesIdList(List<Integer> myEnrollActivitiesIdList) {
        this.myEnrollActivitiesIdList = myEnrollActivitiesIdList;
    }

    public LiveData<List<Activities>> getMyEnrollActivitiesLiveData() {
        return myEnrollActivitiesLiveData;
    }

    public void setMyEnrollActivitiesLiveData(LiveData<List<Activities>> myEnrollActivitiesLiveData) {
        this.myEnrollActivitiesLiveData = myEnrollActivitiesLiveData;
    }

    public LiveData<List<Activities>> queryMyEnrollActivities(int userAccount){
        setMyEnrollActivitiesIdList(enrollRepository.getUserEnrollActivitiesList(userAccount));
        setMyEnrollActivitiesLiveData(
                activitiesRepository.getUserEnrollActivitiesLiveData(getMyEnrollActivitiesIdList()));
        return getMyEnrollActivitiesLiveData();
    }

    //MyHoldActivitiesPage


    public int getMyChargeOrganizationId() {
        return myChargeOrganizationId;
    }

    public void setMyChargeOrganizationId(int myChargeOrganizationId) {
        this.myChargeOrganizationId = myChargeOrganizationId;
    }

    public LiveData<List<Activities>> getMyHoldActivitiesLiveData() {
        return myHoldActivitiesLiveData;
    }

    public void setMyHoldActivitiesLiveData(LiveData<List<Activities>> myHoldActivitiesLiveData) {
        this.myHoldActivitiesLiveData = myHoldActivitiesLiveData;
    }

    public LiveData<List<Activities>> queryMyHoldActivities(int userId){
        setMyChargeOrganizationId(organizationRepository.queryPersonInChargeOrgid(userId));
        setMyHoldActivitiesLiveData(
                activitiesRepository.getUserHoldActivitiesLiveData(getMyChargeOrganizationId()));
        return getMyHoldActivitiesLiveData();
    }

    //SettingsPage
    public void updateUserPassword(User user){
        userRepository.updateUser(user);
    }
}
