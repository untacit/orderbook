(function() {
    'use strict';

    angular
        .module('orderbookApp')
        .controller('PurchasedBookDialogController', PurchasedBookDialogController);

    PurchasedBookDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PurchasedBook', 'OrderBookDomain', 'Book'];

    function PurchasedBookDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PurchasedBook, OrderBookDomain, Book) {
        var vm = this;

        vm.purchasedBook = entity;
        vm.clear = clear;
        vm.save = save;
        vm.orderbookdomains = OrderBookDomain.query();
        vm.books = Book.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.purchasedBook.id !== null) {
                PurchasedBook.update(vm.purchasedBook, onSaveSuccess, onSaveError);
            } else {
                PurchasedBook.save(vm.purchasedBook, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('orderbookApp:purchasedBookUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
