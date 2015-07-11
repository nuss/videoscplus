cordova.define("org.basislager.videocapture.VideoCapture", function(require, exports, module) { // var exec = require('cordova/exec');

var VideoCapture = function() {

}

/*
 * @param device: 0 -> front-side camera, 1 -> back-side camera
 */
VideoCapture.prototype.openCamera = function(device, successCallback, errorCallback) {
	this.device = device;
	cordova.exec(successCallback, errorCallback, "VideoCapture", "openCamera", [this.device]);
}

VideoCapture.prototype.startCapture = function(successCallback, errorCallback){
	cordova.exec(successCallback, errorCallback, "VideoCapture", "startCapture", [this.device]);
}

VideoCapture.prototype.stopCapture = function(successCallback){
	cordova.exec(successCallback, function(err){
		console.log(err);
	}, "VideoCapture", "stopCapture", [this.device]);
}

VideoCapture.prototype.setResolution = function(width, height, successCallback, errorCallback) {
	cordova.exec(successCallback, errorCallback, "VideoCapture", "setResolution", [this.device]);
}

module.exports = VideoCapture;
});
