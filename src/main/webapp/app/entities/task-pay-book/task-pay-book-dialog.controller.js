(function() {
    'use strict';

    angular
        .module('orderbookApp')
        .controller('TaskPayBookDialogController', TaskPayBookDialogController);

    TaskPayBookDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'currentTaskId', 'TaskPayBook'];

    function TaskPayBookDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, currentTaskId, TaskPayBook) {
        var vm = this;

        vm.taskPayBook = entity;
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
            if (vm.taskPayBook.id !== null) {
                TaskPayBook.update(params, vm.taskPayBook, onSaveSuccess, onSaveError);
            } else {
                TaskPayBook.save(params, vm.taskPayBook, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('orderbookApp:taskPayBookUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
