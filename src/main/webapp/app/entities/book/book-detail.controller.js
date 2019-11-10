(function() {
    'use strict';

    angular
        .module('orderbookApp')
        .controller('BookDetailController', BookDetailController);

    BookDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Book', 'OrderBookDomain'];

    function BookDetailController($scope, $rootScope, $stateParams, previousState, entity, Book, OrderBookDomain) {
        var vm = this;

        vm.book = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('orderbookApp:bookUpdate', function(event, result) {
            vm.book = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();