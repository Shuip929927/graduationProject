package cn.yangcy.pzc.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import cn.yangcy.pzc.model.activities.Activities;
import cn.yangcy.pzc.model.activities.ActivitiesRepository;
import cn.yangcy.pzc.model.organization.Organization;
import cn.yangcy.pzc.model.organization.OrganizationRepository;

public class StudemtUnionViewModel extends AndroidViewModel {

    private OrganizationRepository organizationRepository;
    private ActivitiesRepository activitiesRepository;
    private LiveData<List<Activities>> activitiesLiveList;
    private LiveData<List<Organization>> organizationLiveList;
    public StudemtUnionViewModel(@NonNull Application application) {
        super(application);
        organizationRepository = new OrganizationRepository(getApplication());
        activitiesRepository = new ActivitiesRepository(getApplication());
        activitiesLiveList = activitiesRepository.getAllActivities();
        organizationLiveList = organizationRepository.getAllOrganization();
    }
    // TODO: Implement the ViewModel


    public LiveData<List<Activities>> getAllActivitiesLiveList() {
        return activitiesLiveList;
    }
}
