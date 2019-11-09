(function() {
    'use strict';
    angular
        .module('orderbookApp')
        .factory('MyTask', MyTask);

    MyTask.$inject = ['$resource', 'DateUtils'];

    function MyTask ($resource, DateUtils) {
        var resourceUrl =  'api/mytasks/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
