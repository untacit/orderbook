(function() {
    'use strict';

    angular
        .module('orderbookApp')
        .factory('procidOrderBook-taskAddShippingInfo-executor', TaskShippingInfoExecutor);

    TaskShippingInfoExecutor.$inject = ['TaskShippingInfo', '$uibModal', '$state'];

    function TaskShippingInfoExecutor(TaskShippingInfo, $uibModal, $state) {
        return {
            execute: function(stateParams) {
                $uibModal.open({
                    templateUrl: 'app/entities/task-shipping-info/task-shipping-info-dialog.html',
                    controller: 'TaskShippingInfoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: TaskShippingInfo.get({id: stateParams.processInstanceId}).$promise,
                        currentTaskId: function() { return stateParams.taskId },
                        translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                            $translatePartialLoader.addPart('taskShippingInfo');
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