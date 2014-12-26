define([
	'backbone',
	'tmpl/scoreboard', 
	'collections/scores' 
], function(Backbone, tmpl, scores) {
	
	var View = Backbone.View.extend({
	
		template: tmpl,
        className: 'wrap',
		model: scores,
		
        initialize: function () {
			$('body').append(this.el);
			this.render();
            this.hide();
        },
		
        render: function () {
            this.$el.html(this.template(this.model.toJSON()));
			return this;
        },
		
        show: function () {
            this.$el.show();
			this.trigger("show", this);
        },
		
        hide: function () {
            this.$el.hide();
        }
		
	});	

    return new View();
});