(function() {
    'use strict';

    angular
        .module('orderbookApp')
        .controller('TaskSelectStoreDialogController', TaskSelectStoreDialogController);

    TaskSelectStoreDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'currentTaskId', 'TaskSelectStore'];

    function TaskSelectStoreDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, currentTaskId, TaskSelectStore) {
        var vm = this;

        vm.taskSelectStore = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            var params = {
                untacitTaskId: currentTaskId,
                untacitTaskAction: 'concludeTask'
            }   
            if (vm.taskSelectStore.id !== null) {
                TaskSelectStore.update(params, vm.taskSelectStore, onSaveSuccess, onSaveError);
            } else {
                TaskSelectStore.save(params, vm.taskSelectStore, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('orderbookApp:taskSelectStoreUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
