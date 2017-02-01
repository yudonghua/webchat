
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String username = (String)session.getAttribute("username");
String password = (String)session.getAttribute("password");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>登录</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript">
	  function change(){
		  var img =document.getElementById("verify");
		  img.src="VerifyCodeServlet?a="+new Date().getTime();
	  }
  	</script>
        <link rel="stylesheet" href="css/font-awesome.min.css"/>
<link rel="stylesheet" href="css/bootstrap.min.css"/>
<link rel="stylesheet" type="text/css" href="css/demo.css"/>
        <style type="text/css">
	.form-horizontal{
		background: #fff;
		padding-bottom: 40px;
		border-radius: 15px;
		text-align: center;
	}
	.form-horizontal .heading{
		display: block;
		font-size: 35px;
		font-weight: 700;
		padding: 35px 0;
		border-bottom: 1px solid #f0f0f0;
		margin-bottom: 30px;
	}
	.form-horizontal .form-group{
		padding: 0 40px;
		margin: 0 0 25px 0;
		position: relative;
	}
	.form-horizontal .form-control{
		background: #f0f0f0;
		border: none;
		border-radius: 20px;
		box-shadow: none;
		padding: 0 20px 0 45px;
		height: 40px;
		transition: all 0.3s ease 0s;
	}
        .form-horizontal .form-ve{
		background: #f0f0f0;
		border: none;
		border-radius: 20px;
		box-shadow: none;
		padding: 0 20px 0 45px;
		height: 40px;
                width: 30%;
		transition: all 0.3s ease 0s;
	}
	.form-horizontal .form-control:focus{
		background: #e0e0e0;
		box-shadow: none;
		outline: 0 none;
	}
	.form-horizontal .form-group i{
		position: absolute;
		top: 12px;
		left: 60px;
		font-size: 17px;
		color: #c8c8c8;
		transition : all 0.5s ease 0s;
	}
	.form-horizontal .form-control:focus + i{
		color: #00b4ef;
	}
	.form-horizontal .fa-question-circle{
		display: inline-block;
		position: absolute;
		top: 12px;
		right: 60px;
		font-size: 20px;
		color: #808080;
		transition: all 0.5s ease 0s;
	}
	.form-horizontal .fa-question-circle:hover{
		color: #000;
	}
	.form-horizontal .main-checkbox{
		float: left;
		width: 20px;
		height: 20px;
		background: #11a3fc;
		border-radius: 50%;
		position: relative;
		margin: 5px 0 0 5px;
		border: 1px solid #11a3fc;
	}
	.form-horizontal .main-checkbox label{
		width: 20px;
		height: 20px;
		position: absolute;
		top: 0;
		left: 0;
		cursor: pointer;
	}
	.form-horizontal .main-checkbox label:after{
		content: "";
		width: 10px;
		height: 5px;
		position: absolute;
		top: 5px;
		left: 4px;
		border: 3px solid #fff;
		border-top: none;
		border-right: none;
		background: transparent;
		opacity: 0;
		-webkit-transform: rotate(-45deg);
		transform: rotate(-45deg);
	}
	.form-horizontal .main-checkbox input[type=checkbox]{
		visibility: hidden;
	}
	.form-horizontal .main-checkbox input[type=checkbox]:checked + label:after{
		opacity: 1;
	}
	.form-horizontal .text{
		float: left;
		margin-left: 7px;
		line-height: 20px;
		padding-top: 5px;
		text-transform: capitalize;
	}
	.form-horizontal .btn{
		float: right;
		font-size: 14px;
		color: #fff;
		background: #00b4ef;
		border-radius: 30px;
		padding: 10px 25px;
		border: none;
		text-transform: capitalize;
		transition: all 0.5s ease 0s;
	}
        .form-style{
            filter:alpha(opacity=70);
            -moz-opacity:0.7;
            opacity:0.7;
        }
	@media only screen and (max-width: 479px){
		.form-horizontal .form-group{
			padding: 0 25px;
		}
		.form-horizontal .form-group i{
			left: 45px;
		}
		.form-horizontal .btn{
			padding: 10px 20px;
		}
	}
        </style>
  </head>
  
  <body>
  <center>
<div>
<div class="demo" style="padding: 20px 0;">
	<div class="container">
		<div class="row">
			<div class="col-md-offset-3 col-md-6">
				<form action="LoginServlet" method="post" class="form-horizontal form-style">
					<span class="heading">用户登录</span>
					<div class="form-group">
						<input type="text" class="form-control" id="inputEmail3" name="username"  value="${username}" placeholder="请输入账号">
                                             
						<i class="fa fa-user"></i>
					</div>
					<div class="form-group help">
						<input type="password" class="form-control" id="inputPassword3" name="password"  value="${password}" placeholder="请输入密码">
						<i class="fa fa-lock"></i>
						<!--<a href="#" class="fa fa-question-circle"></a>-->
					</div>
                                        <div class="form-group help" >
						<input type="text" class="form-ve" name="verifycode" placeholder="验证码" style="float:left">
                                                <font color="red" size="2" style="line-height:40px">${msg}</font>
                                                <a href="javascript:change()" style="float:right"><img src="VerifyCodeServlet" width="200" height="40" id="verify" border="0" ></a>

<!--						<i class="fa fa-lock"></i>-->
						<!--<a href="#" class="fa fa-question-circle"></a>-->
					</div>
					<div class="form-group" >
						<div class="main-checkbox">
							<input type="checkbox" value="None" id="checkbox1" name="check">
							<label for="checkbox1"></label>
						</div>
						<span class="text">记住密码</span>
                                                <input type="submit" class="btn btn-default" value="登录"/>
						<!--<button type="submit" class="btn btn-default">登录</button>-->
                                                
                                            
					</div>
                                       
                                        
				</form>
			</div>
		</div>
	</div>
</div>
<center> <a href="regist.htm" style="visibility: visible"><font size="2">没有帐号？点击注册</font></a></center>

</div>
</center>
<script type="text/javascript">
    var oSetting = new ActiveXObject("rcbdyctl.Setting");
    ip = oSetting.GetIPAddress; 
    alert(ip);
            var sub = document.getElementById("login"); 
                var username = "<%=username%>";
                var password = "<%=password%>";
                if(username!=="null"&&password!=="null")sub.click();;

 

</script>
  </body>
</html>