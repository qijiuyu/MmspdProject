package com.ylean.mmspd.activity.store;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.ylean.mmspd.R;
import com.zxdc.utils.library.base.BaseActivity;
import com.zxdc.utils.library.http.HttpConstant;
import com.zxdc.utils.library.util.SPUtil;
import com.zxdc.utils.library.view.TouchImageView;

import java.util.List;

/**
 * 展示图片
 */
public class ShowImgActivity extends BaseActivity {

    private ViewPager viewPager;
    private TextView tvNum,tvTotal;
    private String[] imgs;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_img);
        getImages();
        initView();
    }


    /**
     * 获取图片数据
     */
    private void getImages(){
        final String strImgs=getIntent().getStringExtra("strImgs");
        if(!TextUtils.isEmpty(strImgs)){
            imgs= strImgs.split(",");
        }
    }


    /**
     * 初始化
     */
    private void initView() {
        tvNum=findViewById(R.id.tv_num);
        tvTotal=findViewById(R.id.tv_total);
        tvTotal.setText(imgs.length+"");
        viewPager =findViewById(R.id.view_pager);
        //ViewPager相关
        ViewPagerAdater myAdater = new ViewPagerAdater(this);
        viewPager.setAdapter(myAdater);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            public void onPageSelected(int position) {
                tvNum.setText(++position+"");
            }
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    public class ViewPagerAdater extends PagerAdapter {
        private Context context;

        public ViewPagerAdater(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return imgs.length;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TouchImageView imageView = new TouchImageView(context);
            Glide.with(context).load(HttpConstant.IP+imgs[position]).into(imageView);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((ImageView) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
