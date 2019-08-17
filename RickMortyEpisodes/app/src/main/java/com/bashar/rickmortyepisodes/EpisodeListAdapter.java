package com.bashar.rickmortyepisodes;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

class EpisodeListAdapter extends RecyclerView.Adapter<EpisodeListAdapter.ViewHolder> {

    private ArrayList<EpisodeDataModel> episodeList;

    EpisodeListAdapter(ArrayList<EpisodeDataModel> data) {
        episodeList = data;
    }

    @NonNull
    @Override
    public EpisodeListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View listItem = layoutInflater.inflate(R.layout.list_item_episode, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodeListAdapter.ViewHolder viewHolder, final int position) {

        final EpisodeDataModel data = episodeList.get(position);
        viewHolder.episodeName.setText(data.getEpisodeName());
        viewHolder.episodeAirDate.setText(data.getEpisodeAirDate());
        viewHolder.episodeNo.setText(data.getEpisodeNumber());
        viewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"click on item: "+ position,Toast.LENGTH_LONG).show();
                //System.out.println(data.getEpisodeName());
                Intent intent = new Intent(view.getContext(), CharacterListActivity.class);
                intent.putExtra("CHARACTER_IDS", data.getCharacterIds());
                intent.putExtra("EPISODE_NAME", data.getEpisodeName());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return episodeList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView episodeName;
        TextView episodeAirDate;
        TextView episodeNo;
        RelativeLayout relativeLayout;
        ViewHolder(View itemView) {
            super(itemView);
            this.episodeName = itemView.findViewById(R.id.episodeName);
            this.episodeAirDate = itemView.findViewById(R.id.episodeAirDate);
            this.episodeNo = itemView.findViewById(R.id.episodeNo);
            relativeLayout = itemView.findViewById(R.id.relativeLayout);
        }
    }
}
