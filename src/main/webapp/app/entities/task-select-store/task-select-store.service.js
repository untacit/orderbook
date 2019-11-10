(function() {
    'use strict';
    angular
        .module('orderbookApp')
        .factory('TaskSelectStore', TaskSelectStore);

    TaskSelectStore.$inject = ['$resource'];

    function TaskSelectStore ($resource) {
        var resourceUrl =  'api/task-select-stores/:id';

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
