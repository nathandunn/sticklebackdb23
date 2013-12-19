<!DOCTYPE html>
<html>
	<head>
		<title>Unauthorized Page</title>
		<meta name="layout" content="main">
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'errors.css')}" type="text/css">
	</head>
	<body>
    You are not authorized to access that page.
		%{--<g:renderException exception="${exception}" />--}%
	</body>
</html>
