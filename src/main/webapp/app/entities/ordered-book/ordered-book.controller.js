(function() {
    'use strict';

    angular
        .module('orderbookApp')
        .controller('OrderedBookController', OrderedBookController);

    OrderedBookController.$inject = ['OrderedBook'];

    function OrderedBookController(OrderedBook) {

        var vm = this;

        vm.orderedBooks = [];

        loadAll();

        function loadAll() {
            OrderedBook.query(function(result) {
                vm.orderedBooks = result;
                vm.searchQuery = null;
            });
        }
    }
})();
