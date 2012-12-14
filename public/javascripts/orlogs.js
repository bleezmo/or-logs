function LogsCtrl($scope,$http) {
    $http.get('/json/logs').success(function(data){
        $scope.orlogs = data;
    });
}
LogsCtrl.$inject = ['$scope','$http'];