package com.bashar.rickmortyepisodes;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class FetchCharacterListTaskTest {


    private ArrayList<CharacterDataModel> characterDataModels = null;
    private CountDownLatch signal = null;

    @Before
    public void setUp() throws Exception {
        signal = new CountDownLatch(1);
    }

    @Test
    public void testFetchSuccess() throws Throwable {
        String url = "https://rickandmortyapi.com/api/character/1,2,183";
        FetchCharacterListTask task = new FetchCharacterListTask();
        task.setListener(new AsyncResponseForCharacterList() {
            @Override
            public void updateUIOnProcessFinish(ArrayList<CharacterDataModel> result) {
                characterDataModels = result;
                signal.countDown();
            }
        }).execute(url);
        signal.await(5, TimeUnit.SECONDS);

        assertFalse(characterDataModels.isEmpty());
        assertTrue(characterDataModels.size() == 3);
    }

    @After
    public void tearDown() throws Exception {
        signal.countDown();
    }


}