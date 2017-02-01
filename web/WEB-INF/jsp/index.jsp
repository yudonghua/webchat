<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page import="javax.servlet.http.*,javax.servlet.*,websocket.Data" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%
String room = request.getParameter("room");
%>
<!DOCTYPE html>
<html>
<head>
    <title>聊天室</title>
    <link rel="stylesheet" href="css/index.css"/>
    <link rel="stylesheet" href="css/style.css"/>
    <link rel="stylesheet" type="text/css" href="css/demo.css"/>
    <script type="text/javascript" src="js/jquery1.42.min.js"></script>
    <script type="text/javascript" src="js/jquery.SuperSlide.2.1.1.js"></script>
    <style>
        #message{
            position : absolute;
            top:0px;
            transition:opacity 2s;
            -moz-transition:opacity 2s; /* Firefox 4 */
            -webkit-transition:opacity 2s; /* Safari and Chrome */
            -o-transition:opacity 2s; /* Opera */
        }
        #roomlist{
            position : absolute;
            top:0px;
            opacity:0;
            transition:opacity 2s;
            -moz-transition:opacity 2s; /* Firefox 4 */
            -webkit-transition:opacity 2s; /* Safari and Chrome */
            -o-transition:opacity 2s; /* Opera */
        }
        .room{
            background:#f8f8f8;box-shadow: 0px 1px 0px rgba(255,255,255,.1), inset 0px 1px 1px rgba(214, 214, 214, 0.7);width:740px; border-radius:5px;position:relative; padding: 20px 0;
            margin:10px 0;font-size:14px;font-family:"宋体"; 
        }
        .bt{
            background:#5EA51B;border-radius:0 30px;text-align:center;height: 31px;width: 85px;
            color:#fff;font-weight:bold;font-size:14px; 
        }
        .txt{
            background:#f8f8f8;box-shadow: 0px 1px 0px rgba(255,255,255,.1), inset 0px 1px 1px rgba(214, 214, 214, 0.7);width:340px; border-radius:5px;position:relative; padding: 2px 0;
            margin:10px 0;font-size:14px;font-family:"宋体";
        }

    </style>
</head>
<body>
    Welcome ${username}<br/><input id="text" type="text" class="txt"/>
    <button onclick="send()" class="bt">发送消息</button>
    <br/>
    <button onclick="change()" class="bt">选择房间</button>
    <br/>
    <div style="position: absolute; ">
        <div id="message"></div>
        <div id="roomlist">
            <div id="roomsn">
                
            </div>
            <form action="Data" method="post">
            <input id="newroom" name="room" type="text" class="txt"/> <button class="bt" type="submit">创建房间</button>
            </form>
        </div>
        
    </div>
    
</body>

<script type="text/javascript">
    var websocket = null;
    if("<%=room%>"=="null")
        inroom="public";
    else inroom="<%=room%>";
    getroom("<%=Data.room%>");
    //判断当前浏览器是否支持WebSocket
    if ('WebSocket' in window) {
        websocket = new WebSocket("ws://139.199.180.48:8080/webchat/websocket");
    }
    else {
        alert('当前浏览器 Not support websocket')
    }

    //连接发生错误的回调方法
    websocket.onerror = function () {
        setMessageInnerHTML("WebSocket连接发生错误");
    };

    //连接成功建立的回调方法
    websocket.onopen = function () {
        document.getElementById('message').innerHTML += "<ul class='say_box'><div class='sy'><p>连接成功</p>"+ "</div><span class='dateview'>"+"${username}"+"</span></ul>";
    }

    //接收到消息的回调方法
    websocket.onmessage = function (event) {
        setMessageInnerHTML(event.data);
    }

    //连接关闭的回调方法
    websocket.onclose = function () {
        setMessageInnerHTML("WebSocket连接关闭");
    }

    //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
    window.onbeforeunload = function () {
        closeWebSocket();
    }

    //将消息显示在网页上
    function setMessageInnerHTML(innerHTML) {
        var myobj=eval("("+innerHTML+")");
        if(inroom==myobj.room)
            document.getElementById('message').innerHTML = "<ul class='say_box'><div class='sy'><p>"+myobj.word+"</p>"+ "</div><span class='dateview'>"+myobj.name+"</span></ul>"
                + document.getElementById('message').innerHTML;
    }

    //关闭WebSocket连接
    function closeWebSocket() {
        websocket.close();
        change();
    }
    function addroom(){
    }

    function change(){
        document.getElementById('message').style.opacity="0.0";
        document.getElementById('roomlist').style.opacity="1";
    }
    function getroom(room){
        document.getElementById('roomsn').innerHTML="";
        var rooms = room.split(" ");
        var i;
        for(i=0;i<rooms.length;i++){
            document.getElementById('roomsn').innerHTML+="<div class='aroom'><p class='room'>"+rooms[i]+"</p></div>";
        }
    }
    //发送消息
    function send() {
        var message = document.getElementById('text').value;
        websocket.send("{ 'room':'"+inroom+"' , 'word':'"+message+"' ,'name':'"+"${username}"+"'}");
    }
    function backroom(){
        document.getElementById('message').style.opacity="1";
        document.getElementById('roomlist').style.opacity="0.0";
    }
    $(function(){
        $(".aroom").click(function(){
            var t=$(this).find('p[class*=room]');
            document.getElementById('message').innerHTML="";
            websocket.send("{ 'room':'"+t.text()+"' , 'word':'我来了' ,'name':'"+"${username}"+"'}");
            inroom=t.text();
            backroom();
        })
    })
</script>
</html>