<#import "parts/pageTemplate.ftl" as pt>
<#include "parts/security.ftl">

<@pt.page>

    <p>${user.username}</p>
    <p>${user.firstname}</p>
    <p>${user.lastname}</p>
    <p>${user.surname}</p>
    <p>${user.birthDate}</p>
    <p>${user.email} <a href="/user/email" class="btn btn-primary">Сменить email</a></p>
    <p><a href="/user/password" class="btn btn-primary">Сменить пароль</a></p>




</@pt.page>

