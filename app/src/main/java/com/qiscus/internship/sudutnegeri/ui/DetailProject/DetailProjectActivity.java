package com.qiscus.internship.sudutnegeri.ui.DetailProject;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;

import com.qiscus.internship.sudutnegeri.R;

public class DetailProjectActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private FloatingActionButton fab;
    AlphaAnimation alphaAnim;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_project);

        




        final FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        tabLayout = (TabLayout) findViewById(R.id.tlDetailProject);

        InfoFragment info = new InfoFragment();

        transaction.replace(R.id.rlFrameContent, info);
        transaction.commit();
        getSupportActionBar();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int nama = tab.getPosition();
                switch (nama){
                    case 0:
                        InfoFragment info1 = new InfoFragment();
                        fragmentManager.beginTransaction()
                                .replace(R.id.rlFrameContent, info1)
                                .addToBackStack("info1")
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                .commit();
                        getSupportActionBar();
                        break;
                    case 1:
                        FotoFragment foto = new FotoFragment();
                        fragmentManager.beginTransaction()
                                .replace(R.id.rlFrameContent, foto)
                                .addToBackStack("foto")
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                .commit();
                        getSupportActionBar();
                        break;
                    case 2:
                        TargetFragment target = new TargetFragment();
                        fragmentManager.beginTransaction()
                                .replace(R.id.rlFrameContent, target)
                                .addToBackStack("target")
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                .commit();
                        getSupportActionBar();
                        break;
                    default:
                        return;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

//       FloatingActionButton fab= (FloatingActionButton) findViewById(R.id.fab);
//       fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), "Nanti ini mengarah ke chat activity", Toast.LENGTH_LONG).show();
//
//            }
//       });
    }


}
