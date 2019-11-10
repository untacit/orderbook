(function() {
    'use strict';

    angular
        .module('orderbookApp')
        .controller('StoreDialogController', StoreDialogController);

    StoreDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Store', 'OrderBookDomain'];

    function StoreDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Store, OrderBookDomain) {
        var vm = this;

        vm.store = entity;
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
            if (vm.store.id !== null) {
                Store.update(vm.store, onSaveSuccess, onSaveError);
            } else {
                Store.save(vm.store, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('orderbookApp:storeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
