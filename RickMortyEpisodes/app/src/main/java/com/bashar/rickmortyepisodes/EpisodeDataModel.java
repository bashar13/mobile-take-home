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
    private String characterIds;

    EpisodeDataModel(JSONObject episodeObject) {

        try {
            episodeName = episodeObject.getString("name");
            episodeAirDate = episodeObject.getString("air_date");
            episodeNumber = episodeObject.getString("episode");

            JSONArray characters = episodeObject.getJSONArray("characters");
            ArrayList<String> characterList = new ArrayList<>();
            for( int i = 0; i< characters.length(); i++) {
                characterList.add(characters.getString(i));
            }

            characterIds = getCharacterIds(characterList);

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

    String getCharacterIds() {
        return characterIds;
    }

    private String getCharacterIds(ArrayList<String> list) {
        StringBuilder ids = new StringBuilder();
        for(String item: list) {
            String id = item.substring(42) + ",";
            ids.append(id);
        }
        String result = ids.substring(0, ids.length()-1);

        return result;
    }


}
