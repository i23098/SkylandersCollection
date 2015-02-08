(function(){
    function Game(gameId, gameTitle) {
        var id = gameId;
        var title = gameTitle;
        
        this.getId = function() {
            return id;
        };
        
        this.getTitle = function() {
            return title;
        };
        
        this.getImgUrl = function() {
            return colApi.category.getImgUrl(id);
        };
    }
    
    skyCol.module.Game = function() {
        this.list = function() {
            var dfd = $.Deferred();
            
            colApi.category.getTopLevelCategories().done(function(categories) {
                var games = [];
                categories.forEach(function(category) {
                    games.push(new Game(category.id, category.title));
                });
                
                dfd.resolve(games);
            }); // TODO: fail!
            
            return dfd.promise();
        };
    };
})();