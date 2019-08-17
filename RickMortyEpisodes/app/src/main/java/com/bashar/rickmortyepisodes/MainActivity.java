package com.bashar.rickmortyepisodes;

import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AsyncResponseForEpisodeList {

    private String TAG = MainActivity.class.getSimpleName();
    private static String url = "https://rickandmortyapi.com/api/episode/";
    private FetchEpisodeListTask asyncTask = new FetchEpisodeListTask();
    boolean asyncTaskFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        asyncTask.delegate = this;
        setContentView(R.layout.activity_main);

        checkNetworkConnection();
    }

    @Override
    public void updateUIOnProcessFinish(ArrayList<EpisodeDataModel> episodeList) {
        Log.e(TAG, "Number of Episodes" + episodeList.size());

        if(!episodeList.isEmpty()) {
            RecyclerView episodeListView = findViewById(R.id.episodeListView);
            EpisodeListAdapter episodeListAdapter = new EpisodeListAdapter(episodeList);
            episodeListView.setHasFixedSize(true);
            episodeListView.setLayoutManager(new LinearLayoutManager(this));
            episodeListView.setAdapter(episodeListAdapter);

            findViewById(R.id.noData).setVisibility(View.GONE);
        } else {
            findViewById(R.id.noData).setVisibility(View.VISIBLE);
        }
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
    }

    private void checkNetworkConnection() {
        NetworkManager manager = new NetworkManager();
        if (manager.isNetworkConnected(this)) {
            getEpisodes();
            asyncTaskFlag = true;
        } else {
            String title = getResources().getString(R.string.no_internet);
            String msg = getResources().getString(R.string.no_internet_msg);
            showDialog(title, msg);
        }
    }

    void getEpisodes() {
        asyncTask.execute(url);
    }

    public void showDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.dialogStyle);
        builder.setTitle(title).setMessage(message).setCancelable(false).setPositiveButton(getText(R.string.settings), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                startActivityForResult(new Intent(Settings.ACTION_SETTINGS), 0);

            }

        }).setNegativeButton(getText(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                finish();

            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!asyncTaskFlag) {
            checkNetworkConnection();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
