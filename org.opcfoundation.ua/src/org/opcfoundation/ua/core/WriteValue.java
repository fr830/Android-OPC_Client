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
import org.opcfoundation.ua.builtintypes.ServiceResult;
import org.opcfoundation.ua.core.StatusCodes;
import org.opcfoundation.ua.core.WriteValue;
import org.opcfoundation.ua.utils.NumericRange;
import org.opcfoundation.ua.builtintypes.DataValue;
import org.opcfoundation.ua.builtintypes.UnsignedInteger;



public class WriteValue extends Object implements Structure, Cloneable {
	
	public static final NodeId ID = Identifiers.WriteValue;
	public static final NodeId BINARY = Identifiers.WriteValue_Encoding_DefaultBinary;
	public static final NodeId XML = Identifiers.WriteValue_Encoding_DefaultXml;	
	

	NumericRange ParsedIndexRange = NumericRange.getEmpty();
	
    protected NodeId NodeId;
    protected UnsignedInteger AttributeId;
    protected String IndexRange;
    protected DataValue Value;
    
    public WriteValue() {}
    
    public WriteValue(NodeId NodeId, UnsignedInteger AttributeId, String IndexRange, DataValue Value)
    {
        this.NodeId = NodeId;
        this.AttributeId = AttributeId;
        this.IndexRange = IndexRange;
        this.Value = Value;
    }
    
    public NodeId getNodeId()
    {
        return NodeId;
    }
    
    public void setNodeId(NodeId NodeId)
    {
        this.NodeId = NodeId;
    }
    
    public UnsignedInteger getAttributeId()
    {
        return AttributeId;
    }
    
    public void setAttributeId(UnsignedInteger AttributeId)
    {
        this.AttributeId = AttributeId;
    }
    
    public String getIndexRange()
    {
        return IndexRange;
    }
    
    public void setIndexRange(String IndexRange)
    {
        this.IndexRange = IndexRange;
    }
    
    public DataValue getValue()
    {
        return Value;
    }
    
    public void setValue(DataValue Value)
    {
        this.Value = Value;
    }
    
    /**
      * Deep clone
      *
      * @return cloned WriteValue
      */
    public WriteValue clone()
    {
        WriteValue result = new WriteValue();
        result.NodeId = NodeId;
        result.AttributeId = AttributeId;
        result.IndexRange = IndexRange;
        result.Value = Value;
        return result;
    }
    
 



	public NumericRange getParsedIndexRange(){
		return ParsedIndexRange;
	}

	public static ServiceResult validate(WriteValue value) {
		 // check for null structure.
	    if (value == null){
	        return new ServiceResult(StatusCodes.Bad_StructureMissing);
	    }

	    // null node ids are always invalid.
	    if (value.getNodeId() == null)
	    {
	        return new ServiceResult(StatusCodes.Bad_NodeIdInvalid);
	    }
	    
	    // must be a legimate attribute value.
	    if (!org.opcfoundation.ua.utils.AttributesUtil.isValid(value.AttributeId))
	    {
	        return new ServiceResult(StatusCodes.Bad_AttributeIdInvalid);
	    }

	    // initialize as empty.
	    value.ParsedIndexRange = NumericRange.getEmpty();

	    // parse the index range if specified.
	    if (!(value.IndexRange == null || value.IndexRange.isEmpty()))
	    {
	        try
	        {
	            value.ParsedIndexRange = NumericRange.parse(value.IndexRange);
	        }
	        catch (Exception e)
	        {
	        	return new ServiceResult(StatusCodes.Bad_IndexRangeInvalid, e);
	            
	        }
	        
	        // check that value provided is actually an array.
	       //TODO? Array array = value.Value.Value as Array;

	       /* if (array == null)
	        {
	            return StatusCodes.BadTypeMismatch;
	        }
	        
	        NumericRange range = value.ParsedIndexRange;

	        // check that the number of elements to write matches the index range.
	        if (range.getEnd() >= 0 && (range.getEnd() - range.getBegin() != array.Length))
	        {
	            return StatusCodes.Bad_IndexRangeInvalid;
	        }

	        // check for single element.
	        if (range.End < 0 && array.Length != 1)
	        {
	            return StatusCodes.Bad_IndexRangeInvalid;
	        }*/
	    }
	    else
	    {
	        value.ParsedIndexRange = NumericRange.getEmpty();
	    }

	    // passed basic validation.
	    return null;
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
}
