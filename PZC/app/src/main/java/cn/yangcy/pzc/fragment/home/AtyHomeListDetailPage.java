package cn.yangcy.pzc.fragment.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import cn.yangcy.pzc.R;
import cn.yangcy.pzc.databinding.HomeDetailPageFragmentBinding;
import cn.yangcy.pzc.model.imformation.Information;
import cn.yangcy.pzc.view.HomeActivity;
import cn.yangcy.pzc.viewmodel.HomeViewModel;

public class AtyHomeListDetailPage extends AppCompatActivity {

    private static final String TAG = "AtyHomeListDetailPage";
//    private HomeViewModel mViewModel;
    private HomeDetailPageFragmentBinding homeDetailPageFragmentBinding;
//    private View view;
    private Information showInfo;
    private static String infoAuthor;
    private static String infoHits;
    private static Information defaultInfo = new Information("error","error","error","error",
            "error");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        showInfo = (Information) getIntent().getSerializableExtra("information");

//        setContentView(R.layout.home_detail_page_fragment);
        homeDetailPageFragmentBinding = DataBindingUtil.setContentView(this,R.layout.home_detail_page_fragment);
//        mViewModel = new ViewModelProvider().get(HomeViewModel.class);
//        showInfo = mViewModel.getClickInfo();
        infoAuthor = getResources().getString(R.string.info_author);
        infoHits = getResources().getString(R.string.info_hits);

        homeDetailPageFragmentBinding.infoDetailTitle.setText(showInfo.getTitle());
        homeDetailPageFragmentBinding.infoDetailType.setText(showInfo.getType());
        homeDetailPageFragmentBinding.infoDetailAuthor.setText(infoAuthor+" "+showInfo.getAuthor());
        homeDetailPageFragmentBinding.infoDetailCreateOn.setText(showInfo.getCreateOn());
        homeDetailPageFragmentBinding.infoDetailHits.setText(infoHits+"\n"+showInfo.getHits());
        homeDetailPageFragmentBinding.infoDetailContent.setText(showInfo.getContent());
    }
}
