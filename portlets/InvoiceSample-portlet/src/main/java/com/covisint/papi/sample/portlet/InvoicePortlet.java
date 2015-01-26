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
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.covisint.papi.sample.portlet.ebay.model.FindResponse;
import com.covisint.papi.sample.portlet.ebay.model.Item;
import com.covisint.papi.sample.portlet.model.Consumer;
import com.covisint.papi.sample.portlet.model.ConsumerModel;
import com.covisint.papi.sample.portlet.model.Invoice;
import com.covisint.papi.sample.portlet.service.ConsumerLocalServiceUtil;
import com.covisint.papi.sample.portlet.service.InvoiceLocalService;
import com.covisint.papi.sample.portlet.service.InvoiceLocalServiceUtil;
import com.covisint.papi.sample.portlet.service.persistence.InvoiceUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * Portlet implementation class InvoicePortlet
 */
public class InvoicePortlet extends MVCPortlet {

	private String loginTemplate;
	private String listTemplate;
	private String purchaseTemplate;
	private String displayInvoiceTemplate;

	@Override
	public void init(PortletConfig config) throws PortletException {
		super.init(config);
		loginTemplate = getInitParameter("login-template");
		listTemplate = getInitParameter("list-template");
		purchaseTemplate = getInitParameter("purchase-template");
		displayInvoiceTemplate = getInitParameter("display-invoice-template");
	}

	@Override
	public void doView(RenderRequest renderRequest,
			RenderResponse renderResponse) throws IOException, PortletException {
		String token = (String) renderRequest.getPortletSession().getAttribute(
				"token");
		System.out.println("token = " + token);
		if (token == null) {
			include(loginTemplate, renderRequest, renderResponse);
		} else {
			String itemJson = renderRequest.getParameter("item");
			System.out.println("itemJson = " + itemJson);
			String pdfFilePath = (String) renderRequest
					.getAttribute("pdfFilePath");
			if(pdfFilePath == null) {
				pdfFilePath = (String) renderRequest.getParameter("pdfFilePath");
			}
			System.out.println("pdfFilePath = " + pdfFilePath);
			if (itemJson != null) {
				Gson gson = new GsonBuilder().create();
				Item item = gson.fromJson(itemJson, Item.class);
				System.out.println(item);
				renderRequest.setAttribute("item", item);
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
					System.out.println("All Person returned "
							+ builder.length() + " bytes");
					System.out.println("Response = " + builder.toString());

					Gson gson = new GsonBuilder().create();
					String responseString = builder.toString();
					FindResponse findResponse = gson.fromJson(responseString,
							FindResponse.class);
					System.out.println(findResponse);
					renderRequest.setAttribute("findResponse", findResponse);
					
					//Get all previous purchase Invoice
					Consumer consumer = (Consumer) renderRequest.getPortletSession()
							.getAttribute("consumer");
					long consumerId = consumer.getConsumerId();
					List<Invoice> invoices = InvoiceLocalServiceUtil.getInvoicesForConsumer(consumerId);
					renderRequest.setAttribute("invoices", invoices);
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SystemException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				include(listTemplate, renderRequest, renderResponse);
			}
		}
	}

	public void processLogin(ActionRequest actionRequest,
			ActionResponse actionResponse) throws NoSuchConsumerException, SystemException {
		String userId = actionRequest.getParameter("userId");
		String password = actionRequest.getParameter("password");
		System.out.println(userId);
		Consumer consumer = ConsumerLocalServiceUtil.getConsumer(userId);
		System.out.println(consumer);
		if (consumer != null) {
			String consumerPass = consumer.getPassword();
			if (consumerPass != null && consumerPass.equals(password)) {
				SessionMessages.add(actionRequest, "request_processed",
						"Welcome!");
				actionRequest.getPortletSession()
						.setAttribute("token", "token");
				actionRequest.getPortletSession().setAttribute("consumer",
						consumer);
			}
		}
	}

	public void processLogout(ActionRequest actionRequest,
			ActionResponse actionResponse) {
		actionRequest.getPortletSession().removeAttribute("token");
		actionRequest.getPortletSession().removeAttribute("consumer");
	}

	public void processPurchase(ActionRequest actionRequest,
			ActionResponse actionResponse) throws IOException, PortletException, SystemException {
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
				actionRequest.setAttribute("pdfFilePath",
						urlPath);
				long invoiceId = CounterLocalServiceUtil.increment();
				Invoice invoice = InvoiceUtil.create(invoiceId);
				invoice.setPath(urlPath);
				Consumer consumer = (Consumer)actionRequest.getPortletSession().getAttribute("consumer");
				long consumerId = consumer.getConsumerId();
				invoice.setConsumerId(consumerId);
				InvoiceLocalServiceUtil.addInvoice(invoice);
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
}
