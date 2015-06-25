/* ========================================================================
 * Copyright (c) 2005-2013 The OPC Foundation, Inc. All rights reserved.
 *
 * OPC Foundation MIT License 1.00
 * 
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 *
 * The complete license agreement can be found here:
 * http://opcfoundation.org/License/MIT/1.00/
 * ======================================================================*/

package org.opcfoundation.ua.stacktest;

import java.util.*;


public class StackEvent {
	public enum StackEventType {
		SocketConnected, /// A socket has been established.
		SocketConnectFailed, /// A socket connection failed.
		SocketClosed, /// The socket was closed.
		RequestQueued, /// The client stack has received a request from the application.
		RequestSent, /// The client stack has completely sent the request.
		RequestReceived, /// The server stack has completely received the request.
		RequestComplete, /// The server stack has passed the request to the application.
		ResponseQueued, /// The server stack has received the response from the application.
		ResponseSent, /// The server stack has completely sent the response.
		ResponseReceived, /// The client stack has completely received the response.
		ResponseComplete, /// The client stack has returned the response to the application.
		MessageSent, /// A TCP message was sent via the socket.
		MessageReceived /// A TCP message was received via the socket.
	}
	public enum StackMessageType {
		Hello, 
		Acknowledge,
	    Disconnect,
	    DataChunk,
	    DataFinal,
	    Abort,
	    Error
	}
	    
	private StackEventType EventType; /// The type of event generated by the stack.
	private Date Timestamp; /// The UTC time of the event.
	private Integer RequestId; /// The request id assigned to the message.
	private StackMessageType MessageType; /// The message type that was sent/received.
	private ArrayList<TestParameter> Fields; /// Additional results that defined for specific test cases.
	public StackEventType getEventType() {
		return EventType;
	}
	public void setEventType(StackEventType eventType) {
		EventType = eventType;
	}
	public StackMessageType getMessageType() {
		return MessageType;
	}
	public void setMessageType(StackMessageType messageType) {
		MessageType = messageType;
	}
	public Integer getRequestId() {
		return RequestId;
	}
	public void setRequestId(Integer requestId) {
		RequestId = requestId;
	}
	public Date getTimestamp() {
		return Timestamp;
	}
	public void setTimestamp(Date timestamp) {
		Timestamp = timestamp;
	}
	public ArrayList<TestParameter> getFields() {
		return Fields;
	}
	public void addField(TestParameter field) {
		Fields.add(field);
	}
}
