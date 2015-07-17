(function() {
    skyCol.load.done(function() {
        $('#loginForm').submit(function() {
            console.log('login');
            
            var username = $('#login-username').val();
            var password = $('#login-password').val();
            
            // FIXME skyColAPI...
            colApi.user.setAuthDetails(username, password).done(function(user) {
                $.sessionStorage.set('skyColUsername', username);
                $.sessionStorage.set('skyColPassword', password);
                
                document.location.href = 'index.jsp';
            }).fail(function() {
                alert('Error logging in');
            });
            
            return false;
        });
        
        $('#newForm').submit(function() {
            console.log('register');
            return false;
        });
        
        $(document.body).css('display', '');
    });
})();