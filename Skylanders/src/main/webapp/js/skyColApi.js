var skyCol = {};

(function() {
    var loadDfd = $.Deferred();
    skyCol.load = loadDfd.promise();
    
    var scripts = [
       'js/mozillaPolyfill.js'
    ];
    
    var loader = $LAB;
    for (var i=0; i<scripts.length; i++) {
        loader = loader.script(scripts[i]);
    }
    
    loader.wait(function(){
        var origItemList = colApi.item.list;
        
        colApi.user.currentUser().done(function(user) {
            $(document).ready(function() {
                loadDfd.resolve(user);
            });
        }).fail(function() {
            // TODO: check why fail?
            $(document).ready(function() {
                loadDfd.resolve(null);
            });
        });
    });
})();