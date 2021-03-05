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
import com.openclassrooms.entrevoisins.data.FavoritesPref;

import java.util.ArrayList;

public class NeighbourActivity extends AppCompatActivity {

    private String mNeighbourName;
    private boolean liked = false;
    private ArrayList<Integer> neighbourFavListId = new ArrayList<>();
    private int neighbourId;
    private int realId = neighbourId - 1;


    private ImageButton mBackButton;
    private ImageView mAvatarNeighbour;
    private TextView mNameAvatarNeighbour;

    // info

    private TextView mNameNeighbour;
    private TextView mAddressNeighbour;
    private TextView mPhoneNeighbour;

    // About me

    private TextView mAboutMeNeighbour;

    // favoris

    private FloatingActionButton mAddFavoriteNeighbour;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neighbour);

        // remplissage de liste avec les données sauvegardé dans les Sharedpreferences
        neighbourFavListId = FavoritesPref.readListFav(getApplicationContext());

        // recuperation des elements du layout
        mBackButton = findViewById(R.id.backButton);
        mAvatarNeighbour = findViewById(R.id.avatarNeighbour);
        mNameNeighbour = findViewById(R.id.name_neighbour);
        mNameAvatarNeighbour = findViewById(R.id.name_neighbour_avatar);
        mAddressNeighbour = findViewById(R.id.address_neighbour);
        mPhoneNeighbour = findViewById(R.id.phone_neighbour);
        mAboutMeNeighbour = findViewById(R.id.about_me_neighbour);
        mAddFavoriteNeighbour = findViewById(R.id.add_favorite);


        // actualisation des informations du voisin sélectionné
        String NeighbourAvatarName = getIntent().getStringExtra("NameNeighbour");
        mNameAvatarNeighbour.setText(NeighbourAvatarName);
        mNeighbourName = getIntent().getStringExtra("NameNeighbour");
        mNameNeighbour.setText(mNeighbourName);
        String NeighbourAddress = getIntent().getStringExtra("AddressNeighbour");
        mAddressNeighbour.setText(NeighbourAddress);
        String NeighbourPhone = getIntent().getStringExtra("PhoneNeighbour");
        mPhoneNeighbour.setText(NeighbourPhone);
        String NeighbourAboutMe = getIntent().getStringExtra("AboutMeNeighbour");
        mAboutMeNeighbour.setText(NeighbourAboutMe);

        // ID voisin actuel
        neighbourId  = (int) getIntent().getLongExtra("IdNeighbour",0);

        // actualisation de la photo "avatar" du voisin
        String NeighbourAvatar = getIntent().getStringExtra("AvatarNeighbour");
        Glide.with(this)
                .load(NeighbourAvatar)
                .centerCrop()
                .into(mAvatarNeighbour);


        if(neighbourFavListId != null){

            setupStar();
        }

        backActivity();
        favorites();
    }






    // méthode pour récupérer le click sur le bouton favoris et pour ajouter l'ID dans les Share
    private void favorites() {

        mAddFavoriteNeighbour.setOnClickListener(new FloatingActionButton.OnClickListener() {

            @Override
            public void onClick(View v) {
                liked = !liked;
                updateStar(mAddFavoriteNeighbour);
                 realId = neighbourId -1;

                if(liked){
                    if(neighbourFavListId != null) {
                        neighbourFavListId = new ArrayList<>(FavoritesPref.readListFav(getApplicationContext()));
                    }else{
                        neighbourFavListId = new ArrayList<>();
                    }
                    neighbourFavListId.add(realId);
                    FavoritesPref.writeFavList(getApplicationContext(), neighbourFavListId);
                    Toast.makeText(getApplicationContext(), mNeighbourName +  " Added to favorites", Toast.LENGTH_SHORT).show();

                }
                else{
                    for (int i = 0; i < neighbourFavListId.size(); i++) {
                         int supId = neighbourFavListId.get(i);
                        if (supId == realId) {

                            neighbourFavListId.remove(neighbourFavListId.get(i));
                        }

                    }
                    //neighbourFavListId.remove(realId);
                    FavoritesPref.writeFavList(getApplicationContext(),neighbourFavListId);
                    Toast.makeText(getApplicationContext(), mNeighbourName + " Removed from favorites", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }



    // méthode pour changer l'image de du bouton favoris
    private void updateStar(FloatingActionButton addFavoriteNeighbour){

        if (liked) {
            addFavoriteNeighbour.setImageResource(R.drawable.ic_star_white_24dp);
        }
        else{
            addFavoriteNeighbour.setImageResource(R.drawable.ic_star_border_white_24dp);
        }
    }



    //Setup l'etoile fav avec la bonne valeur
    private void setupStar (){
        realId = neighbourId - 1;

        for(int i=0;i<neighbourFavListId.size();i++)
        {
            if( realId  == neighbourFavListId.get(i))
            {
                liked = true;
            }
        }
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