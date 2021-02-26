<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld"  prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-nested.tld"  prefix="nested" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld"  prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld"  prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-tiles.tld"  prefix="tiles" %>
<%@ page import="teste.servicos.user.ServicoUser" %>

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
        <body>
            <div id="load" ng-app="load" ng-controller="ctrl">
                <table>
                    <thead>
                        <tr>
                            <th>Nome</th>
                            <th>username</th>
                            <th>email</th>
                            <th>role</th>
                        </tr>
                    </thead>
                    <tbody ng-app="load" ng-controller="ctrl" ng-repeat="u in users" class="clearfix">
                        <tr>
                            <td>{{u.nome}}</td>
                            <td>{{u.username}}</td>
                            <td>{{u.email}}</td>
                            <td>{{u.roles}}</td>
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
                    $scope.users = [];

                    $scope.listarUsers = function(){
                        send(
                            "user.ServicoUser",
                            "loadAll",
                            {},
                            function(result)
                            {
                                $scope.users = result;
                                $scope.$apply();
                            },
                        );
                    };

                    $scope.listarUsers();
                });
            </script>
        </body>
</html>


