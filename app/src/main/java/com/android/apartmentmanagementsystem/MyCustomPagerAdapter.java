package com.android.apartmentmanagementsystem;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.apartmentmanagementsystem.model.Slider;
import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;
import java.util.List;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

class MyCustomPagerAdapter extends PagerAdapter {
    Context context;
    private ArrayList<String> imageUrls;
    LayoutInflater layoutInflater;

    public MyCustomPagerAdapter(Context context, ArrayList<String> imageUrls) {
        this.context = context;
        this.imageUrls = imageUrls;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return imageUrls.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = layoutInflater.inflate(R.layout.item, container, false);

        PhotoView photoView = (PhotoView) itemView.findViewById(R.id.photoView);
        photoView.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(context).load(imageUrls.get(position)).into(photoView);
        container.addView(itemView);
        //listening to image click
        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Toast.makeText(context, "you clicked image " + (position + 1), Toast.LENGTH_LONG).show();*/

            }
        });

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

}
