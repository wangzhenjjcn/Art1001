/*
ART JavaScript Library for SketchUp WebDialogs
Version:      1.0.0
Date:         08.07.2016
*/

/**
 * @namespace
 */
var ART = function (ART) {
    ART.bindEventListener = function (type, tag, callback) {
        $(document).off(type, tag);
        $(document).on(type, tag, callback);
    };
    ART.IS_OSX=function(){
        var sUserAgent = navigator.userAgent; 
        var isWin =  (navigator.platform == "Win64") || (navigator.platform == "Win32") || (navigator.platform == "Windows"); 
        if (isWin){
            return false;
        }else{
            return true;
        }
    };
    ART.UI = function (self) {
        /* Ensure links are opened in the default browser. This ensures that the
         * WebDialog doesn't replace the content with the target URL.
         */
        self.redirect_links = function () {
            $(document).on('click', 'a[href]', function () {
                // Detect skp: actions and don't intercept them.
                if (this.href.indexOf("skp:") != 0 && this.href.indexOf("http:") == 0) {
                    var data = {
                        url: this.href
                    };
                    ART.Sketchup.callback('Window.open_url', data);
                    return false;
                }
            });
        };
        self.messageBox=function(data){
            self.showMsg(data.message,data.type);
        };
        self.showMsg = function (msg, type) {
            var tclass = type == 1 ? 'mgAlert-error' : 'mgAlert-success',
                dom = $('#mgAlert')[0];
            dom.setAttribute('class', 'mgAlert ' + tclass + ' mshow');
            dom.innerHTML = msg;
            setTimeout(function () {
                dom.setAttribute('class', 'mgAlert');
                dom.innerHTML = '';
            }, 2000);
        };
        /* Disables text selection on elements other than input type elements where
         * it makes sense to allow selections. This mimics native windows.
         */
        self.disable_select = function () {
            $(document).on('mousedown selectstart', function (e) {
                return $(e.target).is('input, textarea, select, option');
            });
        };

        /* Disables the context menu with the exception for textboxes in order to
         * mimic native windows.
         */
        self.disable_context_menu = function () {
            $(document).on('contextmenu', function (e) {
                return $(e.target).is('input[type=text], textarea');
            });
        }
        // self.focus_window = function(){
        //     $(document).mouseenter(function() {
        //         window.focus();
        //     });
        // }
        return self;
    }(ART.UI || {}); // UI

    ART.Bridge = function (self) {
        // Creates a hidden textarea element used to pass data from JavaScript to
        // Ruby. Ruby calls UI::WebDialog.get_element_value to fetch the content and
        // parse it as JSON.
        // This avoids many issues in regard to transferring data:
        // * Data can be any size.
        // * Avoid string encoding issues.
        // * Avoid evaluation bug in SketchUp when the string has a single quote.
        self.init = function () {
            var $bridge = $("<textarea id='SU_BRIDGE'></textarea>");
            $bridge.hide();
            $("body").append($bridge);
        };
        self.set_data = function (data) {
            var $bridge = $("#SU_BRIDGE");
            $bridge.text("");
            if (data !== undefined) {
                var json = JSON.stringify(data);
                $bridge.text(json);
            }
        }
        return self;
    }(ART.Bridge || {}); // Bridge

    ART.Sketchup = function (self) {
        self.callback = function (event_name, data) {
            // Defer with a timer in order to allow the UI to update.
            setTimeout(function () {
                ART.Bridge.set_data(data);
                window.location = "skp:callback@" + event_name;
            }, 0);
        }
        return self;
    }(ART.Sketchup || {}); // Sketchup

    return ART;
}(ART || {}); // end @module ART

/*******************************************************************************
 *
 * initializer
 *
 ******************************************************************************/
window.clickEvent ='click';
window.changeEvent ='change';
window.keyDown = 'keydown';
$(function () {
    ART.Bridge.init();
    //ART.Sketchup.callback("Window.ready");
    //ART.UI.disable_context_menu();
    //ART.UI.focus_window();
});
/**
 * [onerror]        
 */
window.onerror = function (message, file, line, column, error) {
    try {
        var backtrace = [];
        if (error && error.stack) {
            backtrace = String(error.stack).split("\n");
        } else {
            backtrace = [file + ':' + line + ':' + column];
        }
        var data = {
            'message': message,
            'backtrace': backtrace
        };
        ART.Sketchup.callback('Window.js_error', data);
    } catch (error) {
        debugger;
        throw error;
    }
};