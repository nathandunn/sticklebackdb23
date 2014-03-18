<%@ page import="edu.uoregon.sticklebackdb.Line" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'line.label', default: 'Line')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>

<body>
<a href="#show-line" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                           default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]"/></g:link></li>
        %{--<li><g:link class="create" action="create"><g:message code="default.new.label"--}%
                                                              %{--args="[entityName]"/></g:link></li>--}%
    </ul>
</div>

<div id="show-line" class="content scaffold-show" role="main">
    <h1><g:message code="default.show.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <ol class="property-list line">

        <li class="fieldcontain">
            <span id="name-label" class="property-label"><g:message code="line.name.label" default="Name"/></span>
            <g:if test="${lineInstance?.name}">
                <span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${lineInstance}"
                                                                                        field="name"/></span>
            </g:if>
            <g:else>
                <span class="property-value" aria-labelledby="paternalStock-label">----</span>
            </g:else>
        </li>


        <li class="fieldcontain">
            <span id="species-label" class="property-label"><g:message code="line.species.label"
                                                                       default="Species"/></span>
            <g:if test="${lineInstance?.species}">
                <span class="property-value" aria-labelledby="species-label"><g:fieldValue bean="${lineInstance}"
                                                                                           field="species"/></span>
            </g:if>
            <g:else>
                <span class="property-value" aria-labelledby="paternalStock-label">----</span>
            </g:else>
        </li>

        <li class="fieldcontain">
            <span id="capture-label" class="property-label"><g:message code="line.capture.label"
                                                                       default="Captures"/></span>
            <g:if test="${lineInstance?.captures}">
                <span class="property-value" aria-labelledby="capture-label">
                    <ul class="nobullet">
                        <g:each var="capture" in="${lineInstance.captures}">
                            <li class="nobullet"><g:link action="show" controller="capture"
                                        id="${capture.id}">${capture.getDisplay()}</g:link>
                            </li>
                        </g:each>
                    </ul>
                </span>
            </g:if>
            <g:else>
                <span class="property-value" aria-labelledby="paternalStock-label">----</span>
            </g:else>
        </li>


        <li class="fieldcontain">
            <span id="comment-label" class="property-label"><g:message code="line.comment.label"
                                                                       default="Comments"/></span>
            <g:if test="${lineInstance?.comment}">
                <span class="property-value" aria-labelledby="comment-label"><g:fieldValue bean="${lineInstance}"
                                                                                           field="comment"/></span>
            </g:if>
            <g:else>
                <span class="property-value" aria-labelledby="paternalStock-label">----</span>
            </g:else>
        </li>

        <li class="fieldcontain">
            <span id="genetic-comment-label" class="property-label"><g:message code="line.comment.label"
                                                                               default="Genetic Notes"/></span>
            <g:if test="${lineInstance?.geneticNote}">
                <span class="property-value" aria-labelledby="comment-label"><g:fieldValue bean="${lineInstance}"
                                                                                           field="geneticNote"/></span>
            </g:if>
            <g:else>
                <span class="property-value" aria-labelledby="paternalStock-label">----</span>
            </g:else>
        </li>

        <li class="fieldcontain">
            <span id="stocks-comment-label" class="property-label"><g:message code="line.comment.label"
                                                                              default="Stocks"/></span>

            <span class="property-value" aria-labelledby="comment-label">
                %{--<g:fieldValue bean="${lineInstance}" field="stocksNote"/>--}%
                <g:each in="${stocks}" var="stock">
                    <g:link action="show" id="${stock.id}" controller="stock">${stock.stockID}</g:link>
                </g:each>
            </span>
        </li>

    </ol>
    <g:form>
        <fieldset class="buttons">
            <g:hiddenField name="id" value="${lineInstance?.id}"/>
            <g:link class="edit" action="edit" id="${lineInstance?.id}"><g:message code="default.button.edit.label"
                                                                                   default="Edit"/></g:link>
            <g:actionSubmit class="delete" action="delete"
                            value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                            onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
        </fieldset>
    </g:form>
</div>
</body>
</html>
