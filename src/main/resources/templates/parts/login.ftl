<#macro login path isRegistredForm>
    <form action="${path}" method="post" xmlns="http://www.w3.org/1999/html">
        <input type="text" name="username" placeholder="Username">
        <input type="password" name="password" placeholder="Password">
        <button type="submit" class="btn btn-info">Login</button>
    </form>
</#macro>
<#macro logout>
    <form method="post" action="/logout">
        <button type="submit" class="btn btn-primary">Logout</button>
    </form>
</#macro>