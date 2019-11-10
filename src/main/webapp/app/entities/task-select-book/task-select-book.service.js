(function() {
    'use strict';
    angular
        .module('orderbookApp')
        .factory('TaskSelectBook', TaskSelectBook);

    TaskSelectBook.$inject = ['$resource'];

    function TaskSelectBook ($resource) {
        var resourceUrl =  'api/task-select-books/:id';

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
