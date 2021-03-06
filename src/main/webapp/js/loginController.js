function loginCtrl($scope, $window, $timeout, $http) {
    this.errormsg = '';
    var object = this;

    this.getData = function () {
        $http.get('api/clients').then(function (response) {
            object.people = response.data;
        });
    };
    this.getEmployeeData = function () {
        $http.get('api/employees').then(function (response) {
            object.employee = response.data;
        });
    };

    this.submit = function () {
        object.found = null;

        let needle = object.username;
        let bigNeedle = object.password;
        for (let i = 0; i < object.people.length; i++){
            if (object.people[i].login == needle && object.people[i].hash == bigNeedle){
                object.found = object.people[i];
            }
        }

        if(object.found != null) {
            $window.location.href = 'http://localhost:8080/';
            $scope.setCookie(this.username, object.found.firstName, object.found.lastName);
            $scope.setServiceCookie('id', object.found.id);

        } else {
            this.errormsg = 'Wrong login or password';

            $timeout(function () {
                object.errormsg = '';
            }, 3000);

        }
    };

    this.submitEmployee = function () {
        object.found = null;

        let needle = object.username;
        let bigNeedle = object.password;
        for (let i = 0; i < object.employee.length; i++){
            if (object.employee[i].login == needle && object.employee[i].hash == bigNeedle){
                object.found = object.employee[i];
            }
        }

        if(object.found != null) {
            //todo DODAC KOLUMNE W TABELI EMPLOYEE LUB NOWA TABELE
            if(object.found.login == 'admin' && object.found.hash == 'admin') {
                $window.location.href = 'http://localhost:8080/indexAdmin.html';
            } else {
                $window.location.href = 'http://localhost:8080/indexEmployee.html';
            }
            $scope.setCookie(this.username, object.found.firstName, object.found.lastName);
            $scope.setServiceCookie('id', object.found.id);

        } else {
            this.errormsg = 'Wrong login or password';

            $timeout(function () {
                object.errormsg = '';
            }, 3000);
        }
    };
    this.getData();
}