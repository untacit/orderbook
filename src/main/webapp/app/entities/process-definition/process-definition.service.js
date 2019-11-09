(function() {
    'use strict';
    angular
        .module('orderbookApp')
        .factory('ProcessDefinition', ProcessDefinition);

    ProcessDefinition.$inject = ['$resource'];

    function ProcessDefinition ($resource) {
        var resourceUrl =  'api/process-definitions/:id';

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
