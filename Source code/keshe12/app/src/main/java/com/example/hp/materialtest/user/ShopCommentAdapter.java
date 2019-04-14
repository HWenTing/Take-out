package com.example.hp.materialtest.user;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hp.materialtest.R;
import com.example.hp.materialtest.Util.MyRequest;
import com.example.hp.materialtest.entity.Shop;
import com.google.gson.Gson;

import java.util.List;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by HP on 2018/9/22.
 */

public class ShopCommentAdapter extends RecyclerView.Adapter<ShopCommentAdapter.ViewHolder> {
    private Context mcontext;
    private List<CommentItem> mCommentList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        View orderView;
        TextView shop_name;
        TextView shop_score;
        TextView comments;
        ImageView shopImage;

        public ViewHolder(View itemView) {
            super(itemView);
            orderView= itemView;
            shop_name= (TextView) itemView.findViewById(R.id.shop_name);
            shop_score = (TextView) itemView.findViewById(R.id.shop_score);
            comments = (TextView) itemView.findViewById(R.id.comments);
            shopImage= (ImageView) itemView.findViewById(R.id.shop_image);
        }
    }

    public ShopCommentAdapter(List<CommentItem> commentList){
        mCommentList=commentList;
    }

    @Override
    public ShopCommentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mcontext == null) {
            mcontext = parent.getContext();
        }
        View view= LayoutInflater.from(mcontext).inflate(R.layout.user_comment_item,parent,false);
        final ShopCommentAdapter.ViewHolder holder = new ShopCommentAdapter.ViewHolder(view);

        /*

        添加事件监听
         */
        return holder;
    }

    public void onBindViewHolder(ShopCommentAdapter.ViewHolder holder, int position) {
        CommentItem commentItem=mCommentList.get(position);
        holder.shop_name.setText(getShopNackname(commentItem.getShop_name()));
        holder.shop_score.setText(commentItem.getShop_score()+"");
        holder.comments.setText(commentItem.getComments());
        Glide.with(mcontext).load(commentItem.getImageID()).into(holder.shopImage);
    }

    @Override
    public int getItemCount() {
        return mCommentList.size();
    }

    private String getShopNackname(String user_name){
        try{
            RequestBody requestBody = new FormBody.Builder()
                    .add("shop_name",user_name)
                    .build();

            Request request = new Request.Builder()
                    .url("http://10.0.2.2:8080/DBDesign/db/get_shop_information.action")
                    .post(requestBody)
                    .build();

            String response = MyRequest.sendRequestWithOkHttp(request);
            Gson gson = new Gson();
            Shop shop = gson.fromJson(response,Shop.class).getShop();
            return shop.getShop_nickname();

        }catch (Exception e){
            return "未命名";
        }
    }
}
