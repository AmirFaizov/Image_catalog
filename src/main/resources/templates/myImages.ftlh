<#import "parts/common.ftlh" as c>

<@c.page>

<div class="card-columns">
    <#list pictures.content as picture>
            <div class="card">
                <img src="files/${picture.imageInfo.name}" class="card-img-top" alt="${picture.name}">
                <div class="card-body">
                    <h5 class="card-title">${picture.name}</h5>
                    <p class="card-text">${picture.descr}</p>
                    <form method="POST" action="/picture/${picture.id}/visibility">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                        <label for="visibility_${picture.id}">Visibility:</label>
                        <select name="visibility" id="visibility/${picture.id}">
                            <option value="PUBLIC" <#if picture.visibility == "public">selected</#if>>Public</option>
                            <option value="PRIVATE" <#if picture.visibility == "private">selected</#if>>Private</option>
                        </select>
                        <button type="submit">Change Visibility</button>
                    </form>
                    <p class="card-text"><small class="text-muted">Uploaded by ${picture.account.firstName}</small></p>
                </div>
            </div>
    </#list>
</div>
</@c.page>