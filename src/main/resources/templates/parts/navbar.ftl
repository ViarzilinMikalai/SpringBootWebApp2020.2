<#include "security.ftl">
<#import "login.ftl" as log>
<nav class="navbar navbar-expand-lg navbar-light bg-light">

    <!-- Links -->
    <ul class="navbar-nav mr-auto">
        <li class="nav-item">
            <a class="nav-link" href="/">Главная</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="/registration">Регистрация</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="/animals">Питомцы</a>
        </li>
        <#if isAdmin?? && isAdmin!>
            <li class="nav-item">
                <a class="nav-link" href="/users">Пользователи</a>
            </li>
        </#if>
    </ul>
    <div class="navbar-text mr-3">
        <#if user??><a href="/user">${name}</a>
            <@log.logout/>
        <#else>
            Guest
        </#if>

    </div>
</nav>