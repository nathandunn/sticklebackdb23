<%@ page import="edu.uoregon.sticklebackdb.DnaLocation" %>



<div class="fieldcontain ${hasErrors(bean: dnaLocationInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="dnaLocation.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${dnaLocationInstance?.name}"/>
</div>

