<%@ page import="teste.domain.SectionImpl" %>
<%@ page import="teste.domain.ComponentsImpl" %>
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

<div id="load" ng-app="load" ng-controller="ctrl">
    <table>
        <thead>
        <tr>
            <th>Titulo da Secção</th>
        </tr>
        </thead>
        <tbody ng-app="load" ng-controller="ctrl" ng-repeat="u in sections" class="clearfix">
            <tr>
                <td>{{u.titulo}}</td>
            </tr>
            <tr>
                <table>
                    <thead>
                        <tr>Titulo do componente</tr>
                        <tr>Texto</tr>
                    </thead>
                    <tbody ng-app="load" ng-controller="ctrl" ng-repeat="s in components" class="clearfix">
                        <tr>{{s.titulo}}</tr>
                        <tr>{{s.texto}}</tr>
                    </tbody>
                </table>
            </tr>
        </tbody>
    </table>
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
    let app = angular.module("load", []);
    app.controller("ctrl", function($scope) {
        $scope.components = [];
        $scope.sections = [];

        $scope.listarsections = function(){
            send(
                "section.ServicoSection",
                "returnAll",
                {},
                function(result)
                {
                    $scope.sections = result;
                    $scope.$apply();
                },
            );
        }

        $scope.adicionarSection = function(){
            let s = {
                '@class' : '<%=SectionImpl.class.getName()%>'
            }
            $scope.sections.push(s);

        }

        $scope.saveSections = function (u){
            send(
                "section.ServicoSection",
                "addSection",
                u,
                function(result)
                {
                    angular.merge(u,result);
                    $scope.$apply();
                },
            );
        }

        $scope.deleteSections = function (u){
            send(
                "section.ServicoSection",
                "deleteSection",
                u,
                function(result)
                {
                    angular.merge(u,result);
                    $scope.$apply();
                },
            );
        }


        $scope.adicionarcomponents = function(){
            let s = {
                '@class' : '<%=ComponentsImpl.class.getName()%>'
            }
            $scope.components.push(s);

        }

        $scope.saveSections = function (u){
            send(
                "section.ServicoSection",
                "addSection",
                u,
                function(result)
                {
                    angular.merge(u,result);
                    $scope.$apply();
                },
            );
        }

        $scope.deleteSections = function (u){
            send(
                "section.ServicoSection",
                "deleteSection",
                u,
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