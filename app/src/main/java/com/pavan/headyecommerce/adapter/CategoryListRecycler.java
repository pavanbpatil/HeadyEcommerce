package com.pavan.headyecommerce.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pavan.headyecommerce.R;
import com.pavan.headyecommerce.helper.CommonUtilities;
import com.pavan.headyecommerce.model.Category;
import com.pavan.headyecommerce.model.IdSelector;
import com.pavan.headyecommerce.model.Product;
import com.pavan.headyecommerce.views.Products;

import java.util.ArrayList;
import java.util.List;

public class CategoryListRecycler extends RecyclerView.Adapter<CategoryListRecycler.ViewHolder> {

    private List<Category> mItems;
    private Context mContext;
    private PostItemListener mItemListener;


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView category_name;
        public ImageView image;
        PostItemListener mItemListener;

        public ViewHolder(View itemView, PostItemListener postItemListener) {
            super(itemView);
            category_name = (TextView) itemView.findViewById(R.id.category_name);
            image = (ImageView) itemView.findViewById(R.id.image);

            this.mItemListener = postItemListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Category item = getItem(getAdapterPosition());
            this.mItemListener.onPostClick(item.getId());
            //Products.catid=getAdapterPosition();
            IdSelector.setCatId(getAdapterPosition());
            Intent i = new Intent(mContext, Products.class);
            mContext.startActivity(i);
            notifyDataSetChanged();
        }
    }

    public CategoryListRecycler(Context context, ArrayList<Category> posts, PostItemListener itemListener) {
        mItems = posts;
        mContext = context;
        mItemListener = itemListener;
    }

    @Override
    public CategoryListRecycler.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View postView = inflater.inflate(R.layout.category_card, parent, false);

        ViewHolder viewHolder = new ViewHolder(postView, this.mItemListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CategoryListRecycler.ViewHolder holder, final int position) {

        final Category item = mItems.get(position);

        holder.category_name.setText(item.getName());

        int pos=position;
        if(pos>10)
        {
            pos=pos-10;
        }
        //This is static content to set only image to category cards randomly. It doesnt have any logic because all images should come from server :)
        switch (pos)
        {
            case 1:holder.image.setImageResource(R.drawable.bars_and_restaurant);
                break;
            case 2:holder.image.setImageResource(R.drawable.computer);
                break;
            case 3:holder.image.setImageResource(R.drawable.construction);
                break;
            case 4:holder.image.setImageResource(R.drawable.dairies_bakeries);
                break;
            case 5:holder.image.setImageResource(R.drawable.education_and_learning);
                break;
            case 6:holder.image.setImageResource(R.drawable.electronics);
                break;
            case 7:holder.image.setImageResource(R.drawable.extra_curricular);
                break;
            case 8:holder.image.setImageResource(R.drawable.health_and_care);
                break;
            case 9:holder.image.setImageResource(R.drawable.home_services);
                break;
            case 10:holder.image.setImageResource(R.drawable.real_estate);
                break;
                default:holder.image.setImageResource(R.drawable.real_estate);

        }


    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }


    public void updateCats(List<Category> items) {
        mItems = items;
    }

    private Category getItem(int adapterPosition) {
        return mItems.get(adapterPosition);
    }

    public interface PostItemListener {
        void onPostClick(int id);
    }


}
