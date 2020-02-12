package cn.yangcy.pzc.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class LooperPagerAdapter extends PagerAdapter {

    private List<Integer> homePics = null;

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        int realPosition = position % homePics.size();
        ImageView imageView = new ImageView(container.getContext());
        imageView.setImageResource(homePics.get(realPosition));
        container.addView(imageView);
//        return super.instantiateItem(container, position);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        super.destroyItem(container, position, object);
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        if(homePics != null){
            return Integer.MAX_VALUE;
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
//        return false;
        return view == object;
    }

    public void setData(List<Integer> pic){
        this.homePics = pic;
    }
    public int getDataRealSize(){
        if (homePics != null) {
            return homePics.size() * 100;
        }
        return 0;
    }

}
