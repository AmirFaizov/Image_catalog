<#import "parts/common.ftlh" as c>
<@c.page>
    <div class="row">
        <div class="col-md-6">
            <h2>Аккаунты</h2>
            <table class="table table-striped">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                <#list accounts as account>
                    <tr>
                        <td>${account.id}</td>
                        <td>${account.firstName}</td>
                        <td>${account.lastName}</td>
                        <td>
                            <form method="post" action="/accounts/${account.id}/delete">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                                <input type="submit" class="btn btn-danger" value="Забанить">
                            </form>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </div>
        <div class="col-md-6">
            <h2>Забаненные аккаунты</h2>
            <table class="table table-striped">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                <#list bannedAccounts as account>
                    <tr>
                        <td>${account.id}</td>
                        <td>${account.firstName}</td>
                        <td>${account.lastName}</td>
                        <td>
                            <form method="post" action="/accounts/${account.id}/recover">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                                <input type="submit" class="btn btn-success" value="Разбанить">
                            </form>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </div>
    </div>
</@c.page>