<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>主页</title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/resources/jquery-easyui-1.4.5/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/resources/jquery-easyui-1.4.5/themes/icon.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/zTree_v3-master/css/zTreeStyle/zTreeStyle.css"
	type="text/css">
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/jquery-easyui-1.4.5/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/jquery-easyui-1.4.5/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/zTree_v3-master/js/jquery.ztree.core.js"></script>
<SCRIPT type="text/javascript">
	var setting = {
		data : {
			key : {
				title : "t"
			},
			simpleData : {
				enable : true
			}
		},
		callback : {
			onClick : onClick
		}
	};

	
	function onClick(event, treeId, treeNode, clickFlag) {

		//alert(treeNode.name);
		// add a new tab panel
		$('#tt').tabs('add', {
			title : treeNode.name,
			content : 'Tab Body',
			closable : true,
			tools : [ {
				iconCls : 'icon-mini-refresh',
				handler : function() {
					alert('refresh');
				}
			} ]
		});

	}

	$(document).ready(function() {
		
		$.post("<%=request.getContextPath()%>/index/getMenus","",function(data,status){
			
			//alert("Data: " + data + "\nStatus: " + status);
			$.fn.zTree.init($("#treeDemo"), setting,data);
		});
		
	});
</SCRIPT>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false"
		style="height: 60px; background: #B3DFDA; padding: 10px">north
		region</div>
	<div data-options="region:'west',split:true,title:'West'"
		style="width: 250px; padding: 10px;">
		<div class="easyui-accordion">
			<div title="About" data-options="iconCls:'icon-ok'"
				style="overflow: auto; padding: 10px;">
				<ul id="treeDemo" class="ztree"></ul>
			</div>
			<div title="Help" data-options="iconCls:'icon-help'"
				style="padding: 10px;">
				<p>help content</p>
			</div>
		</div>
	</div>
	<div
		data-options="region:'east',split:true,collapsed:true,title:'East'"
		style="width: 100px; padding: 10px;">east region</div>
	<div data-options="region:'south',border:false"
		style="height: 50px; background: #A9FACD; padding: 10px;">south
		region</div>
	<div data-options="region:'center',title:'Center'">

		<div id="tt" class="easyui-tabs">
			<div title="welcome" style="padding: 20px; display: none;">主页</div>
		</div>

	</div>
</body>
</html>