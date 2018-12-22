package me.jntalley.parstagram;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import me.jntalley.parstagram.model.Post;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private final List<Post> posts;
    Context context;

    public PostAdapter(List<Post> parsePosts) {
        posts = parsePosts;
    }

    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        posts.addAll(list);
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parentGroup, int viewType) {
        context = parentGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View postView = inflater.inflate(R.layout.item_post, parentGroup,false);
        ViewHolder viewHolder = new ViewHolder(postView);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Post post = posts.get(position);
        Date createdAt = post.getCreatedAt();

        holder.tvUsername.setText(post.getUser().getUsername());
        holder.tvCaption.setText(post.getCaption());
        holder.tvTimeStamp.setText(getRelativeTimeAgo(createdAt));
        holder.tvLikes.setText("Likes " + post.getLikes());
        Glide.with(context)
                .load(post.getImage().getUrl())
                .into(holder.ivPostImage);
        try {
            Glide.with(context)
                    .load(post.getUser().getParseFile("ProfilePic").getUrl())
                    .into(holder.ivProfilePic);
        }catch (NullPointerException e) {
            e.printStackTrace();
        }

        holder.btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!holder.pressedLike){
                    holder.pressedLike = true;
                    holder.btnLike.setImageResource(R.drawable.ufi_heart_active);
                    post.increment("Likes");
                    holder.tvLikes.setText("Likes " + post.getLikes());
                    post.saveInBackground();
                }else {
                    holder.pressedLike = false;
                    holder.btnLike.setImageResource(R.drawable.ufi_heart);
                    post.increment("Likes", -1);
                    holder.tvLikes.setText("Likes " + post.getLikes());
                    post.saveInBackground();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public String getRelativeTimeAgo(Date rawJsonDate) {
        long dateMillis = rawJsonDate.getTime();
        return DateUtils.getRelativeTimeSpanString(dateMillis,
                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
    }

    //create the ViewHolder class (you want this in the Adapter)
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivPostImage;
        public ImageView ivProfilePic;
        public TextView tvUsername;
        public TextView tvCaption;
        public TextView tvLikes;
        public TextView tvTimeStamp;
        public ImageButton btnLike;
        public boolean pressedLike = false;


        //below is the ViewHolder constructor
        public ViewHolder (View itemView) {
            super(itemView); //call super class

            //perform findViewById lookups
            //links above image/ text views to the actual views you created in the xml file
            ivPostImage = (ImageView) itemView.findViewById(R.id.ivPostImage);
            ivProfilePic = (ImageView) itemView.findViewById(R.id.ivProfilePic);
            tvUsername = (TextView) itemView.findViewById(R.id.tvUsername);
            tvCaption = (TextView) itemView.findViewById(R.id.tvCaption);
            tvTimeStamp =(TextView) itemView.findViewById(R.id.tvTimeStamp);
            tvLikes =(TextView) itemView.findViewById(R.id.tvLikes);
            btnLike = itemView.findViewById(R.id.btnLike);


        }
    }
}
