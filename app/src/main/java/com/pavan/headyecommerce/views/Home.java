package com.pavan.headyecommerce.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.support.v7.widget.SearchView;
import android.widget.Toast;
import com.pavan.headyecommerce.R;
import com.pavan.headyecommerce.adapter.CategoryListRecycler;
import com.pavan.headyecommerce.adapter.RankingListRecycler;
import com.pavan.headyecommerce.model.Category;
import com.pavan.headyecommerce.model.Ranking;
import com.pavan.headyecommerce.remote.ApiUtils;
import com.pavan.headyecommerce.remote.DataResponse;
import com.pavan.headyecommerce.remote.SOService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends AppCompatActivity {

    @BindView(R.id.recycler_view)RecyclerView recycler_view;
    @BindView(R.id.recycler_view1)RecyclerView recycler_view1;
    @BindView(R.id.progressbar)ProgressBar progressbar;
    @BindView(R.id.main_layout)LinearLayout main_layout;
    @BindView(R.id.searchview)SearchView searchview;
    private CategoryListRecycler mCategoryAdapter;
    private RankingListRecycler mRankingAdapter;
    private SOService mService;
    public static List<Category> categories;
    public static List<Ranking> rankings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        mService = ApiUtils.getSOService();
        main_layout.setVisibility(View.GONE);
        searchview.setVisibility(View.GONE);
        searchview.setQuery("",true);
        searchview.setFocusable(true);
        searchview.setIconified(false);
        searchview.requestFocusFromTouch();

        mCategoryAdapter=new CategoryListRecycler(Home.this, new ArrayList<Category>(0), new CategoryListRecycler.PostItemListener() {
            @Override
            public void onPostClick(int id) {

            }
        });
        mRankingAdapter=new RankingListRecycler(Home.this, new ArrayList<Ranking>(0), new RankingListRecycler.PostItemListener() {
            @Override
            public void onPostClick(String id) {

            }
        });
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        recycler_view.setLayoutManager(layoutManager);
        recycler_view.setAdapter(mCategoryAdapter);
        recycler_view.setNestedScrollingEnabled(false);
        recycler_view.setHasFixedSize(true);

        RecyclerView.LayoutManager rank_layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        recycler_view1.setLayoutManager(rank_layoutManager);
        recycler_view1.setAdapter(mRankingAdapter);
        recycler_view1.setNestedScrollingEnabled(false);
        recycler_view1.setHasFixedSize(true);
         getData();


    }

    public void getData() {
        progressbar.setVisibility(View.VISIBLE);
        mService.getJsonData().enqueue(new Callback<DataResponse>() {
            @Override
            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {

                progressbar.setVisibility(View.GONE);
                main_layout.setVisibility(View.VISIBLE);
                searchview.setVisibility(View.VISIBLE);

                try {
                    if (response.isSuccessful()) {
                        if(response.body().getCategories().size()>0) {//check for data array size
                            categories=response.body().getCategories();
                            rankings=response.body().getRankings();
                            mCategoryAdapter.updateCats(categories);
                            mRankingAdapter.updateRanks(response.body().getRankings());
                        }
                        else
                        {
                            Toast.makeText(Home.this, "No data available", Toast.LENGTH_SHORT).show();
                        }
                        Log.d("Home", "Data loaded from API");
                    } else {
                        int statusCode = response.code();
                        // handle request errors depending on status code
                    }
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<DataResponse> call, Throwable t) {
                //showErrorMessage();
                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
                Log.d("Home", "error loading from API");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


}
