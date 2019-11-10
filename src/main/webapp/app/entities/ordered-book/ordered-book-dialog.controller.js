(function() {
    'use strict';

    angular
        .module('orderbookApp')
        .controller('OrderedBookDialogController', OrderedBookDialogController);

    OrderedBookDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'OrderedBook', 'Book', 'OrderBookDomain'];

    function OrderedBookDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, OrderedBook, Book, OrderBookDomain) {
        var vm = this;

        vm.orderedBook = entity;
        vm.clear = clear;
        vm.save = save;
        vm.books = Book.query({filter: 'orderedbook-is-null'});
        $q.all([vm.orderedBook.$promise, vm.books.$promise]).then(function() {
            if (!vm.orderedBook.bookId) {
                return $q.reject();
            }
            return Book.get({id : vm.orderedBook.bookId}).$promise;
        }).then(function(book) {
            vm.books.push(book);
        });
        vm.orderbookdomains = OrderBookDomain.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.orderedBook.id !== null) {
                OrderedBook.update(vm.orderedBook, onSaveSuccess, onSaveError);
            } else {
                OrderedBook.save(vm.orderedBook, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('orderbookApp:orderedBookUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
