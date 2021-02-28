<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld"  prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-nested.tld"  prefix="nested" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld"  prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld"  prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-tiles.tld"  prefix="tiles" %>

<style>
    body{
        background-color: silver;
    }

    table{
        display: table;
        text-align: center;
        position: absolute;
        margin: auto;
        width: 100%;
    }

    td, th {
        border: 1px solid #dddddd;
        text-align: left;
        padding: 8px;
    }

    tr:nth-child(even) {
        background-color: #dddddd;
    }
    input{
        width: 100%;
    }
</style>


<html>
<%
    String id = request.getParameter("id");
    String idpag = request.getParameter("idpag");
%>
<div id="load" ng-app="load" ng-controller="ctrl">
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Texto do componente</th>
            <th>Path da imagem</th>
        </tr>
        </thead>
        <tbody ng-app="load" ng-controller="ctrl" ng-repeat="u in sections" class="clearfix">
        <tr>
            <td>{{u.id}}</td>
            <td>{{u.text}}</td>
            <td>{{u.path}}</td>
            <td>
                <button ng-click="deleteComponentes(u)"><span class="glyphicon glyphicon-remove" onClick="refreshPage()"></span></button>
            </td>
        </tr>
        </tbody>
        <tbody>
        <tr>
            <td>
            </td>
            <td>
                <input  placeholder="Insira o texto" type="text" ng-model="u.text">
            </td>
            <td>
                <input  placeholder="Insira o path da imagem" type="text" ng-model="u.path">
            </td>
            <td>
                <button ng-click="SaveComponentes(u)"><span class="glyphicon glyphicon-ok" onClick="refreshPage()" ></span></button>
            </td>
        </tr>
        </tbody>
    </table>
</div>
<script>

    function refreshPage(){
        window.location.reload();
    }

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

        $scope.id = <%=id%>;
        $scope.idpag = <%=idpag%>;
        $scope.page = {
            sections: [
                { components: [] }
            ]
        };

        $scope.listarcomponents = function(){
            send(
                "section.ServicoSection",
                "returnComponents",
                {
                    idpag: $scope.idpag,
                    id: $scope.id,
                },
                function(result)
                {
                    $scope.sections = result;
                    $scope.$apply();
                },
            );
        }


        $scope.deleteComponentes = function (u){
            send(
                "componentes.ServicoComponentes",
                "deleteComponent",
                {
                    id: u.id,
                },
                function(result)
                {
                    angular.merge(u,result);
                    $scope.$apply();
                },
            );
        }

        $scope.SaveComponentes = function (u){
            send(
                "componentes.ServicoComponentes",
                "addComponent",
                {
                    idSection: $scope.id,
                    path: u.path,
                    text: u.text,
                },
                function(result)
                {
                    angular.merge(u,result);
                    $scope.$apply();
                },
            );
        }

        $scope.listarcomponents();
    });
</script>
</body>
</html>