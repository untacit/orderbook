(function() {
    'use strict';

    angular
        .module('orderbookApp')
        .controller('PaymentDetailsController', PaymentDetailsController);

    PaymentDetailsController.$inject = ['PaymentDetails'];

    function PaymentDetailsController(PaymentDetails) {

        var vm = this;

        vm.paymentDetails = [];

        loadAll();

        function loadAll() {
            PaymentDetails.query(function(result) {
                vm.paymentDetails = result;
                vm.searchQuery = null;
            });
        }
    }
})();
