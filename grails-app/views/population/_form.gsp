<%@ page import="edu.uoregon.sticklebackdb.Population" %>



%{--<div class="fieldcontain ${hasErrors(bean: populationInstance, field: 'captureDate', 'error')} ">--}%
	%{--<label for="captureDate">--}%
		%{--<g:message code="population.captureDate.label" default="Capture Date" />--}%
		%{----}%
	%{--</label>--}%
	%{--<g:datePicker name="captureDate" precision="day"  value="${populationInstance?.captureDate}" default="none" noSelection="['': '']" />--}%
%{--</div>--}%

<div class="fieldcontain ${hasErrors(bean: populationInstance, field: 'identification', 'error')} ">
	<label for="identification">
		<g:message code="population.identification.label" default="Identification" />
		
	</label>
	<g:textField name="identification" value="${populationInstance?.identification}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: populationInstance, field: 'sourceLatString', 'error')} ">
	<label for="source">
		<g:message code="population.source.label" default="Source" />
		
	</label>
    Lat:
	<g:textField name="sourceLatString" value="${populationInstance?.sourceLatString}"/>
    Long:
    <g:textField name="sourceLongString" value="${populationInstance?.sourceLongString}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: populationInstance, field: 'comment', 'error')} ">
    <label for="comment">
        <g:message code="population.comment.label" default="Comments" />

    </label>
    <g:textArea name="comment" value="${populationInstance?.comment}"/>
</div>



<div class="fieldcontain ${hasErrors(bean: populationInstance, field: 'common', 'error')} ">
    <label for="common">
        <g:message code="population.common.label" default="Common" />
    </label>
    %{--<g:textArea name="common" value="${populationInstance?.common}"/>--}%
    <g:checkBox name="common" value="${populationInstance?.common}"/>
</div>
