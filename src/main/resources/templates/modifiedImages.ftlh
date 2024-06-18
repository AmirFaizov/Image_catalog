<#import "parts/common.ftlh" as c>

<@c.page>
    <div class="card-columns">
        <#list pictures as picture>
        <div class="card">
        <img src="files/${picture.imageInfo.name}" class="card-img-top" alt="${picture.name}">
        <div class="card-body">
        <h5 class="card-title">${picture.name}</h5>
        <p class="card-text">${picture.descr}</p>
        <#if picture.visibility == "PRIVATE" && picture.account.email != account>
            </div>
            </div>
            </div>
            <#continue>
        <#elseif picture.visibility == "PUBLIC" || picture.account.email == account>
            <#if picture.account.email != account>
                <p class="card-text"><small class="text-muted">Uploaded by ${picture.account.firstName}</small></p>
                </div>
                </div>
                <#continue>
            </#if>
            <p class="card-text"><small class="text-muted">Uploaded by ${picture.account.firstName}</small></p>
            </div>
            </div>
        </#if>
    </#list>
    </div>
</@c.page>