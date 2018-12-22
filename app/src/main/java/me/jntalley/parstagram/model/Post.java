package me.jntalley.parstagram.model;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;


@ParseClassName("Post")
public class Post extends ParseObject {
    //any logic regarding mutating and accessing the parse object should go here
    public static final String KEY_CAPTION = "caption";
    public static final String KEY_USER = "user";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_LIKES = "Likes";

    //create our accesses and mutators below
    public String getCaption() {
        //method to get caption from Parse post
        return getString(KEY_CAPTION);
    }
    public void setCaption(String caption){
        //set a new caption to the post (editing)
        put(KEY_CAPTION, caption);
    }

    public ParseFile getImage() {
        return getParseFile(KEY_IMAGE);
    }
    public void setImage(ParseFile image) {
        put(KEY_IMAGE, image);
    }

    public ParseUser getUser(){
        return getParseUser(KEY_USER);
    }
    public void setUser(ParseUser user){
        //@user in post class put user given
        put(KEY_USER, user);
    }

    public int getLikes() {
        return getInt(KEY_LIKES);
    }
    public void setLikes(int Int) {
        put(KEY_LIKES, Int);
    }


    public static class Query extends ParseQuery<Post> {
        public Query(){
            super(Post.class);
        }
        public Query getTop() {
            orderByDescending("createdAt");
            setLimit(20);
            return this; // this is the post class
        }

        public Query withUser() {
            include("user");
            return this; //this is the post
        }
    }
}
