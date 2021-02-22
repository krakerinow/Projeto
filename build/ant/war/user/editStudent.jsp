<%@ page import="teste.domain.StudentImpl" %>
<%@ page import="teste.domain.UnidadeCurricularImpl" %><%--
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

<%
    String id = request.getParameter("id");
%>



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

        $scope.id = <%=id%>;
        $scope.estudante = {
            unidadeCurriculares:[]
        };


        $scope.setDirty = function(obj){
            obj.dirty=true;
        }

        $scope.loadStudent = function(){

            send(
                "core.ServicoStudents",
                "loadStudent",
                "#myApp",
                {
                    id: $scope.id
                },
                function(result)
                {
                    $scope.estudante = result;

                    $scope.$apply();
                },
                function(erro)
                {
                    alert(erro);
                    $scope.$apply();
                }
            );
        }



        $scope.loadStudent();


        $scope.addUc = function()
        {
            let uc = {
                '@class' : '<%=UnidadeCurricularImpl.class.getName()%>'
            }
            if(!$scope.estudante.unidadeCurriculares)
                $scope.estudante.unidadeCurriculares = [];
            $scope.estudante.unidadeCurriculares.push(uc);
        }

        $scope.saveStudent = function(){

            send(
                "core.ServicoStudents",
                "addStudent",
                "#myApp",
                $scope.estudante,
                function(result)
                {
                    angular.merge($scope.estudante,result);
                    $scope.$apply();
                },
                function(erro)
                {
                    alert(erro);
                    $scope.$apply();
                }
            );
        }

        $scope.saveUc = function(uc){

            send(
                "core.ServicoStudents",
                "addUc",
                "#myApp",
                {
                    studentId: $scope.estudante.id,
                    uc: uc
                },
                function(result)
                {
                    delete uc.dirty;
                    angular.merge(uc,result);
                    $scope.$apply();
                },
                function(erro)
                {
                    alert(erro);
                    $scope.$apply();
                }
            );
        }


        $scope.removeUc = function(u,$index){
            $scope.estudante.unidadeCurriculares.splice($index,1);

        }

    });
</script>


<style>

    .dirty
    {
        border: 2px dashed yellow
    }
</style>


<div id="myApp" ng-app="myApp" ng-controller="myCtrl">

    <div class="messagens"></div>


    <div class="form-horizontal" action="/action_page.php">
        <div class="form-group">
            <label class="control-label col-sm-2">Nome:</label>
            <div class="col-sm-10">
                <input class="form-control" ng-model="estudante.nome">
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-2">Numero:</label>
            <div class="col-sm-10">
                <input class="form-control" ng-model="estudante.numeroDeAluno">
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-2">Activo:</label>
            <div class="col-sm-10">
                <select ng-model="estudante.ativo">
                    <option ng-value="true">Sim</option>
                    <option ng-value="false">Não</option>
                </select>
            </div>
        </div>

        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="button" ng-click="saveStudent()" class="btn btn-default">Salvar</button>
            </div>
        </div>
    </div>


    <h1>UCS <button ng-click="addUc()"><span class="glyphicon glyphicon-plus"></span></button></h1>
    <table class="table">
        <thead>
            <tr>
                <th>Nome</th>
                <th colspan="2"></th>
            </tr>
        </thead>
        <tbody>
            <tr ng-class="{ dirty: u.dirty == true }" ng-repeat="u in estudante.unidadeCurriculares">
                <td><input ng-change="setDirty(u)" type="text" ng-model="u.nome"></td>
                <td>
                    <button ng-if="u.dirty == true" ng-click="saveUc(u)"><span class="glyphicon glyphicon-floppy-disk"></span></button>
                </td>
                <td>
                    <button ng-click="removeUc(u,$index)"><span class="glyphicon glyphicon-remove"></span></button>
                </td>
            </tr>
        </tbody>
    </table>

    <pre>
        {{ estudante | json}}
    </pre>
</div>


</body>
</html>
