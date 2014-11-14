(function() {
    var onFigureOwnedClick = function() {
        var container = $(this).closest('div.figure');
        var item = container.data('item');
        var categoryId = container.data('categoryId');
        
        colApi.item.update(categoryId, {
            'id': item.id,
            'owned': !item.owned
        }).done(function(item) {
            container.data('item', item);
            container.toggleClass('owned', item.owned).toggleClass('not-owned', !item.owned);
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
        
        var category = $(evt.currentTarget).data('category');
        
        $('#figure-list-container').css('display', '');
        $('#figure-list-header').text(category.title);
        
        colApi.item.list(category.id).done(function(items) {
            var figureList = $('#figure-list').empty();
            items.forEach(function(item) {
                figureList.append( 
                    $('<li />').append(
                        $('<div />', {
                            'class': 'figure ' + (item.owned ? '' : ' not-') + 'owned'
                        }).append(
                            $('<span />', {'class': 'figure-name'}).text(item.title)
                        ).append(
                            $('<img />', {'src': colApi.item.getImgUrl(category.id, item.id)})
                        ).append(
                            $('<span />', {'class': 'figure-owned'}).click(onFigureOwnedClick).mouseover(function() { $(this).closest('div.figure').addClass('highlight') }).mouseout(function() { $(this).closest('div.figure').removeClass('highlight') })
                        ).data('item', item).data('categoryId', category.id)
                    )
                );
            });
        });
    };
    
    /**
     * addGameListEntry
     */
    var addGameListEntry = function(category) {
        var li = $('<li />', {'class': 'game-list-entry'})
            .prop('title', category.title)
            .data('category', category)
            .append(
                $('<img />', {'src': colApi.category.getImgUrl(category.id)})
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
            ids.push($(el).data('category').id)
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
        if (user.admin) {
            console.log('TODO: Show admin logged page');                    
        } else {
            console.log('TODO: Show normal user logged page');                    
        }
        
        colApi.category.getTopLevelCategories().done(function(categories) {
            categories.forEach(addGameListEntry);
            
            $('#game-list').sortable({'update': onGameListSort});
            
            if (user.admin) {
                $('#game-list-add-container').css('display', '');
                $('#game-list-add-button').click(onAddGameButtonClick);
            }
        });
    });
})();