var skyCol = {
    module: {}
};

(function() {
    var loadDfd = $.Deferred();
    skyCol.load = loadDfd.promise();
    
    var scripts = [
       'js/mozillaPolyfill.js',
       'js/lib/jquery.storageapi-1.7.3.js',
       'js/module/game.js',
       'js/module/item.js'
    ];
    
    var loader = $LAB;
    for (var i=0; i<scripts.length; i++) {
        loader = loader.script(scripts[i]);
    }
    
    var init = function(user) {
        skyCol.game = new skyCol.module.Game();
        skyCol.item = new skyCol.module.Item(user);
        
        $(document).ready(function() {
            loadDfd.resolve(user);
        });
    };
    
    loader.wait(function(){
        var username = $.sessionStorage.get('skyColUsername');
        if (username && username != '') {
            var password = $.sessionStorage.get('skyColPassword');
            colApi.user.setAuthDetails(username, password).done(function(user) {
                init(user);
            }).fail(function() {
                // TODO: check why fail?
                init(null);
            });
        } else {
            init(null);
        }
    });
})();