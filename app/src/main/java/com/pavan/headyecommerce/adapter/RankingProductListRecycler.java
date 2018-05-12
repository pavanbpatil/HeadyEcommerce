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
import com.pavan.headyecommerce.helper.CommonUtilities;
import com.pavan.headyecommerce.model.Product;
import com.pavan.headyecommerce.model.Product_;
import com.pavan.headyecommerce.model.Ranking;
import com.pavan.headyecommerce.views.Home;
import com.pavan.headyecommerce.views.ProductDetails;
import com.pavan.headyecommerce.views.Products;

import java.util.ArrayList;
import java.util.List;

public class RankingProductListRecycler extends RecyclerView.Adapter<RankingProductListRecycler.ViewHolder> {

    private List<Product_> mItems;
    private Context mContext;
    private PostItemListener mItemListener;


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView product_name,visit_count;
        public ImageView image_product;
        PostItemListener mItemListener;

        public ViewHolder(View itemView, PostItemListener postItemListener) {
            super(itemView);
            product_name = (TextView) itemView.findViewById(R.id.product_name);
            visit_count = (TextView) itemView.findViewById(R.id.visit_count);
            image_product = (ImageView) itemView.findViewById(R.id.image_product);


            this.mItemListener = postItemListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Product_ item = getItem(getAdapterPosition());
            this.mItemListener.onPostClick(item.getId().toString());
            //Toast.makeText(mContext, "" + item.getRanking(), Toast.LENGTH_SHORT).show();

            notifyDataSetChanged();
        }
    }

    public RankingProductListRecycler(Context context, ArrayList<Product_> posts, PostItemListener itemListener) {
        mItems = posts;
        mContext = context;
        mItemListener = itemListener;
    }

    @Override
    public RankingProductListRecycler.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View postView = inflater.inflate(R.layout.ranking_product_card, parent, false);

        ViewHolder viewHolder = new ViewHolder(postView, this.mItemListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RankingProductListRecycler.ViewHolder holder, final int position) {

        final Product_ item = mItems.get(position);

        if(item.getViewCount()!=null && item.getOrderCount()==null && item.getShares()==null)
        {
            holder.visit_count.setText("Viewers : "+item.getViewCount());
        }
        else if(item.getViewCount()==null && item.getOrderCount()!=null && item.getShares()==null)
        {
            holder.visit_count.setText("Orders : "+item.getOrderCount());
        }
        else if(item.getViewCount()==null && item.getOrderCount()==null && item.getShares()!=null)
        {
            holder.visit_count.setText("Shares : "+item.getShares());
        }


        for(int i=0;i<Home.categories.size();i++)
        {
            for(int j=0;j<Home.categories.get(i).getProducts().size();j++) {
                if (Home.categories.get(i).getProducts().get(j).getId()==item.getId())
                {
                    holder.product_name.setText(""+ Home.categories.get(i).getProducts().get(j).getName());
                }
            }
        }


        //This is static content to set only image to category cards. It doesnt have any logic :)
        if(CommonUtilities.getRandomNumber(9999)<3333)
        {
            holder.image_product.setImageResource(R.drawable.arvind);
        }
        else if(CommonUtilities.getRandomNumber(9999)>3333 && CommonUtilities.getRandomNumber(9999)<6666)
        {
            holder.image_product.setImageResource(R.drawable.custom);
        }
        else
        {
            holder.image_product.setImageResource(R.drawable.bargain);
        }

        holder.image_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(int i=0;i<Home.categories.size();i++)
                {
                    for(int j=0;j<Home.categories.get(i).getProducts().size();j++) {
                        if (Home.categories.get(i).getProducts().get(j).getId()==item.getId())
                        {
                            Products.catid= i;
                            ProductDetails.productid=j;
                            Intent intent=new Intent(mContext,ProductDetails.class);
                            mContext.startActivity(intent);
                        }
                    }
                }


            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }


    public void updateRanks(List<Product_> items) {
        mItems = items;
    }

    private Product_ getItem(int adapterPosition) {
        return mItems.get(adapterPosition);
    }

    public interface PostItemListener {
        void onPostClick(String id);
    }


}
