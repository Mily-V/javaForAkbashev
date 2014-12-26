define([
    'backbone',
    'models/score'
], function( Backbone, Score) {

	var Collection = Backbone.Collection.extend({
        model: Score,													//связываем коллекцию с моделью
		
        initialize: function() {
            console.log("Scoreboard is created");
        }
    });

    var Scores = new Collection([
        {name: "AAA", score: 78809},
        {name: "BBB", score: 10000},
        {name: "CCC", score: 79876},
        {name: "DDD", score: 6677},
		{name: "EEE", score: 78809},
        {name: "FFF", score: 10000},
        {name: "GGG", score: 79876},
        {name: "HHH", score: 6677},
		{name: "III", score: 796},
        {name: "JJJ", score: 6}
		
    ]);
    return Scores;
});