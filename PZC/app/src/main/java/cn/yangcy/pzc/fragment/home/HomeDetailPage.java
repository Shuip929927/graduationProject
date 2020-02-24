package cn.yangcy.pzc.fragment.home;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.yangcy.pzc.R;
import cn.yangcy.pzc.databinding.HomeDetailPageFragmentBinding;
import cn.yangcy.pzc.model.imformation.Information;
import cn.yangcy.pzc.viewmodel.HomeViewModel;

public class HomeDetailPage extends Fragment {

    private HomeViewModel mViewModel;
    private HomeDetailPageFragmentBinding homeDetailPageFragmentBinding;
//    private View view;
    private Information showInfo;
    private static String infoAuthor;
    private static String infoHits;


    public static HomeDetailPage newInstance() {
        return new HomeDetailPage();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
//        view =  inflater.inflate(R.layout.home_detail_page_fragment, container, false);
        homeDetailPageFragmentBinding = DataBindingUtil.inflate(inflater,
                R.layout.home_detail_page_fragment,container,false);
        mViewModel = new ViewModelProvider(getActivity()).get(HomeViewModel.class);
        showInfo = mViewModel.getClickInfo();
        infoAuthor = getContext().getResources().getString(R.string.info_author);
        infoHits = getContext().getResources().getString(R.string.info_hits);
        return homeDetailPageFragmentBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        mViewModel = ViewModelProviders.of(this).get(HomeDetailPageViewModel.class);
        // TODO: Use the ViewModel
        homeDetailPageFragmentBinding.infoDetailTitle.setText(showInfo.getTitle());
        homeDetailPageFragmentBinding.infoDetailType.setText(showInfo.getType());
        homeDetailPageFragmentBinding.infoDetailAuthor.setText(infoAuthor+" "+showInfo.getAuthor());
        homeDetailPageFragmentBinding.infoDetailCreateOn.setText(showInfo.getCreateOn());
        homeDetailPageFragmentBinding.infoDetailHits.setText(infoHits+"\n"+showInfo.getHits());
        homeDetailPageFragmentBinding.infoDetailContent.setText(showInfo.getContent());

    }

    @Override
    public void onPause() {
        super.onPause();

    }
}
