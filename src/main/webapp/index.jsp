<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Skylanders Collections</title>

<link rel="stylesheet" type="text/css" href="css/index.css">

<%@include file="WEB-INF/jsp/includes/skyColApi.jsp" %>
<script src="js/index.js"></script>
</head>
<body>
<div id="game-list-container">
  <ul id="game-list">
  </ul>
  <div id="game-list-add-container" style="display: none">
      Title: <input type="text" id="game-list-add-title" />
      <input id="game-list-add-file" type="file" />
      <button id="game-list-add-button">Add Category</button>
  </div>
</div>

<div id="figure-list-container" style="display: none">
<h1 id="figure-list-header"></h1>
<ul id="elements-list"></ul>
<ul id="figure-list"></ul>
</div>
</body>
</html>