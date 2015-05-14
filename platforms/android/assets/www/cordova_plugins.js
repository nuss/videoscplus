cordova.define('cordova/plugin_list', function(require, exports, module) {
module.exports = [
    {
        "file": "plugins/nl.sylvain.cordova.osc/www/OSCListener.js",
        "id": "nl.sylvain.cordova.osc.OSCListener",
        "clobbers": [
            "window.OSCListener"
        ]
    },
    {
        "file": "plugins/nl.sylvain.cordova.osc/www/OSCSender.js",
        "id": "nl.sylvain.cordova.osc.OSCSender",
        "clobbers": [
            "window.OSCSender"
        ]
    },
    {
        "file": "plugins/com.mediamatrixdoo.keepscreenon/www/keepscreenon.js",
        "id": "com.mediamatrixdoo.keepscreenon.keepscreenon",
        "clobbers": [
            "keepscreenon"
        ]
    },
    {
        "file": "plugins/org.basislager.videocapture/www/VideoCapture.js",
        "id": "org.basislager.videocapture.VideoCapture",
        "clobbers": [
            "window.VideoCapture"
        ]
    }
];
module.exports.metadata = 
// TOP OF METADATA
{
    "nl.sylvain.cordova.osc": "0.1.0",
    "com.mediamatrixdoo.keepscreenon": "1.0.1",
    "org.basislager.videocapture": "0.0.1"
}
// BOTTOM OF METADATA
});