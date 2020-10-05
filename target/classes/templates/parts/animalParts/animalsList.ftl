<#import "../pager.ftl" as p>

<h2>Search</h2>
<form method="get" action="animals">
    <input type="text" name="nameFilter" value="${nameFilter!}">
    <input type="text" name="speciesFilter" value="${speciesFilter!}">
    <input type="submit" value="Search">
</form>

<h1>Our animals</h1>

    <#if hasContent>
        <@p.pager url page/>
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

            <#list page.content as animal>
    <#--        <#list animals as animal>-->
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
                    <td>${animal.deleted?then('<a href="animals?repairAnimal=${animal.id}">repair</a>',
                        '<a href="animals?removeAnimal=${animal.id}">delete</a>')}</td>
                </tr>

            </#list>
        </table>
        <@p.pager url page/>
    <#else>
        <h3>Our animal list is empty</h3>
    </#if>
