<%@page import="java.util.List"%>
<%@page import="com.careshare.bean.FoodItemBean"%>

<%
List<FoodItemBean> list=(List<FoodItemBean>)request.getAttribute("foodList");
%>

<!DOCTYPE html>

<html>

<head>

<title>Food Items</title>

<link rel="stylesheet" href="style.css">

</head>

<body>

<div class="navbar">

<h2>CareShare Food Management</h2>

<a href="addFoodItem.jsp">Add Food</a>

</div>

<div class="container">

<div class="card">

<h2>Food Item List</h2>

<table>

<tr>

<th>ID</th>

<th>Name</th>

<th>Type</th>

<th>Description</th>

<th>Category</th>

<th>Action</th>

</tr>

<%
for(FoodItemBean food:list){
%>

<tr>

<td><%=food.getFoodItemId()%></td>

<td><%=food.getFoodItemName()%></td>

<td><%=food.getFoodItemType()%></td>

<td><%=food.getFoodItemDescription()%></td>

<td><%=food.getCategoryId()%></td>

<td>

<a class="button" href="UpdateFoodItemServlet?id=<%=food.getFoodItemId()%>">Edit</a>

<a class="button reject" href="DeleteFoodItemServlet?id=<%=food.getFoodItemId()%>">Delete</a>

</td>

</tr>

<%
}
%>

</table>

</div>

</div>

</body>

</html>
