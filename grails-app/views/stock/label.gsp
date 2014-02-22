<%--
  Created by IntelliJ IDEA.
  User: NathanDunn
  Date: 12/16/13
  Time: 4:20 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
</head>

<body>

${stockInstance.stockIDLabel}
<br/>
${stockInstance.stockName} <g:formatDate date="${stockInstance.fertilizationDate}" type="date" dateStyle="long"/>
<br/>
${stockInstance.comments}
<br/>
<br/>

</body>
</html>