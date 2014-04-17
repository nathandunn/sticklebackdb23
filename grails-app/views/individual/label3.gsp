<%--
  Created by IntelliJ IDEA.
  User: NathanDunn
  Date: 12/16/13
  Time: 4:20 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>

<r:require modules="jquery,jquery-ui"/>

<html>
<head>
    <meta name="layout" content="label">

    <style type="text/css">
    /*@media print {*/
    #label {
        width: 3in;
        height: 1in;
        font-family: monospace;
        font-size: medium;
    }

    #number {
        font-family: monospace;
        font-weight: bold;
        font-size: larger;
        display: inline-block;
    }

    #comment {
        font-family: monospace;
        font-weight: normal;
        font-size: medium;
        display: inline-block;
        overflow: hidden;
        overflow-style: marquee;
        overflow-wrap: break-word;
    }
    </style>

</head>

<body>

%{--<div id='canvas'>--}%

%{--</div>--}%
%{--<canvas id="tutorial" width="550" height="250"></canvas>--}%

%{--<canvas id="canvas">--}%

%{--</canvas>--}%

%{--${stockInstance.stockIDLabel}--}%
%{--<br/>--}%
%{--${stockInstance.stockName} <g:formatDate date="${stockInstance.fertilizationDate}" type="date" dateStyle="long"/>--}%
%{--<br/>--}%
%{--${stockInstance.comments}--}%
%{--<br/>--}%
%{--<br/>--}%


<div id="label">
    Stock Name: ${individualInstance.stock.labelStockName}
    <br/>

    <div id="number">${individualInstance.individualIDLabel}</div>
    %{--${stockInstance.fertilizationDate}--}%
    Fert: <g:formatDate date="${individualInstance.stock.fertilizationDate}" type="date" dateStyle="long"/>
    <br/>

    <div id="comment">
        ${individualInstance.labelComments}
    </div>
</div>

%{--<script>--}%
%{--var maxWidth = 250;--}%
%{--var leftMargin = 3;--}%
%{--var topMargin = 3;--}%
%{--var lineHeight = 20;--}%
%{--var height = lineHeight + topMargin;--}%
%{--var pixelRatio = window.devicePixelRatio;--}%
%{--maxWidth = maxWidth / pixelRatio;--}%

%{--var ctx = document.getElementById('tutorial').getContext('2d');--}%
%{--//    ctx.fillStyle = "solid";--}%
%{--ctx.font = "10pt Courier";--}%
%{--ctx.fillText('Stock Name: ${stockInstance.stockName}', leftMargin, height, maxWidth);--}%

%{--height += lineHeight;--}%
%{--ctx.font = "bold 15pt Courier";--}%
%{--var spaceMargin = 90;--}%
%{--ctx.fillText('${stockInstance.stockIDLabel}', leftMargin, height, spaceMargin);--}%
%{--ctx.font = "10pt Courier";--}%
%{--ctx.fillText('Fert: <g:formatDate date="${stockInstance.fertilizationDate}" type="date" dateStyle="long"/>', leftMargin + spaceMargin, height, maxWidth);--}%


%{--height += lineHeight;--}%
%{--ctx.fillText('${stockInstance.comments}', leftMargin, height, maxWidth);--}%

%{--</script>--}%

</body>
</html>
