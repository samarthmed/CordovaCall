var CordovaCall = {
  getCallState: function(successCallback, errorCallback) {
    errorCallback = errorCallback || this.errorCallback;
    cordova.exec(successCallback, errorCallback, 'CordovaCall', 'getCallState', []);
  },

  toggleMicState: function(successCallback, errorCallback) {
    console.log("CORDOVACALL plugin toggleMicState");
    errorCallback = errorCallback || this.errorCallback;
    cordova.exec(successCallback, errorCallback, 'CordovaCall', 'toggleMicState', []);
  },

  makeCall: function (number, successCallback, errorCallback) {
    console.log("CORDOVACALL make call");
    errorCallback = errorCallback || this.errorCallback;
    cordova.exec(null, errorCallback, 'CordovaCall', 'makeCall', [number]);
  },

  errorCallback: function() {
    console.log("WARNING: CordovaCall errorCallback not implemented");
  }

};

module.exports = CordovaCall;
