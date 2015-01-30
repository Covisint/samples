<%@page import="java.net.URLEncoder"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="org.apache.http.StatusLine"%>
<%@page import="com.covisint.papi.sample.portlet.ebay.model.Item"%>
<%@page import="com.covisint.papi.sample.model.Person" %>
<%@page import="com.covisint.papi.sample.model.Address" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<portlet:defineObjects />
<jsp:include page="/html/header.jsp"></jsp:include>
<portlet:actionURL name="processPurchase" var="purchaseUrl">
</portlet:actionURL>

<%
	Item item = (Item) renderRequest
			.getAttribute("item");
    Person person = (Person) renderRequest.getAttribute("person");
	StatusLine status = (StatusLine) renderRequest
			.getAttribute("status");
	if (item == null) {
	%>
		<h4 class="bg-danger">Error!</h4>
	<%
	} else {
	%>
	<div class="panel panel-default">
	  <div class="panel-heading">
	    <h3 class="panel-title">Provide purchase details!</h3>
	  </div>
	  <div class="panel-body">

		<form action="<%= purchaseUrl %>" name="purchaseForm" method="post">
		<div id="formContent">
		<h2><%=item.getTitle().get(0) %></h2>
		<h3>Item Id : <%= item.getItemId().get(0) %></h3>
		<img class="img-responsive" alt="Thumbnail" src=" <%= item.getGalleryURL().get(0)%>">
		<p>Price (<%= item.getSellingStatus().get(0).getConvertedCurrentPrice().get(0).getCurrencyId() %>): <span id="price"><%= item.getSellingStatus().get(0).getConvertedCurrentPrice().get(0).getValue() %></span>
		<label for="'<portlet:namespace/>quantity'">Quantity : <input name='<portlet:namespace/>quantity' type="number" value="0" id="quantity" onchange="calculateTotal()">
		<p>Total (<%= item.getSellingStatus().get(0).getConvertedCurrentPrice().get(0).getCurrencyId() %>): <span id="total"></span>

		<h4>Shipping Address:</h4>
		<address>
			<p><%= person.addresses.get(0).streets.get(0)%>
			<br><%= person.addresses.get(0).city%>, &nbsp<%= person.addresses.get(0).state%>&nbsp<%= person.addresses.get(0).postal%></p>

			<button name="purchase" id="purchase" value="Purcahse" disabled="disabled" type="submit">Purchase</button>
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