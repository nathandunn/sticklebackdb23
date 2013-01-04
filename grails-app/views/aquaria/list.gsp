<%@ page import="edu.uoregon.sticklebackdb.Aquaria" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'aquaria.label', default: 'Aquaria')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>
<a href="#list-aquaria" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                              default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label"
                                                              args="[entityName]"/></g:link></li>
    </ul>
</div>

<div id="list-aquaria" class="content scaffold-list" role="main">
    <h1><g:message code="default.list.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <table>
        <thead>
        <tr>

            <g:sortableColumn property="container.barcode"
                              title="${message(code: 'aquaria.fishTotal.label', default: 'Container')}"/>

            <g:sortableColumn property="container.size"
                              title="${message(code: 'aquaria.fishTotal.label', default: 'Size (Gallons)')}"/>

            %{--<th>--}%
            %{--Stock--}%
            %{--</th>--}%
            <g:sortableColumn property="stock.barcode"
                              title="${message(code: 'aquaria.fishUnsexed.label', default: 'Stock')}"/>
            %{--<th>--}%
                %{--Stock Name--}%
            %{--</th>--}%
            <g:sortableColumn property="stock.name"
                              title="${message(code: 'aquaria.fishUnsexed.label', default: 'Stock Name')}"/>
            %{--<th>--}%
            %{--Stock Type--}%
            %{--</th>--}%

            <g:sortableColumn property="stock.stockType.identification"
                              title="${message(code: 'aquaria.fishUnsexed.label', default: 'Stock Type')}"/>
            %{--<th>--}%
                %{--Fertilization Date--}%
            %{--</th>--}%
            <g:sortableColumn property="stock.fertilizationDateTime"
                              title="${message(code: 'aquaria.fishUnsexed.label', default: 'Fertilization')}"/>

            <g:sortableColumn property="fishTotal"
                              title="${message(code: 'aquaria.statusFishQuantity.label', default: 'Fish')}"/>

        </tr>
        </thead>
        <tbody>
        <g:each in="${aquariaInstanceList}" status="i" var="aquariaInstance">
            <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

                %{--<td><g:link action="show" id="${aquariaInstance.id}">${fieldValue(bean: aquariaInstance, field: "container")}</g:link></td>--}%
                <td><g:link action="show"
                            id="${aquariaInstance.id}">${aquariaInstance?.container?.barcode}</g:link></td>

                <td>(${aquariaInstance?.container?.size})</td>

                <td>
                    <g:link id="${aquariaInstance?.stock?.id}" controller="stock" action="show">
                        ${aquariaInstance.stock?.barcode}
                    </g:link>
                </td>
                <td>${aquariaInstance.stock?.name}</td>
                <td>${aquariaInstance.stock?.stockType?.identification}</td>
                <td>
                    <g:formatDate date="${aquariaInstance.stock?.fertilizationDateTime}" type="date"
                                  dateStyle="MEDIUM"/></td>
                <td>${aquariaInstance.fishTotal}</td>

            </tr>
        </g:each>
        </tbody>
    </table>

    <div class="pagination">
        <g:paginate total="${aquariaInstanceTotal}"/>
    </div>
</div>
</body>
</html>
