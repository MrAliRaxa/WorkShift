package com.e.workshift.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.e.workshift.R;

public class MainActivity extends AppCompatActivity {

    private ImageView menuBtn;
    private ImageView shareBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        menuBtn=findViewById(R.id.ivMenu);
        shareBtn=findViewById(R.id.ivShare);

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });
        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu=new PopupMenu(MainActivity.this,menuBtn);

                popupMenu.getMenuInflater().inflate(R.menu.dashboard_menu,popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){

                            case R.id.menuRateUs:
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps"));
                                startActivity(browserIntent);
                                break;
                            case R.id.menuShare:
                                Intent sendIntent = new Intent();
                                sendIntent.setAction(Intent.ACTION_SEND);
                                sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
                                sendIntent.setType("text/plain");
                                startActivity(sendIntent);
                                break;
                            case R.id.menuSetting:
                                startActivity(new Intent(MainActivity.this,Setting.class));
                                break;
                            case R.id.menuBackup:
                                startActivity(new Intent(MainActivity.this,Backup.class));

                                break;

                            case R.id.menuPrivacyPolicy:
                                startActivity(new Intent(MainActivity.this,PrivacyPolicy.class));

                                break;
                            case R.id.menuTermsOfService:

                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://policies.google.com/terms?hl=en-US"));
                                startActivity(intent);
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }
}