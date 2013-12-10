<%@ page import="edu.uoregon.sticklebackdb.Capture" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'capture.label', default: 'Capture')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>

<body>
<a href="#show-capture" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                              default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]"/></g:link></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label"
                                                              args="[entityName]"/></g:link></li>
    </ul>
</div>

<div id="show-capture" class="content scaffold-show" role="main">
    <h1><g:message code="default.show.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <ol class="property-list capture">

        <g:if test="${captureInstance?.line}">
            <li class="fieldcontain">
                <span id="line-label" class="property-label"><g:message code="capture.line.label"
                                                                        default="Line"/></span>

                <span class="property-value" aria-labelledby="line-label"><g:link controller="line" action="show"
                                                                                  id="${captureInstance?.line?.id}">${captureInstance?.line?.name}</g:link></span>

            </li>
        </g:if>

        <g:if test="${captureInstance?.population}">
            <li class="fieldcontain">
                <span id="population-label" class="property-label"><g:message code="capture.population.label"
                                                                              default="Population"/></span>

                <span class="property-value" aria-labelledby="population-label"><g:link controller="population"
                                                                                        action="show"
                                                                                        id="${captureInstance?.population?.id}">${captureInstance?.population?.identification}</g:link></span>

            </li>
        </g:if>

        <g:if test="${captureInstance?.captureDate}">
            <li class="fieldcontain">
                <span id="captureDate-label" class="property-label"><g:message code="capture.captureDate.label"
                                                                               default="Capture Date"/></span>

                <span class="property-value" aria-labelledby="captureDate-label"><g:formatDate
                        date="${captureInstance?.captureDate}"/></span>

            </li>
        </g:if>

        <g:if test="${captureInstance?.comment}">
            <li class="fieldcontain">
                <span id="comment-label" class="property-label"><g:message code="capture.comment.label"
                                                                           default="Comment"/></span>

                <span class="property-value" aria-labelledby="comment-label"><g:fieldValue bean="${captureInstance}"
                                                                                           field="comment"/></span>

            </li>
        </g:if>

    </ol>
    <g:form url="[resource: captureInstance, action: 'delete']" method="DELETE">
        <fieldset class="buttons">
            <g:link class="edit" action="edit" resource="${captureInstance}"><g:message code="default.button.edit.label"
                                                                                        default="Edit"/></g:link>
            <g:actionSubmit class="delete" action="delete"
                            value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                            onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
        </fieldset>
    </g:form>
</div>
</body>
</html>
