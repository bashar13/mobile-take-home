package com.bashar.rickmortyepisodes;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class FetchEpisodeListTaskTest {


    private ArrayList<EpisodeDataModel> episodeDataModels = null;
    private CountDownLatch signal = null;

    @Before
    public void setUp() throws Exception {
        signal = new CountDownLatch(1);
    }

    @Test
    public void testFetchSuccess() throws Throwable {
        String url = "https://rickandmortyapi.com/api/episode/";
        FetchEpisodeListTask task = new FetchEpisodeListTask();
        task.setListener(new AsyncResponseForEpisodeList() {
            @Override
            public void updateUIOnProcessFinish(ArrayList<EpisodeDataModel> result) {
                episodeDataModels = result;
                signal.countDown();
            }
        }).execute(url);
        signal.await(5, TimeUnit.SECONDS);

        assertFalse(episodeDataModels.isEmpty());
        assertTrue(episodeDataModels.size() == 31);
    }

    @After
    public void tearDown() throws Exception {
        signal.countDown();
    }


}