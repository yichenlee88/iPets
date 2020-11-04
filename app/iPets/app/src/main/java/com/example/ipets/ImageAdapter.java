package com.example.ipets;

import android.content.Context;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

public class ImageAdapter extends BaseAdapter {

    private ViewGroup layout;
    private Context context;
    private List coll;

    public ImageAdapter(Context context, List coll) {

        super();
        this.context = context;
        this.coll = coll;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowview = inflater.inflate(R.layout.item_photo, parent, false);
        layout = (ViewGroup) rowview.findViewById(R.id.rl_item_photo);
        ImageView imageView = (ImageView) rowview.findViewById(R.id.imageView1);

        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        float dd = dm.density; //取出密度
        float px = 25 * dd; //像素 = dp * 密度
        float screenWidth = dm.widthPixels;
        int newWidth = (int) (screenWidth - px) / 4; // 一行顯示四個縮圖

        layout.setLayoutParams(new GridView.LayoutParams(newWidth, newWidth));
        imageView.setId(position);

        Bitmap bm = MediaStore.Images.Thumbnails.getThumbnail(context
                        .getApplicationContext().getContentResolver(), Long
                        .parseLong((String) coll.get(position)),
                MediaStore.Images.Thumbnails.MICRO_KIND, null);

        imageView.setImageBitmap(bm);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        //點擊照片
        imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(context, "index:" + position, Toast.LENGTH_SHORT)
                        .show();

                ((PhotoGalleryActivity)context).setImageView(position);
            }

        });

        return rowview;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return coll.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return coll.get(arg0);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

}
