package com.cp.musiccontrol;

import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;


import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.Nullable;

public class RNMusicControl extends ReactContextBaseJavaModule implements LifecycleEventListener {
    private static final String TAG = RNMusicControl.class.getSimpleName();
    private static ReactApplicationContext mContext;
    private static final String EVENT_CONTROL = TAG;

    public RNMusicControl(ReactApplicationContext reactContext) {
        super(reactContext);
        mContext = reactContext;
        reactContext.addLifecycleEventListener(this);
        sendEventTOJs();
    }

    @Nullable
    @Override
    public Map<String, Object> getConstants() {
        Map<String,Object> map = new HashMap<>();
        map.put("STATE_PLAY", PlaybackStateCompat.STATE_PLAYING);
        map.put("STATE_PAUSED", PlaybackStateCompat.STATE_PAUSED);
        map.put("STATE_STOPPED", PlaybackStateCompat.STATE_STOPPED);
        map.put("STATE_SKIPPING_TO_NEXT", PlaybackStateCompat.STATE_SKIPPING_TO_NEXT);
        map.put("STATE_SKIPPING_TO_PREVIOUS", PlaybackStateCompat.STATE_SKIPPING_TO_PREVIOUS);
        return map;
    }

    @Override
    public String getName() {
        return TAG;
    }

    @ReactMethod
    public void testPrint(String name, ReadableMap info) {
        Log.i(TAG, name);
        Log.i(TAG, info.toString());
    }
    @ReactMethod
    public void getNativeClass(Callback callback) {
        callback.invoke("BGNativeExampleModule");
    }
    @ReactMethod
    public void testPromises(Boolean isResolve, Promise promise) {
        if(isResolve) {
            promise.resolve(isResolve.toString());
        }
        else {
            promise.reject(isResolve.toString(),"ERROR");
        }
    }

    @Override
    public void onHostResume() {
        Log.i(TAG,"onHostResume");
    }

    @Override
    public void onHostPause() {
        Log.i(TAG,"onHostPause");
    }

    @Override
    public void onHostDestroy() {
        Log.i(TAG,"onHostDestroy");
    }

    private void sendEventTOJs(){
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                WritableMap params = Arguments.createMap();
                params.putString("name","cp");
                mContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                        .emit(EVENT_CONTROL,params);
            }
        };
        Timer timer = new Timer();
        timer.schedule(task,1*1000,1*1000);
    }
}
