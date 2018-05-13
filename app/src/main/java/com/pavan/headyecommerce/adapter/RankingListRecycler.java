package com.pavan.headyecommerce.adapter;


import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.pavan.headyecommerce.R;
import com.pavan.headyecommerce.model.Category;
import com.pavan.headyecommerce.model.Product_;
import com.pavan.headyecommerce.model.Ranking;
import com.pavan.headyecommerce.views.Home;
import com.ramotion.cardslider.CardSliderLayoutManager;
import com.ramotion.cardslider.CardSnapHelper;

import java.util.ArrayList;
import java.util.List;

public class RankingListRecycler extends RecyclerView.Adapter<RankingListRecycler.ViewHolder> {

    private List<Ranking> mItems;
    private Context mContext;
    private PostItemListener mItemListener;
    private RankingProductListRecycler mRankingProductAdapter;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView ranktext;
        public RecyclerView inner_recycler_view;
        PostItemListener mItemListener;

        public ViewHolder(View itemView, PostItemListener postItemListener) {
            super(itemView);
            ranktext = (TextView) itemView.findViewById(R.id.ranktext);
            inner_recycler_view = (RecyclerView) itemView.findViewById(R.id.inner_recycler_view);




            this.mItemListener = postItemListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Ranking item = getItem(getAdapterPosition());
            this.mItemListener.onPostClick(item.getRanking());
            //Toast.makeText(mContext, "" + item.getRanking(), Toast.LENGTH_SHORT).show();

            notifyDataSetChanged();
        }
    }

    public RankingListRecycler(Context context, ArrayList<Ranking> posts, PostItemListener itemListener) {
        mItems = posts;
        mContext = context;
        mItemListener = itemListener;
    }

    @Override
    public RankingListRecycler.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View postView = inflater.inflate(R.layout.ranking_card, parent, false);

        ViewHolder viewHolder = new ViewHolder(postView, this.mItemListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RankingListRecycler.ViewHolder holder, final int position) {

        final Ranking item = mItems.get(position);

        holder.ranktext.setText(item.getRanking());

        mRankingProductAdapter=new RankingProductListRecycler(mContext, new ArrayList<Product_>(0), new RankingProductListRecycler.PostItemListener() {
            @Override
            public void onPostClick(String id) {

            }
        });
        RecyclerView.LayoutManager rank_layoutManager = new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL, false);
        holder.inner_recycler_view.setLayoutManager(rank_layoutManager);
        holder.inner_recycler_view.setAdapter(mRankingProductAdapter);
        holder.inner_recycler_view.setHasFixedSize(true);
        mRankingProductAdapter.updateRanks(Home.rankings.get(position).getProducts());
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }


    public void updateRanks(List<Ranking> items) {
        mItems = items;
    }

    private Ranking getItem(int adapterPosition) {
        return mItems.get(adapterPosition);
    }

    public interface PostItemListener {
        void onPostClick(String id);
    }


}
