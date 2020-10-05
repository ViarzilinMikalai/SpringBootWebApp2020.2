<#import "parts/pageTemplate.ftl" as pt>

<@pt.page>
    <h2>Add animal for ${user.firstname} ${user.lastname} ${user.surname}</h2>
    <form method="post">
        <#if animalError?? && animalError?has_content><div style="color:red">${animalError}</div></#if>
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
        <input type="text" name="animalOwner"
               value="<#if user??>${user.id}</#if>">
        <input type="submit" value="Submit">
    </form>
<h2>User animals</h2>
    <table>
        <tr>
            <th>Id</th>
            <th>Name</th>
            <th>Species</th>
            <th>Sex</th>
            <th>Birth date</th>
            <th>Deleted</th>
            <th></th>
            <th></th>
            <th></th>
        </tr>
        <#if animals?? >
            <#list animals as animal>
                <tr ${animal.deleted?then("class='alert-danger'","")}>
                    <td>${animal.id}</td>
                    <td>${animal.name}</td>
                    <td>${animal.species}</td>
                    <td>${animal.sex}</td>
                    <td><#if animal.birth??>${animal.birth}</#if></td>
                    <td><#if animal.deleted??>
                            ${animal.deleted?then("true", "false")}
                        </#if></td>
                    <td><a href="animals?editAnimal=${animal.id}">edit</a></td>
                    <td><a href="animals?editAnimal=${animal.id}">edit</a></td>
                    <td>${animal.deleted?then('<a href="animals?repairAnimal=${animal.id}">repair</a>',
                        '<a href="animals?removeAnimal=${animal.id}">delete</a>')}</td>
                </tr>
            </#list>
        </#if>
    </table>
</@pt.page>