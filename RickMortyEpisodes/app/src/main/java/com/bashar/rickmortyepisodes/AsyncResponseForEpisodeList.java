package com.bashar.rickmortyepisodes;

import java.util.ArrayList;

interface AsyncResponseForEpisodeList {

    void updateUIOnProcessFinish(ArrayList<EpisodeDataModel> result);
}
