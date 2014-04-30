<%@ page import="edu.uoregon.sticklebackdb.MeasuredValue" %>




<div class="fieldcontain ${hasErrors(bean: measuredValueInstance, field: 'value', 'error')} ">
	<label for="value">
		<g:message code="measuredValue.value.label" default="Value" />
		
	</label>
	<g:textField name="value" value="${measuredValueInstance?.value}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: measuredValueInstance, field: 'type', 'error')} ">
	<label for="type">
		<g:message code="measuredValue.type.label" default="Type" />
		
	</label>
	%{--<g:select name="type" from="${edu.uoregon.sticklebackdb.MeasuredValueTypeEnum?.values()}" keys="${edu.uoregon.sticklebackdb.MeasuredValueTypeEnum.values()*.name()}" value="${measuredValueInstance?.type?.name()}" noSelection="['': '']"/>--}%
    <g:select name="type" value="${measuredValueInstance.type}" from="${edu.uoregon.sticklebackdb.MeasuredValueTypeEnum?.values()}"
    />
    %{--noSelection="['':'- no selection -']"--}%
</div>

%{--<div class="fieldcontain ${hasErrors(bean: measuredValueInstance, field: 'units', 'error')} ">--}%
	%{--<label for="units">--}%
		%{--<g:message code="measuredValue.units.label" default="Units" />--}%
		%{----}%
	%{--</label>--}%
	%{--<g:textField name="units" value="${measuredValueInstance?.units}"/>--}%
%{--</div>--}%

<div class="fieldcontain ${hasErrors(bean: measuredValueInstance, field: 'category', 'error')} ">
    <label for="category">
        <g:message code="measuredValue.category.label" default="Category" />

    </label>
    <g:select id="category" name="category.id" optionValue="name" from="${edu.uoregon.sticklebackdb.Category.listOrderByName()}" optionKey="id" value="${measuredValueInstance?.category?.id}" class="many-to-one" noSelection="['null': '- NONE -']"/>
    <g:link action="create" controller="category">Create Category</g:link>
</div>

<div class="fieldcontain ${hasErrors(bean: measuredValueInstance, field: 'experiment', 'error')} ">
    <label for="experiment">
        <g:message code="measuredValue.experiment.label" default="Experiment" />

    </label>
    <g:select id="experiment" name="experiment.id" optionValue="name" from="${edu.uoregon.sticklebackdb.Experiment.listOrderByName()}" optionKey="id" value="${measuredValueInstance?.experiment?.id}" class="many-to-one" noSelection="['null': '- NONE -']"/>
    <g:link action="create" controller="experiment">Create Experiment</g:link>
</div>


<div class="fieldcontain ${hasErrors(bean: measuredValueInstance, field: 'stock', 'error')} ">
    <label for="stock">
        <g:message code="measuredValue.individual.label" default="Individual" />

    </label>
    <g:select id="individual" name="individual.id" optionValue="individualIDLabel" from="${edu.uoregon.sticklebackdb.Individual.listOrderByIndividualID()}" optionKey="id" value="${measuredValueInstance?.individual?.id}" class="many-to-one" noSelection="['null': '- NONE -']"/>
    %{--<g:textField id="individual.id" name="individual.id" value="${measuredValueInstance?.individual?.individualIDLabel}"/>--}%
    <g:link action="create" controller="individual">Create Individual</g:link>
</div>


<div class="fieldcontain ${hasErrors(bean: measuredValueInstance, field: 'note', 'error')} ">
    <label for="note">
        <g:message code="measuredValue.note.label" default="Note" />

    </label>
    <g:textArea name="note" value="${measuredValueInstance.note}" note="${measuredValueInstance?.note}"/>
</div>
