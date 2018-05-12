package com.pavan.headyecommerce.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pavan.headyecommerce.R;
import com.pavan.headyecommerce.model.Category;
import com.pavan.headyecommerce.views.Products;

import java.util.ArrayList;
import java.util.List;

public class ChildCategoryListRecycler extends RecyclerView.Adapter<ChildCategoryListRecycler.ViewHolder> {

    private List<Integer> mItems;
    private Context mContext;
    private PostItemListener mItemListener;


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView child_category_name;
        public ImageView product_image;
        PostItemListener mItemListener;

        public ViewHolder(View itemView, PostItemListener postItemListener) {
            super(itemView);
            child_category_name = (TextView) itemView.findViewById(R.id.child_category_name);
            product_image = (ImageView) itemView.findViewById(R.id.product_image);

            this.mItemListener = postItemListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Integer item = getItem(getAdapterPosition());
            this.mItemListener.onPostClick(item);
            notifyDataSetChanged();
        }
    }

    public ChildCategoryListRecycler(Context context, ArrayList<Integer> posts, PostItemListener itemListener) {
        mItems = posts;
        mContext = context;
        mItemListener = itemListener;
    }

    @Override
    public ChildCategoryListRecycler.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View postView = inflater.inflate(R.layout.child_cat_card, parent, false);

        ViewHolder viewHolder = new ViewHolder(postView, this.mItemListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ChildCategoryListRecycler.ViewHolder holder, final int position) {

        final Integer item = mItems.get(position);

        holder.child_category_name.setText("Child Category "+item.toString());
        int pos=position;
        if(pos>10)
        {
            pos=pos-10;
        }
        //This is static content to set only image to category cards randomly. It doesnt have any logic because all images should come from server :)
        switch (pos)
        {
            case 1:holder.product_image.setImageResource(R.drawable.bars_and_restaurant);
                break;
            case 2:holder.product_image.setImageResource(R.drawable.computer);
                break;
            case 3:holder.product_image.setImageResource(R.drawable.construction);
                break;
            case 4:holder.product_image.setImageResource(R.drawable.dairies_bakeries);
                break;
            case 5:holder.product_image.setImageResource(R.drawable.education_and_learning);
                break;
            case 6:holder.product_image.setImageResource(R.drawable.electronics);
                break;
            case 7:holder.product_image.setImageResource(R.drawable.extra_curricular);
                break;
            case 8:holder.product_image.setImageResource(R.drawable.health_and_care);
                break;
            case 9:holder.product_image.setImageResource(R.drawable.home_services);
                break;
            case 10:holder.product_image.setImageResource(R.drawable.real_estate);
                break;
            default:holder.product_image.setImageResource(R.drawable.real_estate);

        }

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }


    public void updateCats(List<Integer> items) {
        mItems = items;
    }

    private Integer getItem(int adapterPosition) {
        return mItems.get(adapterPosition);
    }

    public interface PostItemListener {
        void onPostClick(int id);
    }


}
