<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html class="root61">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta name="format-detection" content="telephone=no">

<link rel="stylesheet" type="text/css" href="/css/saved_resource">
<link rel="stylesheet" type="text/css" href="/css/addtocart-album.css">
<link rel="stylesheet" type="text/css" href="/css/base.css">

<title>商品已成功加入购物车</title>
<script type="text/javascript" async=""
	src="./商品已成功加入购物车_files/conversion_async.js.下载"></script>
<script async="" src="./商品已成功加入购物车_files/gtm.js.下载"></script>
<script type="text/javascript">
	window.pageConfig = {
		compatible : true
	};
</script>
<script type="text/javascript" src="/css/saved_resource(1)"></script>
<link rel="stylesheet" type="text/css" href="/css/saved_resource(2)">
<script src="/css/saved_resource(3)"></script>
<script src="/css/saved_resource(4)"></script>
</head>
<body>

	<jsp:include page="commons/header.jsp"></jsp:include>
	<div class="main">
		<div class="success-wrap">
			<div class="w" id="result">
				<div class="m succeed-box">
					<div class="mc success-cont">
						<div class="success-lcol">
							<div class="success-top">
								<b class="succ-icon"></b>
								<h3 class="ftx-02">商品已成功加入购物车！</h3>
							</div>
							<div class="p-item">
								<div class="p-info"></div>
								<div class="clr"></div>
							</div>
						</div>
						<div class="success-btns success-btns-new">
							<div class="success-ad"></div>
							<div><a class="btn-tobback" href="javascript:history.back();" >继续购物</a>
								<a class="btn-addtocart"
									href="http://www.taotao.com/cart/cart.html"
									id="GotoShoppingCart" clstag="pageclick|keycount|201601152|4"><b></b>去购物车结算</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<jsp:include page="commons/footer.jsp" />
</body>
</html>