<%@ page import="edu.uoregon.sticklebackdb.ResearchRole" %>



<div class="fieldcontain ${hasErrors(bean: researchRoleInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="researchRole.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" required="" value="${researchRoleInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: researchRoleInstance, field: 'permissions', 'error')} ">
	<label for="permissions">
		<g:message code="researchRole.permissions.label" default="Permissions" />
		
	</label>
	
</div>

<div class="fieldcontain ${hasErrors(bean: researchRoleInstance, field: 'users', 'error')} ">
	<label for="users">
		<g:message code="researchRole.users.label" default="Users" />
		
	</label>
	
</div>

