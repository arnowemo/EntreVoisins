package com.openclassrooms.entrevoisins.ui.neighbour_list;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import java.util.List;

public class NeighbourActivity extends AppCompatActivity {

    private String mNeighbourName;
    private int mNeighbourId;
    private int mRealId;
    private Neighbour mNeighbour;
    private NeighbourApiService mNeighbourApiService;
    private List<Neighbour> mNeighbours;



    private ImageButton mBackButton;
    private ImageView mAvatarNeighbour;
    private TextView mNameAvatarNeighbour;

    // info

    private TextView mNameNeighbour;
    private TextView mAddressNeighbour;
    private TextView mPhoneNeighbour;

    // About me

    private TextView mAboutMeNeighbour;

    // Bouton favoris

    private FloatingActionButton mAddFavoriteNeighbour;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neighbour);
        mNeighbourApiService = DI.getNeighbourApiService();

        // recuperation de l'objet Neighbour
        Intent intent = getIntent();
        mNeighbour = intent.getParcelableExtra("ObjNeighbour");


        // recuperation des elements du layout
        mBackButton = findViewById(R.id.backButton);
        mAvatarNeighbour = findViewById(R.id.avatarNeighbour);
        mNameNeighbour = findViewById(R.id.name_neighbour);
        mNameAvatarNeighbour = findViewById(R.id.name_neighbour_avatar);
        mAddressNeighbour = findViewById(R.id.address_neighbour);
        mPhoneNeighbour = findViewById(R.id.phone_neighbour);
        mAboutMeNeighbour = findViewById(R.id.about_me_neighbour);
        mAddFavoriteNeighbour = findViewById(R.id.add_favorite);

        mNeighbourName = mNeighbour.getName();
        mNameNeighbour.setText(mNeighbourName);
        mNameAvatarNeighbour.setText(mNeighbourName);
        String NeighbourAddress = mNeighbour.getAddress();
        mAddressNeighbour.setText(NeighbourAddress.replace(";", "à"));
        String NeighbourPhone = mNeighbour.getPhoneNumber();
        mPhoneNeighbour.setText(NeighbourPhone);
        String NeighbourAboutMe = mNeighbour.getAboutMe();
        mAboutMeNeighbour.setText(NeighbourAboutMe);

        mNeighbourId = (int) mNeighbour.getId();
        mRealId = mNeighbourId - 1;
        mNeighbours = mNeighbourApiService.getNeighbours();
        mNeighbour = mNeighbours.get(mRealId);


        String NeighbourAvatar = mNeighbour.getAvatarUrl();

        Glide.with(this)
                .load(NeighbourAvatar)
                .centerCrop()
                .into(mAvatarNeighbour);




        setupStar();
        favorites();
        backActivity();

    }


    // méthode qui recupére le click sur le bouton favoris et modifie la valeur "Favorite" du model Neighbour
    private void favorites() {
        mAddFavoriteNeighbour.setOnClickListener(new FloatingActionButton.OnClickListener() {

            @Override
            public void onClick(View v) {
                mNeighbour.favorite = ! mNeighbour.favorite;

                if(mNeighbour.isFavorite()){

                    mNeighbour.setFavorite(true);
                    Toast.makeText(getApplicationContext(), mNeighbourName +  " Added to favorites", Toast.LENGTH_SHORT).show();

                } else
                    { mNeighbour.setFavorite(false);
                        Toast.makeText(getApplicationContext(), mNeighbourName + " Removed from favorites", Toast.LENGTH_SHORT).show();
                    }

                updateStar(mAddFavoriteNeighbour);
            }
        });

    }

    // méthode pour changer l'image de du bouton favoris
    private void updateStar(FloatingActionButton addFavoriteNeighbour){

        if (mNeighbour.isFavorite()) {
            addFavoriteNeighbour.setImageResource(R.drawable.ic_star_white_24dp);
        }
        else{
            addFavoriteNeighbour.setImageResource(R.drawable.ic_star_border_white_24dp);
        }
    }



    //Setup l'etoile du bouton Favoris avec la bonne valeur
    private void setupStar (){

        updateStar(mAddFavoriteNeighbour);
    }



    //méthode  pour revenir a l'activivité précedente en appuyant sur la fléche retour
    private void backActivity() {
        mBackButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent listNeighbourActivity = new Intent(NeighbourActivity.this, ListNeighbourActivity.class);
                startActivity(listNeighbourActivity);
            }
        });
    }
}