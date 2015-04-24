window.getFileFromURI = function(uri, successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, "GetFilename", "get", [uri]);
}