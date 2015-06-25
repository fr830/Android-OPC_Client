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

package org.opcfoundation.ua.core;

import org.opcfoundation.ua.builtintypes.Structure;
import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.core.Identifiers;
import org.opcfoundation.ua.utils.ObjectUtils;
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.builtintypes.Variant;



public class EventFieldList extends Object implements Structure, Cloneable {
	
	public static final NodeId ID = Identifiers.EventFieldList;
	public static final NodeId BINARY = Identifiers.EventFieldList_Encoding_DefaultBinary;
	public static final NodeId XML = Identifiers.EventFieldList_Encoding_DefaultXml;	
	
    protected UnsignedInteger ClientHandle;
    protected Variant[] EventFields;
    
    public EventFieldList() {}
    
    public EventFieldList(UnsignedInteger ClientHandle, Variant[] EventFields)
    {
        this.ClientHandle = ClientHandle;
        this.EventFields = EventFields;
    }
    
    public UnsignedInteger getClientHandle()
    {
        return ClientHandle;
    }
    
    public void setClientHandle(UnsignedInteger ClientHandle)
    {
        this.ClientHandle = ClientHandle;
    }
    
    public Variant[] getEventFields()
    {
        return EventFields;
    }
    
    public void setEventFields(Variant[] EventFields)
    {
        this.EventFields = EventFields;
    }
    
    /**
      * Deep clone
      *
      * @return cloned EventFieldList
      */
    public EventFieldList clone()
    {
        EventFieldList result = new EventFieldList();
        result.ClientHandle = ClientHandle;
        result.EventFields = EventFields==null ? null : EventFields.clone();
        return result;
    }
    


	public NodeId getTypeId() {
		return ID;
	}

	public NodeId getXmlEncodeId() {
		return XML;
	}

	public NodeId getBinaryEncodeId() {
		return BINARY;
	}
	
	public String toString() {
		return "EventFieldList: "+ObjectUtils.printFieldsDeep(this);
	}

}
