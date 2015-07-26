package com.tutosandroidfrance.wearsample

import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.github.florent37.EmmetWearableListenerService
import com.github.florent37.davinci.daemon.DaVinciDaemon
import com.google.android.gms.wearable.MessageEvent
import com.tutosandroidfrance.wearprotocol.SmartphoneProtocol
import com.tutosandroidfrance.wearprotocol.WearProtocol
import groovy.json.JsonSlurper
import groovy.transform.CompileStatic



@CompileStatic
public class WearService extends EmmetWearableListenerService implements SmartphoneProtocol {

    WearProtocol wearProtocol
    String baseURL = "http://192.168.1.181"
    int directionDuration = 1000

    @Override
    public void onCreate() {
        super.onCreate()

        //initialized the data service
        getEmmet().registerReceiver(SmartphoneProtocol.class, this)

        //initializes sends data to the watch
        wearProtocol = getEmmet().createSender(WearProtocol.class)
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        baseURL = SP.getString("baseURL", "http://192.168.1.181");

        directionDuration = Integer.parseInt(SP.getString("directionDuration","1000"));
    }

    //when the watch sends a hello message to the  smartphone
    @Override
    public void up() {
        def moveUri = "${baseURL}/api/forward/${directionDuration}"

        def slurper = new JsonSlurper()

        def randomJoke = slurper.parse(new URL("http://api.icndb.com/jokes/random"))



        wearProtocol.onAndroidVersionsReceived(randomJoke.toString())


    }
    @Override
    public void down() {

        def slurper = new JsonSlurper()
        def moveUri = "${baseURL}/api/backward/${directionDuration}"
        def response = slurper.parse(new URL(moveUri))
        wearProtocol.onAndroidVersionsReceived(response.toString())


    }
    @Override
    public void left() {

        def slurper = new JsonSlurper()
        def moveUri = "${baseURL}/api/left/${directionDuration}"
        def response = slurper.parse(new URL(moveUri))
        wearProtocol.onAndroidVersionsReceived(response.toString())


    }
    @Override
    public void right() {

        def slurper = new JsonSlurper()
        def moveUri = "${baseURL}/api/right/${directionDuration}"
        def response = slurper.parse(new URL(moveUri))
        wearProtocol.onAndroidVersionsReceived(response.toString())


    }




    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        super.onMessageReceived(messageEvent)

        //allows DaVinciDaemon listen to messages@
        DaVinciDaemon.with(getApplicationContext()).handleMessage(messageEvent)
    }
}
