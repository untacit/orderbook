(function() {
    'use strict';

    angular
        .module('orderbookApp')
        .controller('OrderBookDomainDetailController', OrderBookDomainDetailController);

    OrderBookDomainDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'OrderBookDomain', 'PaymentDetails', 'PurchasedBook', 'Book', 'Buyer', 'Store'];

    function OrderBookDomainDetailController($scope, $rootScope, $stateParams, previousState, entity, OrderBookDomain, PaymentDetails, PurchasedBook, Book, Buyer, Store) {
        var vm = this;

        vm.orderBookDomain = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('orderbookApp:orderBookDomainUpdate', function(event, result) {
            vm.orderBookDomain = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
