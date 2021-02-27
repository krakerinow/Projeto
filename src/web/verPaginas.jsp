<%@ page import="teste.domain.SectionImpl" %>
<%@ page import="teste.domain.PageImpl" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld"  prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-nested.tld"  prefix="nested" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld"  prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld"  prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-tiles.tld"  prefix="tiles" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>


    <style>
        body{
            background-color: silver;
        }


    </style>

<body>
<%
    String id = request.getParameter("id");
%>

<div id="load" ng-app="load" ng-controller="ctrl">
    <h2 class="title is-1 has-text-centered has-text-white">{{ page.title }}</h2>

    <div ng-repeat="s in page.sections" style="padding-bottom: 1em;">
        <h2>Section Title.:</h2>
        <h3>{{ s.title }}</h3>
        <h2>Component.:</h2>
        <div ng-repeat="c in sections" style="padding-bottom: 1em;">
            <h3>{{ c.text }}</h3>
            <h3>{{ c.path }}</h3>
        </div>
        <hr/>
    </div>
</div>
</body>

<script>
    function send(servico, metodo, data, callbackOk){
        $.ajax({
            type: "POST",
            url: "<%=request.getContextPath()%>/soa",
            contentType: "application/json",
            dataType: 'json',
            data:  JSON.stringify({
                servico: servico,
                metodo: metodo,
                data: data,
            }),
            success: function(result){
                console.log(JSON.stringify(result));
                callbackOk(result.data);
            },
            statusCode:{
                401: function(){
                    console.log(401);
                }
            },
            error: function(resposta)
            {

            }
        });
    }

    let app = angular.module("load", []);
    app.controller("ctrl", function($scope) {

        $scope.pageid = <%=id%>;
        $scope.page = {
            sections: [
                { components: [] }
            ]
        };
        $scope.secId=[];


        $scope.loadPage = function(){
            send(
                "Page.ServicoPage",
                "loadPage",
                {
                    id: $scope.pageid,
                },
                function(result) {
                    $scope.page = result;
                    $scope.$apply();
                },
                function(erro) {
                    alert(erro);
                    $scope.$apply();
                }
            );
        }

        $scope.listarcomponents = function(){
            send(
                "section.ServicoSection",
                "Components",
                {
                    idpag: $scope.pageid,
                },
                function(result)
                {
                    $scope.sections= result;
                    $scope.$apply();
                },
            );
        }

        $scope.loadPage();
        $scope.listarcomponents();
    });
</script>
</body>
</html>