define([
    'backbone',
], function(Backbone) {
	
    var Model = Backbone.Model.extend({	

		idAttribute: "_id",
		
        default: {
			_id: 0,
            x: 0,
			y: 0,
			w: 0,
			h: 0,
			offsetX: 0,
			offsetY: 0,
			orientation: "horizon"
        },
		
		draw: function(ctx) {
			ctx.beginPath();
			ctx.rect(this.get("x"), this.get("y"), this.get("w"), this.get("h"));
			ctx.fillStyle = "#FFAC84";
			ctx.fill();
			ctx.lineWidth = 2;
			ctx.strokeStyle = "black";
			ctx.stroke();
		},
		
		turn: function() {
			var buf = this.get("w");
			this.set("w", this.get("h"));
			this.set("h", buf);	
		}
		
    });
	
    return Model;
});