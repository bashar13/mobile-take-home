package com.bashar.rickmortyepisodes;

import java.util.ArrayList;

interface AsyncResponseForCharacterList {

    void updateUIOnProcessFinish(ArrayList<CharacterDataModel> result);
}
