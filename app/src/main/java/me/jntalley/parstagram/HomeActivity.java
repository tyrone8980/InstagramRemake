package me.jntalley.parstagram;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Movie;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.jntalley.parstagram.model.Post;

public class HomeActivity extends AppCompatActivity {

    ArrayList<Post> posts;
    PostAdapter postAdapter;
    RecyclerView rvPosts;

    private TabLayout bottomTabLayout;
    private SwipeRefreshLayout swipeContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        bottomTabLayout = findViewById(R.id.tabs);
        bottomTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 1) {
                    tab.select();
                    startCreatePostActivity();
                }
                if(tab.getPosition()==2){
                    tab.select();
                    startProfileActivity();
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setLogo(R.drawable.nav_logo_whiteout);
//        getSupportActionBar().setDisplayUseLogoEnabled(true);
//        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.rgb(255,255,255)));

        loadPosts();

        posts = new ArrayList<>();
        postAdapter = new PostAdapter(posts);
        //resolve the recycler view
        rvPosts = (RecyclerView) findViewById(R.id.rvPosts);
        rvPosts.setLayoutManager(new LinearLayoutManager(this));
        rvPosts.setAdapter(postAdapter);

        swipeContainer = findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadPosts(true);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        TabLayout.Tab tab = tabLayout.getTabAt(0);
        tab.select();
        posts.clear();
        postAdapter.clear();
        loadPosts();

    }

    private void startCreatePostActivity() {
        Intent intent = new Intent(this, CreatePostActivity.class);
        startActivityForResult(intent,1101);
    }

    private void startProfileActivity() {
        Intent intent = new Intent(this,ProfileActivity.class);
        startActivity(intent);
    }

    private void loadPosts() {
        loadPosts(false);
    }

    private void loadPosts(final boolean refresh) {
        final Post.Query postsQuery = new Post.Query();
        postsQuery
                .getTop()
                .withUser();

        postsQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if (e == null) {
                    if (refresh) {
                        posts.clear();
                    }
                    posts.addAll(objects);
                    postAdapter.notifyDataSetChanged();

                    if (swipeContainer.isRefreshing()) {
                        swipeContainer.setRefreshing(false);
                    }

                    for (int i = 0; i < objects.size(); i++) {
                        Log.d("HomeActivity", "Post[" + i + "] = " + objects.get(i).getCaption()
                                + "\nusername = " + objects.get(i).getUser().getUsername());
                    }


                } else {
                    e.printStackTrace();
                }
            }
        });
    }
}
