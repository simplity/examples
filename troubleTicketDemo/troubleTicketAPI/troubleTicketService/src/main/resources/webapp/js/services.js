'use strict';

/* Services */

angular.module('myApp.services', ['ngResource'])

  .factory('TroubleTickets', function($resource){
    return $resource('api/troubleTicket/:id',
      {id: '@id'},
      {getTicket: {method: 'GET', params: {id: 0}}},
      {queryTicket: {method: 'GET', params: {id: 0}}}
    )
  })

  .value('version', '0.1');
