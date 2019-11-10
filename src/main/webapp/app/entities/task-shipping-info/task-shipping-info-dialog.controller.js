(function() {
    'use strict';

    angular
        .module('orderbookApp')
        .controller('TaskShippingInfoDialogController', TaskShippingInfoDialogController);

    TaskShippingInfoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'currentTaskId', 'TaskShippingInfo'];

    function TaskShippingInfoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, currentTaskId, TaskShippingInfo) {
        var vm = this;

        vm.taskShippingInfo = entity;
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
            if (vm.taskShippingInfo.id !== null) {
                TaskShippingInfo.update(params, vm.taskShippingInfo, onSaveSuccess, onSaveError);
            } else {
                TaskShippingInfo.save(params, vm.taskShippingInfo, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('orderbookApp:taskShippingInfoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
