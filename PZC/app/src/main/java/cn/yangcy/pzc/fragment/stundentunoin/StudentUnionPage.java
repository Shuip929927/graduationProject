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
import cn.yangcy.pzc.viewmodel.HomeViewModel;

public class StudentUnionPage extends Fragment {

    private static final String TAG = "StudentUnionPage";
    private HomeViewModel mViewModel;

    public static StudentUnionPage newInstance() {
        return new StudentUnionPage();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.student_union_page_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        mViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        mViewModel = new ViewModelProvider(getActivity()).get(HomeViewModel.class);
        // TODO: Use the ViewModel
    }

}
