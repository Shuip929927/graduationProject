package cn.yangcy.pzc.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import cn.yangcy.pzc.model.activities.Activities;
import cn.yangcy.pzc.model.activities.ActivitiesRepository;
import cn.yangcy.pzc.model.department.DepartmentRepository;
import cn.yangcy.pzc.model.enroll.ActivitiesEnrollRepository;
import cn.yangcy.pzc.model.major.MajorRepository;
import cn.yangcy.pzc.model.organization.Organization;
import cn.yangcy.pzc.model.organization.OrganizationRepository;
import cn.yangcy.pzc.model.user.UserRepository;

public class MyPageViewModel extends AndroidViewModel {

    private UserRepository userRepository;
    private DepartmentRepository departmentRepository;
    private MajorRepository majorRepository;
    private OrganizationRepository organizationRepository;
    private ActivitiesRepository activitiesRepository;
    private ActivitiesEnrollRepository enrollRepository;

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
        majorRepository = new MajorRepository(application);
        organizationRepository = new OrganizationRepository(application);
        activitiesRepository = new ActivitiesRepository(application);
        enrollRepository = new ActivitiesEnrollRepository(application);
    }

}
