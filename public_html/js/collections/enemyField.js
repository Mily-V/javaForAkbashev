define([
    'backbone',
    'models/cell'
], function( Backbone, Cell) {
	
	var WIDTH_CELL = 40,
		HEIGHT_CELL = 40,
		CELL_NUMBER = 100,
		CELL_IN_LINE = 10,
		LEFT_INDENT = 500,										// отступ слева
		x0 = LEFT_INDENT, 
		y0 = 10,
		i = 0;
	
	var EnemyField = Backbone.Collection.extend({
	
        model: Cell,
	
		create: function() {
			for (i = 0; i < CELL_NUMBER; i++) {
				this.add({
					_id: i, 
					x: x0, 
					y: y0, 
					w: WIDTH_CELL, 
					h: HEIGHT_CELL, 
					number: i, 
					status: "empty", 
					block: false,
					lock: false,
					value: "empty"
				});
				x0 = x0 + WIDTH_CELL;
				if ((i+1) % CELL_IN_LINE == 0) {
					y0 = y0 + HEIGHT_CELL;
					x0 = LEFT_INDENT;
				}
			}					
		}
		
    });

	var enemyField = new EnemyField();
	
    return enemyField;
});