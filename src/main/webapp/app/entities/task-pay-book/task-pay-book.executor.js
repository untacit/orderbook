(function() {
    'use strict';

    angular
        .module('orderbookApp')
        .factory('procidOrderBook-taskPayBook-executor', TaskPayBookExecutor);

    TaskPayBookExecutor.$inject = ['TaskPayBook', '$uibModal', '$state'];

    function TaskPayBookExecutor(TaskPayBook, $uibModal, $state) {
        return {
            execute: function(stateParams) {
                $uibModal.open({
                    templateUrl: 'app/entities/task-pay-book/task-pay-book-dialog.html',
                    controller: 'TaskPayBookDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: TaskPayBook.get({id: stateParams.processInstanceId}).$promise,
                        currentTaskId: function() { return stateParams.taskId },
                        translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                            $translatePartialLoader.addPart('taskPayBook');
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