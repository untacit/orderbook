(function() {
    'use strict';

    angular
        .module('orderbookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('ordered-book', {
            parent: 'entity',
            url: '/ordered-book',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'orderbookApp.orderedBook.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/ordered-book/ordered-books.html',
                    controller: 'OrderedBookController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('orderedBook');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('ordered-book-detail', {
            parent: 'ordered-book',
            url: '/ordered-book/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'orderbookApp.orderedBook.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/ordered-book/ordered-book-detail.html',
                    controller: 'OrderedBookDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('orderedBook');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'OrderedBook', function($stateParams, OrderedBook) {
                    return OrderedBook.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'ordered-book',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('ordered-book-detail.edit', {
            parent: 'ordered-book-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ordered-book/ordered-book-dialog.html',
                    controller: 'OrderedBookDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['OrderedBook', function(OrderedBook) {
                            return OrderedBook.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('ordered-book.new', {
            parent: 'ordered-book',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ordered-book/ordered-book-dialog.html',
                    controller: 'OrderedBookDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                price: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('ordered-book', null, { reload: 'ordered-book' });
                }, function() {
                    $state.go('ordered-book');
                });
            }]
        })
        .state('ordered-book.edit', {
            parent: 'ordered-book',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ordered-book/ordered-book-dialog.html',
                    controller: 'OrderedBookDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['OrderedBook', function(OrderedBook) {
                            return OrderedBook.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('ordered-book', null, { reload: 'ordered-book' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('ordered-book.delete', {
            parent: 'ordered-book',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ordered-book/ordered-book-delete-dialog.html',
                    controller: 'OrderedBookDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['OrderedBook', function(OrderedBook) {
                            return OrderedBook.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('ordered-book', null, { reload: 'ordered-book' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
