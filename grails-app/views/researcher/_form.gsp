<%@ page import="edu.uoregon.sticklebackdb.Researcher" %>



<div class="fieldcontain ${hasErrors(bean: researcherInstance, field: 'username', 'error')} required">
    <label for="username">
        <g:message code="researcher.username.label" default="Username"/>
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="username" required="" value="${researcherInstance?.username}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: researcherInstance, field: 'name', 'error')} ">
    <label for="name">
        <g:message code="researcher.name.label" default="Name"/>

    </label>
    <g:textField name="name" value="${researcherInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: researcherInstance, field: 'password1', 'error')} ">
    <label for="lastName">
        <g:message code="researcher.password.label" default="Password"/>

    </label>
    <g:passwordField name="password1"/>
    %{--<g:textField name="lastName" value="${researcherInstance?.password1}"/>--}%
</div>

<div class="fieldcontain ${hasErrors(bean: researcherInstance, field: 'password2', 'error')} ">
    <label for="lastName">
        <g:message code="researcher.password-repeat.label" default="Repeat Password"/>

    </label>
    <g:passwordField name="password2"/>
    %{--<g:textField name="lastName" value="${researcherInstance?.password1}"/>--}%
</div>


<div class="fieldcontain ${hasErrors(bean: researcherInstance, field: 'roles', 'error')} ">
    <label for="roles">
        <g:message code="researcher.roles.label" default="Roles"/>
    </label>
    %{--<g:select name="roles" from="${edu.uoregon.sticklebackdb.ResearchRole.list()}" optionKey="id" value="${researcherInstance?.roles*.id}" optionValue="name"  />--}%
    <select name="roles" id="roles">
        <g:each in="${edu.uoregon.sticklebackdb.ResearchRole.list()}" var="role">
        %{--<option value="2" selected="selected">ROLE_USER</option>--}%
        %{--<option value="1" selected="selected">ROLE_ADMINISTRATOR</option>--}%

            <option value="${role.id}"
                <g:if test="${role.id in researcherInstance?.roles*.id}">
                    selected="selected"
                </g:if>>${role.name}</option>
        </g:each>
    </select>
</div>

