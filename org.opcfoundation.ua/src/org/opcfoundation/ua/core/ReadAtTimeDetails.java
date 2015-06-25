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

import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.core.HistoryReadDetails;
import org.opcfoundation.ua.utils.ObjectUtils;
import org.opcfoundation.ua.builtintypes.Structure;
import org.opcfoundation.ua.core.Identifiers;
import org.opcfoundation.ua.builtintypes.DateTime;
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.builtintypes.DateTime;
import org.opcfoundation.ua.core.HistoryReadDetails;



public class ReadAtTimeDetails extends HistoryReadDetails implements Structure, Cloneable {
	
	public static final NodeId ID = Identifiers.ReadAtTimeDetails;
	public static final NodeId BINARY = Identifiers.ReadAtTimeDetails_Encoding_DefaultBinary;
	public static final NodeId XML = Identifiers.ReadAtTimeDetails_Encoding_DefaultXml;	
		
    protected DateTime[] ReqTimes;
    protected Boolean UseSimpleBounds;
    
    public ReadAtTimeDetails() {}
    
    public ReadAtTimeDetails(DateTime[] ReqTimes, Boolean UseSimpleBounds)
    {
        this.ReqTimes = ReqTimes;
        this.UseSimpleBounds = UseSimpleBounds;
    }
    
    public DateTime[] getReqTimes()
    {
        return ReqTimes;
    }
    
    public void setReqTimes(DateTime[] ReqTimes)
    {
        this.ReqTimes = ReqTimes;
    }
    
    public Boolean getUseSimpleBounds()
    {
        return UseSimpleBounds;
    }
    
    public void setUseSimpleBounds(Boolean UseSimpleBounds)
    {
        this.UseSimpleBounds = UseSimpleBounds;
    }
    
    /**
      * Deep clone
      *
      * @return cloned ReadAtTimeDetails
      */
    public ReadAtTimeDetails clone()
    {
        ReadAtTimeDetails result = new ReadAtTimeDetails();
        result.ReqTimes = ReqTimes==null ? null : ReqTimes.clone();
        result.UseSimpleBounds = UseSimpleBounds;
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
		return "ReadAtTimeDetails: "+ObjectUtils.printFieldsDeep(this);
	}
}
