var login = "";

$(function() {
	$("form#loginForm").submit(function(e) {
		e.preventDefault();
		$.ajax({
			type: "POST",
			url: "/auth/signin",
			data: $("form#loginForm").serialize(),
			statusCode: {
				200: function (response) {
					login = $("form#loginForm input[name='login']").val();
					password = $("form#loginForm input[name='password']").val();
					document.cookie = "login = " + login;
					window.localStorage.setItem("login", login);
					window.location.hash = "main";
					console.log("SC_OK");
				},
				403: function (response) {
					//alert(response.status);
                    result = JSON.parse(response.responseText);
                    $("div#error").html(result.error);
					console.log("SC_FORBIDDEN");
				}
			}
		});
	});
});