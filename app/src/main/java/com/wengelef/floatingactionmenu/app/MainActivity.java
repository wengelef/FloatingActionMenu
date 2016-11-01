package com.wengelef.floatingactionmenu.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.wengelef.floatingactionmenu.FloatingActionMenu;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionMenu mFloatingActionMenu = (FloatingActionMenu) findViewById(R.id.fab_menu);

        mFloatingActionMenu.addFab(new FloatingActionMenu.MenuAction(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("FloatingActionMenu", "play clicked!");
            }
        }, R.drawable.ic_play_arrow_white_24dp));

        mFloatingActionMenu.addFab(new FloatingActionMenu.MenuAction(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("FloatingActionMenu", "pause clicked!");
            }
        }, R.drawable.ic_pause_white_24dp));
    }
}
