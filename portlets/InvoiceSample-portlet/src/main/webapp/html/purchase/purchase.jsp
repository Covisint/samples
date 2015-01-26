<%@page import="java.net.URLEncoder"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="org.apache.http.StatusLine"%>
<%@page import="com.covisint.papi.sample.portlet.ebay.model.Item"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<portlet:defineObjects />
<jsp:include page="/html/header.jsp"></jsp:include>
<portlet:actionURL name="processPurchase" var="purchaseUrl">
</portlet:actionURL>

<%
	Item item = (Item) renderRequest
			.getAttribute("item");
	StatusLine status = (StatusLine) renderRequest
			.getAttribute("status");
	if (item == null) {
	%>
		<h4>Error!</h4>
	<%
	} else {
	%>
		<H4>Provide purchase details!</H4>
		<form action="<%= purchaseUrl %>" name="purchaseForm" method="post">
		<div id="formContent">
		<table>
		  <tr bgcolor="#E0F2F7">
		    <th colspan="2"><%=item.getTitle().get(0) %></th>
		  </tr>
		  <tr>
		  	<td colspan="2">Item Id : <%= item.getItemId().get(0) %></td>
		  </tr>
		  <tr>
			<td colspan="2" align="center"><img alt="Thumbnail" src=" <%= item.getGalleryURL().get(0)%>"></td>
		  </tr>
		  <tr>
		  	<td>Price (<%= item.getSellingStatus().get(0).getConvertedCurrentPrice().get(0).getCurrencyId() %>): </td>
		  	<td id="price"><%= item.getSellingStatus().get(0).getConvertedCurrentPrice().get(0).getValue() %>
		  	</td>
		  </tr>
		  <tr>
		  	<td colspan="1">Quantity : </td>
		  	<td colspan="1"><input name='<portlet:namespace/>quantity' type="number" value="0" id="quantity" onchange="calculateTotal()"> </td>
		  </tr>
		  <tr>
		  	<td>Total (<%= item.getSellingStatus().get(0).getConvertedCurrentPrice().get(0).getCurrencyId() %>): </td>
		  	<td id="total"></td>
		  </tr>
		  <tr>
		  	<td colspan="2" align="center"><input type="submit" name="purchase" id="purchase" value="Purchase" disabled="disabled"/></td>
		  </tr>
		</table>
		</div>
		<%
			String itemJson = new Gson().toJson(item);
			String itemString = URLEncoder.encode(itemJson, "UTF-8");
		%>
		<input name='<portlet:namespace/>itemJson' type="hidden" value="<%= itemString %>">
		</form>
		<%
	}
%>
<script type="text/JavaScript">
function calculateTotal() {
	var qty = parseInt(document.getElementById("quantity").value);
	var price = parseFloat(document.getElementById("price").innerHTML);
	document.getElementById("total").innerHTML = qty*price;
	if(qty > 0) {
		document.getElementById("purchase").disabled = false;
	} else {
		document.getElementById("purchase").disabled = true;
	}
}
</script>