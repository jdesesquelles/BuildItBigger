package com.udacity.gradle.jokeactivitylib;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

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
        if (getIntent() != null) {
            // Retrieve the joke from the intent
            joke = getIntent().getStringExtra(JOKE_KEY);
        }
        // Avoid crash when the joke string object is null. Should not happen normally.
        if (joke == null){
            joke = "So funny";
        }

        JokeFragment jokeFragment = JokeFragment.newInstance(joke);
        Log.e("joke", joke);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.joke_fragment_container, jokeFragment, JOKEFRAGMENT_TAG)
                .commit();

    }
}

