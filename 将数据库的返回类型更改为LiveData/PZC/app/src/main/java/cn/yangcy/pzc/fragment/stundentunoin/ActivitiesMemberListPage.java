package cn.yangcy.pzc.fragment.stundentunoin;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Objects;

import cn.yangcy.pzc.R;
import cn.yangcy.pzc.adapter.MyShowMemberListRecyclerViewAdapter;
import cn.yangcy.pzc.model.user.User;
import cn.yangcy.pzc.viewmodel.StudentUnionViewModel;

public class ActivitiesMemberListPage extends Fragment {

    private static final String TAG = "ActivitiesMemberListPag";
    private StudentUnionViewModel mViewModel;
    private MyShowMemberListRecyclerViewAdapter myShowMemberListRecyclerViewAdapter;
    private List<Integer> memberAccountList;
    private LiveData<List<User>> memberLiveData;

    public static ActivitiesMemberListPage newInstance() {
        return new ActivitiesMemberListPage();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.show_member_list_page_fragment, container, false);
        mViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(StudentUnionViewModel.class);
        RecyclerView mRecyclerView = view.findViewById(R.id.show_member_list_recyclerView);
        myShowMemberListRecyclerViewAdapter = new MyShowMemberListRecyclerViewAdapter(mViewModel);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(myShowMemberListRecyclerViewAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        mViewModel = ViewModelProviders.of(this).get(StudentUnionViewModel.class);

    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
