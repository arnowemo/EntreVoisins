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

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.ArrayList;
import java.util.List;

public class NeighbourActivity extends AppCompatActivity {

    Neighbour currentNeighbour;



    private ImageButton mBackButton;
    private ImageView mAvatarNeighbour;
    private TextView mNameAvatarNeighbour;

    // info

    private TextView mNameNeighbour;
    private TextView mAddressNeighbour;
    private TextView mPhoneNeighbour;
    private TextView mWebsiteNeighbour;

    // About me

    private TextView mAboutMeNeighbour;

    // favoris

    private FloatingActionButton mAddFavoriteNeighbour;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neighbour);

        // recuperation des elements du layout
        mBackButton = findViewById(R.id.backButton);
        mAvatarNeighbour = findViewById(R.id.avatarNeighbour);
        mNameNeighbour = findViewById(R.id.name_neighbour);
        mNameAvatarNeighbour = findViewById(R.id.name_neighbour_avatar);
        mAddressNeighbour = findViewById(R.id.address_neighbour);
        mPhoneNeighbour = findViewById(R.id.phone_neighbour);
        mAboutMeNeighbour = findViewById(R.id.about_me_neighbour);
        mAddFavoriteNeighbour = findViewById(R.id.add_favorite);


        // actualisation des informations du voisin selectioné
        String NeighbourAvatarName = getIntent().getStringExtra("NameNeighbour");
        mNameAvatarNeighbour.setText(NeighbourAvatarName);
        String NeighbourName = getIntent().getStringExtra("NameNeighbour");
        mNameNeighbour.setText(NeighbourName);
        String NeighbourAddress = getIntent().getStringExtra("AddressNeighbour");
        mAddressNeighbour.setText(NeighbourAddress);
        String NeighbourPhone = getIntent().getStringExtra("PhoneNeighbour");
        mPhoneNeighbour.setText(NeighbourPhone);
        String NeighbourAboutMe = getIntent().getStringExtra("AboutMeNeighbour");
        mAboutMeNeighbour.setText(NeighbourAboutMe);



        // actualisation de la photo "avatar" du voisin

        String NeighbourAvatar = getIntent().getStringExtra("AvatarNeighbour");
        Glide.with(this)
                .load(NeighbourAvatar)
                .centerCrop()
                .into(mAvatarNeighbour);


        backActivity();
        favorites();

    }

    // methode pour changer l'image de du bouton favoris
    private void updateStar(FloatingActionButton addFavoriteNeighbour){

        if (currentNeighbour.liked) {
            addFavoriteNeighbour.setImageResource(R.drawable.ic_star_white_24dp);
        }
        else{
            addFavoriteNeighbour.setImageResource(R.drawable.ic_star_border_white_24dp);
        }
    }



    // methode pour recuprer le click sur le bouton favoris
    private void favorites() {

    updateStar(mAddFavoriteNeighbour);

        mAddFavoriteNeighbour.setOnClickListener(new FloatingActionButton.OnClickListener() {

            @Override
            public void onClick(View v) {
                currentNeighbour.liked = !currentNeighbour.liked;
                updateStar(mAddFavoriteNeighbour);



            }
        });

    }


    //méthode  pour revenir a l'activivité précedente en appuyant sur la fléche retour
    private void backActivity(){
        mBackButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent listNeighbourActivity = new Intent(NeighbourActivity.this, ListNeighbourActivity.class);
                startActivity(listNeighbourActivity);
            }
        });
    }



}