(function() {
    'use strict';

    angular
        .module('orderbookApp')
        .controller('OrderBookProcessController', OrderBookProcessController);

    OrderBookProcessController.$inject = ['OrderBookProcess'];

    function OrderBookProcessController(OrderBookProcess) {

        var vm = this;

        vm.orderBookProcesses = [];

        loadAll();

        function loadAll() {
            OrderBookProcess.query(function(result) {
                vm.orderBookProcesses = result;
                vm.searchQuery = null;
            });
        }
    }
})();
