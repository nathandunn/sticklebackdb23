<%@ page import="edu.uoregon.sticklebackdb.FinclipLocation" %>



<div class="fieldcontain ${hasErrors(bean: finclipLocationInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="finclipLocation.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${finclipLocationInstance?.name}" size="80"/>
</div>

