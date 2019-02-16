$(document).ready(function() {
//	hljs.initHighlightingOnLoad();
	marked.setOptions({
		langPrefix : '',
		highlight : function(code, lang) {
			console.log('[highlight]' + lang);
			return code;
		}
	});
	$('.content').each(function(i) {
		// var text = $(this).text();
		// $(this).html(marked(text));
	});

	/*
	$('.thumbnail').hover(function() {
		$(this).find('.discription').slideDown(250); // .fadeIn(250)
	}, function() {
		$(this).find('.discription').slideUp(250); // .fadeOut(205)
	});
	*/

	$('#input_tags').on('beforeItemRemove', function(event) {
		event.cancel = true;
	});
	echo.init();
});

