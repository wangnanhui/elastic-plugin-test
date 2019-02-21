package org.elasticsearch.plugin.handler;

import java.io.IOException;
import java.util.Date;

import org.elasticsearch.client.node.NodeClient;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.rest.BaseRestHandler;
import org.elasticsearch.rest.BytesRestResponse;
import org.elasticsearch.rest.RestController;
import org.elasticsearch.rest.RestRequest;
import org.elasticsearch.rest.RestRequest.Method;
import org.elasticsearch.rest.RestStatus;

public class PrintPluginHandler extends BaseRestHandler {

	private String printName = "printPluginTest"; 
	@Inject
	public PrintPluginHandler(Settings settings, RestController restController) {
		super(settings);
		restController.registerHandler(Method.GET, "print", this);// 注册
	}

	@Override
	public String getName() {

		return printName;
	}
	/**
	 * 处理业务逻辑
	 */
	@Override
	protected RestChannelConsumer prepareRequest(RestRequest request, NodeClient client) throws IOException {
		// 接收的参数
		System.out.println("params==" + request.params());

		long t1 = System.currentTimeMillis();

		String name = request.param("name");

		long cost = System.currentTimeMillis() - t1;
		// 返回内容，这里返回消耗时间 请求参数 插件名称
		return channel -> {
			XContentBuilder builder = channel.newBuilder();
			builder.startObject();
			builder.field("cost", cost);
			builder.field("name", name);
			builder.field("time", new Date());
			builder.field("pluginName",printName);
			builder.field("print","this is print plugin test");
			builder.endObject();
			channel.sendResponse(new BytesRestResponse(RestStatus.OK, builder));
		}; 

	}

}
