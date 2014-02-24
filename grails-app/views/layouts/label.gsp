<%--
  Created by IntelliJ IDEA.
  User: NathanDunn
  Date: 2/24/14
  Time: 10:06 AM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
%{--<!doctype html>--}%
<html lang="en">
<head>
    <meta charset="utf-8">
    <title><g:layoutTitle default="Print"/> </title>
    %{--<link href="labels.css" rel="stylesheet" type="text/css" >--}%
    <style>
    body {
        width: 8.5in;
        margin: 0in .1875in;
    }
    .label{
        /* Avery 5160 labels -- CSS and HTML by MM at Boulder Information Services */
        width: 2.025in; /* plus .6 inches from padding */
        height: .875in; /* plus .125 inches from padding */
        padding: .125in .3in 0;
        margin-right: .125in; /* the gutter */

        float: left;

        text-align: center;
        overflow: hidden;

        outline: 1px dotted; /* outline doesn't occupy space like border does */
    }
    .page-break  {
        clear: left;
        display:block;
        page-break-after:always;
    }
    </style>

    <g:layoutHead/>
    <r:layoutResources/>
</head>
<body>
<g:layoutBody/>

<r:layoutResources/>
</body>
</html>