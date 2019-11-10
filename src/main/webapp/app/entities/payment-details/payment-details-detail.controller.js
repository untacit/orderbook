(function() {
    'use strict';

    angular
        .module('orderbookApp')
        .controller('PaymentDetailsDetailController', PaymentDetailsDetailController);

    PaymentDetailsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PaymentDetails', 'OrderBookDomain'];

    function PaymentDetailsDetailController($scope, $rootScope, $stateParams, previousState, entity, PaymentDetails, OrderBookDomain) {
        var vm = this;

        vm.paymentDetails = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('orderbookApp:paymentDetailsUpdate', function(event, result) {
            vm.paymentDetails = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
