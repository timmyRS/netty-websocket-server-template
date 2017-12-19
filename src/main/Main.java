package main;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;

import java.io.File;

public class Main
{
	private static final int PORT = 8080;
	private static final File CERT_FILE = new File("server.crt");
	private static final File PRIV_FILE = new File("server.key");

	public static void main(String[] args) throws Exception
	{
		System.out.println("Loading Certificate...");
		if(!CERT_FILE.exists() || !PRIV_FILE.exists())
		{
			System.out.println("Missing Certificate and/or Private Key!");
			return;
		}
		final SslContext sslCtx = SslContextBuilder.forServer(CERT_FILE, PRIV_FILE).build();
		System.out.print("Starting Server...");
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try
		{
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
					.childHandler(new WebSocketServerInitializer(sslCtx));
			Channel ch = b.bind(PORT).sync().channel();
			// TODO: Start required threads
			System.out.println(" Server online.");
			ch.closeFuture().sync();
		}
		finally
		{
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
}
