<%@ page import="edu.uoregon.sticklebackdb.Aquaria" %>



<div class="fieldcontain ${hasErrors(bean: aquariaInstance, field: 'container', 'error')} required">
	<label for="container">
		<g:message code="aquaria.container.label" default="Container" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="container" name="container.id" from="${edu.uoregon.sticklebackdb.Container.list()}" optionKey="id" required="" value="${aquariaInstance?.container?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: aquariaInstance, field: 'fishTotal', 'error')} required">
	<label for="fishTotal">
		<g:message code="aquaria.fishTotal.label" default="Fish Total" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="fishTotal" value="${fieldValue(bean: aquariaInstance, field: 'fishTotal')}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: aquariaInstance, field: 'fishUnsexed', 'error')} required">
	<label for="fishUnsexed">
		<g:message code="aquaria.fishUnsexed.label" default="Fish Unsexed" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="fishUnsexed" value="${fieldValue(bean: aquariaInstance, field: 'fishUnsexed')}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: aquariaInstance, field: 'status', 'error')} ">
	<label for="status">
		<g:message code="aquaria.status.label" default="Status" />
		
	</label>
	<g:textField name="status" value="${aquariaInstance?.status}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: aquariaInstance, field: 'statusContainer', 'error')} required">
	<label for="statusContainer">
		<g:message code="aquaria.statusContainer.label" default="Status Container" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="statusContainer" type="number" value="${aquariaInstance.statusContainer}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: aquariaInstance, field: 'statusFishQuantity', 'error')} required">
	<label for="statusFishQuantity">
		<g:message code="aquaria.statusFishQuantity.label" default="Status Fish Quantity" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="statusFishQuantity" type="number" value="${aquariaInstance.statusFishQuantity}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: aquariaInstance, field: 'statusStock', 'error')} required">
	<label for="statusStock">
		<g:message code="aquaria.statusStock.label" default="Status Stock" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="statusStock" type="number" value="${aquariaInstance.statusStock}" required=""/>
</div>

