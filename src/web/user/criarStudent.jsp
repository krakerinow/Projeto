<%@ page import="teste.domain.StudentImpl" %><%--
  Created by IntelliJ IDEA.
  User: jmachado
  Date: 02/12/2019
  Time: 08:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.9/angular.min.js"></script>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
</head>
<body>




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
        $scope.firstName = "John";
        $scope.lastName = "Doe";

        $scope.estudantes = [];

        $scope.addStudent = function(){
            let s = {
                '@class' : '<%=StudentImpl.class.getName()%>'
            }
            $scope.estudantes.push(s);


        }

        $scope.saveStudent = function(s){

            send(
                "core.ServicoStudents",
                "addStudent",
                "#myApp",
                s,
                function(result)
                {
                    angular.merge(s,result);
                    $scope.$apply();
                },
                function(erro)
                {
                    alert(erro);
                    $scope.$apply();
                }
            );
        }

        $scope.listStudents = function(){

            send(
                "core.ServicoStudents",
                "loadAll",
                "#myApp",
                {},
                function(result)
                {
                    $scope.estudantes = result;
                    $scope.$apply();
                },
                function(erro)
                {
                    alert(erro);
                    $scope.$apply();
                }
            );
        }

        $scope.listStudents();

        $scope.removeStudent = function(s,$index){
            $scope.estudantes.splice($index,1);

        }

    });
</script>


<div id="myApp" ng-app="myApp" ng-controller="myCtrl">

    <div class="messagens"></div>


    <div>
        <button ng-click="addStudent()"><span class="glyphicon glyphicon-plus"></span></button>
    </div>
    <table class="table">
        <thead>
            <tr>
                <th>Nome</th>
                <th>Numero</th>
                <th>Ativo</th>
                <th colspan="3"></th>
            </tr>
        </thead>
        <tbody>
            <tr ng-repeat="s in estudantes">
                <td><input type="text" ng-model="s.nome"></td>
                <td><input type="text" ng-model="s.numeroDeAluno"></td>
                <td>
                    <select ng-model="s.ativo">
                        <option ng-value="true">Sim</option>
                        <option ng-value="false">Não</option>
                    </select>
                </td>
                <td>
                    <button ng-click="saveStudent(s)"><span class="glyphicon glyphicon-disk"></span></button>
                </td>
                <td>
                    <button ng-click="removeStudent(s,$index)"><span class="glyphicon glyphicon-remove"></span></button>
                </td>
                <td>
                    <a href="<%=request.getContextPath()%>/user/uc.do?id={{s.id}}"><span class="glyphicon glyphicon-user"></span></a>
                </td>
            </tr>
        </tbody>
    </table>

    <pre>
        {{ estudantes | json}}
    </pre>
</div>


</body>
</html>
