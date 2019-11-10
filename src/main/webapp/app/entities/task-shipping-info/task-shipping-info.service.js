(function() {
    'use strict';
    angular
        .module('orderbookApp')
        .factory('TaskShippingInfo', TaskShippingInfo);

    TaskShippingInfo.$inject = ['$resource'];

    function TaskShippingInfo ($resource) {
        var resourceUrl =  'api/task-shipping-infos/:id';

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
