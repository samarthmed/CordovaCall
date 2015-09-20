package io.samarthmed.CordovaCall;


import android.content.Context;
import android.media.AudioManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.LOG;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by samarthmediratta on 19/09/15.
 */
public class CordovaCall extends CordovaPlugin {

    private CallStateListener listener;

    private void prepareListener () {
        if (listener == null) {
            listener = new CallStateListener();
            TelephonyManager TelephonyMgr = (TelephonyManager) cordova.getActivity().getSystemService(Context.TELEPHONY_SERVICE);
            TelephonyMgr.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
        }
    }

    private void toggleMicState (CallbackContext callbackContext) {

        AudioManager AudioMgr = (AudioManager) cordova.getActivity().getSystemService(Context.AUDIO_SERVICE);

        Boolean isMute = AudioMgr.isMicrophoneMute();

        String msg = "Not done anything";

        if (isMute) {
            AudioMgr.setMicrophoneMute(false);
            msg = "Not Mute";
        } else {
            AudioMgr.setMicrophoneMute(true);
            msg = "Mute";
        }

        PluginResult result = new PluginResult(PluginResult.Status.OK, msg);
        callbackContext.sendPluginResult(result);
    }

    public boolean execute(String action, JSONArray args, CallbackContext callbackContext)
            throws JSONException {

        LOG.d("CordovaCall", "Java code called");

        if (action.equals("getCallState")) {
            LOG.d("CordovaCall", "CORDOVA ACTION CALLED" + action);
            prepareListener();
            listener.setCallbackContext(callbackContext);
            return true;
        } else if (action.equals("toggleMicState")) {
            LOG.d("CordovaCall", "CORDOVA ACTION CALLED" + action);
            toggleMicState(callbackContext);
            return true;
        } else {
            return false;
        }
    }


    private class CallStateListener extends PhoneStateListener {

        CallbackContext callbackContext;

        void setCallbackContext (CallbackContext callbackContext) {
            this.callbackContext = callbackContext;
        }

        public void onCallStateChanged(int state , String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            if (callbackContext == null) return;

            String msg = "";

            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE:
                    msg = "IDLE";
                    break;

                case TelephonyManager.CALL_STATE_OFFHOOK:
                    msg = "OFFHOOK";
                    break;

                case TelephonyManager.CALL_STATE_RINGING:
                    msg = "RINGING";
                    break;
            }

            PluginResult result = new PluginResult(PluginResult.Status.OK, msg);
            result.setKeepCallback(true);

            callbackContext.sendPluginResult(result);
        }
    }
}
