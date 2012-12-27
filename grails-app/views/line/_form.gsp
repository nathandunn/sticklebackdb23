<%@ page import="edu.uoregon.sticklebackdb.Line" %>



<div class="fieldcontain ${hasErrors(bean: lineInstance, field: 'abbreviation', 'error')} ">
	<label for="abbreviation">
		<g:message code="line.abbreviation.label" default="Abbreviation" />
		
	</label>
	<g:textField name="abbreviation" value="${lineInstance?.abbreviation}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: lineInstance, field: 'captureKey', 'error')} ">
	<label for="captureKey">
		<g:message code="line.captureKey.label" default="Capture Key" />
		
	</label>
	<g:textField name="captureKey" value="${lineInstance?.captureKey}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: lineInstance, field: 'captured', 'error')} ">
	<label for="captured">
		<g:message code="line.captured.label" default="Captured" />
		
	</label>
	<g:checkBox name="captured" value="${lineInstance?.captured}" />
</div>

<div class="fieldcontain ${hasErrors(bean: lineInstance, field: 'comment', 'error')} ">
	<label for="comment">
		<g:message code="line.comment.label" default="Comment" />
		
	</label>
	<g:textField name="comment" value="${lineInstance?.comment}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: lineInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="line.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${lineInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: lineInstance, field: 'species', 'error')} ">
	<label for="species">
		<g:message code="line.species.label" default="Species" />
		
	</label>
	<g:textField name="species" value="${lineInstance?.species}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: lineInstance, field: 'status', 'error')} ">
	<label for="status">
		<g:message code="line.status.label" default="Status" />
		
	</label>
	<g:field name="status" type="number" value="${lineInstance.status}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: lineInstance, field: 'storedLineName', 'error')} ">
	<label for="storedLineName">
		<g:message code="line.storedLineName.label" default="Stored Line Name" />
		
	</label>
	<g:textField name="storedLineName" value="${lineInstance?.storedLineName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: lineInstance, field: 'storedLineNameAbbreviation', 'error')} ">
	<label for="storedLineNameAbbreviation">
		<g:message code="line.storedLineNameAbbreviation.label" default="Stored Line Name Abbreviation" />
		
	</label>
	<g:textField name="storedLineNameAbbreviation" value="${lineInstance?.storedLineNameAbbreviation}"/>
</div>

