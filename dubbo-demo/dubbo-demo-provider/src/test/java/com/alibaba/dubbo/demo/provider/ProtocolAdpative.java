package com.alibaba.dubbo.demo.provider;

import com.alibaba.dubbo.common.extension.ExtensionLoader;

public class ProtocolAdpative implements com.alibaba.dubbo.rpc.Protocol {
	// 没有打上@Adaptive 的方法如果被调到抛异常
	public void destroy() {
		throw new UnsupportedOperationException(
				"method public abstract void com.alibaba.dubbo.rpc.Protocol.destroy() of interface com.alibaba.dubbo.rpc.Protocol is not adaptive method!");
	}

	// 没有打上@Adaptive 的方法如果被调到抛异常
	public int getDefaultPort() {
		throw new UnsupportedOperationException(
				"method public abstract int com.alibaba.dubbo.rpc.Protocol.getDefaultPort() of interface com.alibaba.dubbo.rpc.Protocol is not adaptive method!");
	}

	// 接口中 export 方法打上@Adaptive 注册
	public com.alibaba.dubbo.rpc.Exporter export(com.alibaba.dubbo.rpc.Invoker arg0) {
		if (arg0 == null)
			throw new IllegalArgumentException("com.alibaba.dubbo.rpc.Invoker argument == null");
		// 参数类中要有 URL 属性
		if (arg0.getUrl() == null)
			throw new IllegalArgumentException("com.alibaba.dubbo.rpc.Invoker argument getUrl() == null");
		// 从入参获取统一数据模型 URL
		com.alibaba.dubbo.common.URL url = arg0.getUrl();
		String extName = (url.getProtocol() == null ? "dubbo" : url.getProtocol());
		// 从统一数据模型 URL 获取协议，协议名就是 spi 扩展点实现类的 key
		if (extName == null)
			throw new IllegalStateException("Fail to get extension(com.alibaba.dubbo.rpc.Protocol) name from url("+ url.toString() + ") use keys([protocol])");
		// 利用 dubbo 服务查找机制根据名称找到具体的扩展点实现
		com.alibaba.dubbo.rpc.Protocol extension = (com.alibaba.dubbo.rpc.Protocol) ExtensionLoader
				.getExtensionLoader(com.alibaba.dubbo.rpc.Protocol.class).getExtension(extName);
		// 调具体扩展点的方法
		return extension.export(arg0);
	}

	// 接口中 refer 方法打上@Adaptive 注册
	public com.alibaba.dubbo.rpc.Invoker refer(java.lang.Class arg0, com.alibaba.dubbo.common.URL arg1) {
		// 统一数据模型 URL 不能为空
		if (arg1 == null)
			throw new IllegalArgumentException("url == null");
		com.alibaba.dubbo.common.URL url = arg1;
		// 从统一数据模型 URL 获取协议，协议名就是 spi 扩展点实现类的 key
		String extName = (url.getProtocol() == null ? "dubbo" : url.getProtocol());
		if (extName == null)
			throw new IllegalStateException("Failtogetextension(com.alibaba.dubbo.rpc.Protocol) name from url("+ url.toString() + ")use keys([protocol])");
		// 利用 dubbo 服务查找机制根据名称找到具体的扩展点实现
		com.alibaba.dubbo.rpc.Protocol extension = (com.alibaba.dubbo.rpc.Protocol) ExtensionLoader
				.getExtensionLoader(com.alibaba.dubbo.rpc.Protocol.class).getExtension(extName);
		// 调具体扩展点的方法
		return extension.refer(arg0, arg1);
	}
}
