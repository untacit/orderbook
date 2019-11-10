(function() {
    'use strict';
    angular
        .module('orderbookApp')
        .factory('TaskSetBuyerInfo', TaskSetBuyerInfo);

    TaskSetBuyerInfo.$inject = ['$resource'];

    function TaskSetBuyerInfo ($resource) {
        var resourceUrl =  'api/task-set-buyer-infos/:id';

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
