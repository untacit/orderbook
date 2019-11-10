(function() {
    'use strict';

    angular
        .module('orderbookApp')
        .controller('PaymentDetailsDeleteController',PaymentDetailsDeleteController);

    PaymentDetailsDeleteController.$inject = ['$uibModalInstance', 'entity', 'PaymentDetails'];

    function PaymentDetailsDeleteController($uibModalInstance, entity, PaymentDetails) {
        var vm = this;

        vm.paymentDetails = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PaymentDetails.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
