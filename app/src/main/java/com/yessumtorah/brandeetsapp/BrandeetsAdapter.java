package com.yessumtorah.brandeetsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BrandeetsAdapter extends RecyclerView.Adapter<BrandeetsAdapter.BrandsViewHolder> {
    private Context mContext;
    private ArrayList<Brand> mBrandsList;
    private OnItemClickListener listener;
    private static final String TAG = "BrandeetsAdapter";
    private BrandeetsActivity mBrandeetsActivity;

    public BrandeetsAdapter(Context context, ArrayList<Brand> brandsList) {
        mContext = context;
        mBrandsList = brandsList;
    }

    public BrandeetsAdapter(Context context, ArrayList<Brand> brandsList, OnItemClickListener listener) {
        mContext = context;
        mBrandsList = brandsList;
        this.listener = listener;
    }


    @NonNull
    @Override
    public BrandsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.brand_item, parent, false);

        return new BrandsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BrandsViewHolder holder, int position) {
        final Brand currentItem = mBrandsList.get(position);

        String logoSrc = currentItem.getLogoSrc();
        String ext = currentItem.getExt();
        String name = currentItem.getName();
        String price = currentItem.getPrice();

        holder.mTextViewBrandName.setText(name + '.' + ext);
        holder.mTextViewPrice.setText("Price: " + price);

        holder.mImageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d(TAG, currentItem.toString());
            }
        });

        holder.mTextViewBrandName.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d(TAG, currentItem.getName());
            }
        });

        //Picasso.with(mContext).load(imageUrl).fit().centerInside().into(holder.mImageView);

    }

    @Override
    public int getItemCount() {
        return mBrandsList.size();
    }



    public class BrandsViewHolder extends RecyclerView.ViewHolder {
     //   private final ItemBinding binding;
        public ImageView mImageView;
        public TextView mTextViewBrandName;
        public TextView mTextViewPrice;

        public BrandsViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_view);
            mTextViewBrandName = itemView.findViewById(R.id.brand_name);
            mTextViewPrice = itemView.findViewById(R.id.price);
        }
    }


}
