(function() {
    'use strict';
    angular
        .module('orderbookApp')
        .factory('TaskPayBook', TaskPayBook);

    TaskPayBook.$inject = ['$resource'];

    function TaskPayBook ($resource) {
        var resourceUrl =  'api/task-pay-books/:id';

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
