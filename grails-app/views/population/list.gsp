<%@ page import="edu.uoregon.sticklebackdb.ResearcherService; edu.uoregon.sticklebackdb.Population" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'population.label', default: 'Population')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>
<a href="#list-population" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                                 default="Skip to content&hellip;"/></a>

<shiro:hasRole name="${ResearcherService.ROLE_ADMINISTRATOR}">
    <div class="nav" role="navigation">
        <ul>
            <li><g:link class="create" action="create"><g:message code="default.new.label"
                                                                  args="[entityName]"/></g:link></li>
        </ul>
    </div>
</shiro:hasRole>

<div id="list-population" class="content scaffold-list" role="main">
    <h1><g:message code="default.list.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <table>
        <thead>
        <tr>

            <g:sortableColumn property="identification"
                              title="${message(code: 'population.identification.label', default: 'Identification')}"/>

            <g:sortableColumn property="source"
                              title="${message(code: 'population.source.label', default: 'Source (Lat/Long)')}"/>

        </tr>
        </thead>
        <tbody>
        <g:each in="${populationInstanceList}" status="i" var="populationInstance">
            <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

                %{--<td><g:link action="show" id="${populationInstance.id}">${fieldValue(bean: populationInstance, field: "captureDate")}</g:link></td>--}%

                <td>
                    <g:link action="show" id="${populationInstance.id}">
                        ${fieldValue(bean: populationInstance, field: "identification")}
                    </g:link>
                </td>

                <td>${fieldValue(bean: populationInstance, field: "sourceDisplay")}</td>

                %{--<td>${fieldValue(bean: populationInstance, field: "comments")}</td>--}%

            </tr>
        </g:each>
        </tbody>
    </table>

    <div class="pagination">
        <g:paginate total="${populationInstanceTotal}"/>
    </div>
</div>
</body>
</html>
