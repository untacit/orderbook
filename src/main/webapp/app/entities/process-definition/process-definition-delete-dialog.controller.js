(function() {
    'use strict';

    angular
        .module('orderbookApp')
        .controller('ProcessDefinitionDeleteController',ProcessDefinitionDeleteController);

    ProcessDefinitionDeleteController.$inject = ['$uibModalInstance', 'entity', 'ProcessDefinition'];

    function ProcessDefinitionDeleteController($uibModalInstance, entity, ProcessDefinition) {
        var vm = this;

        vm.processDefinition = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ProcessDefinition.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
