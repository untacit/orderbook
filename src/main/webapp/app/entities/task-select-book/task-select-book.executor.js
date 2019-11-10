(function() {
    'use strict';

    angular
        .module('orderbookApp')
        .factory('procidOrderBook-taskSelectBook-executor', TaskSelectBookExecutor);

    TaskSelectBookExecutor.$inject = ['TaskSelectBook', '$uibModal', '$state'];

    function TaskSelectBookExecutor(TaskSelectBook, $uibModal, $state) {
        return {
            execute: function(stateParams) {
                $uibModal.open({
                    templateUrl: 'app/entities/task-select-book/task-select-book-dialog.html',
                    controller: 'TaskSelectBookDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: TaskSelectBook.get({id: stateParams.processInstanceId}).$promise,
                        currentTaskId: function() { return stateParams.taskId },
                        translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                            $translatePartialLoader.addPart('taskSelectBook');
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