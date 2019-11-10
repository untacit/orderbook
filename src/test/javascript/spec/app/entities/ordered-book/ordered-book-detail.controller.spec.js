'use strict';

describe('Controller Tests', function() {

    describe('OrderedBook Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockOrderedBook, MockBook, MockOrderBookDomain;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockOrderedBook = jasmine.createSpy('MockOrderedBook');
            MockBook = jasmine.createSpy('MockBook');
            MockOrderBookDomain = jasmine.createSpy('MockOrderBookDomain');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'OrderedBook': MockOrderedBook,
                'Book': MockBook,
                'OrderBookDomain': MockOrderBookDomain
            };
            createController = function() {
                $injector.get('$controller')("OrderedBookDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'orderbookApp:orderedBookUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
