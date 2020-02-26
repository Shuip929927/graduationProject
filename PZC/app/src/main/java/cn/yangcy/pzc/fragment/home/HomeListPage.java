package cn.yangcy.pzc.fragment.home;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

import cn.yangcy.pzc.R;
import cn.yangcy.pzc.adapter.MyInformationPagingAdapter;
import cn.yangcy.pzc.adapter.MyLooperPagerAdapter;
import cn.yangcy.pzc.adapter.MyInformationRecyclerViewAdapter;
import cn.yangcy.pzc.model.imformation.Information;
import cn.yangcy.pzc.viewmodel.HomeViewModel;

public class HomeListPage extends Fragment {

    private static final String TAG = "HomeListPage";
    private HomeViewModel mViewModel;
    private View view;
    private ViewPager mLoopPager;
    private MyLooperPagerAdapter myLooperPagerAdapter;
    private static List<Integer> homePics = new ArrayList<>();
    private Handler mHandler;
    private RecyclerView mRecyclerView;
    private MyInformationRecyclerViewAdapter myInformationRecyclerViewAdapter;
    private MyInformationPagingAdapter myInformationPagingAdapter;

    static {
        homePics.add(R.drawable.loopager_img1);
        homePics.add(R.drawable.loopager_img2);
        homePics.add(R.drawable.loopager_img3);
    }

    public static HomeListPage newInstance() {
        return new HomeListPage();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.home_page_list_fragment, container, false);
        mViewModel = new ViewModelProvider(getActivity()).get(HomeViewModel.class);

        mLoopPager = view.findViewById(R.id.home_looper);
        myLooperPagerAdapter = new MyLooperPagerAdapter();
        myLooperPagerAdapter.setData(homePics);
        mLoopPager.setAdapter(myLooperPagerAdapter);
        mLoopPager.setCurrentItem(myLooperPagerAdapter.getDataRealSize(),false);
        mRecyclerView = view.findViewById(R.id.home_recyclerView);
//        myInformationRecyclerViewAdapter = new MyInformationRecyclerViewAdapter(mViewModel);
        myInformationPagingAdapter = new MyInformationPagingAdapter(mViewModel);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false));
//        mRecyclerView.setAdapter(myInformationRecyclerViewAdapter);
        mRecyclerView.setAdapter(myInformationPagingAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        mViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        // TODO: Use the ViewModel
        mHandler = new Handler();
        Log.d(TAG, "post ");
        mHandler.post(mLooperTask);

//        mViewModel.getInfoListLive().observe(getViewLifecycleOwner(), new Observer<List<Information>>() {
//            @Override
//            public void onChanged(List<Information> information) {
//                myInformationRecyclerViewAdapter.setInfoList(information);
//                myInformationRecyclerViewAdapter.notifyDataSetChanged();
//            }
//        });

        mViewModel.getInfoPagedList().observe(getViewLifecycleOwner(), new Observer<PagedList<Information>>() {
            @Override
            public void onChanged(PagedList<Information> information) {
                myInformationPagingAdapter.submitList(information);
            }
        });

    }

    private Runnable mLooperTask = new Runnable() {
        @Override
        public void run() {
            int currentItem = mLoopPager.getCurrentItem();
            mLoopPager.setCurrentItem(++currentItem, true);
            mHandler.postDelayed(this, 3000);
        }
    };

    public void initRecyclerView(){

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: REMOVE");
        mHandler.removeCallbacks(mLooperTask);
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
