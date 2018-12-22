package me.jntalley.parstagram;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

import me.jntalley.parstagram.model.Post;

public class ParseApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Post.class);
        final Parse.Configuration configuration = new Parse.Configuration.Builder(this)
                .applicationId("IceStation")
                .clientKey("RuleBrittania")
                .server("http://jntalley-parsetagram.herokuapp.com/parse")
                .build();

        Parse.initialize(configuration);
    }
}
