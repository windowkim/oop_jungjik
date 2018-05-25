package com.total.administor.oop_jungjik;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    ArrayList<Integer> id_dyn = new ArrayList<Integer>();
    ArrayList<Integer> id_dyn_sub = new ArrayList<Integer>();

    private final int editoractivity = 0;
    private User user = new User(1, "1");
    private int SelectedDay = 0;
    private ArrayList<Content> contents = new ArrayList<Content>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String name[] = {"Google", "Naver", "교가"};
        String s[] = {"https://www.google.com/",
                "https://www.naver.com/", "file:///android_asset/school_song.html"};

        for (int i = 0; i < 3; i++) {
            contents.add(new Content(name[i],s[i],"첫째날", 0,0));
        }

        User puser = (User) getIntent().getSerializableExtra("User");
        user = puser;
        String username = user.getName();
        Toast toast = Toast.makeText(getApplicationContext(), "환영합니다! " + username + "님~", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, -500);
        toast.show();

        
        //navigation drawer
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        Log.v("MainActivity", "memunum"+Integer.toString(navigationView.getMenu().size()));

            addMenuItemInNaviMenuDrawer(contents);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    MessageFragment.newInstance(user,contents)).commit();
            // navigationView.setCheckedItem(R.id.nav_message);

        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != 1)
            return;
        if (requestCode == 1) {
            contents = (ArrayList<Content>) data.getSerializableExtra("Contents");
            Log.v("MainActivity", "EditorActivity result : ");
            NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
            Log.v("MainActivity", Integer.toString(navView.getMenu().size()));
            for(int i=0;i<id_dyn.size();i++)
                navView.getMenu().removeItem(id_dyn.get(i));
            for(int i=0;i<id_dyn_sub.size();i++)

                navView.getMenu().removeGroup(id_dyn_sub.get(i));
            for(int i=0;i<id_dyn_sub.size();i++)
                navView.getMenu().removeItem(id_dyn_sub.get(i));
            Log.v("MainActivity", Integer.toString(id_dyn.size()));
            id_dyn.clear();
            id_dyn_sub.clear();
            Log.v("MainActivity", contents.get(0).getDay());
            Log.v("MainActivity", Integer.toString(id_dyn.size()));
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    MessageFragment.newInstance(user,contents)).commit();
            addMenuItemInNaviMenuDrawer(contents);
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int check = 0;

        Log.v("content htmls", contents.get(0).getHtml());
        Log.v("content htmls", contents.get(1).getHtml());
        Log.v("content htmls", contents.get(2).getHtml());
        if(item.getItemId() == R.id.nav_time)
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    MessageFragment.newInstance(user,contents)).commit();


        for(int i = 0;i<contents.size();i++)
        {
            if (contents.get(i).getIntDay() == 0){//첫째날일때
                Log.v("contentID", Integer.toString(id_dyn.size()));
                Log.v("itemID", Integer.toString(item.getItemId()));
                if(contents.get(i).getItemId() == item.getItemId()) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            WebFragment.newInstance(contents.get(i).getHtml())).commit();
                    Log.v("content", Integer.toString(contents.get(i).getItemId()));
                    Log.v("contenthtml",contents.get(i).getHtml());
                }
                
                check++;
            }
        }

        for(int i = 0;i<contents.size();i++)
        {
            if (contents.get(i).getIntDay() == 1){//둘째날일때
                if(contents.get(i).getItemId() == item.getItemId())
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            WebFragment.newInstance(contents.get(i).getHtml())).commit();



                check++;
            }
        }

        for(int i = 0;i<contents.size();i++)
        {
            if (contents.get(i).getIntDay() == 2){//셋째날일때
                if(contents.get(i).getItemId() == item.getItemId())
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            WebFragment.newInstance(contents.get(i).getHtml())).commit();


                check++;
            }
        }

        for(int i = 0;i<contents.size();i++)
        {
            if (contents.get(i).getIntDay() == 3){//마지막날일때
                if(contents.get(i).getItemId() == item.getItemId())
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            WebFragment.newInstance(contents.get(i).getHtml())).commit();


                check++;
            }
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {//fragment close
            drawer.closeDrawer(GravityCompat.START);

        } else {//make first fragment
            super.onBackPressed();
        }
    }
    private void addMenuItemInNaviMenuDrawer(ArrayList<Content> contents) {
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);

        Menu menu = navView.getMenu();
        Menu submenu;

        int id = 50;
        int _id = 200;
        int check = 0;

        submenu = menu.addSubMenu(_id, _id, 0,"첫째 날");


            for(int i = 0;i<contents.size();i++)
        {
            if (contents.get(i).getIntDay() == 0){

                submenu.add(0,id,submenu.FIRST,contents.get(i).getName()).setIcon(R.mipmap.ic_firstday);//example, need to add
                contents.get(i).setItemId(id);
                id_dyn.add(id);
                Log.v("itemId", Integer.toString(submenu.size()));
                check++;
                id++;
            }
        }

        if(check>0)
            id_dyn_sub.add(_id);

        check = 0;
        submenu = menu.addSubMenu(_id, _id, 0,"둘째 날");

        for(int i = 0;i<contents.size();i++)
        {
            if (contents.get(i).getIntDay() == 1){

                submenu.add(0,id,submenu.FIRST,contents.get(i).getName()).setIcon(R.mipmap.ic_firstday);//example, need to add
                contents.get(i).setItemId(id);
                id_dyn.add(id);
                check++;
                id++;
            }
        }Log.v("SecondDay",Integer.toString(check));

        if(check>0)
            id_dyn_sub.add(_id);


        check = 0;
        submenu = menu.addSubMenu(_id, _id, 0,"셋째 날");
        for(int i = 0;i<contents.size();i++)
        {
            if (contents.get(i).getIntDay() == 2){

                submenu.add(0,id,submenu.FIRST,contents.get(i).getName()).setIcon(R.mipmap.ic_firstday);//example, need to add
                contents.get(i).setItemId(id);
                id_dyn.add(id);
                check++;
                id++;
            }
        }Log.v("ThirdDay",Integer.toString(check));

        if(check>0)
            id_dyn_sub.add(_id);




        check = 0;
        submenu = menu.addSubMenu(_id, _id, 0,"마지막 날");
        for(int i = 0;i<contents.size();i++)
        {
            if (contents.get(i).getIntDay() == 3){

                submenu.add(0,id,submenu.FIRST,contents.get(i).getName()).setIcon(R.mipmap.ic_firstday);//example, need to add
                contents.get(i).setItemId(id);
                id_dyn.add(id);
                check++;
            }
        }Log.v("lastDay",Integer.toString(check));
        if(check>0)
            id_dyn_sub.add(_id);




        navView.invalidate();

    }
}
