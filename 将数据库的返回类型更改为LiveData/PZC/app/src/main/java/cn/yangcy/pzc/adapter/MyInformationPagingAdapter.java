package cn.yangcy.pzc.adapter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.yangcy.pzc.R;
import cn.yangcy.pzc.model.imformation.Information;
import cn.yangcy.pzc.viewmodel.HomeViewModel;

public class MyInformationPagingAdapter extends PagedListAdapter<Information, MyInformationPagingAdapter.MyViewHolder> {

    private static final String TAG = "MyInfoPagingAdapter";
    List<Information> infoList = new ArrayList<>();
    private static String infoAuthor;
    private static String infoHits;
    private static String infoLoading;
    private HomeViewModel mHomeViewModel;

    public MyInformationPagingAdapter(HomeViewModel mHomeViewModel) {
        super(new DiffUtil.ItemCallback<Information>() {
            @Override
            public boolean areItemsTheSame(@NonNull Information oldItem, @NonNull Information newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Information oldItem, @NonNull Information newItem) {
                return oldItem.getTitle().equals(newItem.getTitle());
            }
        });
        this.mHomeViewModel = mHomeViewModel;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return null;
        LayoutInflater mLayoutInflater = LayoutInflater.from(parent.getContext());
        View cellView = mLayoutInflater.inflate(R.layout.home_page_recycler_cell, parent, false);
        infoAuthor = parent.getContext().getResources().getString(R.string.info_author);
        infoHits = parent.getContext().getResources().getString(R.string.info_hits);
        infoLoading = parent.getContext().getResources().getString(R.string.paging_loading);
        return new MyViewHolder(cellView);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final Information information = getItem(position);
        if (information == null) {
            holder.cell_title.setText(infoLoading);
        } else {
            holder.cell_type.setText(information.getType());
            holder.cell_title.setText(information.getTitle());
            holder.cell_author.setText(infoAuthor + " " + information.getAuthor());
            holder.cell_createOn.setText(information.getCreateOn());
            holder.cell_hits.setText(infoHits + "\n" + information.getHits());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG, "onClick: " + information.toString());
                    mHomeViewModel.setClickInformation(information);
                    //选择使用Fragment显示详细信息，但底部导航栏还存在
                    NavController mNavController = Navigation.findNavController(holder.itemView);
                    mNavController.navigate(R.id.action_home_list_page_to_home_detail_page);

                    //选择使用Activity显示详细信息，但显示不够平滑
                    //看到List页面点击数更新，还没进行跳转
//                Intent detail = new Intent(holder.itemView.getContext(), AtyHomeListDetailPage.class);
//                detail.putExtra("information", information);
//                holder.itemView.getContext().startActivity(detail);
                }
            });
        }
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView cell_type, cell_title, cell_author, cell_createOn, cell_hits;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cell_type = itemView.findViewById(R.id.cell_type);
            cell_title = itemView.findViewById(R.id.cell_title);
            cell_author = itemView.findViewById(R.id.cell_author);
            cell_createOn = itemView.findViewById(R.id.cell_create_on);
            cell_hits = itemView.findViewById(R.id.cell_hits);
        }
    }
}
