(function() {
    'use strict';

    angular
        .module('orderbookApp')
        .controller('LocationDetailController', LocationDetailController);

    LocationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Location', 'Buyer', 'Store'];

    function LocationDetailController($scope, $rootScope, $stateParams, previousState, entity, Location, Buyer, Store) {
        var vm = this;

        vm.location = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('orderbookApp:locationUpdate', function(event, result) {
            vm.location = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
