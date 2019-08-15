package com.bashar.rickmortyepisodes;

import java.util.ArrayList;

interface AsyncResponse {

    void updateUIOnProcessFinish(ArrayList<EpisodeDataModel> result);
}
