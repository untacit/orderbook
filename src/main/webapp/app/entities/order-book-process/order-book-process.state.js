(function() {
    'use strict';

    angular
        .module('orderbookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('order-book-process', {
            parent: 'entity',
            url: '/order-book-process',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'orderbookApp.orderBookProcess.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/order-book-process/order-book-processes.html',
                    controller: 'OrderBookProcessController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('orderBookProcess');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('order-book-process-detail', {
            parent: 'order-book-process',
            url: '/order-book-process/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'orderbookApp.orderBookProcess.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/order-book-process/order-book-process-detail.html',
                    controller: 'OrderBookProcessDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('orderBookProcess');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'OrderBookProcess', function($stateParams, OrderBookProcess) {
                    return OrderBookProcess.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'order-book-process',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('order-book-process-detail.edit', {
            parent: 'order-book-process-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/order-book-process/order-book-process-dialog.html',
                    controller: 'OrderBookProcessDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['OrderBookProcess', function(OrderBookProcess) {
                            return OrderBookProcess.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('order-book-process.new', {
            parent: 'order-book-process',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/order-book-process/order-book-process-dialog.html',
                    controller: 'OrderBookProcessDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                id: null,
                                businessKey: null,
                                orderBookDomain: {
                                    id: null
                                }
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('order-book-process', null, { reload: 'order-book-process' });
                }, function() {
                    $state.go('order-book-process');
                });
            }]
        })
        .state('order-book-process.edit', {
            parent: 'order-book-process',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/order-book-process/order-book-process-dialog.html',
                    controller: 'OrderBookProcessDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['OrderBookProcess', function(OrderBookProcess) {
                            return OrderBookProcess.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('order-book-process', null, { reload: 'order-book-process' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('order-book-process.delete', {
            parent: 'order-book-process',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/order-book-process/order-book-process-delete-dialog.html',
                    controller: 'OrderBookProcessDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['OrderBookProcess', function(OrderBookProcess) {
                            return OrderBookProcess.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('order-book-process', null, { reload: 'order-book-process' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
