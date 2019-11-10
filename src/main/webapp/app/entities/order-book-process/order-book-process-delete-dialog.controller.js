(function() {
    'use strict';

    angular
        .module('orderbookApp')
        .controller('OrderBookProcessDeleteController',OrderBookProcessDeleteController);

    OrderBookProcessDeleteController.$inject = ['$uibModalInstance', 'entity', 'OrderBookProcess'];

    function OrderBookProcessDeleteController($uibModalInstance, entity, OrderBookProcess) {
        var vm = this;

        vm.orderBookProcess = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            OrderBookProcess.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
