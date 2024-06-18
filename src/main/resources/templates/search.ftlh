<#import "parts/common.ftlh" as c>
<@c.page>
    <div class="card-columns">
        <#list pictures.content as picture>
            <div class="card">
                <img src="files/${picture.imageInfo.name}" class="card-img-top" alt="${picture.name}">
                <div class="card-body">
                    <h5 class="card-title">${picture.name}</h5>
                    <p class="card-text">${picture.descr}</p>
                </div>
            </div>
        </#list>
    </div>
    <div>
        <ul class="pagination">
            <li class="page-item disabled">
                <a class="page-link" href="#" tabindex="-1">Страницы</a>
            </li>
            <#list 0..pictures.getTotalPages() as p>
                <#if p==pictures.getNumber()>
                    <li class="page-item active">
                        <a class="page-link" href="#" tabindex="-1">${p}</a>
                    </li>
                <#else>
                    <li class="page-item">
                        <a class="page-link" href="${urlForPagination}?page=${p}&search=${searchString}" tabindex="-1">${p}</a>
                    </li>
                </#if>
            </#list>
        </ul>
    </div>
</@c.page>