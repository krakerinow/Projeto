<%@ page import="teste.domain.PageImpl" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<body>
<div id="myApp" ng-app="myApp" ng-controller="myCtrl">
    <div id="load" ng-app="load" ng-controller="ctrl">
        <table>
            <thead>
            <tr>
                <th>Titulo</th>
                <th>Roles</th>
                <th>Owner</th>
            </tr>
            </thead>
            <tbody ng-app="load" ng-controller="ctrl" ng-repeat="p in pages" class="clearfix">
            <tr>
                <td>{{p.title}}</td>
                <td>{{u.roles}}</td>
                <td>{{u.owner}}</td>
                    <button ng-click="removePage(p)"><span class="glyphicon glyphicon-remove"></span></button>
                </td>
            </tr>
            </tbody>
            <tbody>
            <tr>
                <td>
                    <input type="text" ng-model="p.title">
                </td>

                <td>
                    <input type="text" ng-model="p.roles">
                </td>
                <td>
                    <button ng-click="AddPage(u)"><span class="btn btn-success" ></span></button>
                </td>
            </tr>
            </tbody>
        </table>
    </div>





</div>

<script>
    function send(serviceName, method, divMensagens, data, callbackOk, callbackErro){
        $.ajax({
            type: "POST",
            url: "<%=request.getContextPath()%>/soa",
            contentType: "application/json",
            dataType: 'json',
            data:  JSON.stringify({
                servico: serviceName,
                op: method,
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
        $scope.pages = [];

        $scope.addPage = function(){
            let p = {
                '@class' : '<%=PageImpl.class.getName()%>'
            };
            $scope.pages.push(p);
        };

        $scope.savePage = function(p){
            send(
                "page.ServicoPage",
                "addPage",
                "#myApp",
                p,
                function(result)
                {
                    angular.merge(p, result);
                    $scope.$apply();
                },
                function(erro)
                {
                    alert(erro);
                    $scope.$apply();
                }
            );
        };

        $scope.listPages = function() {
            send(
                "page.ServicoPage",
                "loadAll",
                "#myApp",
                {},
                function(result) {
                    $scope.pages = result;
                    $scope.$apply();
                },
                function(erro) {
                    alert(erro);
                    $scope.$apply();
                }
            );
        };

        $scope.removePage = function(p) {
            send(
                "page.ServicoPage",
                "deletePage",
                "#myApp",
                p,
                function(result) {
                    $scope.$apply();
                },
                function (erro) {
                    alert(erro);
                    $scope.$apply();
                }
            )
        };

        $scope.listPages();
    });
</script>
</body>
</html>
