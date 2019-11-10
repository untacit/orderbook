(function() {
    'use strict';
    angular
        .module('orderbookApp')
        .factory('OrderBookDomain', OrderBookDomain);

    OrderBookDomain.$inject = ['$resource', 'DateUtils'];

    function OrderBookDomain ($resource, DateUtils) {
        var resourceUrl =  'api/order-book-domains/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.startDate = DateUtils.convertLocalDateFromServer(data.startDate);
                        data.endDate = DateUtils.convertLocalDateFromServer(data.endDate);
                    }
                    return data;
                }
            }
        });
    }
})();
