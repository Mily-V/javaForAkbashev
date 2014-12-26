define([
    'backbone',
    'models/ship'
], function( Backbone, Ship) {
	
	var WIDTH_CELL = 40,
		HEIGHT_CELL = 40,
		i = 0;
	
	var Collection = Backbone.Collection.extend({
	
        model: Ship	
		
    });

	var ships = new Collection([
		{_id:0,x:525,y:10, w:4*WIDTH_CELL, h:HEIGHT_CELL, offsetX:0, offsetY:0,orientation:"horizon"},
		{_id:1,x:525,y:10, w:3*WIDTH_CELL, h:HEIGHT_CELL, offsetX:0, offsetY:0,orientation:"horizon"},
		{_id:2,x:525,y:10, w:3*WIDTH_CELL, h:HEIGHT_CELL, offsetX:0, offsetY:0,orientation:"horizon"},  
		{_id:3,x:525,y:10, w:2*WIDTH_CELL, h:HEIGHT_CELL, offsetX:0, offsetY:0,orientation:"horizon"},  
		{_id:4,x:525,y:10, w:2*WIDTH_CELL, h:HEIGHT_CELL, offsetX:0, offsetY:0,orientation:"horizon"},
		{_id:5,x:525,y:10, w:2*WIDTH_CELL, h:HEIGHT_CELL, offsetX:0, offsetY:0,orientation:"horizon"}, 
		{_id:6,x:525,y:10, w:WIDTH_CELL, h:HEIGHT_CELL, offsetX:0, offsetY:0,orientation:"horizon"},
		{_id:7,x:525,y:10, w:WIDTH_CELL, h:HEIGHT_CELL, offsetX:0, offsetY:0,orientation:"horizon"},  
		{_id:8,x:525,y:10, w:WIDTH_CELL, h:HEIGHT_CELL, offsetX:0, offsetY:0,orientation:"horizon"},
		{_id:9,x:525,y:10, w:WIDTH_CELL, h:HEIGHT_CELL, offsetX:0, offsetY:0,orientation:"horizon"}
	]);
	
	
    return ships;
});