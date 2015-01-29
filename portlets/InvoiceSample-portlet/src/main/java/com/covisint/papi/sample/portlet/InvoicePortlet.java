package com.covisint.papi.sample.portlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.covisint.papi.sample.model.Person;
import com.covisint.papi.sample.model.TokenResponse;
import com.covisint.papi.sample.portlet.ebay.model.FindResponse;
import com.covisint.papi.sample.portlet.ebay.model.Item;
import com.covisint.papi.sample.portlet.model.Invoice;
import com.covisint.papi.sample.portlet.service.InvoiceLocalServiceUtil;
import com.covisint.papi.sample.portlet.service.persistence.InvoiceUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.User;
import com.liferay.portal.service.CompanyServiceUtil;
import com.liferay.portal.service.UserServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;
import com.liferay.util.portlet.PortletProps;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

/**
 * Portlet implementation class InvoicePortlet
 */
public class InvoicePortlet extends MVCPortlet {
	
	private static Log _log = LogFactoryUtil.getLog(InvoicePortlet.class);

	private String loginTemplate;
	private String listTemplate;
	private String purchaseTemplate;
	private String displayInvoiceTemplate;

	@Override
	public void init(PortletConfig config) throws PortletException {
		super.init(config);
		listTemplate = getInitParameter("list-template");
		purchaseTemplate = getInitParameter("purchase-template");
		displayInvoiceTemplate = getInitParameter("display-invoice-template");
	}

	@Override
	public void doView(RenderRequest renderRequest,
			RenderResponse renderResponse) throws IOException, PortletException {
		// Get current logged in user
		String remoteUser = renderRequest.getRemoteUser();
		if (remoteUser == null) {
			ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest
					.getAttribute(WebKeys.THEME_DISPLAY);
			String currenturl = themeDisplay.getURLCurrent();
			long companyId = themeDisplay.getCompanyId();
			Company company;
			try {
				company = CompanyServiceUtil.getCompanyById(companyId);
				String portalURL = PortalUtil.getPortalURL(
						company.getVirtualHostname(),
						PortalUtil.getPortalPort(false), false);
				renderResponse.getWriter().write(
						"Please <a href=\"" + portalURL
								+ "/c/portal/login?redirect=" + currenturl
								+ "\">login</a> to proceed!");
			} catch (PortalException e) {
				_log.error("portal exception", e);
				e.printStackTrace();
			} catch (SystemException e) {
				_log.error("portal exception", e);
				e.printStackTrace();
			}
		} else {
			String token = (String) renderRequest.getPortletSession()
					.getAttribute("token");
			_log.debug("token = " + token);
			if (token == null) {
				token = getToken();
				renderRequest.getPortletSession().setAttribute("token", token);
			}
			String itemJson = renderRequest.getParameter("item");
			_log.debug("itemJson = " + itemJson);
			String pdfFilePath = (String) renderRequest
					.getAttribute("pdfFilePath");
			if (pdfFilePath == null) {
				pdfFilePath = (String) renderRequest
						.getParameter("pdfFilePath");
			}
			_log.debug("pdfFilePath = " + pdfFilePath);
			if (itemJson != null) {
				Gson gson = new GsonBuilder().create();
				Item item = gson.fromJson(itemJson, Item.class);
				_log.debug(item);
				renderRequest.setAttribute("item", item);
				User user = null;
				try {
					user = UserServiceUtil.getUserById(Long
							.parseLong(remoteUser));
				} catch (NumberFormatException e) {
					_log.error(e);
					e.printStackTrace();
				} catch (PortalException e) {
					_log.error(e);
					e.printStackTrace();
				} catch (SystemException e) {
					_log.error(e);
					e.printStackTrace();
				}
				String screenName = user.getScreenName();
				Person person = getPersons(getToken(), screenName);
				renderRequest.setAttribute("person", person);
				include(purchaseTemplate, renderRequest, renderResponse);
			} else if (pdfFilePath != null) {
				
				include(displayInvoiceTemplate, renderRequest, renderResponse);
			} else {
				final StringBuilder builder = new StringBuilder(2048);
				CloseableHttpClient httpClient = HttpClients.createDefault();
				try {
					URI uri = new URIBuilder(
							"http://svcs.ebay.com/services/search/FindingService/v1?SECURITY-APPNAME=Happiest-b69f-4286-bca8-9d02140be5a1&OPERATION-NAME=findItemsByKeywords&SERVICE-VERSION=1.0.0&RESPONSE-DATA-FORMAT=JSON&REST-PAYLOAD&keywords=iphone%203g&paginationInput.entriesPerPage=3")
							.build();
					HttpGet httpGet = new HttpGet(uri);
					System.out.println("AllPersonPortlet Request: "
							+ httpGet.toString());
					CloseableHttpResponse response = httpClient
							.execute(httpGet);
					renderRequest.setAttribute("status",
							response.getStatusLine());
					HttpEntity entity = response.getEntity();
					InputStream inputStream = entity.getContent();
					BufferedReader br = new BufferedReader(
							new InputStreamReader(inputStream));
					String line;

					while ((line = br.readLine()) != null) {
						builder.append(line);
					}
					_log.debug("All Person returned "
							+ builder.length() + " bytes");
					_log.debug("Response = " + builder.toString());

					Gson gson = new GsonBuilder().create();
					String responseString = builder.toString();
					FindResponse findResponse = gson.fromJson(responseString,
							FindResponse.class);
					_log.debug(findResponse);
					renderRequest.setAttribute("findResponse", findResponse);

					// Get all previous purchase Invoice
					try {
						User user = UserServiceUtil.getUserById(Long
								.parseLong(remoteUser));
						long userId = user.getUserId();
						List<Invoice> invoices = InvoiceLocalServiceUtil
								.getInvoicesForUser(userId);
						renderRequest.setAttribute("invoices", invoices);
					} catch (PortalException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SystemException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				include(listTemplate, renderRequest, renderResponse);
			}
		}
	}

	private Person getPersons(String token, String screenName) throws ClientProtocolException,
			IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String personAPIUrl = PortletProps.get("personAPIUrl");
		personAPIUrl = personAPIUrl+screenName.toUpperCase();
		_log.debug("personAPIUrl=" + personAPIUrl);
		HttpGet personGet = new HttpGet(personAPIUrl);
		personGet.addHeader("Accept",
				"application/vnd.com.covisint.platform.person.v1+json");
		personGet.addHeader("Authorization", token);
		HttpResponse response = httpClient.execute(personGet);

		if (response.getStatusLine().getStatusCode() == 200) {
			HttpEntity entity = response.getEntity();
			InputStream inputStream = entity.getContent();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					inputStream));
			String line;

			StringBuilder builder = new StringBuilder(2048);
			while ((line = br.readLine()) != null) {
				builder.append(line);
			}
			_log.debug("Response = " + builder.toString());

			Gson gson = new GsonBuilder().create();
			String responseString = builder.toString();
			Person person = gson.fromJson(responseString,
					Person.class);
			return person;
		} else {
			_log.error(response.getStatusLine().getStatusCode());
			throw new ClientProtocolException("Bad response" + response.getStatusLine().getStatusCode());
		}
	}

	private String getToken() throws ClientProtocolException, IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String tokenAPIUrl = PortletProps.get("tokenAPIUrl");
		HttpGet tokenGet = new HttpGet(tokenAPIUrl);
		tokenGet.addHeader("Accept",
				"application/vnd.com.covisint.platform.oauth.token.v1+json");
		tokenGet.addHeader("Type", "client_credentials");
		String base64EncodedKeys = getBase64CodedKeys();
		tokenGet.addHeader("Authorization", "Basic " + base64EncodedKeys);
		tokenGet.addHeader("Accept",
				"application/vnd.com.covisint.platform.oauth.token.v1+json");
		HttpResponse response = httpClient.execute(tokenGet);

		if (response.getStatusLine().getStatusCode() == 200) {
			HttpEntity entity = response.getEntity();
			InputStream inputStream = entity.getContent();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					inputStream));
			String line;

			StringBuilder builder = new StringBuilder(2048);
			while ((line = br.readLine()) != null) {
				builder.append(line);
			}
			_log.debug("All Person returned " + builder.length()
					+ " bytes");
			_log.debug("Response = " + builder.toString());

			Gson gson = new GsonBuilder().create();
			String responseString = builder.toString();
			TokenResponse tokenResponse = gson.fromJson(responseString,
					TokenResponse.class);
			_log.debug(tokenResponse);
			return tokenResponse.getAccess_token();
		}
		return null;
	}

	private String getBase64CodedKeys() {
		_log.debug("ClientId/ClientSecret" + System.getenv("env.apiClientId")+"/"+System.getenv("env.apiClientSecret"));
		String apiClientId = System.getProperty("env.apiClientId");//PortletProps.get("env.apiClientId");
		String apiClientSecret = System.getProperty("env.apiClientSecret");//PortletProps.get("env.apiClientSecret");
		String stringToEncode = apiClientId + ":" + apiClientSecret;
		String encodedString = Base64.encodeBase64String(stringToEncode
				.getBytes());
		return encodedString;
	}

	public void processPurchase(ActionRequest actionRequest,
			ActionResponse actionResponse) throws IOException,
			PortletException, SystemException {
		System.out.println("processAction");
		String itemString = actionRequest.getParameter("itemJson");
		String itemJson = URLDecoder.decode(itemString, "UTF-8");
		System.out.println("itemJson = " + itemJson);
		Gson gson = new GsonBuilder().create();
		Item item = gson.fromJson(itemJson, Item.class);

		String quantity = actionRequest.getParameter("quantity");
		System.out.println("quantity = " + quantity);
		StringBuilder builder = new StringBuilder();
		builder.append("<html><body>");
		builder.append("<table border=0>");
		builder.append("<tr bgcolor=\"#E0F2F7\">");
		builder.append("<th colspan=\"2\">" + item.getTitle().get(0) + "</th>");
		builder.append("</tr>");
		builder.append("<tr>");
		builder.append("<td colspan=\"2\">Item Id : " + item.getItemId().get(0)
				+ "</td>");
		builder.append("</tr>");
		builder.append("<tr>");
		builder.append("<td colspan=\"2\" align=\"center\"><img alt=\"Thumbnail\" src="
				+ item.getGalleryURL().get(0) + "></td>");
		builder.append("</tr>");
		builder.append("<tr>");
		builder.append("<td>Price ("
				+ item.getSellingStatus().get(0).getConvertedCurrentPrice()
						.get(0).getCurrencyId() + "): </td>");
		builder.append("<td id=\"price\">"
				+ item.getSellingStatus().get(0).getConvertedCurrentPrice()
						.get(0).getValue());
		builder.append("</td>");
		builder.append("</tr>");
		builder.append("<tr>");
		builder.append("<td colspan=\"1\">Quantity : </td>");
		builder.append("<td colspan=\"1\">" + quantity + "</td>");
		builder.append("</tr>");
		builder.append("<tr>");
		builder.append("<td>Total ("
				+ item.getSellingStatus().get(0).getConvertedCurrentPrice()
						.get(0).getCurrencyId() + "): </td>");
		Double total = Double.valueOf(item.getSellingStatus().get(0)
				.getConvertedCurrentPrice().get(0).getValue());
		Integer qty = Integer.valueOf(quantity);
		total = total * qty;
		builder.append("<td id=\"total\">" + total + "</td>");
		builder.append("</tr>");
		builder.append("</table>");

		URI uri;
		try {

			uri = new URIBuilder("http://api.html2pdfrocket.com/pdf")
					.addParameter("apikey",
							"3187d478-9b17-41a4-ad14-73f55c76b0bf")
					.addParameter("value", builder.toString()).build();
			// "http://api.html2pdfrocket.com/pdf"?apikey=3187d478-9b17-41a4-ad14-73f55c76b0bf&value="+builder.toString())
			HttpGet httpGet = new HttpGet(uri);
			System.out.println("html2pdfrocket Request: " + httpGet.toString());
			CloseableHttpClient httpClient = HttpClients.createDefault();
			CloseableHttpResponse response = httpClient.execute(httpGet);
			actionRequest.setAttribute("status", response.getStatusLine());
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String realPath = getPortletContext().getRealPath("/");
				String urlPath = "/html/pdfs/" + System.currentTimeMillis()
						+ ".pdf";
				File directories = new File(realPath + "/html/pdfs/");
				directories.mkdirs();
				File newFile = new File(realPath + urlPath);
				newFile.createNewFile();
				FileOutputStream fos = new FileOutputStream(newFile);
				HttpEntity entity = response.getEntity();
				InputStream inputStream = entity.getContent();
				byte[] buffer = new byte[1024];
				int len = -1;
				while ((len = inputStream.read(buffer)) > -1) {
					fos.write(buffer, 0, len);
				}
				inputStream.close();
				fos.close();
				actionRequest.setAttribute("pdfFilePath", urlPath);
				long invoiceId = CounterLocalServiceUtil.increment();
				Invoice invoice = InvoiceUtil.create(invoiceId);
				invoice.setPath(urlPath);
				String remoteUser = actionRequest.getRemoteUser();
				invoice.setUserId(Long.parseLong(remoteUser));
				InvoiceLocalServiceUtil.addInvoice(invoice);
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
}
