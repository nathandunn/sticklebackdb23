<%@ page import="edu.uoregon.stockdb.Experiment" %>



<div class="fieldcontain ${hasErrors(bean: experimentInstance, field: 'name', 'error')} ">
    <label for="name">
        <g:message code="experiment.name.label" default="Name"/>

    </label>
    <g:textField name="name" value="${experimentInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: experimentInstance, field: 'researcher', 'error')} ">
    <label for="researcher">
        <g:message code="experiment.researcher.label" default="Researcher"/>

    </label>
    <g:select id="researcher" name="researcher.id" optionValue="fullName"
              from="${edu.uoregon.stockdb.Researcher.list()}" optionKey="id"
              value="${currentUserId}" class="many-to-one" noSelection="['null': '']"/>
</div>

%{--<div class="fieldcontain ${hasErrors(bean: experimentInstance, field: 'strains', 'error')} ">--}%
    %{--<label for="strains">--}%
        %{--<g:message code="experiment.strains.label" default="Strains"/>--}%

    %{--</label>--}%
    %{--<g:select name="strains" from="${edu.uoregon.stockdb.Strain.list()}" optionValue="name" multiple="multiple"--}%
              %{--optionKey="id" size="5" value="${experimentInstance?.strains*.id}" class="many-to-many"/>--}%
%{--</div>--}%

%{--<div class="fieldcontain ${hasErrors(bean: experimentInstance, field: 'measuredValues', 'error')} ">--}%
    %{--<label for="measuredValues">--}%
        %{--<g:message code="experiment.measuredValues.label" default="Measured Values"/>--}%

    %{--</label>--}%
    %{--<g:select name="measuredValues"--}%
              %{--from="${availableMeasuredValues}"--}%
              %{--optionValue="name" multiple="multiple"--}%
              %{--optionKey="id" size="5" value="${experimentInstance?.measuredValues*.id}" class="many-to-many"/>--}%
%{--</div>--}%

<div class="fieldcontain ${hasErrors(bean: experimentInstance, field: 'whenPerformed', 'error')} ">
    <label for="whenPerformed">
        <g:message code="experiment.whenPerformed.label" default="When Performed"/>

    </label>
    <g:datePicker name="whenPerformed" precision="day" value="${experimentInstance?.whenPerformed}" default="${new Date()}" relativeYears="[0..-8]"
                  noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: experimentInstance, field: 'note', 'error')} ">
    <label for="note">
        <g:message code="experiment.note.label" default="Note"/>

    </label>
    <g:textArea name="note" value="${experimentInstance?.note}" cols="40" rows="2"/>
</div>

