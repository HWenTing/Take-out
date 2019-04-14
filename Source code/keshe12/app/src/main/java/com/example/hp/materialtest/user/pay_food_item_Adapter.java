package com.example.hp.materialtest.user;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.example.hp.materialtest.R;

import java.util.List;

/**
 * Created by HP on 2018/9/22.
 */

public class pay_food_item_Adapter extends RecyclerView.Adapter<pay_food_item_Adapter.ViewHolder>  {

    private Context mcontext;
    private List<pay_food_item> mpay_food_item;

    static class ViewHolder extends RecyclerView.ViewHolder {
        View Pay_food_View;

        TextView Pay_food_name;
        TextView Pay_food_price;


        public ViewHolder(View itemView) {
            super(itemView);
            Pay_food_View= itemView;

            Pay_food_name= (TextView) itemView.findViewById(R.id.pay_food_name);
            Pay_food_price=(TextView) itemView.findViewById(R.id.pay_food_money);

        }
    }
    public pay_food_item_Adapter(List<pay_food_item> pay_food_item_List){
        mpay_food_item=pay_food_item_List;
    }
    @Override
    public pay_food_item_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mcontext==null){
            mcontext=parent.getContext();
        }
        View view= LayoutInflater.from(mcontext).inflate(R.layout.pay_food_item,parent,false);
        final pay_food_item_Adapter.ViewHolder holder = new pay_food_item_Adapter.ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(pay_food_item_Adapter.ViewHolder holder, int position) {
        pay_food_item pay_food_item = mpay_food_item.get(position);
        holder.Pay_food_name.setText(pay_food_item.getFood_name());
        holder.Pay_food_price.setText(pay_food_item.getPrice()+"¥");
    }

    @Override
    public int getItemCount() {
        return mpay_food_item.size();

    }
}
