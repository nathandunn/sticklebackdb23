<%@ page import="edu.uoregon.sticklebackdb.ResearcherService; edu.uoregon.sticklebackdb.MeasuredValue" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'measuredValue.label', default: 'MeasuredValue')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>
<a href="#list-measuredValue" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                                    default="Skip to content&hellip;"/></a>

<shiro:hasRole name="${ResearcherService.ROLE_ADMINISTRATOR}">
<div class="nav" role="navigation">
    <ul>

        <li><g:link class="create" action="create"><g:message code="default.new.label"
                                                              args="[entityName]"/></g:link></li>
    </ul>
</div>
    </shiro:hasRole>

<div id="list-measuredValue" class="content scaffold-list" role="main">
    <h1><g:message code="default.list.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <table>
        <thead>
        <tr>


            <g:sortableColumn property="value" title="${message(code: 'measuredValue.value.label', default: 'Value')}"/>

            <g:sortableColumn property="category.name" title="${message(code: 'measuredValue.category.label', default: 'Name')}"/>

            <g:sortableColumn property="category.type" title="${message(code: 'measuredValue.type.label', default: 'Type')}"/>

            <g:sortableColumn property="stock.name" title="${message(code: 'measuredValue.stock.label', default: 'Stock')}"/>

        </tr>
        </thead>
        <tbody>
        <g:each in="${measuredValueInstanceList}" status="i" var="measuredValueInstance">
            <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

                <td><g:link action="show"
                            id="${measuredValueInstance.id}">
                    ${measuredValueInstance.value ?: "no value"}
                    %{--${fieldValue(bean: measuredValueInstance, field: "value")}--}%
                </g:link></td>


                <td>
                    <g:link action="show" controller="category"
                            id="${measuredValueInstance?.category?.id}">${measuredValueInstance?.category?.name}</g:link>
                </td>

                <td>${measuredValueInstance.category.type}</td>

                <td>
                    <g:link action="show" controller="stock"
                            id="${measuredValueInstance?.stock?.id}">${measuredValueInstance?.stock?.name}</g:link>
                </td>

            </tr>
        </g:each>
        </tbody>
    </table>

    <div class="pagination">
        <g:paginate total="${measuredValueInstanceTotal}"/>
    </div>
</div>
</body>
</html>
