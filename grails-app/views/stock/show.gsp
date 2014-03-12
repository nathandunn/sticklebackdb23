<%@ page import="edu.uoregon.sticklebackdb.Stock" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'stock.label', default: 'Stock')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>

<body>
<a href="#show-stock" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                            default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]"/></g:link></li>
        %{--<li><g:link class="create" action="create"><g:message code="default.new.label"--}%
        %{--args="[entityName]"/></g:link></li>--}%
        <li><g:link class="create" action="createFromBreeding">New Stock from Breeding</g:link></li>
        <li><g:link class="create" action="createFromCapture">New Stock from Capture</g:link></li>
    </ul>
</div>

<div id="show-stock" class="content scaffold-show" role="main">

<h1><g:message code="default.show.label"
               args="[entityName]"/> ${stockInstance.isCapture() ? 'from Capture' : 'from Breeding'}</h1>
<g:if test="${flash.message}">
    <div class="message" role="status">${flash.message}</div>
</g:if>
<ol class="property-list stock">

%{-- * * * Stock ID * * * --}%
<li class="fieldcontain">
    <span id="fishStock-label" class="property-label"><g:message code="stock.stockID.label"
                                                                 default="Stock ID"/></span>
    <g:if test="${stockInstance?.stockID}">
        <span class="property-value" aria-labelledby="stockID-label">${stockInstance?.stockIDLabel}</span>
    </g:if>
    <g:else>
        <span class="property-value" aria-labelledby="stockID-label">----</span>
    </g:else>
</li>

%{-- * * * Stock Name * * * --}%
<li class="fieldcontain">
    <span id="name-label" class="property-label"><g:message code="stock.stockName.label"
                                                            default="Stock Name"/></span>
    <g:if test="${stockInstance?.stockName}">
        <span class="property-value" aria-labelledby="stockName-label"><g:fieldValue bean="${stockInstance}"
                                                                                     field="stockName"/></span>
    </g:if>
    <g:else>
        <span class="property-value" aria-labelledby="stockName-label">----</span>
    </g:else>
</li>

%{-- * * * Stock Line (with link) * * * --}%
<li class="fieldcontain">
    <span id="line-label" class="property-label"><g:message code="stock.line.label" default="Line"/></span>
    <g:if test="${stockInstance?.line}">
        <span class="property-value" aria-labelledby="line-label">
            <g:link controller="line" action="show"
                    id="${stockInstance?.line?.id}">${stockInstance?.line?.name}</g:link>
            <g:if test="${stockInstance?.line?.captures?.captureDate}">

                <g:each in="${stockInstance?.line?.captures}" var="capture">
                    <g:if test="${capture.captureDate}">
                        <g:link action="show" controller="capture" id="${capture.id}">
                            Capture <g:formatDate date="${capture.captureDate}" type="date" dateStyle="short"/>
                        </g:link>
                    </g:if>
                </g:each>
            </g:if>
        </span>
    </g:if>
    <g:else>
        <span class="property-value" aria-labelledby="line-label">----</span>
    </g:else>
</li>


%{-- * * * Stock fertilization date * * * --}%
<g:if test="${stockInstance.isBred()}">
    <li class="fieldcontain">
        <span id="fertilizationDate-label" class="property-label"><g:message code="stock.fertilizationDate.label"
                                                                             default="Fertilization Date"/></span>
        <g:if test="${stockInstance?.fertilizationDate}">
            <span class="property-value" aria-labelledby="fertilizationDate-label"><g:formatDate type="date"
                                                                                                 style="MEDIUM"
                                                                                                 date="${stockInstance?.fertilizationDate}"/></span>
        </g:if>
        <g:else>
            <span class="property-value" aria-labelledby="fertilizationDate-label">----</span>
        </g:else>
    </li>

%{-- * * * Stock Capture * * * --}%
%{--<li class="fieldcontain">--}%
%{--<span id="capture-label" class="property-label"><g:message code="stock.capture.label"--}%
%{--default="Capture"/></span>--}%
%{--<g:if test="${stockInstance?.capture}">--}%
%{--<span class="property-value" aria-labelledby="capture-label">--}%
%{--<g:link controller="capture" action="show" id="${stockInstance?.capture?.id}">--}%
%{--${stockInstance?.capture?.getDisplay()}--}%
%{--</g:link>--}%
%{--</span>--}%
%{--</g:if>--}%
%{--<g:else>--}%
%{--<span class="property-value" aria-labelledby="capture-label">----</span>--}%
%{--</g:else>--}%
%{--</li>--}%

    <br>
    <hr>

%{-- * * * Maternal Stock ID * * * --}%
    <li class="fieldcontain">
        <span id="maternalStockID-label" class="property-label"><g:message code="stock.maternalStockID.label"
                                                                           default="Maternal Stock ID"/></span>
        <g:if test="${stockInstance?.maternalStock}">
            <span class="property-value" aria-labelledby="maternalStockID-label"><g:link controller="stock"
                                                                                         action="show"
                                                                                         id="${stockInstance?.maternalStock?.id}">${stockInstance?.maternalStock?.stockIDLabel}</g:link></span>
        </g:if>
        <g:else>
            <span class="property-value" aria-labelledby="maternalStock-label">----</span>
        </g:else>
    </li>

%{-- * * * Maternal Individual ID * * * --}%
    <li class="fieldcontain">
        <span id="maternalID-label" class="property-label"><g:message code="stock.maternalIndividualID"
                                                                      default="Maternal Individual ID"/></span>
        <g:if test="${stockInstance?.maternalIndividual}">
            <span class="property-value" aria-labelledby="maternalIndividual-label">
                <g:link controller="individual" action="show" id="${stockInstance?.maternalIndividual?.id}">
                    ${stockInstance?.maternalIndividual?.individualIDLabel}</g:link>
            </span>
        </g:if>
    %{--<g:elseif test="${stockInstance?.maternalIndividualID}">--}%
    %{--<span class="property-value"--}%
    %{--aria-labelledby="maternalIndividual-label">${stockInstance?.maternalIndividualID}</span>--}%
    %{--</g:elseif>--}%
        <g:else>
            <span class="property-value" aria-labelledby="maternalStock-label">----</span>
        </g:else>
    </li>

%{-- * * * Maternal Line * * * --}%
    <li class="fieldcontain">
        <span id="maternalLine-label" class="property-label"><g:message code="stock.maternalLine"
                                                                        default="Maternal Line"/></span>
        <g:if test="${stockInstance?.maternalStock}">
            <span class="property-value"><g:link controller="line" action="show"
                                                 id="${stockInstance?.maternalStock?.line?.id}">${stockInstance?.maternalStock?.line?.name}</g:link></span>
        </g:if>
        <g:else>
            <span class="property-value" aria-labelledby="maternalLine-label">----</span>
        </g:else>
    </li>

    <br>
    <hr>

%{-- * * * Paternal Stock ID * * * --}%
    <li class="fieldcontain">
        <span id="paternalStockID-label" class="property-label"><g:message code="stock.paternalID.label"
                                                                           default="Paternal Stock ID"/></span>
        <g:if test="${stockInstance?.paternalStock}">
            <span class="property-value" aria-labelledby="paternalStockID-label"><g:link controller="stock"
                                                                                         action="show"
                                                                                         id="${stockInstance?.paternalStock?.id}">${stockInstance?.paternalStock?.stockIDLabel}</g:link></span>
        </g:if>
        <g:else>
            <span class="property-value" aria-labelledby="paternalStockID-label">----</span>
        </g:else>
    </li>

%{-- * * * Paternal Individual ID * * * --}%
    <li class="fieldcontain">
        <span id="paternalID-label" class="property-label"><g:message code="stock.paternalIndividualID.label"
                                                                      default="Paternal Individual ID"/></span>
        <g:if test="${stockInstance?.paternalIndividual}">
            <span class="property-value" aria-labelledby="paternalIndividual-label"><g:link controller="individual"
                                                                                            action="show"
                                                                                            id="${stockInstance?.paternalIndividual?.id}">${stockInstance?.paternalIndividual?.individualIDLabel}</g:link></span>
        </g:if>
    %{--<g:elseif test="${stockInstance?.paternalIndividualID}">--}%
    %{--<span class="property-value"--}%
    %{--aria-labelledby="paternalIndividual-label">${stockInstance?.paternalIndividualID}</span>--}%
    %{--</g:elseif>--}%
        <g:else>
            <span class="property-value" aria-labelledby="paternalStock-label">----</span>
        </g:else>
    </li>

%{-- * * * Paternal Line * * * --}%
    <li class="fieldcontain">
        <span id="paternalLine-label" class="property-label"><g:message code="stock.paternalLine"
                                                                        default="Paternal Line"/></span>
        <g:if test="${stockInstance?.paternalStock}">
            <span class="property-value"><g:link controller="line" action="show"
                                                 id="${stockInstance?.paternalStockID}">${stockInstance?.paternalStock?.line?.name}</g:link></span>
        </g:if>
        <g:else>
            <span class="property-value" aria-labelledby="paternalLine-label">----</span>
        </g:else>
    </li>

</g:if>
<br>
<hr>

%{-- * * * Stock researcher comments * * * --}%
<li class="fieldcontain">
    <span id="comment-label" class="property-label"><g:message code="stock.comments.label"
                                                               default="Comments"/></span>
    <g:if test="${stockInstance?.comments}">
        <span class="property-value" aria-labelledby="comments-label"><g:fieldValue bean="${stockInstance}"
                                                                                    field="comments"/></span>
    </g:if>
    <g:else>
        <span class="property-value" aria-labelledby="comments-label">----</span>
    </g:else>
</li>

<br>
<hr>

%{-- * * * List Individuals within this Stock * * * --}%
<li class="fieldcontain">
    <span id="individuals-label" class="property-label">
        <g:message code="stock.individuals.label" default="Individuals"/>
    </span>
    <span class="property-value" aria-labelledby="stock-label">
        %{--<g:link controller="individual" action="query" params="[pStockID: stockInstance.stockID]">Show Stock Individuals</g:link>--}%
        <g:each in="${stockInstance.individuals}" var="individual" status="st">
            <g:link action="show" controller="individual" id="${individual.id}">
                ${individual.individualIDLabel}
            </g:link>
            <g:if test="${st.intValue() < stockInstance.individuals.size() - 1}">
                ,
            </g:if>
        </g:each>
    </ul>
    </span>

</li>

</ol>
<g:form>
    <fieldset class="buttons">
        <g:hiddenField name="id" value="${stockInstance?.id}"/>
        <g:link target="_blank" action="bracket" params="[stockID: stockInstance?.stockID]">Lineage</g:link>
        <g:link action="print" id="${stockInstance?.id}">Print</g:link>
        <g:link class="edit" action="edit" id="${stockInstance?.id}"><g:message code="default.button.edit.label"
                                                                                default="Edit"/></g:link>
        <g:actionSubmit class="delete" action="delete"
                        value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                        onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
    </fieldset>
</g:form>
</div>
</body>
</html>
