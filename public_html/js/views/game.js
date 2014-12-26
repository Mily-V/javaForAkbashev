define([
    'backbone',
    'tmpl/game',
	'views/buildField',
	'views/gameField',
	'collections/yourField',
	'gameplay'
], function(Backbone, tmpl, build, game, field, gameplay){


    var View = Backbone.View.extend({

		template: tmpl,
        className: 'wrap',
		collection: field,

		events: {
			"click button.fieldBattle__ready": "ready" 
		},
		
        initialize: function () {
			$('body').append(this.el);
			this.render_build();
			this.hide();
        },
		
		ready: function() {
			this.render_game();
			gameplay.setShips(0,0,1,1);
		},
		
        render_build: function () {
            this.$el.html(this.template);
			this.$el.find(".fieldBattle__myField").append(build.render());
			return this;
        },
		
		render_game: function () {
            this.$el.html(this.template);
			this.$el.find(".fieldBattle__myField").append(game.render());
			return this;
        },
		
        show: function () {
            this.$el.show();
            gameplay.startGame();
			this.trigger("show", this);
        },
		
        hide: function () {
            this.$el.hide();
        }
	});

    return new View();
});