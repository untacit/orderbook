(function() {
    'use strict';

    angular
        .module('orderbookApp')
        .controller('PurchasedBookController', PurchasedBookController);

    PurchasedBookController.$inject = ['PurchasedBook'];

    function PurchasedBookController(PurchasedBook) {

        var vm = this;

        vm.purchasedBooks = [];

        loadAll();

        function loadAll() {
            PurchasedBook.query(function(result) {
                vm.purchasedBooks = result;
                vm.searchQuery = null;
            });
        }
    }
})();
