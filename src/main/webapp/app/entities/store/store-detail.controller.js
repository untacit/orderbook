(function() {
    'use strict';

    angular
        .module('orderbookApp')
        .controller('StoreDetailController', StoreDetailController);

    StoreDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Store', 'OrderBookDomain'];

    function StoreDetailController($scope, $rootScope, $stateParams, previousState, entity, Store, OrderBookDomain) {
        var vm = this;

        vm.store = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('orderbookApp:storeUpdate', function(event, result) {
            vm.store = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
