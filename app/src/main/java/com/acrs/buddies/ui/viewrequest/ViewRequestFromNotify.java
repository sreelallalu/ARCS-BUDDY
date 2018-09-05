package com.acrs.buddies.ui.viewrequest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.acrs.buddies.R;
import com.bumptech.glide.Glide;

public class ViewRequestFromNotify extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_request);

        ImageView imageView = findViewById(R.id.user_pic);
        TextView user_name = findViewById(R.id.user_name);
        TextView user_location = findViewById(R.id.user_location);
        Intent intent = getIntent();

        String url = intent.getStringExtra("imageUrl");
        String username = intent.getStringExtra("username");
        String userlocation = intent.getStringExtra("userlocation");

        user_name.setText(username);
        user_location.setText(userlocation);
        getSupportActionBar().setTitle("Emergency");
        Glide.with(this)
                .load(url)
                .into(imageView);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

    }

    @Override
    public void onBackPressed() {
      //  startActivity(new Intent(this, DashBoardActvity.class));
        finish();
    }
}
