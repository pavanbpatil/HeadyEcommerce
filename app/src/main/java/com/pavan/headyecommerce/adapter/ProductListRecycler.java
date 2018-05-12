package com.pavan.headyecommerce.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.pavan.headyecommerce.R;
import com.pavan.headyecommerce.model.Category;
import com.pavan.headyecommerce.model.Product;
import com.pavan.headyecommerce.views.ProductDetails;
import com.pavan.headyecommerce.views.Products;

import java.util.ArrayList;
import java.util.List;

public class ProductListRecycler extends RecyclerView.Adapter<ProductListRecycler.ViewHolder> {

    private List<Product> mItems;
    private Context mContext;
    private PostItemListener mItemListener;


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView product_name,product_variant;
        PostItemListener mItemListener;

        public ViewHolder(View itemView, PostItemListener postItemListener) {
            super(itemView);
            product_name = (TextView) itemView.findViewById(R.id.product_name);
            product_variant = (TextView) itemView.findViewById(R.id.product_variant);

            this.mItemListener = postItemListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Product item = getItem(getAdapterPosition());
            this.mItemListener.onPostClick(item.getId());
            ProductDetails.productid=getAdapterPosition();
            Intent i = new Intent(mContext, ProductDetails.class);
            mContext.startActivity(i);
            notifyDataSetChanged();
        }
    }

    public ProductListRecycler(Context context, ArrayList<Product> posts, PostItemListener itemListener) {
        mItems = posts;
        mContext = context;
        mItemListener = itemListener;
    }

    @Override
    public ProductListRecycler.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View postView = inflater.inflate(R.layout.product_card, parent, false);

        ViewHolder viewHolder = new ViewHolder(postView, this.mItemListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ProductListRecycler.ViewHolder holder, final int position) {

        final Product item = mItems.get(position);

        holder.product_name.setText(item.getName());
        holder.product_variant.setText("Available in "+item.getVariants().size()+" variants");

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }


    public void updateProducts(List<Product> items) {
        mItems = items;
    }

    private Product getItem(int adapterPosition) {
        return mItems.get(adapterPosition);
    }

    public interface PostItemListener {
        void onPostClick(int id);
    }


}
