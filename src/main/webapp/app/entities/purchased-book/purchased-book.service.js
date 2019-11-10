(function() {
    'use strict';
    angular
        .module('orderbookApp')
        .factory('PurchasedBook', PurchasedBook);

    PurchasedBook.$inject = ['$resource'];

    function PurchasedBook ($resource) {
        var resourceUrl =  'api/purchased-books/:id';

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
