<%@ page import="teste.domain.PageImpl" %>
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
    <body>
        <div id="load" ng-app="load" ng-controller="ctrl">
            <table>
                <thead>
                <tr>
                    <th>id</th>
                    <th>title</th>
                    <th>role</th>
                </tr>
                </thead>
                <tbody ng-app="load" ng-controller="ctrl" ng-repeat="u in paginas" class="clearfix">
                <tr>
                    <td>{{u.id}}</td>
                    <td>{{u.title}}</td>
                    <td>{{u.roles}}</td>
                    <td>
                        <a class="button" href="<%=request.getContextPath()%>/editPage.do?id={{u.id}}">
                            <span class="glyphicon glyphicon-cog" style="color: black; padding-left: 13px;"/>
                        </a>
                    </td>
                    <td>
                        <button ng-click="deletePaginas(u)"><span class="glyphicon glyphicon-remove"></span></button>
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
                        <input type="text" ng-model="u.roles">
                    </td>
                    <td>
                        <button ng-click="SavePaginas(u)"><span class="glyphicon glyphicon-ok" ></span></button>
                    </td>
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
                $scope.paginas = [];

                $scope.listarPaginas = function(){
                    send(
                        "Page.ServicoPage",
                        "loadAll",
                        {},
                        function(result)
                        {
                            $scope.paginas = result;
                            $scope.$apply();
                        },
                    );
                }

                $scope.adicionarPagina = function(){
                    let s = {
                        '@class' : '<%=PageImpl.class.getName()%>'
                    }
                    $scope.paginas.push(s);

                }

                $scope.SavePaginas = function (u){
                    send(
                        "Page.ServicoPage",
                        "addPage",
                        u,
                        function(result)
                        {
                            angular.merge(u,result);
                            $scope.$apply();
                        },
                    );
                }

                $scope.deletePaginas = function (u){
                    send(
                        "Page.ServicoPage",
                        "deletePage",
                        u,
                        function(result)
                        {
                            angular.merge(u,result);
                            $scope.$apply();
                        },
                    );
                }


                $scope.listarPaginas();
            });
        </script>
    </body>
</html>