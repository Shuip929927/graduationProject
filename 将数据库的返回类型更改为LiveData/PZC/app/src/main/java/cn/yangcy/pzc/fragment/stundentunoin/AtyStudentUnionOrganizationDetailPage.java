package cn.yangcy.pzc.fragment.stundentunoin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import cn.yangcy.pzc.R;
import cn.yangcy.pzc.databinding.StudentUnionOrganizationDetialPageBinding;
import cn.yangcy.pzc.model.enroll.OrganizationEnroll;
import cn.yangcy.pzc.model.organization.Organization;
import cn.yangcy.pzc.model.user.UserRepository;
import cn.yangcy.pzc.viewmodel.StudentUnionViewModel;

public class AtyStudentUnionOrganizationDetailPage extends AppCompatActivity {

    private static final String TAG = "SU_OrgDetailPagePage";
    public static AtyStudentUnionOrganizationDetailPage INSTANCE;
    private StudentUnionViewModel mViewModel;
    private StudentUnionOrganizationDetialPageBinding binding;
    private int organizationId;
    private int mMenuVisibleLevel = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        INSTANCE = this;
        binding = DataBindingUtil.setContentView(this, R.layout.student_union_organization_detial_page);
        mViewModel = new ViewModelProvider(this).get(StudentUnionViewModel.class);
        binding.setLifecycleOwner(this);
        Intent intent = getIntent();
        organizationId = intent.getIntExtra("organization_id", -1);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_organization, menu);
        switch (mMenuVisibleLevel) {
            case 1:
                menu.findItem(R.id.organization_member).setVisible(false);
                menu.findItem(R.id.organization_info_edit).setVisible(false);
                break;
            case 2:
                menu.findItem(R.id.organization_member).setVisible(true);
                menu.findItem(R.id.organization_info_edit).setVisible(false);
                break;
            default:
                menu.findItem(R.id.organization_member).setVisible(true);
                menu.findItem(R.id.organization_info_edit).setVisible(true);
                break;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.organization_member:
                intent = new Intent(this, AtyStudentUnionOrganizationMemberPage.class);
                intent.putExtra("organization_id", organizationId);
                startActivity(intent);
//                finish();
                break;
            case R.id.organization_info_edit:
                intent = new Intent(this, AtyStudentUnionOrganizationControlView.class);
                intent.putExtra("organization_id", organizationId);
                startActivity(intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showMenuLevel3(int level) {
        mMenuVisibleLevel = level;
        supportInvalidateOptionsMenu();
    }
}
