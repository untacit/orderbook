(function() {
    'use strict';

    angular
        .module('orderbookApp')
        .controller('TaskSelectBookDialogController', TaskSelectBookDialogController);

    TaskSelectBookDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'currentTaskId', 'TaskSelectBook'];

    function TaskSelectBookDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, currentTaskId, TaskSelectBook) {
        var vm = this;

        vm.taskSelectBook = entity;
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
            if (vm.taskSelectBook.id !== null) {
                TaskSelectBook.update(params, vm.taskSelectBook, onSaveSuccess, onSaveError);
            } else {
                TaskSelectBook.save(params, vm.taskSelectBook, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('orderbookApp:taskSelectBookUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
