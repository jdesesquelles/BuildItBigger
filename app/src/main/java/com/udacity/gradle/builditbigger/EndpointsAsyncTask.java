package com.udacity.gradle.builditbigger;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Pair;
import android.widget.ProgressBar;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

import com.udacity.gradle.builditbigger.jokebackend.myApi.MyApi;



/**
 * Created by ebal on 16/10/15.
 */
public class EndpointsAsyncTask extends AsyncTask<Context, Void, String> {
    private static MyApi myApiService = null;
    private Context context;
    private OnTaskCompleted listener;
    private Fragment fragment;

    public EndpointsAsyncTask(OnTaskCompleted listener) {
        this.listener=listener;
    }

    @Override
    protected String doInBackground(Context... params) {

        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            myApiService = builder.build();
        }

        context = params[0];

        try {
            return myApiService.tellJoke().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
//        Joker myJoke = new Joker();
        if (listener != null) {listener.onTaskCompleted(result);}
//        Intent intent = new Intent(context, JokeActivity.class);
//        intent.putExtra(JokeActivity.JOKE_KEY, result);
//        context.startActivity(intent);
//        Toast.makeText(context, result, Toast.LENGTH_LONG).show();
    }
}
