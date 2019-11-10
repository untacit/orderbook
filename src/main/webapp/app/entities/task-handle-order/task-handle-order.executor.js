(function() {
    'use strict';

    angular
        .module('orderbookApp')
        .factory('procidOrderBook-taskHandleOrder-executor', TaskHandleOrderExecutor);

    TaskHandleOrderExecutor.$inject = ['TaskHandleOrder', '$uibModal', '$state'];

    function TaskHandleOrderExecutor(TaskHandleOrder, $uibModal, $state) {
        return {
            execute: function(stateParams) {
                $uibModal.open({
                    templateUrl: 'app/entities/task-handle-order/task-handle-order-dialog.html',
                    controller: 'TaskHandleOrderDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: TaskHandleOrder.get({id: stateParams.processInstanceId}).$promise,
                        currentTaskId: function() { return stateParams.taskId },
                        translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                            $translatePartialLoader.addPart('taskHandleOrder');
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