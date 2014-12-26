define([
    'backbone',
    'tmpl/main'
], function( Backbone, tmpl) {

    var View = Backbone.View.extend({
	
		template: tmpl,
		className: 'wrap',
		
        initialize: function () {
			$('body').append(this.el);
			this.render();
            this.hide();
        },
		
        render: function () {
            this.$el.html(this.template);
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