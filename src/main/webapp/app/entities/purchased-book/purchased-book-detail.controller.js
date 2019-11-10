(function() {
    'use strict';

    angular
        .module('orderbookApp')
        .controller('PurchasedBookDetailController', PurchasedBookDetailController);

    PurchasedBookDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PurchasedBook', 'OrderBookDomain'];

    function PurchasedBookDetailController($scope, $rootScope, $stateParams, previousState, entity, PurchasedBook, OrderBookDomain) {
        var vm = this;

        vm.purchasedBook = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('orderbookApp:purchasedBookUpdate', function(event, result) {
            vm.purchasedBook = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
