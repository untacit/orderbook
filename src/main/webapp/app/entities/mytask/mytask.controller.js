(function() {
    'use strict';

    angular
        .module('orderbookApp')
        .controller('MyTaskController', MyTaskController);

    MyTaskController.$inject = ['MyTask'];

    function MyTaskController(MyTask) {

        var vm = this;

        vm.tasks = [];

        loadAll();

        function loadAll() {
            MyTask.query(function(result) {
                vm.tasks = result;
            });
        }
    }
})();
