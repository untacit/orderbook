(function() {
    'use strict';

    angular
        .module('orderbookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('process-definition', {
            parent: 'entity',
            url: '/process-definition',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'orderbookApp.processDefinition.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/process-definition/process-definitions.html',
                    controller: 'ProcessDefinitionController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('processDefinition');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('process-definition-detail', {
            parent: 'process-definition',
            url: '/process-definition/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'orderbookApp.processDefinition.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/process-definition/process-definition-detail.html',
                    controller: 'ProcessDefinitionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('processDefinition');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ProcessDefinition', function($stateParams, ProcessDefinition) {
                    return ProcessDefinition.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'process-definition',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('process-definition-detail.edit', {
            parent: 'process-definition-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/process-definition/process-definition-dialog.html',
                    controller: 'ProcessDefinitionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ProcessDefinition', function(ProcessDefinition) {
                            return ProcessDefinition.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('process-definition.new', {
            parent: 'process-definition',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/process-definition/process-definition-dialog.html',
                    controller: 'ProcessDefinitionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                specificationFile: null,
                                specificationFileContentType: null,
                                camundaDeploymentId: null,
                                camundaProcessDefinitionId: null,
                                bpmnProcessDefinitionId: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('process-definition', null, { reload: 'process-definition' });
                }, function() {
                    $state.go('process-definition');
                });
            }]
        })
        .state('process-definition.edit', {
            parent: 'process-definition',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/process-definition/process-definition-dialog.html',
                    controller: 'ProcessDefinitionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ProcessDefinition', function(ProcessDefinition) {
                            return ProcessDefinition.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('process-definition', null, { reload: 'process-definition' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('process-definition.delete', {
            parent: 'process-definition',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/process-definition/process-definition-delete-dialog.html',
                    controller: 'ProcessDefinitionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ProcessDefinition', function(ProcessDefinition) {
                            return ProcessDefinition.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('process-definition', null, { reload: 'process-definition' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
