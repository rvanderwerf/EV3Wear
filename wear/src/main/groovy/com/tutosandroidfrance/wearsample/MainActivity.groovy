package com.tutosandroidfrance.wearsample

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.wearable.view.BoxInsetLayout
import android.support.wearable.view.DotsPageIndicator
import android.support.wearable.view.GridViewPager
import android.widget.TextView
import com.arasthel.swissknife.SwissKnife
import com.arasthel.swissknife.annotations.InjectView
import com.arasthel.swissknife.annotations.OnClick
import com.arasthel.swissknife.annotations.Profile
import com.github.florent37.Emmet

import com.tutosandroidfrance.wearprotocol.SmartphoneProtocol
import com.tutosandroidfrance.wearprotocol.WearProtocol
import groovy.transform.CompileStatic


@CompileStatic
public class MainActivity extends Activity implements WearProtocol {

    private final static String TAG = MainActivity.class.getCanonicalName()


    private Emmet emmet
    SmartphoneProtocol smartphoneProtocol

    private Context mContext

    // for some reason SwissKnife is not injecting here, see https://github.com/Arasthel/SwissKnife/issues/38
    //@InjectView(value=R.id.pager)
    //GridViewPager pager

    //@InjectView(value=R.id.page_indicator)
    //DotsPageIndicator dotsPageIndicator

    @InjectView(R.id.title)
    TextView firstTextView

    @OnClick(R.id.up)
    public void up() {
        smartphoneProtocol.up()
        firstTextView.text = "up"
    }

    @OnClick(R.id.backward)
    public void down() {
        smartphoneProtocol.down()
        firstTextView.text = "down"
    }

    @OnClick(R.id.left)
    public void left() {
        smartphoneProtocol.left()
        firstTextView.text = "left"
    }

    @OnClick(R.id.right)
    public void right() {
        smartphoneProtocol.right()
        firstTextView.text = "right"
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        SwissKnife.inject(this)



        //initialise Emmet
        emmet = new Emmet()
        emmet.onCreate(this)

        emmet.registerReceiver(WearProtocol.class, this)
        smartphoneProtocol = emmet.createSender(SmartphoneProtocol.class)

        //smartphoneProtocol.hello() //send the hello message
    }

    @Override
    protected void onDestroy() {
        super.onDestroy()
        emmet.onDestroy() //don't forget
    }

    //send to the phone
    @Override
    public void onAndroidVersionsReceived(String androidVersions) {
       // if (androidVersions != null)
        //startMainScreen()
            //firstTextView.text = "command sent!"
    }


}
