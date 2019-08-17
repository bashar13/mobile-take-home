package com.bashar.rickmortyepisodes;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

class ParseEpisodeListTask extends AsyncTask<String, Void, ArrayList<EpisodeDataModel>> {

    private String TAG = ParseEpisodeListTask.class.getSimpleName();
    ArrayList<EpisodeDataModel> episodeList = new ArrayList<>();
    public AsyncResponseForEpisodeList delegate = null;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //Toast.makeText(get,"Json Data is downloading",Toast.LENGTH_LONG).show();

    }

    @Override
    protected ArrayList<EpisodeDataModel> doInBackground(String... params) {

        HTTPHandler sh = new HTTPHandler();
        // Making a request to url and getting response
        String url = params[0];
        do {
            String jsonStr = sh.makeServiceCall(url);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonStr);
                    JSONArray episodes = jsonObject.getJSONArray("results");
                    for (int i=0; i< episodes.length(); i++) {
                        JSONObject episode = episodes.getJSONObject(i);
                        EpisodeDataModel episodeDataModel = new EpisodeDataModel(episode);
                        episodeList.add(episodeDataModel);
                    }
                    JSONObject info = jsonObject.getJSONObject("info");
                    String nextUrl = info.getString("next");
                    url = nextUrl;
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                }
            }

        } while(!url.isEmpty());

        for (EpisodeDataModel data: episodeList) {
            System.out.println(data.getCharacterIds());
        }

        return episodeList;
    }

    @Override
    protected void onPostExecute(ArrayList<EpisodeDataModel> result) {
        super.onPostExecute(result);

        delegate.updateUIOnProcessFinish(result);
    }
}
