<%@ page import="teste.domain.SectionImpl" %>
<%@ page import="teste.domain.ComponentsImpl" %>
<%@ page import="teste.domain.ComponentTextImpl" %>
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
        margin-left: 30px;
        margin-top: 30px;
    }

    td, th {
        border: 1px solid #dddddd;
        text-align: left;
        padding: 8px;
    }

    tr:nth-child(even) {
        background-color: #dddddd;
    }
</style>


<html>
<%
    String id = request.getParameter("id");
%>
<div id="load" ng-app="load" ng-controller="ctrl">
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Titulo da Secção</th>
        </tr>
        </thead>
        <tbody ng-app="load" ng-controller="ctrl" ng-repeat="u in sections" class="clearfix">
        <tr>
            <td>{{u.id}}</td>
            <td>{{u.title}}</td>
            <td>
                <a class="button" href="<%=request.getContextPath()%>/editSection.do?id={{u.id}}">
                    <span class="glyphicon glyphicon-cog" style="color: black; padding-left: 9px;"/>
                </a>
            </td>
            <td>
                <button ng-click="deleteSections(u)"><span class="glyphicon glyphicon-remove" onClick="refreshPage()"></span></button>
            </td>
        </tr>
        </tbody>
        <tbody>
        <tr>
            <td>
            </td>
            <td>
                <input type="text" ng-model="u.title">
            </td>
            <td>
                <button ng-click="SaveSections(u)"><span class="glyphicon glyphicon-ok" onClick="refreshPage()" ></span></button>
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
        $scope.page = {
            sections: [
                { components: [] }
            ]
        };

        $scope.listarsections = function(){
            send(
                "section.ServicoSection",
                "returnSections",
                {
                    id: $scope.id,
                },
                function(result)
                {
                    $scope.sections = result;
                    $scope.$apply();
                },
            );
        }


        $scope.deleteSections = function (u){
            send(
                "section.ServicoSection",
                "deleteSection",
                {
                    id: u.id,
                    title: u.title,
                },
                function(result)
                {
                    angular.merge(u,result);
                    $scope.$apply();
                },
            );
        }

        $scope.SaveSections = function (u){
            send(
                "section.ServicoSection",
                "addSection",
                {
                    idPage: $scope.id,
                    title: u.title,
                },
                function(result)
                {
                    angular.merge(u,result);
                    $scope.$apply();
                },
            );
        }

        $scope.listarsections();
    });
</script>
</body>
</html>