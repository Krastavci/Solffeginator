package hr.fer.solffeginator;

import android.app.Application;
import android.content.Context;

/**
 * Created by Valerio on 5/18/2015.
 */
public class App extends Application{

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getContext() {
        return mContext;
    }
}
