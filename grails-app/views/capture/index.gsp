<%@ page import="edu.uoregon.sticklebackdb.Capture" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'capture.label', default: 'Capture')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>
<a href="#list-capture" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                              default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><g:link class="create" action="create"><g:message code="default.new.label"
                                                              args="[entityName]"/></g:link></li>
    </ul>
</div>

<div id="list-capture" class="content scaffold-list" role="main">
    <h1><g:message code="default.list.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <table>
        <thead>
        <tr>

            <th><g:message code="capture.line.label" default="Line"/></th>

            <th><g:message code="capture.population.label" default="Population"/></th>

            <g:sortableColumn property="captureDate"
                              title="${message(code: 'capture.captureDate.label', default: 'Capture Date')}"/>

            %{--<g:sortableColumn property="comment" title="${message(code: 'capture.comment.label', default: 'Comment')}" />--}%

            <th></th>
        </tr>
        </thead>
        <tbody>
        <g:each in="${captureInstanceList}" status="i" var="captureInstance">
            <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

                <td>
                    <g:link action="show" controller="line"
                            id="${captureInstance?.line?.id}">
                        ${captureInstance?.line?.name}
                    </g:link></td>

                <td>
                    <g:link action="show" controller="population" id="${captureInstance?.population?.id}">
                        ${captureInstance?.population?.identification}
                    </g:link>
                </td>

                <td><g:formatDate date="${captureInstance.captureDate}"/></td>

                %{--<td>${fieldValue(bean: captureInstance, field: "comment")}</td>--}%

                <td>
                    <g:link action="show" id="${captureInstance.id}">Details</g:link>
                </td>

            </tr>
        </g:each>
        </tbody>
    </table>

    <div class="pagination">
        <g:paginate total="${captureInstanceCount ?: 0}"/>
    </div>
</div>
</body>
</html>
