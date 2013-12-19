<%@ page import="edu.uoregon.sticklebackdb.Researcher" %>



<div class="fieldcontain ${hasErrors(bean: researcherInstance, field: 'username', 'error')} required">
	<label for="username">
		<g:message code="researcher.username.label" default="Username" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="username" required="" value="${researcherInstance?.username}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: researcherInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="researcher.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${researcherInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: researcherInstance, field: 'passwordHash', 'error')} ">
	<label for="passwordHash">
		<g:message code="researcher.passwordHash.label" default="Password Hash" />
		
	</label>
	<g:textField name="passwordHash" value="${researcherInstance?.passwordHash}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: researcherInstance, field: 'permissions', 'error')} ">
	<label for="permissions">
		<g:message code="researcher.permissions.label" default="Permissions" />
		
	</label>
	
</div>

<div class="fieldcontain ${hasErrors(bean: researcherInstance, field: 'roles', 'error')} ">
	<label for="roles">
		<g:message code="researcher.roles.label" default="Roles" />
		
	</label>
	<g:select name="roles" from="${edu.uoregon.sticklebackdb.ResearchRole.list()}" multiple="multiple" optionKey="id" size="5" value="${researcherInstance?.roles*.id}" class="many-to-many"/>
</div>

