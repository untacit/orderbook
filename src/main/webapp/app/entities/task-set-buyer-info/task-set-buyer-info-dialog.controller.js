(function() {
    'use strict';

    angular
        .module('orderbookApp')
        .controller('TaskSetBuyerInfoDialogController', TaskSetBuyerInfoDialogController);

    TaskSetBuyerInfoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'currentTaskId', 'TaskSetBuyerInfo', 'Buyer'];

    function TaskSetBuyerInfoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, currentTaskId, TaskSetBuyerInfo, Buyer) {
        var vm = this;

        vm.taskSetBuyerInfo = entity;
        vm.clear = clear;
        vm.save = save;
        vm.buyers = Buyer.query();

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
            if (vm.taskSetBuyerInfo.id !== null) {
                TaskSetBuyerInfo.update(params, vm.taskSetBuyerInfo, onSaveSuccess, onSaveError);
            } else {
                TaskSetBuyerInfo.save(params, vm.taskSetBuyerInfo, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('orderbookApp:taskSetBuyerInfoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
