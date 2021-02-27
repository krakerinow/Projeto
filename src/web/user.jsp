<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld"  prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-nested.tld"  prefix="nested" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld"  prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld"  prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-tiles.tld"  prefix="tiles" %>
<%@ page import="teste.domain.UserImpl" %>

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
        <body>
            <div id="load" ng-app="load" ng-controller="ctrl">
                <table>
                    <thead>
                        <tr>
                            <th>Nome</th>
                            <th>Username</th>
                            <th>Email</th>
                            <th>Password</th>
                            <th>Role</th>
                        </tr>
                    </thead>
                    <tbody ng-app="load" ng-controller="ctrl" ng-repeat="u in users" class="clearfix">
                        <tr>
                            <td>{{u.nome}}</td>
                            <td>{{u.username}}</td>
                            <td>{{u.email}}</td>
                            <td>{{u.password}}</td>
                            <td>{{u.roles}}</td>
                            <td>
                                <button ng-click="deleteUser(u)"><span class="glyphicon glyphicon-remove" onClick="refreshPage()"></span></button>
                            </td>
                        </tr>
                    </tbody>
                    <tbody>
                        <tr>
                            <td>
                                <input placeholder="Insira o nome" type="text" ng-model="u.nome">
                            </td>

                            <td>
                                <input  placeholder="Insira o username" type="text" ng-model="u.username">
                            </td>

                            <td>
                                <input placeholder="Insira o email" type="text" ng-model="u.email">
                            </td>
                            <td>
                                <input placeholder="Insira a password" type="password" ng-model="u.password">
                            </td>
                            <td>
                                <input placeholder="Insira os roles" type="text" ng-model="u.roles">
                            </td>
                            <td>
                                <button ng-click="SaveUser(u)"><span class="glyphicon glyphicon-ok" onClick="refreshPage()"></span></button>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>


            <div id="xpto">

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
                             //   $(divMensagens).append("<div class='msg'>" + resposta + "</div>");
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
                    }

                    $scope.adicionarUser = function(){
                        let s = {
                            '@class' : '<%=UserImpl.class.getName()%>'
                        }
                        $scope.users.push(s);

                    }

                    $scope.SaveUser = function (u){
                        send(
                            "user.ServicoUser",
                            "addUser",
                            u,
                            function(result)
                            {
                                angular.merge(u,result);
                                $scope.$apply();
                            },
                        );
                    }

                    $scope.deleteUser = function (u){
                        send(
                            "user.ServicoUser",
                            "deleteUser",
                            u,
                            function(result)
                            {
                                angular.merge(u,result);
                                $scope.$apply();
                            },
                        );
                    }


                    $scope.listarUsers();
                });
            </script>
        </body>
</html>


