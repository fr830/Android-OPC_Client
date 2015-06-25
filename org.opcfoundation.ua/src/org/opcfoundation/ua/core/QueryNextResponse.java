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

import org.opcfoundation.ua.builtintypes.ServiceResponse;
import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.core.Identifiers;
import org.opcfoundation.ua.utils.ObjectUtils;
import org.opcfoundation.ua.core.QueryDataSet;
import org.opcfoundation.ua.core.ResponseHeader;


public class QueryNextResponse extends Object implements ServiceResponse {

	public static final NodeId ID = Identifiers.QueryNextResponse;
	public static final NodeId BINARY = Identifiers.QueryNextResponse_Encoding_DefaultBinary;
	public static final NodeId XML = Identifiers.QueryNextResponse_Encoding_DefaultXml;	
	
    protected ResponseHeader ResponseHeader;
    protected QueryDataSet[] QueryDataSets;
    protected byte[] RevisedContinuationPoint;
    
    public QueryNextResponse() {}
    
    public QueryNextResponse(ResponseHeader ResponseHeader, QueryDataSet[] QueryDataSets, byte[] RevisedContinuationPoint)
    {
        this.ResponseHeader = ResponseHeader;
        this.QueryDataSets = QueryDataSets;
        this.RevisedContinuationPoint = RevisedContinuationPoint;
    }
    
    public ResponseHeader getResponseHeader()
    {
        return ResponseHeader;
    }
    
    public void setResponseHeader(ResponseHeader ResponseHeader)
    {
        this.ResponseHeader = ResponseHeader;
    }
    
    public QueryDataSet[] getQueryDataSets()
    {
        return QueryDataSets;
    }
    
    public void setQueryDataSets(QueryDataSet[] QueryDataSets)
    {
        this.QueryDataSets = QueryDataSets;
    }
    
    public byte[] getRevisedContinuationPoint()
    {
        return RevisedContinuationPoint;
    }
    
    public void setRevisedContinuationPoint(byte[] RevisedContinuationPoint)
    {
        this.RevisedContinuationPoint = RevisedContinuationPoint;
    }
    
    /**
      * Deep clone
      *
      * @return cloned QueryNextResponse
      */
    public QueryNextResponse clone()
    {
        QueryNextResponse result = new QueryNextResponse();
        result.ResponseHeader = ResponseHeader==null ? null : ResponseHeader.clone();
        if (QueryDataSets!=null) {
            result.QueryDataSets = new QueryDataSet[QueryDataSets.length];
            for (int i=0; i<QueryDataSets.length; i++)
                result.QueryDataSets[i] = QueryDataSets[i].clone();
        }
        result.RevisedContinuationPoint = RevisedContinuationPoint;
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
		return ObjectUtils.printFieldsDeep(this);
	}
	
}
