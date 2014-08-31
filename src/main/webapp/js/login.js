(function() {
    skyCol.load.done(function() {
        $('#loginForm').submit(function() {
            console.log('login');
            
            var login = $('#login-username').val();
            var password = $('#login-password').val();
            
            colApi.user.login(login, password).done(function() {
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