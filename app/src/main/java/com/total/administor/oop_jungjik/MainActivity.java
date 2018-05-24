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

    private final int editoractivity = 0;
    private ArrayList<Integer> Day = new ArrayList<Integer>();
    private User user = new User(1, "1");
    private int SelectedDay = 0;
    private ArrayList<Content> contents = new ArrayList<Content>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        String s[] = {"A", "B", "C"};
        for (int i = 0; i < 3; i++) {
            contents.add(new Content(s[i]," ", 0,0));
        }
        for (int i = 1; i < 5; i++) {
            Day.add(i);
        }
      /*  Button editorbutton = (Button) findViewById(R.id.editorbutton);
        Button checkbutton = (Button) findViewById(R.id.checkbutton);
        ArrayList<Button> Daybutton = new ArrayList<Button>();
        Daybutton.add((Button) findViewById(R.id.day1button));
        Daybutton.add((Button) findViewById(R.id.day2button));
        Daybutton.add((Button) findViewById(R.id.day3button));
        Daybutton.add((Button) findViewById(R.id.day4button));
        editorbutton.setText("컨텐츠 편집");
        checkbutton.setText("컨텐츠 시청 확인");
      */  User puser = (User) getIntent().getSerializableExtra("User");
        user = puser;
        String username = user.getName();
        Toast toast = Toast.makeText(getApplicationContext(), "환영합니다! " + username + "님~", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, -500);
        toast.show();
      /*  if (!user.isEditor()) // 새내기
        {
            editorbutton.setVisibility(View.GONE);
            checkbutton.setVisibility(View.GONE);
        }

        editorbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                intent.putExtra("Contents", contents);
                MainActivity.this.startActivityForResult(intent, editoractivity);
            }
        });
        for (int i = 0; i < 4; i++) {
            final int index = i;
            Daybutton.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, ContentsArrayActivity.class);
                    intent.putExtra("Contents", contents);
                    intent.putExtra("Day", index);
                    MainActivity.this.startActivity(intent);
                }
            });
        }*/
        
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



            addMenuItemInNaviMenuDrawer(contents);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ProfileFragment()).commit();
            // navigationView.setCheckedItem(R.id.nav_message);

        }
    }

    /*protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK)
            return;
        if (requestCode == editoractivity) {
            contents = (ArrayList<Content>) data.getSerializableExtra("Contents");
        }

    }*/

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //int i = item.getItemId();
       /* if (i == R.id.nav_message) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new MessageFragment()).commit();

        } else if (i == R.id.nav_chat) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ChatFragment()).commit();

        } else if (i == R.id.nav_profile) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ProfileFragment()).commit();

        } else if (i == R.id.nav_send) {
            Toast.makeText(this, "Send", Toast.LENGTH_SHORT).show();

        } else if (i == R.id.nav_share) {
            Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();

        } else if (i == R.id.simpleWebView) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new WebFragment()).commit();

        } else*/
            


        int check = 0;

        for(int i = 0;i<contents.size();i++)
        {
            if (contents.get(i).getIntDay() == 0){//첫째날일때
                if(id_dyn.indexOf(item.getItemId()) == check)
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new WebFragment()).commit();
                
                check++;
            }
        }

        submenu = menu.addSubMenu("둘째 날");
        for(int i = 0;i<contents.size();i++)
        {
            if (contents.get(i).getIntDay() == 0){
             if(id_dyn.indexOf(item.getItemId()) == check)
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new WebFragment()).commit();
                
                check++;
            }
        }

        submenu = menu.addSubMenu("셋째 날");
        for(int i = 0;i<contents.size();i++)
        {
            if (contents.get(i).getIntDay() == 0){

                 if(id_dyn.indexOf(item.getItemId()) == check)
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new WebFragment()).commit();
                
                check++;
            }
        }

        submenu = menu.addSubMenu("마지막 날");
        for(int i = 0;i<contents.size();i++)
        {
            if (contents.get(i).getIntDay() == 0){

                 if(id_dyn.indexOf(item.getItemId()) == check)
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new WebFragment()).commit();
                
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
            //   getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
            //           new MainFragment());
        }
    }
    private void addMenuItemInNaviMenuDrawer(ArrayList<Content> contents) {
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);

        Menu menu = navView.getMenu();
        Menu submenu;


        int check = 0;
        submenu = menu.addSubMenu("첫째 날");
        for(int i = 0;i<contents.size();i++)
        {
            if (contents.get(i).getIntDay() == 0){

                submenu.add(contents.get(i).getName()).setIcon(R.drawable.ic_chat);//example, need to add
                id_dyn.add(submenu.getItem(check).getItemId());
                check++;
            }
        }

        submenu = menu.addSubMenu("둘째 날");
        for(int i = 0;i<contents.size();i++)
        {
            if (contents.get(i).getIntDay() == 0){

                submenu.add(contents.get(i).getName());
                id_dyn.add(submenu.getItem(check).getItemId());
                check++;
            }
        }

        submenu = menu.addSubMenu("셋째 날");
        for(int i = 0;i<contents.size();i++)
        {
            if (contents.get(i).getIntDay() == 0){

                submenu.add(contents.get(i).getName());
                id_dyn.add(submenu.getItem(check).getItemId());
                check++;
            }
        }

        submenu = menu.addSubMenu("마지막 날");
        for(int i = 0;i<contents.size();i++)
        {
            if (contents.get(i).getIntDay() == 0){

                submenu.add(contents.get(i).getName());
                id_dyn.add(submenu.getItem(check).getItemId());
                check++;
            }
        }




        navView.invalidate();

    }
}
