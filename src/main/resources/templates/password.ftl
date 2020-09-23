<#import "parts/pageTemplate.ftl" as pt>
<#--<#include "parts/security.ftl">-->

<@pt.page>
    <form method="post" action="/user/password" class="form-group">
        <input name="oldPassword" type="password"
               class="form-control ${(oldPasswordError??)?string('is-invalid', '')}">
        <#if oldPasswordError??>
            <div class="invalid-feedback">
                ${oldPasswordError}
            </div>
        </#if>
        <input name="newPassword" type="password"
               class="form-control ${(newPasswordError??)?string('is-invalid', '')}">
        <#if newPasswordError??>
            <div class="invalid-feedback">
                ${newPasswordError}
            </div>
        </#if>
        <input name="confirmPassword" type="password"
               class="form-control ${(confirmPasswordError??)?string('is-invalid', '')}">
        <#if confirmPasswordError??>
            <div class="invalid-feedback">
                ${confirmPasswordError}
            </div>
        </#if>
        <button type="submit">Submit</button>
    </form>
</@pt.page>