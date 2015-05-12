cordova.define("org.basislager.videocapture.VideoCapture", function(require, exports, module) { // var exec = require('cordova/exec');

// exports.coolMethod = function(arg0, success, error) {
//     exec(success, error, "VideoCapture", "coolMethod", [arg0]);
// };

var VideoCapture = function(device) {
	this.device = device;
}

VideoCapture.prototype.startCapture = function(successCallback, errorCallback){
	cordova.exec(successCallback, errorCallback, "VideoCapture", "startCapture", [this.device]);
}

VideoCapture.prototype.stopCapture = function(successCallback){
	cordova.exec(successCallback, function(err){
		console.log(err);
	}, "VideoCapture", "stopCapture", [this.device]);
}

module.exports = VideoCapture;
});
