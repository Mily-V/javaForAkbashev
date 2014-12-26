define([
    'backbone',
], function(Backbone) {
	
    var Cell = Backbone.Model.extend({	

		idAttribute: "_id",
		
        default: {
			_id: 0,
            x: 0,
			y: 0,
			w: 0,
			h: 0,
			number: 0,								
			status: "empty",
			block: false,
			lock: false,
			value: "empty"
        },
		
		draw: function(ctx) {
			ctx.beginPath();
			ctx.rect(this.get("x"), this.get("y"), this.get("w"), this.get("h"));			
			if (this.get("value") == "empty")					// сюда не стреляли
				ctx.fillStyle = "#F9FFD9";
			if (this.get("value") == "miss")					// промахнулись
				ctx.fillStyle = "#CDCDB4";
			if (this.get("value") == "hurt")					// ранен корабль
				ctx.fillStyle = "#FF777B";
			if (this.get("value") == "kill")					// утонул кораблик
				ctx.fillStyle = "#FF4C4B";	
			if (this.get("value") == "busy")					// тут твой корабль
				ctx.fillStyle = "#55ABA8";				
			ctx.lineWidth = 2;
			if (this.get("status") == "empty")
				ctx.strokeStyle = "black";
			if (this.get("status") == "ok") {
				ctx.fillStyle = "#94CAC8";
				ctx.lineWidth = 4;
				ctx.strokeStyle = "#55ABA8";
			}
			if (this.get("status") == "bad") {	
				ctx.fillStyle = "#FF777B";
				ctx.lineWidth = 4;
				ctx.strokeStyle = "#FF4C4B";
			}
			ctx.fill();
			ctx.stroke();	
		}
		
    });
	
    return Cell;
});