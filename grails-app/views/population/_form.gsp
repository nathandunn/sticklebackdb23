<%@ page import="edu.uoregon.sticklebackdb.Population" %>



<div class="fieldcontain ${hasErrors(bean: geneticsInstance, field: 'captureDate', 'error')} ">
	<label for="captureDate">
		<g:message code="genetics.captureDate.label" default="Capture Date" />
		
	</label>
	<g:datePicker name="captureDate" precision="day"  value="${geneticsInstance?.captureDate}" default="none" noSelection="['': '']" />
</div>

<div class="fieldcontain ${hasErrors(bean: geneticsInstance, field: 'comments', 'error')} ">
	<label for="comments">
		<g:message code="genetics.comments.label" default="Comments" />
		
	</label>
	<g:textField name="comments" value="${geneticsInstance?.comments}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: geneticsInstance, field: 'identification', 'error')} ">
	<label for="identification">
		<g:message code="genetics.identification.label" default="Identification" />
		
	</label>
	<g:textField name="identification" value="${geneticsInstance?.identification}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: geneticsInstance, field: 'source', 'error')} ">
	<label for="source">
		<g:message code="genetics.source.label" default="Source" />
		
	</label>
	<g:textField name="source" value="${geneticsInstance?.source}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: geneticsInstance, field: 'species', 'error')} ">
	<label for="species">
		<g:message code="genetics.species.label" default="Species" />
		
	</label>
	<g:textField name="species" value="${geneticsInstance?.species}"/>
</div>

