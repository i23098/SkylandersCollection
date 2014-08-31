<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Skylanders Collection</title>

<%@include file="WEB-INF/jsp/includes/skyColApi.jsp" %>
<script src="js/login.js"></script>
</head>
<body style="display: none">
Welcome to skylanders collections, where you can keep track of you collection.

<form id="loginForm" action="#">
<fieldset>
<legend>Login</legend>
<p>
  <label for="login-username">Username</label>
  <input type="text" id="login-username" />
</p>
<p>
  <label for="login-password">Password</label>
  <input type="password" id="login-password" />
</p>
<p>
  <input type="submit" value="Login" />
</p>
</fieldset>
</form>

<form id="newForm" action="#">
<fieldset>
<legend>New account</legend>
<p>
  <label for="new-username">Username</label>
  <input type="text" id="new-username" />
</p>
<p>
  <label for="new-password">Password</label>
  <input type="password" id="new-password" />
</p>
<p>
  <input type="submit" value="Login" />
</p>
</fieldset>
</form>

</body>
</html>