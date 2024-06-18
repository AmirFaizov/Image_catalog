<#macro pager url pictures>
    <div>
        <ul class="pagination">
            <li class="page-item disabled">
                <a class="page-link" href="#" tabindex="-1">Страницы</a>
            </li>
        </ul>
        <#list 1..pictures.getTotalPages() as p>
            <#if p==pictures.getNumber()>
        <li class="page-item active">
            <a class="page-link" href="#" tabindex="-1">${p}</a>
        </li>
        <#else>
         <li class="page-item">
            <a class="page-link" href="url?page=${p}" tabindex="-1">${p}</a>
        </li>
        </#if>
        </#list>
    </div>
</#macro>