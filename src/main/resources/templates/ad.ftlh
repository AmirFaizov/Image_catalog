<#import "parts/common.ftlh" as c>

<@c.page>
    <#if error?has_content>
        <div class="alert alert-danger">${error}</div>
    </#if>
    <form method="GET" action="/genre">
        <ul>
            <#list genres as genre>
                    <input type="checkbox" name="genreId" value="${genre.id}">
                    ${genre.name}
            </#list>
        </ul>
        <input type="submit" value="Filter">
    </form>
    <form action="/search" method="get">
        <label for="tag">Tag:</label>
        <input type="text" id="tag" name="search">
        <button type="submit" class="btn btn-primary">Search</button>
        <a href="/add" class="btn btn-primary">Add new picture</a>
        <a href="/my-images" class="btn btn-primary">My Images</a>
    </form>
    <form method="GET" action="/changed">
        <button type="submit" class="btn btn-primary">Show Changed Pictures</button>
    </form>
    <div class="card-columns">
        <#list pictures.content as picture>
            <#if picture.account.email != account && picture.visibility == "PRIVATE">
                </div>
                <#continue>
            </#if>
            <div class="card">
                <img src="files/${picture.imageInfo.name}" class="card-img-top" alt="${picture.name}">
                <div class="card-body">
                    <h5 class="card-title">${picture.name}</h5>
                    <p class="card-text">${picture.descr}</p>
                        <form method="POST" action="/picture/${picture.id}/duo">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                            <label for="x_${picture.id}">X:</label>
                            <input type="number" name="x" id="x_${picture.id}" value="0">
                            <br>
                            <label for="y_${picture.id}">Y:</label>
                            <input type="number" name="y" id="y_${picture.id}" value="0">
                            <br>
                            <label for="angle_${picture.id}">Angle:</label>
                            <input type="number" name="angle" id="angle_${picture.id}" value="0">
                            <select name="smile" id="smile_${picture.id}">
                                <br>
                                <option value="a">GOOD MORNING</option>
                                <option value="b">CLOUD</option>
                                <option value="c">SURPRISED CAT</option>
                            </select>
                            <button type="submit">Save</button>
                        </form>
            <#if picture.account.email != account>
                <p class="card-text"><small class="text-muted">Uploaded by ${picture.account.firstName}</small></p>
                </div>
                </div>
                <#continue>
                    <#elseif picture.account.email == account>
                        <form method="post" action="picture/${picture.id}/delete">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                            <button type="submit">delete</button>
                        </form>
                        <form method="POST" action="/picture/${picture.id}/visibility">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                        <label for="visibility_${picture.id}">Visibility:</label>
                        <#if picture.visibility == "PUBLIC">
                        <select name="visibility" id="visibility_${picture.id}">
                            <option value="PUBLIC" <#if picture.visibility == "PUBLIC">selected</#if>>Public</option>
                            <option value="PRIVATE" <#if picture.visibility == "PRIVATE">selected</#if>>Private</option>
                        </select>
                        <#elseif picture.visibility == "PRIVATE">
                        <select name="visibility" id="visibility_${picture.id}">
                            <option value="PRIVATE" <#if picture.visibility == "PRIVATE">selected</#if>>Private</option>
                            <option value="PUBLIC" <#if picture.visibility == "PUBLIC">selected</#if>>Public</option>
                        </select>
                        </#if>
                        <button type="submit">Change Visibility</button>
                    </form>
                    </#if>
                    <p class="card-text"><small class="text-muted">Uploaded by ${picture.account.firstName}</small></p>
                </div>
            </div>
        </#list>
    </div>
    <div>
        <ul class="pagination">
            <li class="page-item disabled">
                <a class="page-link" href="#" tabindex="-1">Страницы</a>
            </li>
        <#list 0..pictures.getTotalPages() -1 as p>
            <#if p==pictures.getNumber()>
                <li class="page-item active">
                    <a class="page-link" href="#" tabindex="-1">${p}</a>
                </li>
            <#else>
                <li class="page-item">
                    <a class="page-link" href="${url}?page=${p}" tabindex="-1">${p}</a>
                </li>
            </#if>
        </#list>
        </ul>
    </div>
</@c.page>