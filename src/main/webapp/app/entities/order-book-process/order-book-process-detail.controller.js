(function() {
    'use strict';

    angular
        .module('orderbookApp')
        .controller('OrderBookProcessDetailController', OrderBookProcessDetailController);

    OrderBookProcessDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'OrderBookProcess'];

    function OrderBookProcessDetailController($scope, $rootScope, $stateParams, previousState, entity, OrderBookProcess) {
        var vm = this;

        vm.orderBookProcess = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('orderbookApp:orderBookProcessUpdate', function(event, result) {
            vm.orderBookProcess = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
