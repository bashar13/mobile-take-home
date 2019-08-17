package com.bashar.rickmortyepisodes;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

class AdapterCharacterList extends RecyclerView.Adapter<AdapterCharacterList.ViewHolder> {

    private ArrayList<CharacterDataModel> characterList;

    public AdapterCharacterList(ArrayList<CharacterDataModel> data) {
        characterList = data;
    }

    @NonNull
    @Override
    public AdapterCharacterList.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View listItem = layoutInflater.inflate(R.layout.list_item_character, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCharacterList.ViewHolder viewHolder, final int position) {

        final CharacterDataModel data = characterList.get(position);
        viewHolder.charName.setText(data.getCharName());
        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"click on item: "+ position,Toast.LENGTH_LONG).show();
                System.out.println(data.getCharName());
//                for(int i=0; i<data.getCharacterList().size(); i++) {
//                    System.out.println(data.getCharacterList().get(i));
//                }
//                Intent intent = new Intent(view.getContext(), CharacterListActivity.class);
//                intent.putExtra("CHARACTER_IDS", data.getCharacterIds());
//                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return characterList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView charName;
        public LinearLayout linearLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.charName = itemView.findViewById(R.id.charName);

            linearLayout = itemView.findViewById(R.id.linearLayout);
        }
    }
}
