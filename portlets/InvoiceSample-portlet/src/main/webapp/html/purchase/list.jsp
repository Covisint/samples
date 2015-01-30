<%@page import="com.covisint.papi.sample.portlet.model.Invoice"%>
<%@page import="java.util.List"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="com.covisint.papi.sample.portlet.ebay.model.Item"%>
<%@page
	import="com.covisint.papi.sample.portlet.ebay.model.SearchResult"%>
<%@page
	import="com.covisint.papi.sample.portlet.ebay.model.ResponseObject"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.apache.http.HttpStatus"%>
<%@page import="org.apache.http.StatusLine"%>
<%@page
	import="com.covisint.papi.sample.portlet.ebay.model.FindResponse"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<jsp:include page="/html/header.jsp"></jsp:include>

<portlet:defineObjects />

<%
	FindResponse findResponse = (FindResponse) renderRequest
			.getAttribute("findResponse");
	List<Invoice> invoices = (List<Invoice>) renderRequest
			.getAttribute("invoices");
	StatusLine status = (StatusLine) renderRequest
			.getAttribute("status");
	if (status.getStatusCode() != HttpStatus.SC_OK) {
%>
<div class="grid">
	<h4>Communication error!</h4>
<%=status.getStatusCode()%>
	<br />
<%=status.getReasonPhrase()%>
<%
	} else {
%>
	<h4>Select item to purchase</h4>
	<ul>
	<%
		ArrayList<ResponseObject> responseObjects = findResponse
					.getResponseObjects();
			for (int i = 0; i < responseObjects.size(); i++) {
				ResponseObject responseObject = responseObjects.get(i);
				ArrayList<SearchResult> searchResults = responseObject
						.getSearchResult();
				for (int j = 0; j < searchResults.size(); j++) {
					SearchResult searchResult = searchResults.get(j);
					ArrayList<Item> items = searchResult.getItem();
					for (int k = 0; k < items.size(); k++) {
						Item item = items.get(k);
						ArrayList<String> titles = item.getTitle();
						for (int l = 0; l < titles.size(); l++) {
							String title = titles.get(l);
							String itemJson = new Gson().toJson(item);
	%>
		<li>
			<a
			href='<portlet:renderURL><portlet:param name="item" value="<%=itemJson%>"/></portlet:renderURL>'>
			<img alt="Picture" src="<%=item.getGalleryURL().get(0)%>">
			</a>
			<br/>
			<a class="title-link"
			href='<portlet:renderURL><portlet:param name="item" value="<%=itemJson%>"/></portlet:renderURL>'>
			<%=title%>
			</a>
		</li>
	<%
		}
					}
				}
			}
	%>
	</ul>
</div>


<%
	if (invoices != null && invoices.size() > 0) {
%>
	<div class="sidebar">
		<div class="list-group">
			<span class="active list-group-item">Previous Purchases</span>
	<%
		for (int i = 0; i < invoices.size(); i++) {
					Invoice invoice = invoices.get(i);
					String pdfPath = invoice.getPath();
					String pdfName = pdfPath.substring(pdfPath
							.lastIndexOf('/') + 1);
	%>
	<a
		href='<portlet:renderURL><portlet:param name="pdfFilePath" value="<%=pdfPath%>"/></portlet:renderURL>' class="list-group-item"><%=pdfName%></a>
	<%
		}
	%>
	</div>
</div>
<%
	}

	}
%>
