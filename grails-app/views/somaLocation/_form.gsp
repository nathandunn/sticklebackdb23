<%@ page import="edu.uoregon.sticklebackdb.SomaLocation" %>



<div class="fieldcontain ${hasErrors(bean: somaLocationInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="somaLocation.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${somaLocationInstance?.name}" size="80"/>
</div>

