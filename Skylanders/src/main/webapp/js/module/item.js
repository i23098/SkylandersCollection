(function(){
    function Item(item, isOwned) {
        var id = item.id;
        var title = item.title;
        var extra = item.extra ? JSON.parse(JSON.stringify(item.extra)) : {};
        var gameId = item.categoryId;
        
        this.getType = function() {
            if (extra.figure) return 'FIGURE';
            if (extra.item) return 'ITEM';
            
            return 'UNKNOWN';
        };
        
        this.getId = function() {
            return id;
        };
        
        this.getTitle = function() {
            return title;
        };
        
        var _this = this;
        (function() {
            var extraKeys = Object.keys(extra);
            for (var i = 0; i < extraKeys.length; i++) {
                (function(key) {
                    _this['get' + key[0].toUpperCase() + key.substr(1)] = function() {
                        return extra[key];
                    }
                })(extraKeys[i]);
            }
        })();
        
        this.getVariant = this.getVariant || function() {
            return null;
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
