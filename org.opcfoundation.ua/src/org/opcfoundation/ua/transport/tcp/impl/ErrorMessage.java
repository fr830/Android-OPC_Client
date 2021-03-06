/* ========================================================================
 * Copyright (c) 2005-2013 The OPC Foundation, Inc. All rights reserved.
 *
 * OPC Reciprocal Community License ("RCL") Version 1.00
 * 
 * Unless explicitly acquired and licensed from Licensor under another 
 * license, the contents of this file are subject to the Reciprocal 
 * Community License ("RCL") Version 1.00, or subsequent versions as 
 * allowed by the RCL, and You may not copy or use this file in either 
 * source code or executable form, except in compliance with the terms and 
 * conditions of the RCL.
 * 
 * All software distributed under the RCL is provided strictly on an 
 * "AS IS" basis, WITHOUT WARRANTY OF ANY KIND, EITHER EXPRESS OR IMPLIED, 
 * AND LICENSOR HEREBY DISCLAIMS ALL SUCH WARRANTIES, INCLUDING WITHOUT 
 * LIMITATION, ANY WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR 
 * PURPOSE, QUIET ENJOYMENT, OR NON-INFRINGEMENT. See the RCL for specific 
 * language governing rights and limitations under the RCL.
 *
 * The complete license agreement can be found here:
 * http://opcfoundation.org/License/RCL/1.00/
 * ======================================================================*/

package org.opcfoundation.ua.transport.tcp.impl;

import java.lang.reflect.Field;

import org.opcfoundation.ua.builtintypes.StatusCode;
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.encoding.IEncodeable;

/**
 * ErrorMessage is a message used in TCP Handshake.
 *
 * 
 */
public class ErrorMessage implements IEncodeable {

	UnsignedInteger Error;
	String Reason;
	
	static
	{
		try {
			fields = new Field[]{
					ErrorMessage.class.getDeclaredField("Error"),
			ErrorMessage.class.getDeclaredField("Reason"),
			};
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		
	}
	
	private static Field[] fields;
	
	/**
	 * @return the fields
	 */
	public static Field[] getFields() {
		return fields;
	}
	
	public ErrorMessage() {}
	
	public ErrorMessage(UnsignedInteger error, String reason) {
		this.Error = error;
		this.Reason = reason;
	}
	
	public ErrorMessage(StatusCode code, String reason) {
		this.Error = code.getValue();
		this.Reason = reason;
	}
	
	public UnsignedInteger getError() {
		return Error;
	}
	public void setError(UnsignedInteger error) {
		Error = error;
	}
	public String getReason() {
		return Reason;
	}
	public void setReason(String reason) {
		Reason = reason;
	}		
	
}
