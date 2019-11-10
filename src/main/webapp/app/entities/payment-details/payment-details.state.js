(function() {
    'use strict';

    angular
        .module('orderbookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('payment-details', {
            parent: 'entity',
            url: '/payment-details',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'orderbookApp.paymentDetails.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/payment-details/payment-details.html',
                    controller: 'PaymentDetailsController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('paymentDetails');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('payment-details-detail', {
            parent: 'payment-details',
            url: '/payment-details/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'orderbookApp.paymentDetails.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/payment-details/payment-details-detail.html',
                    controller: 'PaymentDetailsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('paymentDetails');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PaymentDetails', function($stateParams, PaymentDetails) {
                    return PaymentDetails.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'payment-details',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('payment-details-detail.edit', {
            parent: 'payment-details-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/payment-details/payment-details-dialog.html',
                    controller: 'PaymentDetailsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PaymentDetails', function(PaymentDetails) {
                            return PaymentDetails.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('payment-details.new', {
            parent: 'payment-details',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/payment-details/payment-details-dialog.html',
                    controller: 'PaymentDetailsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nameOnCard: null,
                                creditCard: null,
                                expiryDate: null,
                                ccv: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('payment-details', null, { reload: 'payment-details' });
                }, function() {
                    $state.go('payment-details');
                });
            }]
        })
        .state('payment-details.edit', {
            parent: 'payment-details',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/payment-details/payment-details-dialog.html',
                    controller: 'PaymentDetailsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PaymentDetails', function(PaymentDetails) {
                            return PaymentDetails.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('payment-details', null, { reload: 'payment-details' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('payment-details.delete', {
            parent: 'payment-details',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/payment-details/payment-details-delete-dialog.html',
                    controller: 'PaymentDetailsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PaymentDetails', function(PaymentDetails) {
                            return PaymentDetails.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('payment-details', null, { reload: 'payment-details' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
