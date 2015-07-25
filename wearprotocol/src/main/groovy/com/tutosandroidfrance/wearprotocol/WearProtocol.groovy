package com.tutosandroidfrance.wearprotocol

import groovy.transform.CompileStatic;

import java.util.List;

/**
 * Created by florentchampigny on 02/05/15.
 */
@CompileStatic
public interface WearProtocol {
    void onAndroidVersionsReceived(List<AndroidVersion> androidVersions)
}
