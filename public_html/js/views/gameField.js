define([
    'backbone',
	'models/cell',
	'models/ship',
	'collections/yourField',
	'collections/enemyField',
	'collections/ships',
    'gameplay'
], function(Backbone, Cell, Ship, field, enemyField, ships, gameplay){

	var WIDTH_CANVAS = 1200,
		HEIGHT_CANVAS = 600,
		WIDTH_CELL = 40,
		HEIGHT_CELL = 40,
		CELL_NUMBER = 100,
		CELL_IN_LINE = 10,
		SHIP_NUMBER = 1,
		i = 0,
		j = 0,
		mouseX = 0,
		mouseY = 0,
		currentShip = new Ship;

    var View = Backbone.View.extend({

		tagName: 'canvas',
		events: {
			'mousedown' : 'mouseDown',
			'mousemove' : 'mouseMove',
			'mouseup' : 'mouseUp'
		},
		
        initialize: function () {
			this.el.width = WIDTH_CANVAS;
            this.el.height = HEIGHT_CANVAS;
			this.el.id = "drawing";
			this.ctx = this.el.getContext('2d');
        },
		
        render: function () {
			enemyField.create();
			this.draw();			
			return this.el;
        },
		
		draw: function() {
			for (i = 0; i < CELL_NUMBER; i++) 
				if (field.get(i).get("status") == "empty")
					field.get(i).draw(this.ctx);
			for (i = 0; i < CELL_NUMBER; i++) 
				if (field.get(i).get("status") != "empty")
					field.get(i).draw(this.ctx);
					
			for (i = 0; i < CELL_NUMBER; i++) 
				if (enemyField.get(i).get("status") == "empty")
					enemyField.get(i).draw(this.ctx);
		},
		
		mouseDown: function(evt) {
			mouseX = evt.pageX - this.el.offsetLeft;
			mouseY = evt.pageY - this.el.offsetTop;
			for (i = 0; i < CELL_NUMBER; i++) {
				if (
					mouseX > enemyField.get(i).get("x") && 
					mouseX < enemyField.get(i).get("x") + WIDTH_CELL &&							
					mouseY > enemyField.get(i).get("y") && 
					mouseY < enemyField.get(i).get("y") + HEIGHT_CELL
				) {
				    gameplay.fire(i%10+1,(i-i%10)/10+1);
					//enemyField.get(i).set("value", "miss");
				}
			}
		},
		
		mouseUp: function (evt) {
			this.draw();
		}
				
	});

    return new View();
});