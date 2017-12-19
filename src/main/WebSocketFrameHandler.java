package main;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

public class WebSocketFrameHandler extends SimpleChannelInboundHandler<WebSocketFrame>
{
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception
	{
		super.userEventTriggered(ctx, evt);
		if(evt == WebSocketServerProtocolHandler.ServerHandshakeStateEvent.HANDSHAKE_COMPLETE)
		{
			// TODO: Handle the newly connected users
		}
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame frame)
	{
		if(frame instanceof TextWebSocketFrame)
		{
			String rawdata = ((TextWebSocketFrame) frame).text();
			// TODO: Handle user text
		}
	}
}
