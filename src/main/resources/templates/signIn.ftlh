<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>

<@c.page>
<h2>SignIn</h2>
<@l.login "/signIn" />
    <a href="/signUp">Add new user</a>
</@c.page>