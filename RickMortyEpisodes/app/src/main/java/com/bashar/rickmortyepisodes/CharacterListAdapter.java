package com.bashar.rickmortyepisodes;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

class CharacterListAdapter extends RecyclerView.Adapter<CharacterListAdapter.ViewHolder> {

    private ArrayList<CharacterDataModel> characterList;

    CharacterListAdapter(ArrayList<CharacterDataModel> data) {
        characterList = data;
    }

    @NonNull
    @Override
    public CharacterListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View listItem = layoutInflater.inflate(R.layout.list_item_character, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterListAdapter.ViewHolder viewHolder, final int position) {

        final CharacterDataModel data = characterList.get(position);
        viewHolder.charName.setText(data.getCharName());
        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(view.getContext(),"click on item: "+ position,Toast.LENGTH_LONG).show();
                //System.out.println(data.getCharName());
                Intent intent = new Intent(view.getContext(), CharacterInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("CHAR_INFO", (Serializable)data);
                intent.putExtra("CHAR_DATA", bundle);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return characterList.size();
    }

    void removeItem(int position) {
        characterList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, characterList.size());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView charName;
        LinearLayout linearLayout;
        ViewHolder(View itemView) {
            super(itemView);
            this.charName = itemView.findViewById(R.id.charName);

            linearLayout = itemView.findViewById(R.id.linearLayout);
        }
    }
}
