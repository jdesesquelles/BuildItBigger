package com.udacity.gradle.builditbigger;

import android.app.Application;
import android.content.Context;
import android.test.ApplicationTestCase;
import android.util.Log;
import android.util.Pair;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by ebal on 17/10/15.
 */
public class TestEndpointsAsyncTask extends ApplicationTestCase<Application> implements OnTaskCompleted{

    CountDownLatch timer = new CountDownLatch(1);

    public TestEndpointsAsyncTask() {
        super(Application.class);
    }

    public void testEndpointAsyncTask () {
        try {
//             new EndpointsAsyncTask(null).execute(new Pair<Context, String>(getInstrumentation().getTargetContext(), "Manfred")).get();
            new EndpointsAsyncTask(null).execute(getContext());
            timer.await(30, TimeUnit.SECONDS);
//            Log.e("testEndpointAsyncTask - result", endpointsAsyncTaskResult);
//            assertNotNull(result);
        } catch (InterruptedException iE) {
            Log.e("InterruptedException", iE.toString());
            fail("Time out");
        }
    }

    @Override
    public void onTaskCompleted(String result) {
        assertNotNull(result);
        timer.countDown();
    }

}
