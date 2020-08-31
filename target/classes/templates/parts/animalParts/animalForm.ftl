<h2>Search</h2>
<form method="get" action="animals">
    <input type="text" name="nameFilter" value="${nameFilter!}">
    <input type="text" name="speciesFilter" value="${speciesFilter!}">
    <input type="submit" value="Search">
</form>


<h2>Add animal</h2>
<#if animalError?? && animalError?has_content><div style="color:red">${animalError}</div></#if>
<form method="post">
    <input type="hidden" name="id"
           value="<#if animal?? && animal.id??>${animal.id}</#if>">
    <input type="text" name="name"
           class="form-control ${(nameError??)?string('is-invalid', '')}"
           value="<#if animal?? && animal.name??>${animal.name}</#if>"
           placeholder="Введите кличку животного">

    <input type="text" name="species"
           class="form-control ${(speciesError??)?string('is-invalid', '')}"
           value="<#if animal?? && animal.species??>${animal.species}</#if>">

    <input type="text" name="sex"
           class="form-control ${(sexError??)?string('is-invalid', '')}"
           value="<#if animal?? && animal.sex??>${animal.sex}</#if>">

    <input type="date" name="birth"
           class="form-control ${(birthError??)?string('is-invalid', '')}"
           value="<#if animal?? && animal.birth??>${animal.birth}</#if>">

    <input type="submit" value="Submit">
</form>
