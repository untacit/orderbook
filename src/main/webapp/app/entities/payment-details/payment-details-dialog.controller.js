(function() {
    'use strict';

    angular
        .module('orderbookApp')
        .controller('PaymentDetailsDialogController', PaymentDetailsDialogController);

    PaymentDetailsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PaymentDetails', 'OrderBookDomain'];

    function PaymentDetailsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PaymentDetails, OrderBookDomain) {
        var vm = this;

        vm.paymentDetails = entity;
        vm.clear = clear;
        vm.save = save;
        vm.orderbookdomains = OrderBookDomain.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.paymentDetails.id !== null) {
                PaymentDetails.update(vm.paymentDetails, onSaveSuccess, onSaveError);
            } else {
                PaymentDetails.save(vm.paymentDetails, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('orderbookApp:paymentDetailsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
