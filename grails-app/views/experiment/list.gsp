<%@ page import="edu.uoregon.sticklebackdb.ResearcherService; edu.uoregon.sticklebackdb.Experiment" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'experiment.label', default: 'Experiment')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>
<a href="#list-experiment" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                                 default="Skip to content&hellip;"/></a>
<shiro:hasRole name="${ResearcherService.ROLE_ADMINISTRATOR}">
    <div class="nav" role="navigation">
        <ul>

            <li><g:link class="create" action="create"><g:message code="default.new.label"
                                                                  args="[entityName]"/></g:link></li>
        </ul>
    </div>
</shiro:hasRole>
<div id="list-experiment" class="content scaffold-list" role="main">
    <h1><g:message code="default.list.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <table>
        <thead>
        <tr>

            <g:sortableColumn property="name" title="${message(code: 'experiment.note.label', default: 'Name')}"/>

            <th><g:message code="experiment.researcher.label" default="Researcher"/></th>

            <g:sortableColumn property="whenPerformed"
                              title="${message(code: 'experiment.whenPerformed.label', default: 'When Performed')}"/>

        </tr>
        </thead>
        <tbody>
        <g:each in="${experimentInstanceList}" status="i" var="experimentInstance">
            <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

                <td><g:link action="show"
                            id="${experimentInstance.id}">${fieldValue(bean: experimentInstance, field: "name")}</g:link></td>

                <td>${experimentInstance?.researcher?.name}</td>

                <td><g:formatDate date="${experimentInstance.whenPerformed}" type="date" dateStyle="MEDIUM"/></td>

            </tr>
        </g:each>
        </tbody>
    </table>

    <div class="pagination">
        <g:paginate total="${experimentInstanceTotal}"/>
    </div>
</div>
</body>
</html>
