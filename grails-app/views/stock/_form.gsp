<%@ page import="edu.uoregon.sticklebackdb.Stock" %>



<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'abbreviation', 'error')} ">
	<label for="abbreviation">
		<g:message code="stock.abbreviation.label" default="Abbreviation" />
		
	</label>
	<g:textField name="abbreviation" value="${stockInstance?.abbreviation}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'barcode', 'error')} ">
	<label for="barcode">
		<g:message code="stock.barcode.label" default="Barcode" />
		
	</label>
	<g:field name="barcode" type="number" value="${stockInstance.barcode}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'breedingType', 'error')} ">
	<label for="breedingType">
		<g:message code="stock.breedingType.label" default="Breeding Type" />
		
	</label>
	<g:textField name="breedingType" value="${stockInstance?.breedingType}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'comment', 'error')} ">
	<label for="comment">
		<g:message code="stock.comment.label" default="Comment" />
		
	</label>
	<g:textField name="comment" value="${stockInstance?.comment}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'containerStatus', 'error')} ">
	<label for="containerStatus">
		<g:message code="stock.containerStatus.label" default="Container Status" />
		
	</label>
	<g:textField name="containerStatus" value="${stockInstance?.containerStatus}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'crossDate', 'error')} ">
	<label for="crossDate">
		<g:message code="stock.crossDate.label" default="Cross Date" />
		
	</label>
	<g:datePicker name="crossDate" precision="day"  value="${stockInstance?.crossDate}" default="none" noSelection="['': '']" />
</div>

<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'crossType', 'error')} ">
	<label for="crossType">
		<g:message code="stock.crossType.label" default="Cross Type" />
		
	</label>
	<g:field name="crossType" type="number" value="${stockInstance.crossType}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'experimentStatus', 'error')} ">
	<label for="experimentStatus">
		<g:message code="stock.experimentStatus.label" default="Experiment Status" />
		
	</label>
	<g:textField name="experimentStatus" value="${stockInstance?.experimentStatus}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'fertilizationDateTime', 'error')} ">
	<label for="fertilizationDateTime">
		<g:message code="stock.fertilizationDateTime.label" default="Fertilization Date Time" />
		
	</label>
	<g:datePicker name="fertilizationDateTime" precision="day"  value="${stockInstance?.fertilizationDateTime}" default="none" noSelection="['': '']" />
</div>

<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'fishAgeDays', 'error')} ">
	<label for="fishAgeDays">
		<g:message code="stock.fishAgeDays.label" default="Fish Age Days" />
		
	</label>
	<g:field name="fishAgeDays" type="number" value="${stockInstance.fishAgeDays}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'fishStock', 'error')} ">
	<label for="fishStock">
		<g:message code="stock.fishStock.label" default="Fish Stock" />
		
	</label>
	<g:field name="fishStock" type="number" value="${stockInstance.fishStock}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'groupStatus', 'error')} ">
	<label for="groupStatus">
		<g:message code="stock.groupStatus.label" default="Group Status" />
		
	</label>
	<g:textField name="groupStatus" value="${stockInstance?.groupStatus}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'individualStatus', 'error')} ">
	<label for="individualStatus">
		<g:message code="stock.individualStatus.label" default="Individual Status" />
		
	</label>
	<g:textField name="individualStatus" value="${stockInstance?.individualStatus}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'line', 'error')} ">
	<label for="line">
		<g:message code="stock.line.label" default="Line" />
		
	</label>
	<g:select id="line" name="line.id" from="${edu.uoregon.sticklebackdb.Line.list()}" optionKey="id" value="${stockInstance?.line?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'maternalIndividual', 'error')} ">
	<label for="maternalIndividual">
		<g:message code="stock.maternalIndividual.label" default="Maternal Individual" />
		
	</label>
	<g:select id="maternalIndividual" name="maternalIndividual.id" from="${edu.uoregon.sticklebackdb.Individual.list()}" optionKey="id" value="${stockInstance?.maternalIndividual?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'maternalIndividualLabel', 'error')} ">
	<label for="maternalIndividualLabel">
		<g:message code="stock.maternalIndividualLabel.label" default="Maternal Individual Label" />
		
	</label>
	<g:textField name="maternalIndividualLabel" value="${stockInstance?.maternalIndividualLabel}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="stock.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${stockInstance?.name}"/>
</div>


<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'paternalIndividual', 'error')} ">
	<label for="paternalIndividual">
		<g:message code="stock.paternalIndividual.label" default="Paternal Individual" />
		
	</label>
	<g:select id="paternalIndividual" name="paternalIndividual.id" from="${edu.uoregon.sticklebackdb.Individual.list()}" optionKey="id" value="${stockInstance?.paternalIndividual?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'paternalIndividualLabel', 'error')} ">
	<label for="paternalIndividualLabel">
		<g:message code="stock.paternalIndividualLabel.label" default="Paternal Individual Label" />
		
	</label>
	<g:textField name="paternalIndividualLabel" value="${stockInstance?.paternalIndividualLabel}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'stockType', 'error')} ">
	<label for="stockType">
		<g:message code="stock.stockType.label" default="Stock Type" />
		
	</label>
	<g:select id="stockType" name="stockType.id" from="${edu.uoregon.sticklebackdb.Genetics.list()}" optionKey="id" value="${stockInstance?.stockType?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

