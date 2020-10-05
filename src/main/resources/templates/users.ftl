<#import "parts/pageTemplate.ftl" as pt>

<@pt.page>
    <h1>List of users</h1>
    <table>
        <thead>
        <tr>
            <th>Name</th>
            <th>Role</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <#list users as user>
            <tr>
                <td>${user.username}</td>
                <td><#list user.roles as role>${role}<#sep>, </#list></td>
                <td><a href="/users/${user.id}">edit</a></td>
                <td>
                    <a href="animals/addanimal/${user.id}">Add animal</a>
                </td>
            </tr>
        </#list>
        </tbody>
    </table>
</@pt.page>