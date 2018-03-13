package com.decode.gallery;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class GalleryActivity extends AppCompatActivity implements ICallback, View.OnClickListener {

    public static final int PREVIEW_REQUEST_TYPE = 1;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_VIDEO_CAPTURE = 1;

    //private Button mPreviewButton;
    //private TextView resultText;
    private int resultValue = 0;
    private TabLayout tab;
    private ViewPager viewPager;

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigation;

    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
//        mPreviewButton = (Button) findViewById(R.id.button_preview);
//        mPreviewButton.setOnClickListener(this);
//        resultText = (TextView) findViewById(R.id.text_result);
        tab = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.pager);
        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigation = findViewById(R.id.drawer_navigation);
        fab=findViewById(R.id.fab_button);

        fab.setOnClickListener(this);



        navigation.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem mi) {
                        // set item as selected to persist highlight
                        mi.setChecked(true);
                        drawer.closeDrawers();
                        if (mi.getItemId()==R.id.action_photo) {
                            viewPager.setCurrentItem(0);
                        } else if (mi.getItemId()==R.id.action_video) {
                            viewPager.setCurrentItem(1);
                        }

                        return true;
                    }
                });



        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.menu_icon);

        if (savedInstanceState != null) {
            resultValue = savedInstanceState.getInt("result", 0);
            //resultText.setText("Result" + resultValue);
        }

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                GalleryFragment fragment = new GalleryFragment();
                Bundle arguments = new Bundle();
                arguments.putInt("type", position);
                fragment.setArguments(arguments);
                return fragment;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                if (position == 0) return "Photos";
                else return "Videos";
            }

            @Override
            public int getCount() {
                return 2;
            }
        });
        tab.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.gallery_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawer.openDrawer(GravityCompat.START);
        }
        if (item.getItemId() == R.id.action_photo) {
            viewPager.setCurrentItem(0);
            return true;
        } else if (item.getItemId() == R.id.action_video) {
            viewPager.setCurrentItem(1);
            return true;
        }
        return false;
    }


//    @Override
//    public void onClick(View viewPager) {
//        if (viewPager.getId()==R.id.button_preview) {
//            Intent intent = new Intent(this, PreviewActivity.class);
//            startActivityForResult(intent,PREVIEW_REQUEST_TYPE);
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        if (data.getAction()==MediaStore.ACTION_IMAGE_CAPTURE) {
//
//        } else if (data.getAction()==MediaStore.ACTION_VIDEO_CAPTURE){
//
//        }

        //resultText.setText("Result" + resultCode);
//resultValue=resultCode;
       // if (requestCode == PREVIEW_REQUEST_TYPE) viewPager.setCurrentItem(resultCode);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("result", resultValue);
    }

    @Override
    public void preview(Media media) {
        Intent intent = new Intent(this, PreviewActivity.class);
        intent.putExtra("color", media.getColor());
        startActivityForResult(intent, PREVIEW_REQUEST_TYPE);
    }

    @Override
    public void onClick(View view) {
        String message = "Open Camera";
        int duration = Snackbar.LENGTH_SHORT;

        Snackbar snackbar = Snackbar.make(viewPager, message, duration);
        snackbar.setAction("Image", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getApplicationContext();
                CharSequence text = "Open camera!";
                int toastDuration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, toastDuration);
                toast.show();
            }
        });
        snackbar.setActionTextColor(Color.RED);
        snackbar.show();

        if (viewPager.getCurrentItem()==0) {
            dispatchTakePictureIntent();
        } else {
            dispatchTakeVideoIntent();
        }

    }

    private void dispatchTakePictureIntent() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void dispatchTakeVideoIntent() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
    }
}


