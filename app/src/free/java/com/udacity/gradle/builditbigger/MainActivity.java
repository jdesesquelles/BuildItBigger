package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.util.Log;
import android.widget.ProgressBar;
import android.support.v4.app.Fragment;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.InterstitialAd;
import com.udacity.gradle.jokeactivitylib.JokeActivity;
import java.util.concurrent.CountDownLatch;


public class MainActivity extends AppCompatActivity implements OnTaskCompleted  {

    private InterstitialAd mInterstitialAd;
    private CountDownLatch mLatch;
    private String MFRAG = "mainFragment";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Adding the fragment dynamically
        getSupportFragmentManager().beginTransaction().add(R.id.fragment, new MainActivityFragment(), MFRAG).commit();
        // Interstitial ads
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                getJokeFromServer();
            }

            @Override
            public void onAdLoaded() {
                mLatch.countDown();
            }
        });
        requestNewInterstitial();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view) throws InterruptedException{
        mLatch.await();
        mInterstitialAd.show();
    }

    private void getJokeFromServer(){
        toggleSpinner();
        new EndpointsAsyncTask(this).execute(this);
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mInterstitialAd.loadAd(adRequest);
        mLatch = new CountDownLatch(1);
        Log.e("Ad loding", "Just started");
    }

    @Override
    public void onTaskCompleted(String result) {
        toggleSpinner();
        Intent intent = new Intent(this, JokeActivity.class);
        intent.putExtra(JokeActivity.JOKE_KEY, result);
        startActivity(intent);
    }

    private void toggleSpinner(){
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(MFRAG);
        ProgressBar spinner = (ProgressBar) fragment.getView().findViewById(R.id.jokeprogressBar);
        if (spinner.getVisibility()== View.GONE) {spinner.setVisibility(View.VISIBLE);}
        else if (spinner.getVisibility()== View.VISIBLE) {spinner.setVisibility(View.GONE);}
    }

}
