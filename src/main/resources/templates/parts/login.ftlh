<#macro login path>
    <form class="form-signin" action="/signIn" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
        <div class="form-label-group">
        <input type="email" id="inputEmail" name="email" class="form-control" placeholder="Email address" required autofocus>
        <label for="inputEmail">Email address</label>
    </div>

    <div class="form-label-group">
        <input type="password" id="inputPassword" name="password" class="form-control" placeholder="Password" required>
        <label for="inputPassword">Password</label>
    </div>

    <button class="btn btn-lg btn-primary btn-block text-uppercase" type="submit">Sign in</button>
</form>
</#macro>
