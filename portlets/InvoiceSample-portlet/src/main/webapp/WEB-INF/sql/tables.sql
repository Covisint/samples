create table Invoice_Consumer (
	consumerId LONG not null primary key,
	consumerName VARCHAR(75) null,
	password_ VARCHAR(75) null
);

create table Invoice_Invoice (
	invoiceId LONG not null primary key,
	path_ VARCHAR(75) null,
	userId LONG
);