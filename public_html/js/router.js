define([
    'backbone',
    'views/main',
    'views/game',
    'views/login',
	'views/scoreboard',
	'views/manager'
], function(Backbone, mainScreen, gameScreen, loginScreen, scoreboardScreen, viewManager) {

    var Router = Backbone.Router.extend({
	
        routes: {
            'scoreboard': 'scoreboardAction',
            'game': 'gameAction',
            'login': 'loginAction',
            '*default': 'defaultActions'
        },
		
		initialize : function() {
			viewManager.addView(gameScreen);
			viewManager.addView(scoreboardScreen);
			viewManager.addView(mainScreen);
			viewManager.addView(loginScreen);
            $('#loader').hide();
		},
		
        defaultActions: function () {
            mainScreen.show();
        },
		
        scoreboardAction: function () {
            scoreboardScreen.show();
        },
		
        gameAction: function () {
            gameScreen.show();
        },
        loginAction: function () {
            loginScreen.show();
        }	
		
    });
	
    return new Router();
});