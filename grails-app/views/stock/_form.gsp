<%@ page import="edu.uoregon.sticklebackdb.Stock" %>

<r:require modules="jquery,jquery-ui"/>


%{-- stockID --}%
<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'stockID', 'error')} ">
  <label for="stockID">
    <g:message code="stock.stockID.label" default="Stock ID" />
  </label>
  <g:textField name="stockID"  value="${stockInstance?.stockID}" />

</div>


<script>
    $(document).ready(function() {
        var availableTags = [
            <g:each var="stock" in="${edu.uoregon.sticklebackdb.Stock.listOrderByStockName(order: "asc",sort:"stockName").unique(false)}" status="iter">
                "${stock.stockName}"
                ,
            </g:each>
            "ActionScript",
            "AppleScript",
            "Asp",
            "BASIC",
            "C",
            "C++",
            "Clojure",
            "COBOL",
            "ColdFusion",
            "Erlang",
            "Fortran",
            "Groovy",
            "Haskell",
            "Java",
            "JavaScript",
            "Lisp",
            "Perl",
            "PHP",
            "Python",
            "Ruby",
            "Scala",
            "Scheme"
        ];
//        $("#stockName").click(function(){
//            alert('ouch')
//        });
        $("#stockName").autocomplete({
            source: availableTags
        });
    });
</script>

%{-- Name --}%
<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'stockName', 'error')} ">
  <label for="stockName">
    <g:message code="stock.stockName.label" default="Stock Name" />
  </label>
  <g:textField id="stockName" class="ui-autocomplete-input" name="stockName" value="${stockInstance?.stockName}" autocomplete="off"/>
</div>

%{-- Line --}%
<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'line', 'error')} ">
  <label for="line">
    <g:message code="stock.line.label" default="Line" />
  </label>
  <g:select id="line" name="line.id" from="${edu.uoregon.sticklebackdb.Line.list()}" optionKey="id"
            value="${stockInstance.line?.id}"  style="width:200px;font-size: 12px"
            class="many-to-one"  noSelection="['null': '- Choose Line -']"
            optionValue="name"
            />
</div>

%{-- Fertilization Date --}%
<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'fertilizationDate', 'error')} ">
  <label for="fertilizationDate">
    <g:message code="stock.fertilizationDate.label" default="Fertilization Date" />
  </label>
  <g:datePicker name="fertilizationDate" precision="day"  value="${stockInstance?.fertilizationDate}" default="none" noSelection="['': '']" />
</div>

%{-- Maternal Stock ID --}%
<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'maternalStockID', 'error')} ">
  <label for="maternalStockID">
    <g:message code="stock.maternalStockID.label" default="Maternal Stock ID" />
  </label>
  <script>
    function setMaternalIds(data){
                alert(data);  
                
                var select = $("#matIndivIDSelect");
                select.empty(); 
                 
                  /*for(int i = 0; i < length){
                    var key = data[i].key
                    var value = data[i].value
                    alert(key + " "  + value)
                  }*/
                 
                  //alert(key + " -> " + data[key])
                //  var obj = data[i];
                  for(var key in data){    
                    alert("moo " + key);
                      var name = key;
                      var value = obj[key];
                    //  select.append('<option value=1>' + value + '</option>');
                  }
                   select.append('<option value=1>My Option</option>');            
    }
  </script>
  <g:select id="stock" name="stock.id" from="${edu.uoregon.sticklebackdb.Stock.list()}"
            value="${stockInstance?.maternalStockID}"
            class="many-to-one" noSelection="['null': '- Choose Stock -']"
            optionValue="stockID" optionKey="stockID"  
            onchange="
${remoteFunction( action: 'findIndividualsForStock'
  , controller: 'individual'
  , params: '\'stockID=\' + this.value '
  , method: 'POST'
  , onSuccess: 'setMaternalIds(data);'
  , onError: 'alert(\'error\');'
)}"  
            />
</div>

%{-- Maternal Individual ID --}%
<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'maternalIndividualID', 'error')} ">
  <label for="maternalIndividual">
    <g:message code="stock.maternalIndividualID.label" default="Maternal Individual ID" />
  </label>
  <g:select id="matIndivIDSelect" name="matIndivIDSelect.id"  from="${edu.uoregon.sticklebackdb.Individual.list()}" 
            value="${stockInstance?.maternalIndividualID}"  style="width:200px;font-size: 12px"
            class="many-to-one" noSelection="['null': '- Choose Individual -']"
            optionValue="individualID"  optionKey="individualID"
            />  
</div>

%{-- Paternal Stock ID --}%
<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'paternalStockID', 'error')} ">
  <label for="paternalStockID">
    <g:message code="stock.paternalStockID.label" default="Paternal Stock ID" />
  </label>
  <script>
   function setPaternalIds(data){
        alert(data);
        var select = $("#patIndivIDSelect");
        select.empty();                
        select.add(new Option(data[0])); 
   }
  </script>

  <g:select id="paternalStockIDSelect" name="paternalStockIDSelect.id" from="${edu.uoregon.sticklebackdb.Stock.list()}" optionKey="id"
            value="${stockInstance?.paternalStockID}"
            class="many-to-one" noSelection="['null': '- Choose Stock -']"
            optionValue="stockID" optionKey="stockID"
            onchange="
${remoteFunction( action: 'findIndividualsForStock'
  , controller: 'individual'
  , params: '\'stockID=\' + this.value '
  , method: 'POST'
  , onSuccess: 'setPaternalIds(data);'
  , onError: 'alert(\'error\');'
)}" 
            />
</div>

%{-- Paternal Individual --}%
<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'paternalIndividual', 'error')} ">
  <label for="paternalIndividual">
    <g:message code="stock.paternalIndividual.label" default="Paternal Individual" />
  </label> 
  <g:select id="patIndivIDSelect" name="patIndivIDSelect.id" from="${edu.uoregon.sticklebackdb.Individual.list()}" optionKey="id"
            value="${stockInstance?.paternalIndividualID}"  style="width:200px;font-size: 12px"
            class="many-to-one" noSelection="['null': '- Choose Individual -']"
            optionValue="individualID" optionKey="individualID"
            />  
</div>


%{-- Comments --}%
<div class="fieldcontain ${hasErrors(bean: stockInstance, field: 'comments', 'error')} ">
  <label for="comments">
    <g:message code="stock.comments.label" default="Comments" />
  </label>
  <g:textArea name="comments" value="${stockInstance?.comments}" />
</div>