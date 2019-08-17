package com.bashar.rickmortyepisodes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AsyncResponseForEpisodeList {

    private String TAG = MainActivity.class.getSimpleName();
    private ParseEpisodeListTask asyncTask = new ParseEpisodeListTask();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        asyncTask.delegate = this;
        setContentView(R.layout.activity_main);

        getEpisodes();

    }

    void getEpisodes() {

        String url = "https://rickandmortyapi.com/api/episode/";
        asyncTask.execute(url);
    }

    @Override
    public void updateUIOnProcessFinish(ArrayList<EpisodeDataModel> episodeList) {
        Log.e(TAG, "Number of Episodes" + episodeList.size());

        RecyclerView episodeListView = findViewById(R.id.episodeListView);
        EpisodeListAdapter episodeListAdapter = new EpisodeListAdapter(episodeList);
        episodeListView.setHasFixedSize(true);
        episodeListView.setLayoutManager(new LinearLayoutManager(this));
        episodeListView.setAdapter(episodeListAdapter);
    }
}
