<%@ page import="edu.uoregon.sticklebackdb.ResearcherService; edu.uoregon.sticklebackdb.Population" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'population.label', default: 'Population')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>

<body>
<a href="#show-population" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
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

<div id="show-population" class="content scaffold-show" role="main">
    <h1><g:message code="default.show.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <ol class="property-list population">

        %{--<g:if test="${populationInstance?.captureDate}">--}%
        %{--<li class="fieldcontain">--}%
        %{--<span id="captureDate-label" class="property-label"><g:message code="population.captureDate.label" default="Capture Date" /></span>--}%
        %{----}%
        %{--<span class="property-value" aria-labelledby="captureDate-label"><g:formatDate date="${populationInstance?.captureDate}" /></span>--}%
        %{----}%
        %{--</li>--}%
        %{--</g:if>--}%

        <li class="fieldcontain">
            <span id="identification-label" class="property-label"><g:message code="population.identification.label"
                                                                              default="Identification"/></span>

            <span class="property-value" aria-labelledby="identification-label"><g:fieldValue
                    bean="${populationInstance}" field="identification"/></span>

        </li>

        <li class="fieldcontain">
            <span id="source-label" class="property-label"><g:message code="population.source.label"
                                                                      default="Source (Lat/Long)"/></span>

            <span class="property-value" aria-labelledby="source-label">
                <g:if test="${populationInstance?.sourceLatString}">
                    <g:fieldValue bean="${populationInstance}" field="sourceDisplay"/>
                </g:if>
                <g:else>
                    ----
                </g:else>
            </span>

        </li>

        <g:if test="${populationInstance?.comment}">
            <li class="fieldcontain">
                <span id="comment-label" class="property-label"><g:message code="population.comment.label"
                                                                           default="Comments"/></span>

                <span class="property-value" aria-labelledby="comment-label">
                    <g:if test="${populationInstance.comment}">
                        <g:fieldValue bean="${populationInstance}" field="comment"/>
                    </g:if>
                    <g:else>
                        ----
                    </g:else>
                </span>

            </li>
        </g:if>


        <li class="fieldcontain">
            <span id="capture-label" class="property-label"><g:message code="population.capture.label"
                                                                       default="Captures"/></span>

            <span class="property-value" aria-labelledby="capture-label">
                %{--<g:fieldValue bean="${populationInstance}" field="capture"/>--}%
                <ul style="margin-left: 10px;">
                    <g:each in="${populationInstance.captures}" var="capture">
                        <li>
                            <g:link action="show" id="${capture.id}" controller="capture">
                                ${capture.display}
                            </g:link>
                        </li>
                    </g:each>
                </ul>
            </span>

        </li>


        <li class="fieldcontain">
            <span id="common-label" class="property-label"><g:message code="population.common.label"
                                                                      default="Common"/></span>

            <span class="property-value"
                  aria-labelledby="common-label">${populationInstance.common ? 'true' : 'false'}</span>

        </li>

    </ol>
    <g:form>
        <fieldset class="buttons">
            <g:hiddenField name="id" value="${populationInstance?.id}"/>
            <g:link class="edit" action="edit" id="${populationInstance?.id}"><g:message
                    code="default.button.edit.label" default="Edit"/></g:link>
            <g:actionSubmit class="delete" action="delete"
                            value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                            onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
        </fieldset>
    </g:form>
</div>
</body>
</html>
