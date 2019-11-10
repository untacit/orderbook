(function() {
    'use strict';

    angular
        .module('orderbookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('purchased-book', {
            parent: 'entity',
            url: '/purchased-book',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'orderbookApp.purchasedBook.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/purchased-book/purchased-books.html',
                    controller: 'PurchasedBookController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('purchasedBook');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('purchased-book-detail', {
            parent: 'purchased-book',
            url: '/purchased-book/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'orderbookApp.purchasedBook.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/purchased-book/purchased-book-detail.html',
                    controller: 'PurchasedBookDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('purchasedBook');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PurchasedBook', function($stateParams, PurchasedBook) {
                    return PurchasedBook.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'purchased-book',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('purchased-book-detail.edit', {
            parent: 'purchased-book-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/purchased-book/purchased-book-dialog.html',
                    controller: 'PurchasedBookDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PurchasedBook', function(PurchasedBook) {
                            return PurchasedBook.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('purchased-book.new', {
            parent: 'purchased-book',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/purchased-book/purchased-book-dialog.html',
                    controller: 'PurchasedBookDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                quantity: null,
                                price: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('purchased-book', null, { reload: 'purchased-book' });
                }, function() {
                    $state.go('purchased-book');
                });
            }]
        })
        .state('purchased-book.edit', {
            parent: 'purchased-book',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/purchased-book/purchased-book-dialog.html',
                    controller: 'PurchasedBookDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PurchasedBook', function(PurchasedBook) {
                            return PurchasedBook.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('purchased-book', null, { reload: 'purchased-book' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('purchased-book.delete', {
            parent: 'purchased-book',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/purchased-book/purchased-book-delete-dialog.html',
                    controller: 'PurchasedBookDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PurchasedBook', function(PurchasedBook) {
                            return PurchasedBook.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('purchased-book', null, { reload: 'purchased-book' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
