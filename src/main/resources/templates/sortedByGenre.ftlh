<#import "parts/common.ftlh" as c>

<@c.page>
    <form method="GET" action="/genre">
        <ul>
            <#list genres as genre>
                <input type="checkbox" name="genreId" value="${genre.id}">
                ${genre.name}
            </#list>
        </ul>
        <input type="submit" value="Filter">
    </form>
<div class="card-columns">
    <#list pictures as picture>
        <div class="card">
            <img src="files/${picture.getImageInfo().getName()}" class="card-img-top img-fluid" alt="${picture.name}">
            <div class="card-body">
                <h5 class="card-title">${picture.name}</h5>
                <p class="card-text">${picture.descr}</p>
                <p class="card-text"><small class="text-muted">Uploaded by ${picture.account.firstName}</small></p>
            </div>
        </div>
    </#list>
</div>
</@c.page>