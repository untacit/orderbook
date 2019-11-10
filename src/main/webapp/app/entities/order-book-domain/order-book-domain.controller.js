(function() {
    'use strict';

    angular
        .module('orderbookApp')
        .controller('OrderBookDomainController', OrderBookDomainController);

    OrderBookDomainController.$inject = ['OrderBookDomain'];

    function OrderBookDomainController(OrderBookDomain) {

        var vm = this;

        vm.orderBookDomains = [];

        loadAll();

        function loadAll() {
            OrderBookDomain.query(function(result) {
                vm.orderBookDomains = result;
                vm.searchQuery = null;
            });
        }
    }
})();
