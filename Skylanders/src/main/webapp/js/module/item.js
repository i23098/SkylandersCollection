(function(){
    function Item(item, isItemOwned) {
        var id = item.id;
        var title = item.title;
        var extra = item.extra;
        var gameId = item.categoryId;
        var isOwned = isItemOwned;
        
        this.getId = function() {
            return id;
        };
        
        this.getTitle = function() {
            return title;
        };
        
        this.getExtra = function() {
            
        };
        
        this.getGameId = function() {
            return gameId;
        };
        
        this.isOwned = function() {
            return isOwned;
        }
        
        this.setOwned = function(isItemOwned) {
            var dfd = $.Deferred();
            
            var _this = this;
            colApi.itemOwnership.set(gameId, id, isItemOwned).done(function(isItemOwned) {
                isOwned = isItemOwned;
                
                dfd.resolve(_this);
            }); // TODO: fail!
            
            return dfd.promise();
        }
        
        this.getImgUrl = function() {
            return colApi.item.getImgUrl(gameId, id);
        };
    }
    
    skyCol.module.Item = function(currentUser) {
        var user = currentUser;
        
        this.list = function(gameId) {
            var dfd = $.Deferred();
            
            $.when(
                colApi.item.list(gameId),
                user ? colApi.itemOwnership.list(gameId) : []
            ).done(function(items, ownedItems) {
                items = items.map(function(item) {
                    return new Item(item, (ownedItems.indexOf(item.id) != -1));
                });
                
                dfd.resolve(items);
            }); // TODO: fail!
            
            return dfd.promise();
        };
    };
})();
