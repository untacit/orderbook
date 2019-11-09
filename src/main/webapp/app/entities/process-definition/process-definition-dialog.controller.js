(function() {
    'use strict';

    angular
        .module('orderbookApp')
        .controller('ProcessDefinitionDialogController', ProcessDefinitionDialogController);

    ProcessDefinitionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'ProcessDefinition'];

    function ProcessDefinitionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, ProcessDefinition) {
        var vm = this;

        vm.processDefinition = entity;
        vm.clear = clear;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.processDefinition.id !== null) {
                ProcessDefinition.update(vm.processDefinition, onSaveSuccess, onSaveError);
            } else {
                ProcessDefinition.save(vm.processDefinition, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('orderbookApp:processDefinitionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setSpecificationFile = function ($file, processDefinition) {
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        processDefinition.specificationFile = base64Data;
                        processDefinition.specificationFileContentType = $file.type;
                    });
                });
            }
        };

    }
})();
