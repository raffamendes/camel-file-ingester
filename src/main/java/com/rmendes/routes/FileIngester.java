package com.rmendes.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.processor.aggregate.GroupedBodyAggregationStrategy;

public class FileIngester extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		from("ftp:admin@127.0.0.1:2121/teste?password=admin&passiveMode=true&streamDownload=true&autoCreate=false&move=finished&charset=iso-8859-1")
			.split().tokenize("\n")
			.aggregate(constant(true), new GroupedBodyAggregationStrategy())
			.completionSize(2)
			.to("kafka:file-full?brokers=10.74.215.91:9092&lingerMs=5&compressionCodec=snappy")
		.log("File: ${body}");
	}
}
