(function() {
    'use strict';

    angular
        .module('orderbookApp')
        .controller('ProcessDefinitionDetailController', ProcessDefinitionDetailController);

    ProcessDefinitionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'ProcessDefinition'];

    function ProcessDefinitionDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, ProcessDefinition) {
        var vm = this;

        vm.processDefinition = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('orderbookApp:processDefinitionUpdate', function(event, result) {
            vm.processDefinition = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
