(function() {
    'use strict';

    angular
        .module('orderbookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('mytask', {
            parent: 'app',
            url: '/mytask',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'untacitTasklistApp.task.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/mytask/mytasks.html',
                    controller: 'MyTaskController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('task');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('mytask.execute', {
            parent: 'mytask',
            url: '/execute/{processDefinitionId}/{taskDefinitionKey}/{processInstanceId}/{taskId}',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$injector', function($stateParams, $injector) {
                var taskExecutorKey = $stateParams.processDefinitionId.split(':')[0] + "-" + $stateParams.taskDefinitionKey +'-executor';
                $injector.get(taskExecutorKey).execute($stateParams);
            }]
        });
    }

})();
