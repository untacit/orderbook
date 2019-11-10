(function() {
    'use strict';

    angular
        .module('orderbookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('order-book-domain', {
            parent: 'entity',
            url: '/order-book-domain',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'orderbookApp.orderBookDomain.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/order-book-domain/order-book-domains.html',
                    controller: 'OrderBookDomainController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('orderBookDomain');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('order-book-domain-detail', {
            parent: 'order-book-domain',
            url: '/order-book-domain/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'orderbookApp.orderBookDomain.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/order-book-domain/order-book-domain-detail.html',
                    controller: 'OrderBookDomainDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('orderBookDomain');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'OrderBookDomain', function($stateParams, OrderBookDomain) {
                    return OrderBookDomain.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'order-book-domain',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        });
    }

})();
