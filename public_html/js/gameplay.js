define([
    'backbone',
	'collections/enemyField',
], function(Backbone, enemyField) {
    var ws,
        started = false,
        finished = false,
        myName = "",
        enemyName = "";

    var View = Backbone.View.extend({

        startGame: function() {
            ws = new WebSocket("ws://localhost:8090/gameplay");
            ws.onopen = function() {
                console.log("Соединение установлено.");
            };
            ws.onmessage = function(event) {
                var data = JSON.parse(event.data);
                console.log(data);

                if(data.status == "DECK") {
                    i = (data.y - 1) * 10 + data.x - 1;
                    enemyField.get(i).set("value", "hurt");
                }

                if(data.status == "GAME_OVER") {
                    i = (data.y - 1) * 10 + data.x - 1;
                    enemyField.get(i).set("value", "hurt");
                }

                if(data.status == "EMPTY") {
                    i = (data.y - 1) * 10 + data.x - 1;
                    enemyField.get(i).set("value", "miss");
                }
            };
            ws.onclose = function (event) {
                console.log("Соединение разорвано.");
            };
        },

        fire: function(x, y) {
            var message = {
                action: "fire",
                x: x,
                y: y
            }
            console.log(JSON.stringify(message));
            ws.send(JSON.stringify(message));
        },

        setShips: function(x, y, width, height) {
            var message = {
                action: "setShips",
                "ships": [
                    {
                        x: 1,
                        y: 1,
                        width: 2,
                        height: 1
                    }
                ]
            }
            console.log(JSON.stringify(message));
            ws.send(JSON.stringify(message));
        }
    });

    return new View();
});