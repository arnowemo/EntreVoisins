package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.model.Neighbour;

import static android.media.CamcorderProfile.get;

public class NeighbourActivity extends AppCompatActivity {

    int position;
    Neighbour neighbour;

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



        //
        String NeighbourAvatar = getIntent().getStringExtra("AvatarNeighbour");
        Glide.with(this)
                .load(NeighbourAvatar)
                .centerCrop()
                .into(mAvatarNeighbour);


        backActivity();
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