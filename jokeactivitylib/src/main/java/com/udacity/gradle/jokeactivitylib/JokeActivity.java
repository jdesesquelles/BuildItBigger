package com.udacity.gradle.jokeactivitylib;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.net.Uri;
import android.widget.Toast;

/**
 * Created by ebal on 17/10/15.
 */

public class JokeActivity extends AppCompatActivity implements JokeFragment.OnFragmentInteractionListener{

    private String joke;
    public static String JOKE_KEY = "Joke key";
    private static final String JOKEFRAGMENT_TAG = "JKTAG";


    @Override
    public void onFragmentInteraction(String joke) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);
//        JokeFragment jokeFragment = new JokeFragment();

        if (getIntent() != null) {
            // Retrieve the joke from the intent
            joke = getIntent().getStringExtra(JOKE_KEY);
            // Pass it on to the fragment as an argument
//            Bundle bundle = new Bundle();
//            bundle.putString("joke", joke);
//            jokeFragment.setArguments(bundle);

        }
        if (joke == null){
            joke = "So funny";
//            Bundle bundle = new Bundle();
//            bundle.putString("joke", joke);
//            jokeFragment.setArguments(bundle);
        }
        JokeFragment jokeFragment = JokeFragment.newInstance(joke);
        Log.e("joke", joke);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.joke_fragment_container, jokeFragment, JOKEFRAGMENT_TAG)
//                .addToBackStack(null)
                .commit();

    }
}

