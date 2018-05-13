package com.pavan.headyecommerce.views;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.pavan.headyecommerce.R;
import com.pavan.headyecommerce.adapter.CategoryListRecycler;
import com.pavan.headyecommerce.adapter.ChildCategoryListRecycler;
import com.pavan.headyecommerce.adapter.ProductListRecycler;
import com.pavan.headyecommerce.model.Category;
import com.pavan.headyecommerce.model.IdSelector;
import com.pavan.headyecommerce.model.Product;
import com.pavan.headyecommerce.remote.ApiUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Products extends AppCompatActivity {

    @BindView(R.id.recycler_view)RecyclerView recycler_view;
    private ProductListRecycler mProductAdapter;
    private ChildCategoryListRecycler mChildCatAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }});
        ButterKnife.bind(this);
        mProductAdapter=new ProductListRecycler(Products.this, new ArrayList<Product>(0), new ProductListRecycler.PostItemListener() {
            @Override
            public void onPostClick(int id) {

            }
        });


        mChildCatAdapter=new ChildCategoryListRecycler(Products.this, new ArrayList<Integer>(0), new ChildCategoryListRecycler.PostItemListener() {
            @Override
            public void onPostClick(int id) {

            }
        });

        if(Home.categories.get(IdSelector.getCatId()).getProducts().size()>0)
        {
            setTitle(Home.categories.get(IdSelector.getCatId()).getName());
            RecyclerView.LayoutManager rank_layoutManager = new LinearLayoutManager(this);
            recycler_view.setLayoutManager(rank_layoutManager);
            recycler_view.setAdapter(mProductAdapter);
            recycler_view.setHasFixedSize(true);
            mProductAdapter.updateProducts(Home.categories.get(IdSelector.getCatId()).getProducts());
        }
       else if(Home.categories.get(IdSelector.getCatId()).getChildCategories().size()>0)
        {
            setTitle(Home.categories.get(IdSelector.getCatId()).getName()+ " Sub Categories");
            RecyclerView.LayoutManager rank_layoutManager = new GridLayoutManager(this,2);
            recycler_view.setLayoutManager(rank_layoutManager);
            recycler_view.setAdapter(mChildCatAdapter);
            recycler_view.setHasFixedSize(true);
            mChildCatAdapter.updateCats(Home.categories.get(IdSelector.getCatId()).getChildCategories());
        }

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
