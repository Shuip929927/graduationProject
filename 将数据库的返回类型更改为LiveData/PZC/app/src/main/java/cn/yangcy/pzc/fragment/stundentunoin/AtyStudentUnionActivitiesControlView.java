package cn.yangcy.pzc.fragment.stundentunoin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioGroup;

import cn.yangcy.pzc.R;
import cn.yangcy.pzc.databinding.StudentUnionActivitesControlViewBinding;
import cn.yangcy.pzc.model.activities.Activities;
import cn.yangcy.pzc.viewmodel.StudentUnionViewModel;

public class AtyStudentUnionActivitiesControlView extends AppCompatActivity {

    private static final String TAG = "AtyStudentUnionActiviti";
    private StudentUnionActivitesControlViewBinding binding;
    private StudentUnionViewModel mViewModel;
    private int activitiesId;
    private LiveData<Activities> activitiesLiveData;
    private Activities act;
    private boolean isShowMenu = true;

    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.student_union_activities_control_view);
        binding = DataBindingUtil.setContentView(this,R.layout.student_union_activites_control_view);
        mViewModel = new ViewModelProvider(this).get(StudentUnionViewModel.class);
        binding.setLifecycleOwner(this);
        Intent intent = getIntent();
        activitiesId = intent.getIntExtra("activities_id", -1);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save,menu);
        if(isShowMenu){
            menu.findItem(R.id.organization_save).setVisible(true);
        } else {
            menu.findItem(R.id.organization_save).setVisible(false);
        }
        return true;
    }

    public void hideMenu(){
        isShowMenu = false;
        supportInvalidateOptionsMenu();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.organization_save:
                Log.i(TAG, "onOptionsItemSelected: ");
                builder = new AlertDialog.Builder(this).setTitle(R.string.info_save).setMessage(R.string.info_check_save)
                        .setPositiveButton(R.string.info_save, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String activitiesTitle = binding.etActivitiesTitle.getText().toString().trim();
                                String activitiesHoldOrg = binding.etActivitiesHoldOrganization.getText().toString().trim();
                                String activitiesStartTime = binding.etActivitiesStartTime.getText().toString().trim();
                                String description = binding.etActivitiesDescription.getText().toString();
                                Intent back = new Intent(AtyStudentUnionActivitiesControlView.this, AtyStudentUnionActivitiesDetailPage.class);
                                back.putExtra("activities_id", activitiesId);
                                dialog.dismiss();
                                startActivity(back);
                                finish();

                            }
                        }).setNegativeButton(R.string.info_cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.create().show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
