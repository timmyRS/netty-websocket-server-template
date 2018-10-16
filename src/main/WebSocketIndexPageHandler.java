package main;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.util.CharsetUtil;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class WebSocketIndexPageHandler extends SimpleChannelInboundHandler<FullHttpRequest>
{

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest req)
	{
		ByteBuf buf = Unpooled.copiedBuffer("200", CharsetUtil.UTF_8);
		DefaultFullHttpResponse res = new DefaultFullHttpResponse(HTTP_1_1, OK);
		// TODO: Handle any request other than the websocket path
		res.content().writeBytes("You seem to be in the wrong place.".getBytes());
		buf.release();
		HttpUtil.setContentLength(res, res.content().readableBytes());
		ctx.channel().writeAndFlush(res).addListener(ChannelFutureListener.CLOSE);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
	{
		cause.printStackTrace();
		ctx.close();
	}
}
