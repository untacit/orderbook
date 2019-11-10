(function() {
    'use strict';

    angular
        .module('orderbookApp')
        .controller('BuyerDialogController', BuyerDialogController);

    BuyerDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Buyer', 'OrderBookDomain'];

    function BuyerDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Buyer, OrderBookDomain) {
        var vm = this;

        vm.buyer = entity;
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
            if (vm.buyer.id !== null) {
                Buyer.update(vm.buyer, onSaveSuccess, onSaveError);
            } else {
                Buyer.save(vm.buyer, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('orderbookApp:buyerUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
