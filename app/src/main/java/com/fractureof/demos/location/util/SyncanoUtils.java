package com.fractureof.demos.location.util;

import android.util.Log;

import com.google.gson.JsonObject;
import com.syncano.library.api.Response;
import com.syncano.library.choice.TraceStatus;
import com.syncano.library.data.CodeBox;
import com.syncano.library.data.Trace;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static junit.framework.Assert.assertTrue;

/**
 * Created by tyler on 20/05/2016.
 */
public class SyncanoUtils {

    public static JSONArray queryCodeBox(int cbxId, JsonObject input, String dbgSubject) {
        return queryCodeBox(cbxId,input,dbgSubject,5000,100);
    }

    public static JSONArray queryCodeBox(int cbxId, JsonObject input, String dbgSubject,
                                  int timeoutMillis, int trySleepDurMillis) {
        CodeBox cbx = new CodeBox(cbxId);
        Response<Trace> response = cbx.run(input);
        if (response.isSuccess()) {
            Log.d("loading " + dbgSubject,"Success");
            Trace trace = cbx.getTrace();
            long start = System.currentTimeMillis();
            // wait until codebox finishes execution
            while (System.currentTimeMillis() - start < timeoutMillis && trace.getStatus() != TraceStatus.SUCCESS) {
                assertTrue(trace.fetch().isSuccess());
                try {
                    Thread.sleep(trySleepDurMillis);
                } catch (InterruptedException e) {
                    Log.d("loading "+dbgSubject,String.format("Interrupted. %s",e.getStackTrace().toString()));
                    e.printStackTrace();
                }
            }
            try {
                return new JSONArray(trace.getOutput());
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        } else {
            Log.d("loading date plans",String.format("Error: %s", response.getError()));
        }
        return null;
    }
}
