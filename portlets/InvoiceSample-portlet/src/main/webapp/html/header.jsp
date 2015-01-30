<%@page import="com.liferay.portal.service.UserServiceUtil"%>
<%@page import="com.liferay.portal.model.User"%>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>

<portlet:defineObjects />
<%
	String remoteUser = renderRequest.getRemoteUser();
	User user = UserServiceUtil.getUserById(Long.parseLong(remoteUser));
%>
<table class="table-header">
	<tr>
		<%
			if (user != null) {
		%>
		<td align="left"><a href="javascript: history.go(-1)">Back</a></td>
		<td align="right">Welcome <%= user.getFullName()%></td>
		<%
			}
		%>
	</tr>
</table>