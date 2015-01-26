<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.IOException"%>
<%@page import="javax.portlet.RenderResponse"%>
<%@page import="java.io.File"%>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>

<portlet:defineObjects />
<jsp:include page="/html/header.jsp"></jsp:include>
<%@ page language="java" contentType="application/pdf; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String pdfFilePath = (String) renderRequest
			.getAttribute("pdfFilePath");
	if (pdfFilePath == null) {
		pdfFilePath = (String) renderRequest
				.getParameter("pdfFilePath");
	}
	String realPath = getServletContext().getRealPath("/");

	String fileName = realPath + pdfFilePath.substring(1);
	fileName = fileName.replace('\\', '/');
	System.out.println(fileName);
%>
<object data="${pageContext.request.contextPath}<%=pdfFilePath %>"
	type="application/pdf" width="500" height="300">
	<a href="${pageContext.request.contextPath}<%=fileName%>">Download
		file.pdf</a>
</object>