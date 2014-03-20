<%@ page import="edu.uoregon.stockdb.Category" %>



<div class="fieldcontain ${hasErrors(bean: categoryInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="category.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${categoryInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: categoryInstance, field: 'note', 'error')} ">
    <label for="note">
        <g:message code="category.note.label" default="Note" />

    </label>
    <g:textArea name="note" value="${categoryInstance?.note}"/>
</div>

%{--<div class="fieldcontain ${hasErrors(bean: categoryInstance, field: 'measuredValues', 'error')} ">--}%
	%{--<label for="measuredValues">--}%
		%{--<g:message code="category.measuredValues.label" default="Measured Values" />--}%
		%{----}%
	%{--</label>--}%
	
%{--<ul class="one-to-many">--}%
%{--<g:each in="${categoryInstance?.measuredValues?}" var="m">--}%
    %{--<li><g:link controller="measuredValue" action="show" id="${m.id}">${m?.encodeAsHTML()}</g:link></li>--}%
%{--</g:each>--}%
%{--<li class="add">--}%
%{--<g:link controller="measuredValue" action="create" params="['category.id': categoryInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'measuredValue.label', default: 'MeasuredValue')])}</g:link>--}%
%{--</li>--}%
%{--</ul>--}%

%{--</div>--}%

