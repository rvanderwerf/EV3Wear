package com.tutosandroidfrance.wearsample

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.wearable.view.DotsPageIndicator
import android.support.wearable.view.GridViewPager
import com.arasthel.swissknife.SwissKnife
import com.arasthel.swissknife.annotations.InjectView
import com.github.florent37.Emmet
import com.tutosandroidfrance.wearprotocol.AndroidVersion

import com.tutosandroidfrance.wearprotocol.SmartphoneProtocol
import com.tutosandroidfrance.wearprotocol.WearProtocol
import groovy.transform.CompileStatic

import java.util.ArrayList
import java.util.List

@CompileStatic
public class MainActivity extends Activity implements WearProtocol {

    private final static String TAG = MainActivity.class.getCanonicalName()

    private GridViewPager pager
    private DotsPageIndicator dotsPageIndicator

    //la liste des éléments à afficher
    private List<Map> elementList = new ArrayList<>()

    private Emmet emmet

    private Context mContext

    //@InjectView(value=R.id.pager)
    //GridViewPager pager

    //@InjectView(value=R.id.page_indicator)
    //DotsPageIndicator dotsPageIndicator



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //SwissKnife.inject(this)

        pager = (GridViewPager) findViewById(R.id.pager)
        dotsPageIndicator = (DotsPageIndicator) findViewById(R.id.page_indicator)
        dotsPageIndicator.setPager(pager)

        //initialise Emmet
        emmet = new Emmet()
        emmet.onCreate(this)

        emmet.registerReceiver(WearProtocol.class, this)
        SmartphoneProtocol smartphoneProtocol = emmet.createSender(SmartphoneProtocol.class)

        smartphoneProtocol.hello() //send the hello message
    }

    @Override
    protected void onDestroy() {
        super.onDestroy()
        emmet.onDestroy() //don't forget
    }

    //send to the phone
    @Override
    public void onAndroidVersionsReceived(List<Map> androidVersions) {
        if (androidVersions != null && this.elementList != null && this.elementList.isEmpty()) {
            this.elementList.addAll(androidVersions)
            startMainScreen()
        }
    }

    public void startMainScreen() {
        //consider performing the actions in the graphics UIThread
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //display here in viewpager

                if (pager != null && pager.getAdapter() == null)
                    pager.setAdapter(new ElementGridPagerAdapter(MainActivity.this, elementList, getFragmentManager()))
            }
        })
    }
}
