<#import "parts/pageTemplate.ftl" as pt>

<@pt.page>
    <form method="post" action="/user/email" class="form-group">
        <input type="email" name="oldEmail"
               class="form-control ${(oldEmailError?? || oldEmailEmpty??)?string('is-invalid', '')}"
               value="<#if oldEmail??>${oldEmail}</#if>"
               placeholder="Введите ваш e-mail">
        <#if oldEmailError??>
            <div class="invalid-feedback">
                ${oldEmailError}
            </div>
        </#if>
        <#if oldEmailEmpty??>
            <div class="invalid-feedback">
                ${oldEmailEmpty}
            </div>
        </#if>
        <input type="email" name="newEmail"
               class="form-control ${(newEmailError?? || notUniqueError??)?string('is-invalid', '')}"
               value="<#if newEmail??>${newEmail}</#if>"
               placeholder="Введите ваш e-mail">
        <#if newEmailError??>
            <div class="invalid-feedback">
                ${newEmailError}
            </div>
        </#if>
        <#if notUniqueError??>
            <div class="invalid-feedback">
                ${notUniqueError}
            </div>
        </#if>
        <button class="btn btn-info" type="submit">Submit</button>
    </form>
</@pt.page>