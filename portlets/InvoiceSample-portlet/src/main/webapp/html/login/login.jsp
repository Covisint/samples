<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<jsp:include page="/html/header.jsp"></jsp:include>
<portlet:defineObjects />
<portlet:actionURL name="processLogin" var="loginUrl">
</portlet:actionURL>
<aui:form action="<%= loginUrl.toString()%>" method="post" name="login">
	<aui:input name="userId" type="text" label="UserId" placeholder="email" required="true"></aui:input>
	<aui:input name="password" type="password" label="Password"></aui:input>
	<aui:button type="submit" value="Login"></aui:button>
</aui:form>
