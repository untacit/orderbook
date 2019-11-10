(function() {
    'use strict';

    angular
        .module('orderbookApp')
        .controller('BuyerController', BuyerController);

    BuyerController.$inject = ['Buyer'];

    function BuyerController(Buyer) {

        var vm = this;

        vm.buyers = [];

        loadAll();

        function loadAll() {
            Buyer.query(function(result) {
                vm.buyers = result;
                vm.searchQuery = null;
            });
        }
    }
})();
