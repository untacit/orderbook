(function() {
    'use strict';

    angular
        .module('orderbookApp')
        .controller('OrderBookProcessDialogController', OrderBookProcessDialogController);

    OrderBookProcessDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'OrderBookProcess'];

    function OrderBookProcessDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, OrderBookProcess) {
        var vm = this;

        vm.orderBookProcess = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.orderBookProcess.id !== null) {
                OrderBookProcess.update(vm.orderBookProcess, onSaveSuccess, onSaveError);
            } else {
                OrderBookProcess.save(vm.orderBookProcess, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('orderbookApp:orderBookProcessUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
