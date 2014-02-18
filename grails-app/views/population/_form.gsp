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

<div class="fieldcontain ${hasErrors(bean: populationInstance, field: 'sourceLat', 'error')} ">
	<label for="source">
		<g:message code="population.source.label" default="Source" />
		
	</label>
    Lat:
	<g:textField name="sourceLat" value="${populationInstance?.sourceLat}"/>
    Long:
    <g:textField name="sourceLong" value="${populationInstance?.sourceLong}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: populationInstance, field: 'species', 'error')} ">
	<label for="species">
		<g:message code="population.species.label" default="Species" />
		
	</label>
	<g:textField name="species" value="${populationInstance?.species}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: populationInstance, field: 'comment', 'error')} ">
    <label for="comment">
        <g:message code="population.comment.label" default="Comments" />

    </label>
    <g:textArea name="comment" value="${populationInstance?.comment}"/>
</div>


