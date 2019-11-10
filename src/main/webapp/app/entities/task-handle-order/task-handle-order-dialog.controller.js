(function() {
    'use strict';

    angular
        .module('orderbookApp')
        .controller('TaskHandleOrderDialogController', TaskHandleOrderDialogController);

    TaskHandleOrderDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'currentTaskId', 'TaskHandleOrder'];

    function TaskHandleOrderDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, currentTaskId, TaskHandleOrder) {
        var vm = this;

        vm.taskHandleOrder = entity;
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
            if (vm.taskHandleOrder.id !== null) {
                TaskHandleOrder.update(params, vm.taskHandleOrder, onSaveSuccess, onSaveError);
            } else {
                TaskHandleOrder.save(params, vm.taskHandleOrder, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('orderbookApp:taskHandleOrderUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
