<%@page import="com.covisint.papi.sample.portlet.model.Consumer"%>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>

<portlet:defineObjects />
<portlet:actionURL name="processLogout" var="logoutUrl">
</portlet:actionURL>
<%
	Consumer consumer = (Consumer) portletSession
			.getAttribute("consumer");
%>
<table width="100%">
	<tr>
		<%
			if (consumer != null) {
		%>
		<td align="left"><a href="javascript: history.go(-1)">Back</a></td>
		<td align="right"><%= consumer.getConsumerName()%><aui:a href="${ logoutUrl }">Logout</aui:a></td>
		<%
			}
		%>
	</tr>
</table>