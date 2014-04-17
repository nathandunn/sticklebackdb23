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

</head>

<body>

%{--<div id='canvas'>--}%

%{--</div>--}%
<canvas id="tutorial" width="550" height="250"></canvas>

%{--<canvas id="canvas">--}%

%{--</canvas>--}%

%{--${stockInstance.stockIDLabel}--}%
%{--<br/>--}%
%{--${stockInstance.stockName} <g:formatDate date="${stockInstance.fertilizationDate}" type="date" dateStyle="long"/>--}%
%{--<br/>--}%
%{--${stockInstance.comments}--}%
%{--<br/>--}%
%{--<br/>--}%


<script>
    var maxWidth = 250;
    var leftMargin = 3 ;
    var topMargin = 3 ;
    var lineHeight = 20 ;
    var height = lineHeight + topMargin ;
    var pixelRatio =  window.devicePixelRatio;
    maxWidth = maxWidth / pixelRatio;

    var ctx = document.getElementById('tutorial').getContext('2d');
//    ctx.fillStyle = "solid";
    ctx.font = "10pt Courier";
    ctx.fillText('Stock Name: ${stockInstance.stockName}', leftMargin, height, maxWidth);

    height += lineHeight ;
    ctx.font = "bold 15pt Courier";
	var spaceMargin = 90 ; 
    ctx.fillText('${stockInstance.stockIDLabel}',leftMargin,height,spaceMargin);
    ctx.font = "10pt Courier";
	ctx.fillText('Fert: <g:formatDate date="${stockInstance.fertilizationDate}" type="date" dateStyle="long"/>', leftMargin+spaceMargin,  height, maxWidth);


    height += lineHeight ;
    ctx.fillText('${stockInstance.comments}', leftMargin, height, maxWidth);

</script>

</body>
</html>
