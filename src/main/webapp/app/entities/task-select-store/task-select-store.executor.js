(function() {
    'use strict';

    angular
        .module('orderbookApp')
        .factory('procidOrderBook-taskSelectPickUpStore-executor', TaskSelectStoreExecutor);

    TaskSelectStoreExecutor.$inject = ['TaskSelectStore', '$uibModal', '$state'];

    function TaskSelectStoreExecutor(TaskSelectStore, $uibModal, $state) {
        return {
            execute: function(stateParams) {
                $uibModal.open({
                    templateUrl: 'app/entities/task-select-store/task-select-store-dialog.html',
                    controller: 'TaskSelectStoreDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: TaskSelectStore.get({id: stateParams.processInstanceId}).$promise,
                        currentTaskId: function() { return stateParams.taskId },
                        translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                            $translatePartialLoader.addPart('taskSelectStore');
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