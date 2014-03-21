<html>
<head>
  <!-- Integrate with Sitemesh layouts           -->
  <meta name="layout" content="main" />

  <!--                                           -->
  <!-- Any title is fine                         -->
  <!--                                           -->
  <title>Edit Experiment Values - ${experimentInstance.name}</title>

  <!--                                           -->
  <!-- This script loads your compiled module.   -->
  <!-- If you add any GWT meta tags, they must   -->
  <!-- be added before this line.                -->
  <!--                                           -->
    <script>
     //var experimentId = ${experimentInstance.id} ;
     var properties = {
         experimentId: "${experimentInstance.id}"
     } ;

</script>
  <script type="text/javascript" src="${resource(dir: 'gwt/edu.uoregon.sticklebackdb.ExperimentEntry', file: 'edu.uoregon.sticklebackdb.ExperimentEntry.nocache.js')}"></script>
</head>

<!--                                           -->
<!-- The body can have arbitrary html, or      -->
<!-- you can leave the body empty if you want  -->
<!-- to create a completely dynamic ui         -->
<!--                                           -->
<body>
<fieldset class="buttons">
    <g:link action="show" class="show" controller="experiment" id="${experimentInstance?.id}">Done Editing</g:link>
</fieldset>
  <!-- OPTIONAL: include this if you want history support -->
  <iframe id="__gwt_historyFrame" style="width:0;height:0;border:0"></iframe>

<h3>Experiment</h3>
${experimentInstance.name} <g:formatDate date="${experimentInstance.whenPerformed}"/> ${experimentInstance.researcher?.name}

<div id="edit-table"></div>

<fieldset class="buttons">
    <g:link action="show" class="show" controller="experiment" id="${experimentInstance?.id}">Done Editing</g:link>
</fieldset>

  <!-- Add the rest of the page here, or leave it -->
  <!-- blank for a completely dynamic interface.  -->
</body>
</html>
