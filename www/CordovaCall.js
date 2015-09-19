var CordovaCall = {
    getCallState: function (successCallback, errorCallback) {
        errorCallback = errorCallback || this.errorCallback;
        cordova.exec(successCallback, errorCallback, 'CordovaCall', 'getCallState', []);
    },

    errorCallback: function() {
        console.log("WARNING: CordovaCall errorCallback not implemented");
    }
};

module.exports = CordovaCall;
