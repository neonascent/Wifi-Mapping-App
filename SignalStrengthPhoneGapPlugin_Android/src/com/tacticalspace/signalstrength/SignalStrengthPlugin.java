/**
 * 
 */
package com.tacticalspace.signalstrength;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

// things I need
import android.util.Log;
import android.content.Context;
import android.net.NetworkInfo.DetailedState;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiInfo; // public String getSSID (),  public int getRssi ()
import android.telephony.NeighboringCellInfo;
import android.telephony.ServiceState; // operator name public String getOperatorAlphaLong ()
import android.telephony.SignalStrength; // public int getCdmaDbm (), public int getEvdoDbm (), public int getGsmSignalStrength ()
import android.telephony.TelephonyManager;

// or just .toString() them all??

import com.phonegap.api.Plugin;
import com.phonegap.api.PluginResult;
import com.phonegap.api.PluginResult.Status;

/**
 * @author User
 *
 */
public class SignalStrengthPlugin extends Plugin {

	/* (non-Javadoc)
	 * @see com.phonegap.api.Plugin#execute(java.lang.String, org.json.JSONArray, java.lang.String)
	 */
	@Override
	public PluginResult execute(String action, JSONArray data, String callbackId) {
		Log.d("SignalStrengthPlugin", "Plugin Called");
		PluginResult result = null;
		
		// important difference for Phonegap 1.5!  http://simonmacdonald.blogspot.com.au/2012/04/updates-to-my-phonegap-plugins.html
	try {
    		
	        // get Wifi Data
	        JSONObject wifiData = new JSONObject();
	        WifiManager wm = (WifiManager) this.ctx.getSystemService(Context.WIFI_SERVICE);
	        WifiInfo wi = wm.getConnectionInfo();
	        if ((wi != null) || (WifiInfo.getDetailedStateOf(wi.getSupplicantState()) != DetailedState.IDLE) ) {
				wifiData.put("State", "ACTIVE");
	        	wifiData.put("RSSI", wi.getRssi());
	        	Log.d("SignalStrengthPlugin", "RSSI =" + wi.getRssi());
				wifiData.put("SSID", wi.getSSID());
	        } else {
	        	wifiData.put("State", "IDLE");
	        }
	        
	        
			// get network
	        // having to get cell from NeighboringCellInfo list, as we can't bind to Signal Strength Change listener cause we are one-off
	        // we'll just list all of them then
	        JSONObject telephonyData = new JSONObject();
	        JSONObject cellsData = new JSONObject();
	        TelephonyManager tm = (TelephonyManager) this.ctx.getSystemService(Context.TELEPHONY_SERVICE);
			List<NeighboringCellInfo> n = tm.getNeighboringCellInfo();
			
	        int rss = 0;
	        int cid = 0;
		    for (NeighboringCellInfo nci : n)
		    {
		    	rss = -113 + 2*nci.getRssi();
		        cellsData.put("CellID", nci.getCid());
		        cellsData.put("RSSI", rss);
		    }
	        telephonyData.put("cells", cellsData);
	        // for later
	        // import android.telephony.ServiceState; // operator name public String getOperatorAlphaLong ()
	 	    // import android.telephony.SignalStrength; // public int getCdmaDbm (), public int getEvdoDbm (), public int getGsmSignalStrength ()
	        
	        // results!
	        JSONObject signalstrengthData = new JSONObject();
	        signalstrengthData.put("wifi",  wifiData);
	        signalstrengthData.put("cell",  telephonyData);
	        Log.d("SignalStrengthPlugin", "Returning "+ signalstrengthData.toString());
	        result = new PluginResult(Status.OK, signalstrengthData);
	
			return result;
		} catch (JSONException e) {
			result = new PluginResult(Status.ERROR);

			return result;
		}
		
	}
	
	


}
