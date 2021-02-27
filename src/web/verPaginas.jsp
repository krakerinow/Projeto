<%@ page import="teste.domain.SectionImpl"%>
<%@ page import="teste.domain.ComponentsImpl" %>
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
            background-color: #333333;
            color: #ffffff;
        }


    </style>

<body>
<%
    String id = request.getParameter("id");
%>

<div id="myApp" ng-app="myApp" ng-controller="myCtrl">
    <div ng-repeat="p in page" style="padding-bottom: 1em;">
        <h2> {{ p.title }}</h2>
    <div ng-repeat="s in p.sections" style="padding-bottom: 1em;">
        listarComponentes(s);
        <h2>Section Title.:</h2>
        <h3>{{ s.title }}</h3>
        <h2>Component.:</h2>
        <div ng-repeat="c in s.components" style="padding-bottom: 1em;">

            <h3>&nbsp;{{ c.text }}</h3>
        </div>
    </div>
</div>

</div>

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

    let app = angular.module("myApp", []);
    app.controller("myCtrl", function($scope) {

        $scope.id = <%=id%>;
        $scope.page = {
            sections: [
                { components: [] }
            ]
        };

        $scope.loadPage = function(){
            send(
                "Page.ServicoPage",
                "loadPage",
                {
                    id: $scope.id,
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
        };

        $scope.listarcomponents = function(s){
            send(
                "section.ServicoSection",
                "returnComponents",
                {
                    idpag: $scope.id,
                    id:$scope.page.sections[s].id,
                },
                function(result)
                {
                    $scope.sections= result;
                    $scope.$apply();
                },
            );
        }

        for(var i=0;i<$scope.page.sections.length;i++){
            $scope.listarcomponents();
        }
        $scope.loadPage();
    });
</script>
</body>
</html>