(function() {
    'use strict';

    angular
        .module('orderbookApp')
        .controller('BuyerDetailController', BuyerDetailController);

    BuyerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Buyer', 'OrderBookDomain'];

    function BuyerDetailController($scope, $rootScope, $stateParams, previousState, entity, Buyer, OrderBookDomain) {
        var vm = this;

        vm.buyer = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('orderbookApp:buyerUpdate', function(event, result) {
            vm.buyer = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
