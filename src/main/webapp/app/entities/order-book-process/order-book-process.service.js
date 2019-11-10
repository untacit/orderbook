(function() {
    'use strict';
    angular
        .module('orderbookApp')
        .factory('OrderBookProcess', OrderBookProcess);

    OrderBookProcess.$inject = ['$resource'];

    function OrderBookProcess ($resource) {
        var resourceUrl =  'api/order-book-processes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
