<%@ page import="edu.uoregon.stockdb.ResearcherService; edu.uoregon.stockdb.Category" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'category.label', default: 'Category')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>

<body>
<a href="#show-category" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                               default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>

        <li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]"/></g:link></li>
        <shiro:hasRole name="${ResearcherService.ROLE_ADMINISTRATOR}">
            <li><g:link class="create" action="create"><g:message code="default.new.label"
                                                                  args="[entityName]"/></g:link></li>
        </shiro:hasRole>
    </ul>
</div>

<div id="show-category" class="content scaffold-show" role="main">
    <h1><g:message code="default.show.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <ol class="property-list category">

        <li class="fieldcontain">
            <span id="name-label" class="property-label"><g:message code="category.name.label"
                                                                    default="Name"/></span>

            <span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${categoryInstance}"
                                                                                    field="name"/></span>

        </li>

        <li class="fieldcontain">
            <span id="note-label" class="property-label"><g:message code="category.note.label"
                                                                    default="Note"/></span>

            <span class="property-value" aria-labelledby="note-label"><g:fieldValue bean="${categoryInstance}"
                                                                                    field="note"/></span>

        </li>

        <li class="fieldcontain">
            <span id="map-label" class="property-label"><g:message code="category.measuredValues.label"
                                                                   default="Measured Values"/></span>

            <span class="property-value" aria-labelledby="map-label">

                <g:each in="${categories}" var="category">
                    <h3>
                        Category:
                        <g:link action="show" controller="category"
                                id="${category.category.id}">
                            ${category.category.name}
                        </g:link>
                    </h3>
                    <table>
                    <g:each in="${category.groupedMeasuredValuesList}" var="measuredValue">
                        <tr>
                            <td>
                                ${measuredValue.key}
                            </td>
                            <td>
                                <g:each in="${measuredValue.value.strains}" var="strain">
                                    <g:link action="show" controller="strain"
                                            id="${strain.id}">
                                        ${strain.name}</g:link>
                                    &nbsp;
                                </g:each>
                            </td>
                        </tr>
                    </g:each>
                </g:each>
            </table>
            </span>

        </li>

        %{--<g:if test="${categoryInstance?.measuredValues}">--}%
        %{--<li class="fieldcontain">--}%
        %{--<span id="measuredValues-label" class="property-label"><g:message code="category.measuredValues.label"--}%
        %{--default="Measured Values"/></span>--}%

        %{--<span class="property-value" aria-labelledby="measuredValues-label">--}%
        %{--<table>--}%

        %{--<g:each in="${measuredValues}" var="m">--}%
        %{--<tr>--}%
        %{--<td>--}%
        %{--<g:link controller="measuredValue" action="show" id="${m.id}">${m?.value}</g:link>--}%
        %{--</td>--}%
        %{--<td>--}%
        %{--<g:link controller="strain" action="show"--}%
        %{--id="${m.strain.id}">${m?.strain.name}</g:link>--}%
        %{--</td>--}%
        %{--<td>--}%
        %{--<g:link controller="experiment" action="show"--}%
        %{--id="${m.experiment.id}">${m?.experiment.name}</g:link>--}%
        %{--</td>--}%
        %{--</tr>--}%
        %{--</g:each>--}%
        %{--</table>--}%
        %{--</span>--}%

        %{--</li>--}%
        %{--</g:if>--}%

    </ol>
    <g:form>
        <fieldset class="buttons">
            <g:hiddenField name="id" value="${categoryInstance?.id}"/>
            <g:link class="edit" action="edit" id="${categoryInstance?.id}"><g:message code="default.button.edit.label"
                                                                                       default="Edit"/></g:link>
            <g:actionSubmit class="delete" action="delete"
                            value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                            onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
        </fieldset>
    </g:form>
</div>
</body>
</html>
