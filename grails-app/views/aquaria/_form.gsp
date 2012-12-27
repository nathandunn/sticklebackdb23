<%@ page import="edu.uoregon.sticklebackdb.Aquaria" %>



<div class="fieldcontain ${hasErrors(bean: aquariaInstance, field: 'container', 'error')} ">
	<label for="container">
		<g:message code="aquaria.container.label" default="Container" />
		
	</label>
	<g:select id="container" name="container.id" from="${edu.uoregon.sticklebackdb.Container.list()}" optionKey="id" value="${aquariaInstance?.container?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: aquariaInstance, field: 'fishTotal', 'error')} ">
	<label for="fishTotal">
		<g:message code="aquaria.fishTotal.label" default="Fish Total" />
		
	</label>
	<g:field name="fishTotal" type="number" value="${aquariaInstance.fishTotal}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: aquariaInstance, field: 'fishUnsexed', 'error')} ">
	<label for="fishUnsexed">
		<g:message code="aquaria.fishUnsexed.label" default="Fish Unsexed" />
		
	</label>
	<g:field name="fishUnsexed" type="number" value="${aquariaInstance.fishUnsexed}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: aquariaInstance, field: 'status', 'error')} ">
	<label for="status">
		<g:message code="aquaria.status.label" default="Status" />
		
	</label>
	<g:textField name="status" value="${aquariaInstance?.status}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: aquariaInstance, field: 'statusContainer', 'error')} ">
	<label for="statusContainer">
		<g:message code="aquaria.statusContainer.label" default="Status Container" />
		
	</label>
	<g:field name="statusContainer" type="number" value="${aquariaInstance.statusContainer}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: aquariaInstance, field: 'statusFishQuantity', 'error')} ">
	<label for="statusFishQuantity">
		<g:message code="aquaria.statusFishQuantity.label" default="Status Fish Quantity" />
		
	</label>
	<g:field name="statusFishQuantity" type="number" value="${aquariaInstance.statusFishQuantity}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: aquariaInstance, field: 'statusStock', 'error')} ">
	<label for="statusStock">
		<g:message code="aquaria.statusStock.label" default="Status Stock" />
		
	</label>
	<g:field name="statusStock" type="number" value="${aquariaInstance.statusStock}"/>
</div>

