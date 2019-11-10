'use strict';

describe('Controller Tests', function() {

    describe('TaskSetBuyerInfo Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTaskSetBuyerInfo, MockBuyer;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTaskSetBuyerInfo = jasmine.createSpy('MockTaskSetBuyerInfo');
            MockBuyer = jasmine.createSpy('MockBuyer');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'TaskSetBuyerInfo': MockTaskSetBuyerInfo,
                'Buyer': MockBuyer
            };
            createController = function() {
                $injector.get('$controller')("TaskSetBuyerInfoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'orderbookApp:taskSetBuyerInfoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
