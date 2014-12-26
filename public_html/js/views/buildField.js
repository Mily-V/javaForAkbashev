define([
    'backbone',
	'models/cell',
	'models/ship',
	'collections/yourField',
	'collections/ships'	
], function(Backbone, Cell, Ship, field, ships){

	var WIDTH_CANVAS = 1200,
		HEIGHT_CANVAS = 600,
		WIDTH_CELL = 40,
		HEIGHT_CELL = 40,
		CELL_NUMBER = 100,
		CELL_IN_LINE = 10,
		SHIP_NUMBER = 10,
		DRAG_DIFFER = 8,
		ENTER_KEY = 13,
		UP_KEY = 38,
		RIGHT_KEY = 39,
		SHIP_START_X = 500,
		SHIP_START_Y = 10,
		i = 0,
		j = 0,
		k = 0,
		alreadyShip = 0,
		widthShip = WIDTH_CELL,
		heightShip = HEIGHT_CELL,
		mouseX = 0,
		mouseY = 0,
		drag = false,
		allowClick = true,
		currentShip = new Ship,
		newShipX = 0,
		newShipY = 0,
		statusCell = "ok",
		helpText = "Put Ship and press ENTER";
		

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
			_.bindAll(this, 'keyDown');
            $(document).on('keydown', this.keyDown);		
        },
		
        render: function () {			
			this.start();
			return this.el;
        },
		
		start: function() {
			field.create();
			this.putShip(alreadyShip);	
			this.draw();
		},
		
		putShip: function(number_ship) {
			if (number_ship < SHIP_NUMBER) {
				currentShip = ships.get(number_ship);
				newShipX = currentShip.get("x");
				newShipY = currentShip.get("y");
			}
		},
		
		draw: function() {
			this.ctx.fillStyle = "#FCE45C";
			this.ctx.fillRect(0, 0, this.el.width, this.el.height);	
			for (i = 0; i < CELL_NUMBER; i++) 
				if (field.get(i).get("status") == "empty")
					field.get(i).draw(this.ctx);
			for (i = 0; i < CELL_NUMBER; i++) 
				if (field.get(i).get("status") != "empty")
					field.get(i).draw(this.ctx);
			if (!this.isShipsAll()) {
				this.ctx.fillStyle = "black";
				this.ctx.strokeStyle = "black";
				this.ctx.font = "60px Palatino Linotype";
				this.ctx.textAlign = "center";
				this.ctx.fillText("Put Ship", 700, 170);
				this.ctx.fillText("and", 700, 240);
				this.ctx.fillText("press ENTER", 700, 310);
				currentShip.draw(this.ctx);		
			}
			else {
				this.ctx.fillStyle = "black";
				this.ctx.strokeStyle = "black";
				this.ctx.font = "60px Palatino Linotype";
				this.ctx.textAlign = "center"
				this.ctx.fillText("Press READY !", 700, 240);	
				allowClick = false;
			}
		},
		
		mouseDown: function(evt) {
			if (allowClick) {
				mouseX = evt.pageX - this.el.offsetLeft;
				mouseY = evt.pageY - this.el.offsetTop;
				if (
					mouseX < currentShip.get("x") + currentShip.get("w") && 
					mouseX > currentShip.get("x") && 
					mouseY < currentShip.get("y") + currentShip.get("h") && 
					mouseY > currentShip.get("y") 
				) {
					drag = true;
					currentShip.set("offsetX", mouseX - currentShip.get("x") + DRAG_DIFFER);
					currentShip.set("offsetY", mouseY - currentShip.get("y") + DRAG_DIFFER);
				}
			}
		},
		
		mouseMove: function(evt) {
			mouseX = evt.pageX - this.el.offsetLeft;
			mouseY = evt.pageY - this.el.offsetTop;
			if (drag) {
				currentShip.set("x", mouseX - currentShip.get("offsetX"));
				currentShip.set("y", mouseY - currentShip.get("offsetY"));
				this.draw();
				for (i = 0; i < CELL_NUMBER; i++) {
					for (j = 0; j < CELL_NUMBER; j++) {
						field.get(j).set("status", "empty");
					}
					if (
						currentShip.get("x") >= field.get(i).get("x") && 
						currentShip.get("x") <= field.get(i).get("x")  + WIDTH_CELL &&							
						currentShip.get("y") >= field.get(i).get("y") && 
						currentShip.get("y") <= field.get(i).get("y") + HEIGHT_CELL
					) {		
						this.changeCellStatus(i);
						break;
					}
				}
			}
		},
		
		mouseUp: function(evt) {
			drag = false;
			currentShip.set("x", newShipX);
			currentShip.set("y", newShipY);	
			for (i = 0; i < CELL_NUMBER; i++) {
				if (field.get(i).get("status") == "bad")
					field.get(i).set("status", "empty");
			}
			this.draw();
		},
		
		keyDown: function(evt) {
			switch (event.keyCode) {
				case ENTER_KEY:
					this.setShip();
					alreadyShip++;
					this.putShip(alreadyShip);
					break; 
				case UP_KEY:
					if (currentShip.get("orientation") == "horizon") {
						currentShip.set("orientation", "vertical");
						currentShip.turn();
					}	
					break;    
				case RIGHT_KEY:
					if (currentShip.get("orientation") == "vertical") {
						currentShip.set("orientation", "horizon");
						currentShip.turn();
					}
					break;
			};
			this.draw();
			
		},

		
		changeCellStatus: function(i) {
			widthShip = currentShip.get("w") / WIDTH_CELL;	
			heightShip = currentShip.get("h") / HEIGHT_CELL;
			if (
				currentShip.get("x") >= field.get(i).get("x") && 
				currentShip.get("x") <= field.get(i).get("x") + WIDTH_CELL/2 && 
				currentShip.get("y") >= field.get(i).get("y") && 
				currentShip.get("y") <= field.get(i).get("y") + HEIGHT_CELL/2 &&
				field.get(i).get("number") % CELL_IN_LINE <= CELL_IN_LINE - widthShip &&
				field.get(i+CELL_IN_LINE*heightShip)
			) {
				newShipX = field.get(i).get("x");
				newShipY = field.get(i).get("y");
				if (currentShip.get("orientation") == "horizon") {
					for (j = 0; j < widthShip; j++) {
						if (field.get(i).get("lock")) {
							statusCell = "bad";
							newShipX = SHIP_START_X;
							newShipY = SHIP_START_Y;
						}
						i++;
					}
					i = i - widthShip;
					for (j = 0; j < widthShip; j++) {					
						field.get(i).set("status", statusCell);
						i++;
					}
				}
				else {
					for (j = 0; j < heightShip; j++) 
						if (field.get(i + CELL_IN_LINE*j).get("lock")) {
							statusCell = "bad";
							newShipX = SHIP_START_X;
							newShipY = SHIP_START_Y;
						}
					for (j = 0; j < heightShip; j++) 
						field.get(i + CELL_IN_LINE*j).set("status", statusCell);	
				}
				statusCell = "ok";
			}
			if (
				currentShip.get("x") >= field.get(i).get("x") + WIDTH_CELL/2 && 
				currentShip.get("x") <= field.get(i).get("x") + WIDTH_CELL && 
				currentShip.get("y") >= field.get(i).get("y") && 
				currentShip.get("y") <= field.get(i).get("y") + HEIGHT_CELL/2 &&
				field.get(i).get("number") % CELL_IN_LINE <= CELL_IN_LINE - widthShip - 1&&
				field.get(i+CELL_IN_LINE*heightShip)
			) {
				newShipX = field.get(i+1).get("x");
				newShipY = field.get(i+1).get("y");
				if (currentShip.get("orientation") == "horizon") {
					for (j = 0; j < widthShip; j++) {
						if (field.get(i+1).get("lock")) {
							statusCell = "bad";
							newShipX = SHIP_START_X;
							newShipY = SHIP_START_Y;
						}
						i++;
					}
					i = i - widthShip;
					for (j = 0; j < widthShip; j++) {
						field.get(i+1).set("status", statusCell);
						i++;
					}
				}	
				else {
					for (j = 0; j < heightShip; j++) 
						if (field.get(i + CELL_IN_LINE*j + 1).get("lock")) {
							statusCell = "bad";
							newShipX = SHIP_START_X;
							newShipY = SHIP_START_Y;
						}
					for (j = 0; j < heightShip; j++) 
						field.get(i + CELL_IN_LINE*j + 1).set("status", statusCell);	
				}
				statusCell = "ok";
			}
			if (
				currentShip.get("x") >= field.get(i).get("x") && 
				currentShip.get("x") <= field.get(i).get("x") + WIDTH_CELL/2 && 
				currentShip.get("y") >= field.get(i).get("y") + HEIGHT_CELL/2 && 
				currentShip.get("y") <= field.get(i).get("y") + HEIGHT_CELL &&
				field.get(i).get("number") % CELL_IN_LINE <= CELL_IN_LINE - widthShip - 1 &&
				field.get(i+CELL_IN_LINE*heightShip)
			) {
				newShipX = field.get(i+CELL_IN_LINE).get("x");
				newShipY = field.get(i+CELL_IN_LINE).get("y");
				if (currentShip.get("orientation") == "horizon") {
					for (j = 0; j < widthShip; j++) {
						if (field.get(i+CELL_IN_LINE).get("lock")) {
							statusCell = "bad";
							newShipX = SHIP_START_X;
							newShipY = SHIP_START_Y;
						}
						i++;
					}
					i = i - widthShip;					
					for (j = 0; j < widthShip; j++) {
						field.get(i+CELL_IN_LINE).set("status", statusCell);
						i++;
					}
				}
				else {
					for (j = 0; j < heightShip; j++) 
						if (field.get(i + CELL_IN_LINE*(j + 1)).get("lock")) {
							statusCell = "bad";
							newShipX = SHIP_START_X;
							newShipY = SHIP_START_Y;
						}
					for (j = 0; j < heightShip; j++) 
						field.get(i + CELL_IN_LINE*(j + 1)).set("status", statusCell);	
				}
				statusCell = "ok";
			}
			if (
				currentShip.get("x") >= field.get(i).get("x") + WIDTH_CELL/2 && 
				currentShip.get("x") <= field.get(i).get("x") + WIDTH_CELL && 
				currentShip.get("y") >= field.get(i).get("y") + HEIGHT_CELL/2 && 
				currentShip.get("y") <= field.get(i).get("y") + HEIGHT_CELL &&
				field.get(i).get("number") % CELL_IN_LINE <= CELL_IN_LINE - widthShip - 1 &&
				field.get(i+CELL_IN_LINE*heightShip)
			) {
				newShipX = field.get(i+CELL_IN_LINE+1).get("x");
				newShipY = field.get(i+CELL_IN_LINE+1).get("y");
				if (currentShip.get("orientation") == "horizon") {
					for (j = 0; j < widthShip; j++) {
						if (field.get(i+CELL_IN_LINE+1).get("lock")) {
							statusCell = "bad";
							newShipX = SHIP_START_X;
							newShipY = SHIP_START_Y;
						}
						i++;
					}
					i = i - widthShip;	
					for (j = 0; j < widthShip; j++) {
						field.get(i+CELL_IN_LINE+1).set("status", statusCell);
						i++;
					}
				}
				else {
					for (j = 0; j < heightShip; j++) 
						if (field.get(i + CELL_IN_LINE*(j + 1) + 1).get("lock")) {
							statusCell = "bad";
							newShipX = SHIP_START_X;
							newShipY = SHIP_START_Y;
						}
					for (j = 0; j < heightShip; j++) 
						field.get(i + CELL_IN_LINE*(j + 1) + 1).set("status", statusCell);	
				}
				statusCell = "ok";
			}	
		},
		
		
		setShip: function() {
			for (i = 0; i < CELL_NUMBER; i++)
				if (field.get(i).get("status") == "ok")
					field.get(i).set("value", "busy");
			for (i = 0; i < CELL_NUMBER; i++) 
				if (field.get(i).get("status") == "ok") {
					j = i;
					break;
				}
			k = 0;
			if (field.get(j + CELL_IN_LINE))
				k--;
			if (field.get(j - CELL_IN_LINE)) {
				k--;
				j = j - CELL_IN_LINE;
			}	
			for (k; k < heightShip; k++) {
				for (i = 0; i < CELL_IN_LINE; i++) 
					field.get(Math.floor(j/CELL_IN_LINE)*CELL_IN_LINE+i).set("block",true);
				for (i = 0; i < j%CELL_IN_LINE-1; i++) 
					field.get(Math.floor(j/CELL_IN_LINE)*CELL_IN_LINE+i).set("block",false);
					
				for (i = j%CELL_IN_LINE+widthShip+1; i < CELL_IN_LINE; i++)
					field.get(Math.floor(j/CELL_IN_LINE)*CELL_IN_LINE+i).set("block",false);
				for (i = 0; i < CELL_IN_LINE; i++) 
					if (field.get(Math.floor(j/CELL_IN_LINE)*CELL_IN_LINE+i).get("block"))
						field.get(Math.floor(j/CELL_IN_LINE)*CELL_IN_LINE+i).set("lock",true);
				j = j + CELL_IN_LINE;
			}	
			for (i = 0; i < CELL_NUMBER; i++) {
				field.get(i).set("status", "empty");
			}					
		},
		
		isShipsAll: function() {
			if (alreadyShip == SHIP_NUMBER)
				return true;
			else
				return false;
		}
		
	});

    return new View();
});