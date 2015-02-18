(function() {
    var onFigureOwnedClick = function() {
        var container = $(this).closest('div.figure');
        var item = container.data('item');
        
        item.setOwned(!item.isOwned()).done(function(item) {
            container.data('item', item);
            container.toggleClass('owned', item.isOwned()).toggleClass('not-owned', !item.isOwned());
        });
    };
    
    var ignoreGameListEntryClick = false; // set to true for a small time after a sort, so mouse-up after reorder click event is ignored
    /**
     * onGameListEntryClick
     */
    var onGameListEntryClick = function(evt) {
        if (ignoreGameListEntryClick) {
            return;
        }
        
        var game = $(evt.currentTarget).data('game');
        
        $('#figure-list-container').css('display', '');
        $('#figure-list-header').text(game.getTitle());
        
        skyCol.item.list(game.getId()).done(function(items) {
            var figureList = $('#figure-list').empty();
            items.forEach(function(item) {
                figureList.append( 
                    $('<li />').append(
                        $('<div />', {
                            'class': 'figure ' + (item.isOwned() ? '' : ' not-') + 'owned'
                        }).append(
                            $('<span />', {'class': 'figure-name'}).text(item.getTitle())
                        ).append(
                            $('<img />', {'src': item.getImgUrl()})
                        ).append(
                            $('<span />', {'class': 'figure-owned'}).click(onFigureOwnedClick).mouseover(function() { $(this).closest('div.figure').addClass('highlight') }).mouseout(function() { $(this).closest('div.figure').removeClass('highlight') })
                        ).data('item', item)
                    )
                );
            });
        });
    };
    
    /**
     * addGameListEntry
     */
    var addGameListEntry = function(game) {
        var li = $('<li />', {'class': 'game-list-entry'})
            .prop('title', game.getTitle())
            .data('game', game)
            .append(
                $('<img />', {'src': game.getImgUrl()})
            ).click(onGameListEntryClick);
        
        $('#game-list').append(li);
    };
    
    /**
     * onGameListSort
     */
    var onGameListSort = function(event, ui) {
        // ignore click event if just after a reorder...
        ignoreGameListEntryClick = true;
        window.setTimeout(function() { ignoreGameListEntryClick = false }, 100);
        
        var ids = [];
        $('#game-list > li').each(function(i, el) {
            ids.push($(el).data('game').id)
        });
        
        colApi.category.reorderTopLevelCategories(ids).done(function(categories) {
            console.log('saved');
            console.log(categories);
        });
    };
    
    /**
     * onAddGameButtonClick
     */
    var onAddGameButtonClick = function() {
        var newGameTitle = $('#game-list-add-title').val().trim();
        if (newGameTitle == '') {
            alert('Please select game tile');
            return;
        }
        
        colApi.category.addTopLevelCategory(newGameTitle, $('#game-list-add-file')).done(function(category) {
            addGameListEntry(category);
        });
    };
    
    skyCol.load.done(function(user) {
        if (!user) {
            document.location.href = 'login.jsp';
            return;
        }
        
        console.log(user);
        if (user.isAdmin) {
            console.log('TODO: Show admin logged page');                    
        } else {
            console.log('TODO: Show normal user logged page');                    
        }
        
        skyCol.game.list().done(function(games) {
            games.forEach(addGameListEntry);
            
            $('#game-list').sortable({'update': onGameListSort});
            
            if (user.isAdmin) {
                $('#game-list-add-container').css('display', '');
                $('#game-list-add-button').click(onAddGameButtonClick);
            }
        });
    });
})();