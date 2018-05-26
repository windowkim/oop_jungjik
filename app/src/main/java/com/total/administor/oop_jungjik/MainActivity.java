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
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    ArrayList<Integer> id_dyn = new ArrayList<Integer>();
    ArrayList<Integer> id_dyn_sub = new ArrayList<Integer>();

    private final int editoractivity = 0;
    private User user = new User(1,"1"," ");
    private int SelectedDay=0, Nfile=0; //
    private ArrayList<String> Days = new ArrayList<String>(); //
    private ArrayList<String> Names = new ArrayList<String>(); //
    private ArrayList<Integer> Minutes = new ArrayList<Integer>(); //
    private ArrayList<Integer> Seconds = new ArrayList<Integer>(); //
    private ArrayList<Content> contents = new ArrayList<Content>(); //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        user = (User) getIntent().getSerializableExtra("User");
        Log.v("isEditor?",Boolean.toString(user.isEditor()));
        Toast toast = Toast.makeText(getApplicationContext(), "환영합니다! " + user.getName()+ "님~", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, -500);
        toast.show();

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    Nfile = jsonResponse.getInt("Nfile"); // 파일 갯수
                    for(int i=0;i<Nfile;i++)
                    {
                        try {
                            // JSONArray Array = jsonResponse.getJSONArray("Contents1");
                            JSONObject jsonContents = jsonResponse.getJSONObject("Contents"+String.valueOf(i+1));
                            final String Name = new String  (jsonContents.getString("Name").getBytes("iso-8859-1"), "euc-kr");
                            final String Day = new String  (jsonContents.getString("Day").getBytes("iso-8859-1"), "euc-kr");
                            Names.add(new String(Name));
                            Days.add(new String(Day));
                            Minutes.add(Integer.parseInt(jsonContents.getString("Minutes")));
                            Seconds.add(Integer.parseInt(jsonContents.getString("Seconds")));
                            Content content = new Content(Names.get(i), Days.get(i), Minutes.get(i), Seconds.get(i));
                            contents.add(content);
                        }
                        catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                //navigation drawer
                Toolbar toolbar = findViewById(R.id.toolbar);
                setSupportActionBar(toolbar);

                drawer = findViewById(R.id.drawer_layout);
                NavigationView navigationView = findViewById(R.id.nav_view);
                navigationView.setNavigationItemSelectedListener(MainActivity.this);

                ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(MainActivity.this, drawer, toolbar,
                        R.string.navigation_drawer_open, R.string.navigation_drawer_close);
                drawer.addDrawerListener(toggle);
                toggle.syncState();

                Log.v("MainActivity", "memunum"+Integer.toString(navigationView.getMenu().size()));

                addMenuItemInNaviMenuDrawer(contents);


            };

        };

        GetContentsInfoRequest GetRequest = new GetContentsInfoRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(GetRequest);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    MessageFragment.newInstance(user,contents)).commit();
            // navigationView.setCheckedItem(R.id.nav_message);

        }



    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK)
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


        if(item.getItemId() == R.id.nav_time)
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    MessageFragment.newInstance(user,contents)).commit();


        for(int i = 0;i<contents.size();i++)
        {
            if (contents.get(i).getIntDay() == 0){//첫째날일때

                if(contents.get(i).getItemId() == item.getItemId()) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            WebFragment.newInstance(user,contents.get(i),i+1)).commit();

                }
                
                check++;
            }
        }

        for(int i = 0;i<contents.size();i++)
        {
            if (contents.get(i).getIntDay() == 1){//둘째날일때
                if(contents.get(i).getItemId() == item.getItemId())
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            WebFragment.newInstance(user,contents.get(i),i+1)).commit();



                check++;
            }
        }

        for(int i = 0;i<contents.size();i++)
        {
            if (contents.get(i).getIntDay() == 2){//셋째날일때
                if(contents.get(i).getItemId() == item.getItemId())
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            WebFragment.newInstance(user,contents.get(i),i+1)).commit();


                check++;
            }
        }

        for(int i = 0;i<contents.size();i++)
        {
            if (contents.get(i).getIntDay() == 3){//마지막날일때
                if(contents.get(i).getItemId() == item.getItemId())
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            WebFragment.newInstance(user,contents.get(i),i+1)).commit();


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

        Log.v("getintday", Integer.toString(contents.size()));
            for(int i = 0;i<contents.size();i++)
        {
            if (contents.get(i).getIntDay() == 0){
                Log.v("getintdayif", Integer.toString(contents.get(i).getIntDay()));
                submenu.add(0,id,submenu.FIRST,contents.get(i).getName()).setIcon(R.mipmap.ic_firstday);//example, need to add
                contents.get(i).setItemId(id);
                id_dyn.add(id);
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
