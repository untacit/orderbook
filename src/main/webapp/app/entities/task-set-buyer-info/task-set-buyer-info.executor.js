(function() {
    'use strict';

    angular
        .module('orderbookApp')
        .factory('procidOrderBook-taskSetBuyerInfo-executor', TaskSetBuyerInfoExecutor);

    TaskSetBuyerInfoExecutor.$inject = ['TaskSetBuyerInfo', '$uibModal', '$state'];

    function TaskSetBuyerInfoExecutor(TaskSetBuyerInfo, $uibModal, $state) {
        return {
            execute: function(stateParams) {
                $uibModal.open({
                    templateUrl: 'app/entities/task-set-buyer-info/task-set-buyer-info-dialog.html',
                    controller: 'TaskSetBuyerInfoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: TaskSetBuyerInfo.get({id: stateParams.processInstanceId}).$promise,
                        currentTaskId: function() { return stateParams.taskId },
                        translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                            $translatePartialLoader.addPart('taskSetBuyerInfo');
                            $translatePartialLoader.addPart('global');
                            return $translate.refresh();
                        }]
                    }
                }).result.then(function() {
                        $state.go('task', null, { reload: 'task' });
                    }, function() {
                        $state.go('^');
                    });
            }
        }
    }
})();