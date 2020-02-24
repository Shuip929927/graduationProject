package cn.yangcy.pzc.fragment.stundentunoin;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.yangcy.pzc.R;
import cn.yangcy.pzc.viewmodel.StudemtUnionViewModel;

public class StudentUnionListPageOrganization extends Fragment {

    private StudemtUnionViewModel mViewModel;

    public static StudentUnionListPageOrganization newInstance() {
        return new StudentUnionListPageOrganization();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.student_union_list_page_organization_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        mViewModel = ViewModelProviders.of(this).get(StudemtUnionViewModel.class);
        mViewModel = new ViewModelProvider(getActivity()).get(StudemtUnionViewModel.class);
        // TODO: Use the ViewModel
    }

}
