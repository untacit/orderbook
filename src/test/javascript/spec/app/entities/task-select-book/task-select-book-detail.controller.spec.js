'use strict';

describe('Controller Tests', function() {

    describe('TaskSelectBook Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTaskSelectBook, MockOrderedBook;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTaskSelectBook = jasmine.createSpy('MockTaskSelectBook');
            MockOrderedBook = jasmine.createSpy('MockOrderedBook');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'TaskSelectBook': MockTaskSelectBook,
                'OrderedBook': MockOrderedBook
            };
            createController = function() {
                $injector.get('$controller')("TaskSelectBookDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'orderbookApp:taskSelectBookUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
