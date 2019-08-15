package com.bashar.rickmortyepisodes;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

class EpisodeDataModel {

    private String TAG = EpisodeDataModel.class.getSimpleName();

    private String episodeName;
    private String episodeAirDate;
    private String episodeNumber;
    private ArrayList<String> characterList = new ArrayList<>();

    EpisodeDataModel(JSONObject episodeObject) {

        try {
            episodeName = episodeObject.getString("name");
            episodeAirDate = episodeObject.getString("air_date");
            episodeNumber = episodeObject.getString("episode");

            JSONArray characters = episodeObject.getJSONArray("characters");
            for( int i = 0; i< characters.length(); i++) {
                characterList.add(characters.getString(i));
            }

        } catch (final JSONException e) {
            Log.e(TAG, "JSON parsing error" + e.getMessage());
        }

    }

    String getEpisodeName() {
        return episodeName;
    }

    String getEpisodeAirDate() {
        return episodeAirDate;
    }

    String getEpisodeNumber() {
        return episodeNumber;
    }

    ArrayList<String> getCharacterList() {
        return characterList;
    }


}
