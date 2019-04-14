package com.example.hp.materialtest.user;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.hp.materialtest.R;

import java.util.List;

import static com.example.hp.materialtest.user.shop_click_Activity.SHOP_IMAGE_ID;
import static com.example.hp.materialtest.user.shop_click_Activity.SHOP_NAME;


public class HeadAdapter extends RecyclerView.Adapter<HeadAdapter.ViewHolder> {
    private Context mcontext;
    private List<Head> mShopList;
    static class ViewHolder extends RecyclerView.ViewHolder {
        View shopView;
        ImageView shopImage;
        TextView shopName;

        public ViewHolder(View itemView) {
            super(itemView);
            shopView= itemView;
            shopImage= (ImageView) itemView.findViewById(R.id.shop_image);
            shopName= (TextView) itemView.findViewById(R.id.shop_name);

        }
    }
    public HeadAdapter(List<Head> shopList){
        mShopList=shopList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mcontext==null){
            mcontext=parent.getContext();
        }
        View view= LayoutInflater.from(mcontext).inflate(R.layout.user_shop_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        //给小框框加监听
        //view
        holder.shopView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Head head = mShopList.get(position);
                Intent intent = new Intent(mcontext,shop_click_Activity.class);
                intent.putExtra(SHOP_NAME, head.getName());
                intent.putExtra(SHOP_IMAGE_ID,head.getImageID());
                intent.putExtra("nickname",head.getNickname());
                mcontext.startActivity(intent);
//                Toast.makeText(v.getContext(),"you clicked view"+head.getName(),Toast.LENGTH_SHORT).show();

            }
        });
//        //图片
//        holder.shopImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int position = holder.getAdapterPosition();
//                Head head = mShopList.get(position);
//                Toast.makeText(v.getContext(),"you clicked image"+head.getName(),Toast.LENGTH_SHORT).show();
//
//            }
//        });
        //文字
        holder.shopName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Head head = mShopList.get(position);
                Toast.makeText(v.getContext(),"you clicked text"+head.getNickname(),Toast.LENGTH_SHORT).show();

            }
        });

        return holder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Head head=mShopList.get(position);
        holder.shopName.setText(head.getNickname());
        Glide.with(mcontext).load(head.getImageID()).into(holder.shopImage);
    }


    @Override
    public int getItemCount() {
        return mShopList.size();
    }


}
