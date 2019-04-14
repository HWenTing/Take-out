package com.example.hp.materialtest.user;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.hp.materialtest.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 2018/9/6.
 */

public class Shop_itemFood_Adapter extends RecyclerView.Adapter<Shop_itemFood_Adapter.ViewHolder> {
    private Context mcontext;
    private List<Shop_itemFood> mShop_itemFood_List;

    public static  List<Shop_itemFood> order_foods = new ArrayList<Shop_itemFood>();

    static class ViewHolder extends RecyclerView.ViewHolder {
        View Shop_itemFood_View;
        ImageView Shop_itemFood_Image;
        TextView Shop_itemFood_name;
        TextView Shop_itemFood_price;
        TextView Shop_itemFood_description;
        Button Shop_itemFood_choose;

        public ViewHolder(View itemView) {
            super(itemView);
            Shop_itemFood_View= itemView;
            Shop_itemFood_Image= (ImageView) itemView.findViewById(R.id.shop_itemFood_image);
            Shop_itemFood_name= (TextView) itemView.findViewById(R.id.user_click_shop_food_name);
            Shop_itemFood_price=(TextView) itemView.findViewById(R.id.user_click_shop_food_price);
            Shop_itemFood_description=(TextView) itemView.findViewById(R.id.user_click_shop_food_description);
            Shop_itemFood_choose = (Button)itemView.findViewById(R.id.user_click_shop_food_choose);
        }
    }
    public Shop_itemFood_Adapter(List<Shop_itemFood> Shop_itemFood_List){
        mShop_itemFood_List=Shop_itemFood_List;
    }
    @Override
    public Shop_itemFood_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mcontext==null){
            mcontext=parent.getContext();
        }
        View view= LayoutInflater.from(mcontext).inflate(R.layout.user_shop_item_food,parent,false);
        final Shop_itemFood_Adapter.ViewHolder holder = new Shop_itemFood_Adapter.ViewHolder(view);
        //给小框框加监听
        //view
        holder.Shop_itemFood_View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Shop_itemFood shop_itemFood = mShop_itemFood_List.get(position);
                ;Toast.makeText(v.getContext(),UserMainActivity.input_account,Toast.LENGTH_SHORT).show();
            }
        });

        //按钮
        holder.Shop_itemFood_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int position = holder.getAdapterPosition();
                Shop_itemFood shop_itemFood = mShop_itemFood_List.get(position);
                order_foods.add(shop_itemFood);

                Toast.makeText(v.getContext(),"you add "+shop_itemFood.getFood_name(),Toast.LENGTH_SHORT).show();

            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(Shop_itemFood_Adapter.ViewHolder holder, int position) {
        Shop_itemFood shop_itemFood = mShop_itemFood_List.get(position);
        holder.Shop_itemFood_name.setText(shop_itemFood.getFood_name());
        holder.Shop_itemFood_description.setText(shop_itemFood.getFood_description());
        holder.Shop_itemFood_price.setText(shop_itemFood.getPrice()+"¥");
        Glide.with(mcontext).load(shop_itemFood.getImageID()).into(holder.Shop_itemFood_Image);
    }

    @Override
    public int getItemCount() {
        return mShop_itemFood_List.size();

    }
}
