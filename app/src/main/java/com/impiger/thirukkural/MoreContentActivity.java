package com.impiger.thirukkural;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.impiger.thirukkural.model.Constants;


public class MoreContentActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {
    private String title;
    private View detailedImage;
    private TextView moreContentOne;
    private TextView moreContentTwo;
    private TextView moreContentThree;
    private TextView moreTitleOne;
    private TextView moreTitleTwo;
    private TextView moreTitleThree;
    private ImageView background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_content);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        detailedImage = findViewById(R.id.background);
        moreTitleOne = (TextView) findViewById(R.id.title_one);
        moreTitleTwo = (TextView) findViewById(R.id.title_two);
        moreTitleThree = (TextView) findViewById(R.id.title_three);
        moreContentOne = (TextView) findViewById(R.id.content_one);
        moreContentTwo = (TextView) findViewById(R.id.content_two);
        moreContentThree = (TextView) findViewById(R.id.content_three);
        background = (ImageView) findViewById(R.id.background);
        setSupportActionBar(toolbar);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        appBarLayout.addOnOffsetChangedListener(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        updateData();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareContent();
            }
        });
    }

    private void updateData() {
        title = getIntent().getExtras().getString(Constants.EXTRA_TITLE_KEY);
        getSupportActionBar().setTitle(title);
        if (Constants.EXTRA_MORE_THIRUKKURAL_POINTS.equals(title)) {
            background.setBackgroundResource(R.drawable.bg);
            moreContentOne.setText(getResources().getString(R.string.second_thirukkural_more_content));
        } else {
            background.setBackgroundResource(R.drawable.second_bg);
            moreContentOne.setText(getResources().getString(R.string.thirukkural_more_content_one));
            moreContentTwo.setText(getResources().getString(R.string.thirukkural_more_content_two));
            moreTitleOne.setText(getResources().getString(R.string.thirukkural_more_title_one));
            moreTitleTwo.setText(getResources().getString(R.string.thirukkural_more_title_two));
            moreTitleThree.setVisibility(View.GONE);
            moreContentTwo.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void shareContent() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, title);
        intent.putExtra(Intent.EXTRA_TEXT, getShareContent(title));
        startActivity(intent);
    }

    private String getShareContent(String title) {
        StringBuilder builder = new StringBuilder();
        if(Constants.EXTRA_MORE_THIRUKKURAL.equals(title)) {
            builder.append(getString(R.string.thirukkural_more_title_one)).append("\n\n");
            builder.append(getString(R.string.thirukkural_more_content_one)).append("\n\n\n");
            builder.append(getString(R.string.thirukkural_more_title_two)).append("\n\n");
            builder.append(getString(R.string.thirukkural_more_content_two)).append("\n");
        }
        return builder.toString();
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
    }
}
