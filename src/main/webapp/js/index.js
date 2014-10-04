(function() {
    var onFigureClick = function() {
        var container = $(this);
        var item = container.data('item');
        var categoryId = container.data('categoryId');
        
        colApi.item.update(categoryId, {
            'id': item.id,
            'owned': !item.owned
        }).done(function(item) {
            container.data('item', item);
            container.find('.figure-owned').toggleClass('owned', item.owned);
        });
    };
    
    /**
     * onElementEntryClick
     */
    var onElementEntryClick = function(evt) {
        evt.preventDefault();
        
        var category = $(this).parent().data('element');
        
        colApi.item.list(category.id).done(function(items) {
            console.log('elementEntryClick...');
            console.log(items);
            
            var figureList = $('#figure-list').empty();
            items.forEach(function(item) {
                figureList.append( 
                    $('<li />').append(
                        $('<span />', {'class': 'figure-name'}).text(item.title)
                    ).append(
                        $('<img />', {'src': colApi.item.getImgUrl(category.id, item.id)})
                    ).append(
                        $('<span />', {'class': 'figure-owned' + (item.owned ? ' owned' : '')})
                    ).data('item', item).data('categoryId', category.id).click(onFigureClick)
                );
            });
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
        
        console.log('clicked...');
        console.log(category);
        
        colApi.category.getCategories(category.id).done(function(elements) {
            $('#figure-list').empty();
            
            var elementsList = $('#elements-list').empty();
            elements.forEach(function(element) {
                elementsList.append(
                    $('<li />').data('element', element).append(
                        $('<a />', {'href': '#'}).text(element.title).click(onElementEntryClick)
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
            console.log('getTopLevelCategories');
            console.log(categories);
            
            categories.forEach(function(category) {
                addGameListEntry(category);
            });
            
            $('#game-list').sortable({'update': onGameListSort});
            
            if (user.admin) {
                $('#game-list-add-container').css('display', '');
                $('#game-list-add-button').click(onAddGameButtonClick);
            }
        });
    });
})();