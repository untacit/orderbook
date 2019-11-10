(function() {
    'use strict';

    angular
        .module('orderbookApp')
        .controller('OrderedBookDetailController', OrderedBookDetailController);

    OrderedBookDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'OrderedBook', 'Book', 'OrderBookDomain'];

    function OrderedBookDetailController($scope, $rootScope, $stateParams, previousState, entity, OrderedBook, Book, OrderBookDomain) {
        var vm = this;

        vm.orderedBook = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('orderbookApp:orderedBookUpdate', function(event, result) {
            vm.orderedBook = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
