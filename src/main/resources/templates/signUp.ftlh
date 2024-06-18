<!DOCTYPE html>
<#import "spring.ftl" as spring/>
<html>
<head>
    <meta charset="UTF-8">
    <title><@spring.message'signUp.page.title'/></title>
    <style>
        .error{
            color: red;
        }
    </style>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
</head>
<body>
<#include "parts/navbar.ftlh">
<#if error?has_content>
    <div class="alert alert-danger">${error}</div>
</#if>
<h1><@spring.message'signUp.header'/></h1>
<h2><@spring.message'signUp.page.description'/></h2>
<form action="/signUp" method="post">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <label for="firstName"><@spring.message 'signUp.form.label.enterFirstName'/></label>
    <#assign firstname><@spring.message code="signUp.form.placeholder.firstName"/></#assign>
    <@spring.formInput "signUpForm.firstName" "placeholder=${firstname}"/>
    <@spring.showErrors "<br>" "error"/>
    <br>
    <br>
    <label for="lastName"><@spring.message'signUp.form.label.enterLastName'/></label>
    <#assign lastname><@spring.message code="signUp.form.placeholder.lastName"/></#assign>
    <@spring.formInput "signUpForm.lastName" "placeholder=${lastname}"/>
    <@spring.showErrors "<br>" "error"/>
    <br>
    <br>
    <label for="email"><@spring.message'signUp.form.label.enterEmail'/></label>
    <#assign email><@spring.message code="signUp.form.placeholder.email"/></#assign>
    <@spring.formInput "signUpForm.email" "placeholder=${email}"/>
    <@spring.showErrors "<br>" "error"/>
    <br>
    <br>
    <label for="password"><@spring.message'signUp.form.label.enterPassword'/></label>
    <#assign password><@spring.message code="signUp.form.placeholder.password"/></#assign>
    <@spring.formPasswordInput "signUpForm.password" "placeholder=${password}"/>
    <@spring.showErrors "<br>" "error"/>
    <input type="submit" value=<@spring.message'singUp.button'/>>
</form>
</body>
</html>