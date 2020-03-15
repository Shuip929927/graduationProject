package cn.yangcy.pzc.fragment.register;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import cn.yangcy.pzc.R;
import cn.yangcy.pzc.databinding.RegisterPage1FragmentBinding;
import cn.yangcy.pzc.viewmodel.RegisterViewModel;

public class RegisterPage1 extends Fragment {

    private static final String TAG = "RegisterPage1";
    private RegisterViewModel mViewModel;
    private  RegisterPage1FragmentBinding registerPage1FragmentBinding;

    public static RegisterPage1 newInstance() {
        return new RegisterPage1();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
//        mViewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);
//        mViewModel = new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication()).
//                create(RegisterViewModel.class);
        mViewModel = new ViewModelProvider(getActivity()).get(RegisterViewModel.class);

//        return inflater.inflate(R.layout.register_page1_fragment, container, false);
        registerPage1FragmentBinding = DataBindingUtil.inflate(inflater,
                R.layout.register_page1_fragment,container,false);

        registerPage1FragmentBinding.setMRegisterViewModel(mViewModel);

        //让RegisterActivity管理
        registerPage1FragmentBinding.setLifecycleOwner(getActivity());

        return registerPage1FragmentBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        registerPage1FragmentBinding.btToRegisterPage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mViewModel.doNext()){
                    NavController mNavController = Navigation.findNavController(v);
                    mNavController.navigate(R.id.action_registerPage1_to_registerPage2);
                }
            }
        });

    }

}
