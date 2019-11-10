(function() {
    'use strict';

    angular
        .module('orderbookApp')
        .controller('PurchasedBookDeleteController',PurchasedBookDeleteController);

    PurchasedBookDeleteController.$inject = ['$uibModalInstance', 'entity', 'PurchasedBook'];

    function PurchasedBookDeleteController($uibModalInstance, entity, PurchasedBook) {
        var vm = this;

        vm.purchasedBook = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PurchasedBook.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
