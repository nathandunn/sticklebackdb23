<%--
  Created by IntelliJ IDEA.
  User: NathanDunn
  Date: 12/16/13
  Time: 4:20 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="label">
</head>

<body>

%{--${individualInstance.individualIDLabel}--}%
%{--<br/>--}%
%{--${individualInstance.stock.stockName}--}%
%{--<br/>--}%
%{--${individualInstance.stock.fertilizationDate}--}%
%{--<br/>--}%

<canvas id="tutorial" width="800" height="150"></canvas>

<script>
    var maxWidth = 280;
    var leftMargin = 3 ;
    var topMargin = 3 ;
    var lineHeight = 20 ;
    var height = lineHeight + topMargin ;
    var pixelRatio =  window.devicePixelRatio;
    maxWidth = maxWidth / pixelRatio;

    var ctx = document.getElementById('tutorial').getContext('2d');
    //    ctx.fillStyle = "solid";
    ctx.font = "10pt Courier";
    ctx.fillText('Stock Name: ${individualInstance.stock.stockName}', leftMargin, height, maxWidth);

    height += lineHeight ;
    ctx.fillText('${individualInstance.individualIDLabel} Fert Date: <g:formatDate date="${individualInstance.stock.fertilizationDate}" type="date" dateStyle="long"/>', leftMargin,  height, maxWidth);

    height += lineHeight ;
    ctx.fillText('${individualInstance.comments}', leftMargin, 70, maxWidth);

</script>

</body>

</html>