package cn.yangcy.pzc.fragment.home;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

import cn.yangcy.pzc.R;
import cn.yangcy.pzc.databinding.HomeDetailPageFragmentBinding;
import cn.yangcy.pzc.model.imformation.Information;
import cn.yangcy.pzc.viewmodel.HomeViewModel;

public class HomeDetailPage extends Fragment {

    private HomeViewModel mViewModel;
    private HomeDetailPageFragmentBinding binding;
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
        binding = DataBindingUtil.inflate(inflater,
                R.layout.home_detail_page_fragment,container,false);
        mViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(HomeViewModel.class);
        showInfo = mViewModel.getClickInfo();
        infoAuthor = getContext().getResources().getString(R.string.info_author);
        infoHits = getContext().getResources().getString(R.string.info_hits);
        return binding.getRoot();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.infoDetailTitle.setText(showInfo.getTitle());
        binding.infoDetailType.setText(showInfo.getType());
        binding.infoDetailAuthor.setText(infoAuthor+" "+showInfo.getAuthor());
        binding.infoDetailCreateOn.setText(showInfo.getCreateOn());
        binding.infoDetailHits.setText(infoHits+"\n"+showInfo.getHits());
        binding.infoDetailContent.setText(showInfo.getContent());

    }
}
