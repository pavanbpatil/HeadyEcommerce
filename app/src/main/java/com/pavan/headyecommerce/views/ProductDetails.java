package com.pavan.headyecommerce.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.like.LikeButton;
import com.like.OnLikeListener;
import com.pavan.headyecommerce.R;
import com.pavan.headyecommerce.helper.CommonUtilities;
import com.pavan.headyecommerce.model.IdSelector;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductDetails extends AppCompatActivity {

    @BindView(R.id.text_product_name1)TextView text_product_name1;
    @BindView(R.id.text_product_name2)TextView text_product_name2;
    @BindView(R.id.text_product_price)TextView text_product_price;
    @BindView(R.id.text_product_tax)TextView text_product_tax;
    @BindView(R.id.text_product_color)TextView text_product_color;
    @BindView(R.id.text_product_size)TextView text_product_size;
    @BindView(R.id.text_product_total)TextView text_product_total;
    @BindView(R.id.button_addtocart)Button button_addtocart;
    @BindView(R.id.button_buynow)Button button_buynow;
    @BindView(R.id.color_spinner)Spinner color_spinner;
    @BindView(R.id.star_button)LikeButton star_button;
    @BindView(R.id.share_button)LikeButton share_button;
    public ArrayAdapter<String> coloradapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }});
        ButterKnife.bind(this);


        text_product_name1.setText(Home.categories.get(IdSelector.getCatId()).getProducts().get(IdSelector.getProductId()).getName().toString().trim());
        text_product_name2.setText(Home.categories.get(IdSelector.getCatId()).getProducts().get(IdSelector.getProductId()).getName().toString().trim());
        text_product_tax.setText(Home.categories.get(IdSelector.getCatId()).getProducts().get(IdSelector.getProductId()).getTax().getValue().toString().trim()+" % "+Home.categories.get(IdSelector.getCatId()).getProducts().get(IdSelector.getProductId()).getTax().getName().toString().trim());


        coloradapter =new ArrayAdapter<String>(getApplicationContext(),R.layout.spinner_item,getAvailableColors());
        coloradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        color_spinner.setAdapter(coloradapter);

        color_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int nondecimalsize=0,nondecimalprice=0;
                String item = parent.getItemAtPosition(position).toString();
                color_spinner.setSelection(position);
                text_product_color.setText(Home.categories.get(IdSelector.getCatId()).getProducts().get(IdSelector.getProductId()).getVariants().get(position).getColor().toString());
                try {
                    nondecimalsize = (int) (Double.parseDouble(Home.categories.get(IdSelector.getCatId()).getProducts().get(IdSelector.getProductId()).getVariants().get(position).getSize().toString()));
                }catch (NullPointerException e){}
                text_product_size.setText(String.valueOf(nondecimalsize));
                text_product_price.setText("\u20B9 "+Home.categories.get(IdSelector.getCatId()).getProducts().get(IdSelector.getProductId()).getVariants().get(position).getPrice().toString());
                String totalaftertax= CommonUtilities.taxCalculator(Home.categories.get(IdSelector.getCatId()).getProducts().get(IdSelector.getProductId()).getVariants().get(position).getPrice(),Home.categories.get(IdSelector.getCatId()).getProducts().get(IdSelector.getProductId()).getTax().getValue());
                nondecimalprice=(int) Double.parseDouble(totalaftertax);
                text_product_total.setText("\u20B9 "+nondecimalprice);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        star_button.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                Toast.makeText(getApplicationContext(),"Product added to your wishlist",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                Toast.makeText(getApplicationContext(),"Product removed from your wishlist",Toast.LENGTH_SHORT).show();
            }
        });

        share_button.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                shareDetails();
            }

            @Override
            public void unLiked(LikeButton likeButton) {

            }
        });


    }

    public List getAvailableColors() {
        List l = new ArrayList();
        int nondecimalsize=0;
            if (Home.categories.get(IdSelector.getCatId()).getProducts().get(IdSelector.getProductId()).getVariants().size() > 0) {
                for (int i = 0; i < Home.categories.get(IdSelector.getCatId()).getProducts().get(IdSelector.getProductId()).getVariants().size(); i++) {
                    try {
                        nondecimalsize = (int) (Double.parseDouble(Home.categories.get(IdSelector.getCatId()).getProducts().get(IdSelector.getProductId()).getVariants().get(i).getSize().toString()));
                    }catch (NullPointerException e)
                    {

                    }
                     l.add(Home.categories.get(IdSelector.getCatId()).getProducts().get(IdSelector.getProductId()).getVariants().get(i).getColor() + " (" + nondecimalsize + ")");
                }
            }

        return l;
    }


    private void shareDetails()
    {

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,
                "Hey check out this product at: https://play.google.com/store/apps/details?id=com.google.android.apps.plus");
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
        share_button.setLiked(false);
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
