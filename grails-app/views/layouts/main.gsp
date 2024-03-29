<%@ page import="edu.uoregon.sticklebackdb.Researcher" %>
<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title><g:layoutTitle default="Grails"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon">
    <link rel="apple-touch-icon" href="${resource(dir: 'images', file: 'apple-touch-icon.png')}">
    <link rel="apple-touch-icon" sizes="114x114" href="${resource(dir: 'images', file: 'apple-touch-icon-retina.png')}">
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'main.css')}" type="text/css">
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'mobile.css')}" type="text/css">
    <g:layoutHead/>
    <r:layoutResources/>
    <nav:resources/>
</head>

<body>
%{--<div id="grailsLogo" role="banner"><a href="http://grails.org"><img src="${resource(dir: 'images', file: 'grails_logo.png')}" alt="Grails"/></a></div>--}%
<div id="grailsLogo" role="banner"><g:link action="list" controller="stock"><img
        src="${resource(dir: 'images', file: 'threespine_stickleback.jpg')}" alt="Grails" height="100px;"/></g:link>
    <g:form action="search" controller="stock" class="search-box" method="get">
        <input class="search-watermark" name="q" type="text" value="${params.q ?: 'Search stocks...'}" onclick="if (this.value=='Search stocks...') {this.value = '';}" />
    </g:form>


    <div class="user-menu">
        <shiro:notUser>
            <g:link controller="auth" action="login">Login</g:link>
        </shiro:notUser>
        <shiro:user>
            <g:set var="researcherId"
                   value="${Researcher.findByUsername(org.apache.shiro.SecurityUtils.subject.principal).id}"/>
            <g:link controller="researcher" action="edit" id="${researcherId}">Edit <shiro:principal/></g:link>
        %{--&nbsp;&nbsp;--}%
            <g:link controller="auth" action="signOut">Logout</g:link>
        </shiro:user>
    </div>
</div>

<div id="menu">
    <nav:render/>
</div>
<g:layoutBody/>
<div class="footer" role="contentinfo"></div>

<div id="spinner" class="spinner" style="display:none;"><g:message code="spinner.alt" default="Loading&hellip;"/></div>
<g:javascript library="application"/>
<r:layoutResources/>
</body>
</html>
