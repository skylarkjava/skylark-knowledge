<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>

<%@page import="org.support.project.web.config.AppConfig"%>
<%@page import="org.support.project.knowledge.util.JspUtilEx"%>
<%@page import="org.support.project.knowledge.control.Control"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtilEx jspUtil = new JspUtilEx(request, pageContext); %>

<!-- scripts -->
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/jquery/dist/jquery.min.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/bootstrap/dist/js/bootstrap.min.js"></script>

<script type="text/javascript" src="<%= request.getContextPath() %>/bower/bluebird/js/browser/bluebird.min.js"></script>

<script type="text/javascript" src="<%= request.getContextPath() %>/bower/notifyjs/dist/notify.min.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/notifyjs/dist/notify-combined.min.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/notifyjs/dist/styles/bootstrap/notify-bootstrap.js"></script>

<script type="text/javascript" src="<%= request.getContextPath() %>/bower/marked/lib/marked.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/highlightjs/highlight.pack.js"></script>

<script type="text/javascript" src="<%= request.getContextPath() %>/bower/bootbox/bootbox.js"></script>

<script type="text/javascript" src="<%= request.getContextPath() %>/bower/notify.js/notify.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/jquery-oembed-all/jquery.oembed.js"></script>


<script type="text/javascript">
var _CONTEXT = '<%= request.getContextPath() %>';

var getCookies = function() {
    var result = new Array();
    var allcookies = document.cookie;
    if( allcookies != '' ) {
        var cookies = allcookies.split( '; ' );
        for( var i = 0; i < cookies.length; i++ ) {
            var cookie = cookies[ i ].split( '=' );
            result[ cookie[ 0 ] ] = decodeURIComponent( cookie[ 1 ] );
        }
    }
    return result;
}

var setCookie = function(c_name, value, expiredays, path) {
	var extime = new Date().getTime();
	var cltime = new Date(extime + (60 * 60 * 24 * 1000 * expiredays));
	var exdate = cltime.toUTCString();
	var s = '';
	s += c_name + '=' + escape(value) + ';';
	if (expiredays) {
		s += ' expires=' + exdate + ';';
	}
	if (path) {
		s += ' path=' + path + ';';
	} else {
		s += ' path=' + _CONTEXT + '/;';
//		s += ' path=/;';
	}

	document.cookie = s;
}
setCookie('<%= AppConfig.get().getSystemName() %>_<%= JspUtilEx.TIME_ZONE_OFFSET %>', (new Date()).getTimezoneOffset(), 60);

<%

java.util.List<String> successes = (java.util.List<String>) request.getAttribute(Control.MSG_SUCCESS);
if (successes != null) {
	for (String msg : successes) {
%>
$.notify('<%= msg %>', 'success');
<%
	}
}

java.util.List<String> infos = (java.util.List<String>) request.getAttribute(Control.MSG_INFO);
if (infos != null) {
	for (String msg : infos) {
%>
$.notify('<%= msg %>', 'info');
<%
	}
}

java.util.List<String> warns = (java.util.List<String>) request.getAttribute(Control.MSG_WARN);
if (warns != null) {
	for (String msg : warns) {
%>
$.notify('<%= msg %>', 'warn');
<%
	}
}

java.util.List<String> errors = (java.util.List<String>) request.getAttribute(Control.MSG_ERROR);
if (errors != null) {
	for (String msg : errors) {
%>
$.notify('<%= msg %>', 'error');
<%
	}
}

%>

<% if (jspUtil.is(Boolean.TRUE, "desktopNotify")) { %>
function onNotifyShow() {
	console.log('notification was shown!');
}

function notifyDesktop(msg, link) {
	$.notify(msg, 'info');
	if (Notify.isSupported) {
		Notify.requestPermission(
			function() {
				var myNotification = new Notify('Notify from Knowledge', {
					body: msg,
					notifyShow: onNotifyShow,
					timeout: 3,
					notifyClick: function() {
						if (link) {
							window.location.href=link;
						}
					}
				});
				myNotification.show();
			},
			function() {
				console.log('notification off. msg show to console.');
				console.log(msg);
			}
		);
	}
}

if (Notify.isSupported) {
	Notify.requestPermission(function() {
		console.log('notification on.');
	}, function() {
		console.log('notification off.');
	});
}

var webSocket;
window.onload = function() {
	var forRtoA = document.createElement('a');
	forRtoA.href = '<%= request.getContextPath() %>/notify';
	console.log(forRtoA.href.replace("http://", "ws://").replace("https://", "wss://"));
	webSocket = new WebSocket(forRtoA.href.replace("http://", "ws://").replace("https://", "wss://"));
	webSocket.onopen = function() {
	}
	webSocket.onclose = function() {
	}
	webSocket.onmessage = function(message) {
		console.log('[RECEIVE] ');
		var result = JSON.parse(message.data);
		console.log(result);
		notifyDesktop(result.message, result.result);
	}
	webSocket.onerror = function(message) {
	}
	<%-- //webSocket.send(message); --%>
}
<% } %>

</script>

<script>
$(document).ready(function() {

    //When checkboxes/radios checked/unchecked, toggle background color
    $('.form-group').on('click','input[type=radio]',function() {
        $(this).closest('.form-group').find('.radio-inline, .radio').removeClass('checked');
        $(this).closest('.radio-inline, .radio').addClass('checked');
    });
    $('.form-group').on('click','input[type=checkbox]',function() {
        $(this).closest('.checkbox-inline, .checkbox').toggleClass('checked');
    });

    //Show additional info text box when relevant checkbox checked
    $('.additional-info-wrap input[type=checkbox]').click(function() {
        if($(this).is(':checked')) {
            $(this).closest('.additional-info-wrap').find('.additional-info').removeClass('hide').find('input,select').removeAttr('disabled');
        }
        else {
            $(this).closest('.additional-info-wrap').find('.additional-info').addClass('hide').find('input,select').val('').attr('disabled','disabled');
        }
    });

    //Show additional info text box when relevant radio checked
    $('input[type=radio]').click(function() {
        $(this).closest('.form-group').find('.additional-info-wrap .additional-info').addClass('hide').find('input,select').val('').attr('disabled','disabled');
        if($(this).closest('.additional-info-wrap').length > 0) {
            $(this).closest('.additional-info-wrap').find('.additional-info').removeClass('hide').find('input,select').removeAttr('disabled');
        }
    });
});
</script>

<script>
	function changeCategory(categoryId,refresh) {
		function setCookie(c_name,value,path,expiredays){

		    // 有効期限の日付
		    var extime = new Date().getTime();
		    var cltime = new Date(extime + (60*60*24*1000*expiredays));
		    var exdate = cltime.toUTCString();
		    // クッキーに保存する文字列を生成
		    var s="";
		    s += c_name +"="+ escape(value);// 値はエンコードしておく
		    s += "; path="+ path;
		    if(expiredays){
		        s += "; expires=" +exdate+"; ";
		    }else{
		        s += "; ";
		    }
		    // クッキーに保存
		    document.cookie=s;
		}
		function reload() {
			window.location.reload();
		}
		setCookie("<%=AppConfig.get().getSystemName()%>_CATEGORY",categoryId,"<%=request.getContextPath()%>/",31);
		if (refresh) {
			reload();
		}
	}
</script>

<!-- /scripts -->
