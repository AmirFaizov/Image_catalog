<#import "parts/common.ftlh" as c>
<#import "spring.ftl" as spring/>

<@c.page>
    <form  method="post" action="/add/file" enctype="multipart/form-data">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
        <div class="form-group">
            <label for="name">Enter name:</label>
            <input id="name" name="name" class="form-control" placeholder="Your picture name">
        </div>
        <div class="form-group">
            <label for="descr">Enter description:</label>
            <input id="descr" name="descr" class="form-control" placeholder="Your description">
        </div>
        <div class="form-group">
            <label for="genres">Choose genres:</label>
            <select multiple class="form-control" id="genres" name="genreIds">
                <#list genres as genre>
                    <option value="${genre.id}">${genre.name}</option>
                </#list>
            </select>
        </div>
        <div class="form-group">
            <label for="file">Choose file:</label>
            <input type="file" name="file" id="file" class="form-control-file">
        </div>
        <button type="submit" class="btn btn-primary">Add Picture</button>
    </form>
    <#if error?has_content>
        <div class="alert alert-danger">${error}</div>
    </#if>
</@c.page>