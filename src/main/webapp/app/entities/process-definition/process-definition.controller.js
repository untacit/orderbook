(function() {
    'use strict';

    angular
        .module('orderbookApp')
        .controller('ProcessDefinitionController', ProcessDefinitionController);

    ProcessDefinitionController.$inject = ['DataUtils', 'ProcessDefinition'];

    function ProcessDefinitionController(DataUtils, ProcessDefinition) {

        var vm = this;

        vm.processDefinitions = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();

        function loadAll() {
            ProcessDefinition.query(function(result) {
                vm.processDefinitions = result;
                vm.searchQuery = null;
            });
        }
    }
})();
