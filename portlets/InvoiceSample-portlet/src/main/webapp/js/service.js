Liferay.Service.register("Liferay.Service.Invoice", "com.covisint.papi.sample.portlet.service", "InvoiceSample-portlet");

Liferay.Service.registerClass(
	Liferay.Service.Invoice, "Consumer",
	{
		getConsumer: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Invoice, "Invoice",
	{
		getInvoicesForConsumer: true
	}
);