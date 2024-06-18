<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>

<@c.page>
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card">
                    <div class="card-header">
                        User Profile
                    </div>
                    <div class="card-body">
                        <div class="form-group row">
                            <label for="firstname" class="col-md-4 col-form-label">First Name:</label>
                            <div class="col-md-8">
                                <input type="text" id="firstname" name="firstname" class="form-control" value="${account.firstName}" readonly>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="lastname" class="col-md-4 col-form-label">Last Name:</label>
                            <div class="col-md-8">
                                <input type="text" id="lastname" name="lastname" class="form-control" value="${account.lastName}" readonly>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="email" class="col-md-4 col-form-label">Email:</label>
                            <div class="col-md-8">
                                <input type="email" id="email" name="email" class="form-control" value="${account.email}" readonly>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="role" class="col-md-4 col-form-label">Role:</label>
                            <div class="col-md-8">
                                <input type="text" id="role" name="role" class="form-control" value="${account.role}" readonly>
                            </div>
                        </div>
                        <#if account.isAdmin()>
                            <div class="form-group row">
                                <div class="col-md-8 offset-md-4">
                                    <a href="/accounts" class="btn btn-primary">View All Accounts</a>
                                </div>
                            </div>
                        </#if>
                        <form action="/logout" method="post">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                            <button type="submit" class="btn btn-danger btn-sm">Sign Out</button>
                        </form>
                    </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</@c.page>