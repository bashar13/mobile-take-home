package com.bashar.rickmortyepisodes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AsyncResponseForEpisodeList {

    private String TAG = MainActivity.class.getSimpleName();
    EpisodesFetcher asyncTask = new EpisodesFetcher();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        asyncTask.delegate = this;
        setContentView(R.layout.activity_main);

        getEpisodes();

        ArrayList<Integer> list1 = new ArrayList<>();
        ArrayList<Integer> list2 = new ArrayList<>();

        list1.add(2);
        list1.add(3);

        list2.add(list1.get(1));
        list2.set(0, 4);

        System.out.println(list1);
        System.out.println(list2);

    }

    void getEpisodes() {

        String url = "https://rickandmortyapi.com/api/episode/";
        asyncTask.execute(url);
    }

    @Override
    public void updateUIOnProcessFinish(ArrayList<EpisodeDataModel> episodeList) {
        Log.e(TAG, "Number of Episodes" + episodeList.size());



//        for (ParentDataModel data: episodeList) {
//            System.out.println(data.getCharacterIds());
//        }

        RecyclerView episodeListView = findViewById(R.id.episodeListView);
        EpisodeListAdapter episodeListAdapter = new EpisodeListAdapter(episodeList);
        episodeListView.setHasFixedSize(true);
        episodeListView.setLayoutManager(new LinearLayoutManager(this));
        episodeListView.setAdapter(episodeListAdapter);
    }
}
