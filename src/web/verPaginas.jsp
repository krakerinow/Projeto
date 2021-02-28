<%@ page import="teste.domain.SectionImpl" %>
<%@ page import="teste.domain.PageImpl" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld"  prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-nested.tld"  prefix="nested" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld"  prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld"  prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-tiles.tld"  prefix="tiles" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
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
    <div id="load" ng-app="load" ng-controller="ctrl" style="padding-left:20px;padding-right: 20px ">
        <div class="w3-panel w3-dark-gray w3-round-xlarge" style="padding:inherit;text-align: center;"><b style="color: white;font-size:25px;font-family: Serif ">{{page.title}}</b></div>
        <div id="accordion" ng-repeat="s in page.sections" style="padding-top: 1% ;" >
            <div class="panel panel-default w3-round-xlarge " >
                <div class="panel-heading w3-grey w3-round-xlarge" >
                    <h4 class="panel-title" style="text-align: center;font-family: -webkit-pictograph " >
                        <b> {{s.title}}</b>
                    </h4>
                </div>
                <div id="collapse1" class="panel-collapse collapse in" ng-repeat="c in s.components">
                    <div class="panel-body" style="text-align: center">{{c.text}}</div>
                    <div ng-if="c.path" class="panel-body" style="text-align: center">
                        <img src="imagens/{{c.path}}"/>
                    </div>
                </div>
            </div>
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

        $scope.loadPage();
    });
</script>
</body>
</html>