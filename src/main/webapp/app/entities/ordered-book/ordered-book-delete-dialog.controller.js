(function() {
    'use strict';

    angular
        .module('orderbookApp')
        .controller('OrderedBookDeleteController',OrderedBookDeleteController);

    OrderedBookDeleteController.$inject = ['$uibModalInstance', 'entity', 'OrderedBook'];

    function OrderedBookDeleteController($uibModalInstance, entity, OrderedBook) {
        var vm = this;

        vm.orderedBook = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            OrderedBook.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
