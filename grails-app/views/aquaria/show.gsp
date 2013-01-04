<%@ page import="edu.uoregon.sticklebackdb.Aquaria" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'aquaria.label', default: 'Aquaria')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>

<body>
<a href="#show-aquaria" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                              default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]"/></g:link></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label"
                                                              args="[entityName]"/></g:link></li>
    </ul>
</div>

<div id="show-aquaria" class="content scaffold-show" role="main">
    <h1><g:message code="default.show.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <ol class="property-list aquaria">

        <li class="fieldcontain">
            <span id="container-label" class="property-label"><g:message code="aquaria.container.label"
                                                                         default="Container"/></span>

            <span class="property-value" aria-labelledby="container-label"><g:link controller="container" action="show"
                                                                                   id="${aquariaInstance?.container?.id}">${aquariaInstance?.container?.barcode}</g:link></span>

        </li>

        <li class="fieldcontain">
            <span id="fishTotal-label" class="property-label"><g:message code="aquaria.fishTotal.label"
                                                                         default="Fish Total"/></span>

            <span class="property-value" aria-labelledby="fishTotal-label"><g:fieldValue bean="${aquariaInstance}"
                                                                                         field="fishTotal"/></span>

        </li>

        <li class="fieldcontain">
            <span id="fishUnsexed-label" class="property-label"><g:message code="aquaria.fishUnsexed.label"
                                                                           default="Fish Unsexed"/></span>

            <span class="property-value" aria-labelledby="fishUnsexed-label"><g:fieldValue bean="${aquariaInstance}"
                                                                                           field="fishUnsexed"/></span>

        </li>

        <li class="fieldcontain">
            <span id="status-label" class="property-label"><g:message code="aquaria.status.label"
                                                                      default="Status"/></span>

            <span class="property-value" aria-labelledby="status-label"><g:fieldValue bean="${aquariaInstance}"
                                                                                      field="status"/></span>

        </li>

        <g:if test="${aquariaInstance?.stock}">
            <li class="fieldcontain">
                <span id="status-label" class="property-label"><g:message code="aquaria.status.label"
                                                                          default="Stock"/></span>

                <span class="property-value" aria-labelledby="status-label">
                    ${aquariaInstance.stock.barcode}
                </span>
            </li>
            <li class="fieldcontain">
                <span id="status-label" class="property-label"><g:message code="aquaria.status.label"
                                                                          default="Age Days"/></span>

                <span class="property-value" aria-labelledby="status-label">
                    <g:formatNumber number="${aquariaInstance.stock.fishAgeDays}"/>
                </span>
            </li>
            <li class="fieldcontain">
                <span id="status-label" class="property-label"><g:message code="aquaria.status.label"
                                                                          default="Fertilization Date"/></span>

                <span class="property-value" aria-labelledby="status-label">
                    <g:formatDate date="${aquariaInstance.stock.fertilizationDateTime}" type="date" dateStyle="MEDIUM"/>
                </span>
            </li>
        </g:if>

    </ol>
    <g:form>
        <fieldset class="buttons">
            <g:hiddenField name="id" value="${aquariaInstance?.id}"/>
            <g:link class="edit" action="edit" id="${aquariaInstance?.id}"><g:message code="default.button.edit.label"
                                                                                      default="Edit"/></g:link>
            <g:actionSubmit class="delete" action="delete"
                            value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                            onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
        </fieldset>
    </g:form>
</div>
</body>
</html>
