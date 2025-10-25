<!doctype html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<html>
<head>
    <title><s:text name="title.application.user"/></title>
    <s:head/>
    <!-- Add custom style-->
    <link href="css/style.css" rel="stylesheet" type="text/css" />
    <!-- Add bootstrap CSS dependency via CDN -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <!-- Add bootstrap select dependency via CDN: CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/css/bootstrap-select.min.css">

</head>
<body>

<!-- Header --->
<tiles:insertAttribute name="header"/>

<!-- Action Errors and Messages via Bootstrap Alerts -->
<div class="container">
    <div class="row">
        <div class="col">
            <s:if test="hasActionErrors()">
                <div class="alert alert-danger" role="alert">
                    <div class="container">
                        <div class="row">
                            <div class="col-md-9">
                                <s:iterator value="actionErrors">
                                    <s:property/><br/>
                                </s:iterator>
                            </div>
                            <div class="col-md-3">
                                <img class="float-right" src="../img/alert.png" style="width: 10vw;" />
                            </div>
                        </div>
                    </div>
                </div>
            </s:if>
            <s:if test="hasActionMessages()">
                <div class="alert alert-info" role="alert">
                    <s:iterator value="actionMessages">
                        <s:property/><br/>
                    </s:iterator>
                </div>
            </s:if>
        </div>
    </div>
</div>

<!-- Bootstrap container -->
<div class="container-fluid" style="width: 90%;">
    <div class="row">
        <div class="col">
            <!-- Content --->
            <tiles:insertAttribute name="content"/>
        </div>
    </div>
</div>



<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.7/dist/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
<!-- Add bootstrap select dependency via CDN: Javascript -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/js/bootstrap-select.min.js"></script>
<!-- Add bootstrap select dependency via CDN: JS Translation (optional) -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/js/i18n/defaults-*.min.js"></script>
</body>

<footer class="bg-light text-center text-lg-start mt-5">
    <!-- Grid container -->
    <div class="container p-4">
        <!--Grid row-->
        <div class="row">
            <!--Grid column-->
            <div class="col-lg-5 col-md-12 mb-4 mb-md-0">
                <h5 class="text-uppercase"><s:text name="footer.description.company.quality.title"/></h5>
                <p>
                    <s:text name="footer.description.company.quality"/>
                </p>
            </div>
            <!--Grid column-->

            <!-- Grid column-->
            <div class="col-lg-2 col-md-12 mb-4 mb-md-0">
                <img src="../img/footer-logo.png" alt="Pinguin AG" width="100%" style="display: block; margin: auto;"/>
            </div>

            <!--Grid column-->
            <div class="col-lg-5 col-md-12 mb-4 mb-md-0">
                <h5 class="text-uppercase"><s:text name="footer.description.company.usecase.title"/></h5>

                <p>
                    <s:text name="footer.description.company.usecase"/>
                </p>
            </div>
            <!--Grid column-->
        </div>
        <!--Grid row-->
    </div>
    <!-- Grid container -->

    <!-- Copyright -->
    <div class="text-center p-3" style="background-color: rgba(0, 0, 0, 0.2);">
        © 2022 Copyright:
        <a class="text-dark" href="#"><s:text name="footer.copyright.url"/></a>
    </div>
    <!-- Copyright -->
</footer>
</html>