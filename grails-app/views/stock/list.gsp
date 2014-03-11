<%@ page import="edu.uoregon.sticklebackdb.Stock" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'stock.label', default: 'Stock')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>
<a href="#list-stock" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                            default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><g:link class="create" action="createFromBreeding">New Stock from Breeding</g:link></li>
        <li><g:link class="create" action="createFromCapture">New Stock from Capture</g:link></li>
    </ul>
</div>

<div id="list-stock" class="content scaffold-list" role="main">
    <h1><g:message code="default.list.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <table>
        <thead>
        <tr>
            <g:sortableColumn property="stockID" title="${message(code: 'stock.stockID.label', default: 'ID')}"/>
            <g:sortableColumn property="stockName"
                              title="${message(code: 'stock.stockName.label', default: 'Stock Name')}"/>
            %{--<g:sortableColumn property="type"--}%
                              %{--title="${message(code: 'stock.stockName.label', default: 'Type')}"/>--}%
            <th>Type</th>
        </tr>
        </thead>
        <tbody>
        <g:each in="${stockInstanceList}" status="i" var="stockInstance">
            <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                <td>
                %{--${stockInstance.id}--}%
                    <g:link action="show" id="${stockInstance.id}" controller="stock">${stockInstance?.stockIDLabel}
                    </g:link></td>
                <td>${fieldValue(bean: stockInstance, field: "stockName")}</td>
                <td>
                <g:if test="${stockInstance.isCapture()}">
                    Captures:
                    <g:each in="${stockInstance.line.captures}" var="capture">
                        <g:formatDate date="${capture.captureDate}" type="date"/>
                    </g:each>
                %{--${stockInstance.isCapture() ? stockInstance.line.captures}--}%
                </g:if>
                <g:else>
                    Bred:
                    <g:if test="${stockInstance.fertilizationDate}">
                    <g:formatDate date="${stockInstance.fertilizationDate}" type="date"/>
                        </g:if>
                    <g:else>
                        N/A
                    </g:else>
                </g:else>
                </td>
            </tr>
        </g:each>
        </tbody>
    </table>

    <div class="pagination">
        <g:paginate total="${stockInstanceTotal}"/>
    </div>
</div>
</body>
</html>
