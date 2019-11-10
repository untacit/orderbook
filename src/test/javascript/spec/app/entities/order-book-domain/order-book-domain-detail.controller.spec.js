'use strict';

describe('Controller Tests', function() {

    describe('OrderBookDomain Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockOrderBookDomain, MockPurchasedBook, MockBuyer, MockStore;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockOrderBookDomain = jasmine.createSpy('MockOrderBookDomain');
            MockPurchasedBook = jasmine.createSpy('MockPurchasedBook');
            MockBuyer = jasmine.createSpy('MockBuyer');
            MockStore = jasmine.createSpy('MockStore');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'OrderBookDomain': MockOrderBookDomain,
                'PurchasedBook': MockPurchasedBook,
                'Buyer': MockBuyer,
                'Store': MockStore
            };
            createController = function() {
                $injector.get('$controller')("OrderBookDomainDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'orderbookApp:orderBookDomainUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
