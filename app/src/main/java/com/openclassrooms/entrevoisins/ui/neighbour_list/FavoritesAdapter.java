package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {

    private List<Neighbour> mListFavorite;
    private NeighbourListener mNeighbourListener;

    public FavoritesAdapter( List<Neighbour> items, NeighbourListener neighbourListener) {
        mListFavorite = items;
        this.mNeighbourListener = neighbourListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_favorites,parent,false);
        return new ViewHolder(view,mNeighbourListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.mNeighbourName.setText(mListFavorite.get(position).getName());
        Glide.with(holder.mNeighbourAvatar.getContext())
                .load(mListFavorite.get(position).getAvatarUrl())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.mNeighbourAvatar);
    }

    @Override
    public int getItemCount() {
        return mListFavorite.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.item_fav_list_avatar)
        public ImageView mNeighbourAvatar;
        @BindView(R.id.item_fav_list_name)
        public TextView mNeighbourName;



      NeighbourListener neighbourListener;



        public ViewHolder(@NonNull View itemView, NeighbourListener neighbourListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            this.neighbourListener = neighbourListener;
        }

        @Override
        public void onClick(View v) {
            neighbourListener.neighbourClick(getAdapterPosition());

        }
    }
    public interface NeighbourListener {
        void neighbourClick(int position);
    }
}
