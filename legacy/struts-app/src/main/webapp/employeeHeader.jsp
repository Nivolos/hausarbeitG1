<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<nav class="navbar navbar-expand-lg navbar-light bg-light mb-3" style="background-color: #e3f2fd;">
    <!-- Navbar content -->
    <s:a action="lendingProcessList" class="navbar-brand">
        <s:text name="title.application.user" />
    </s:a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarText" aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse show navbar-collapse" id="navbarText">
        <ul class="navbar-nav mr-auto">
        <li class="nav-item active">
            <s:a action="showSearch" class="nav-link">
                <s:text name="navbar.admin.publication"/>
            </s:a>
        </li>
            <shiro:hasRole name="admin">
            <!-- Employee menu -->
            <li class="nav-item active">
                <s:a action="newLendingProcess" class="nav-link">
                    <s:text name="navbar.employee.lendingprocess.new"/>
                </s:a>
            </li>
            <li class="nav-item active">
                <s:a action="lendingProcessList" class="nav-link">
                    <s:text name="navbar.employee.lendingprocess.return"/>
                </s:a>
            </li>
            <li class="nav-item dropdown active">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    <s:text name="title.application.admin"/>
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                    <s:a action="libraryUserList" class="dropdown-item">
                        <s:text name="navbar.admin.users"/>
                    </s:a>
                    <s:a action="adminKeywordList" class="dropdown-item">
                        <s:text name="navbar.admin.keywords"/>
                    </s:a>
                    <s:a action="newPublication" class="dropdown-item">
                        <s:text name="navbar.admin.publication.create"/>
                    </s:a>
                    <s:a action="adminPublicationTypeList" class="dropdown-item">
                        <s:text name="navbar.admin.publicationtypes"/>
                    </s:a>
                    <s:a action="adminAuthorList" class="dropdown-item">
                        <s:text name="navbar.admin.authors"/>
                    </s:a>
                </div>
            </li>
            </shiro:hasRole>
        </ul>
    </div>
    <s:a action="logout" class="nav-item mr-3 nav-link btn btn-secondary"
         style="">Logout</s:a>
</nav>