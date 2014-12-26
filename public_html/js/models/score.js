define([
    'backbone',
], function(Backbone) {

    var Model = Backbone.Model.extend({							//extend  создание модели
        default:
        {
            name: '',
            score: 0
        },
		
        initialize: function(){
            console.log("New player is created");
        }
    });
	
    return Model;
});