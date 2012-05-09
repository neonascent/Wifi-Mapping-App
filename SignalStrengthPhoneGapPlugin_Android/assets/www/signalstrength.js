
/**
 *  
 * @return Instance of SignalStrength
 */
var SignalStrength = function() { 

}

/**
 * @param directory The directory for which we want the listing
 * @param successCallback The callback which will be called when directory listing is successful
 * @param failureCallback The callback which will be called when directory listing encouters an error
 */
SignalStrength.prototype.list = function(directory,successCallback, failureCallback) {

	    // Get info
    //return PhoneGap.exec(successCallback, failureCallback, "Device", "getDeviceInfo", []);
    return PhoneGap.exec(successCallback,    //Callback which will be called when plugin is successful
    					failureCallback,     //Callback which will be called when plugin encounters an error
    					'SignalStrengthPlugin',  //Telling PhoneGap that we want to run "SignalStrengthPlugin" Plugin
    					'getDeviceInfo',              //Telling the plugin, which action we want to perform
    					[]);        //Passing a list of arguments to the plugin, not used 
};




/**
 * <ul>
 * <li>Register the Signal Strength Javascript plugin.</li>
 * <li>Also register native call which will be called when this plugin runs</li>
 * </ul>
 */
PhoneGap.addConstructor(function() {
	//Register the javascript plugin with PhoneGap
	PhoneGap.addPlugin('signalStrength', new SignalStrength());
});