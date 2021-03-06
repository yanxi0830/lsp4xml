/**
 *  Copyright (c) 2018 Angelo ZERR.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v2.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v20.html
 *
 *  Contributors:
 *  Angelo Zerr <angelo.zerr@gmail.com> - initial API and implementation
 */
package org.eclipse.lsp4xml.dom;

import java.util.ArrayList;

/**
 * DTD Attribute List declaration <!ATTLIST
 * 
 * @see https://www.w3.org/TR/REC-xml/#attdecls
 *
 */
public class DTDAttlistDecl extends DTDDeclNode {

	/**
	 * Format:
	 * 
	 * <!ATTLIST element-name attribute-name attribute-type "attribute-value>""
	 * 
	 * or
	 * 
	 * <!ATTLIST element-name 
	 * 			 attribute-name1 attribute-type1 "attribute-value1"
	 * 			 attribute-name2 attribute-type2 "attribute-value2"
	 * 			 ...
	 * >
	 */

	public DTDDeclParameter elementName;
	public DTDDeclParameter attributeName;
	public DTDDeclParameter attributeType;
	public DTDDeclParameter attributeValue;

	ArrayList<DTDAttlistDecl> internalChildren; //Holds all additional internal attlist declaractions
	
	public DTDAttlistDecl(int start, int end, DOMDocumentType parentDocumentType) {
		super(start, end, parentDocumentType);
		setDeclType(start + 2, start + 9);
	}

	public DOMDocumentType getParentDocumentType() {
		return parentDocumentType;
	}

	@Override
	public String getNodeName() {
		return getAttributeName();
	}

	/**
	 * Returns the element name
	 * 
	 * @return the element name
	 */
	public String getElementName() {
		return elementName != null ? elementName.getParameter() : null;
	}

	public void setElementName(int start, int end) {
		elementName = addNewParameter(start, end);
	}

	/**
	 * Returns the attribute name
	 * 
	 * @return the attribute name
	 */
	public String getAttributeName() {
		return attributeName != null ? attributeName.getParameter() : null;
	}

	public void setAttributeName(int start, int end) {
		attributeName = addNewParameter(start, end);
	}
	
	public String getAttributeType() {
		return attributeType != null ? attributeType.getParameter() : null;
	}

	public void setAttributeType(int start, int end) {
		attributeType = addNewParameter(start, end);
	}

	public String getAttributeValue() {
		return attributeValue != null ? attributeValue.getParameter() : null;
	}

	public void setAttributeValue(int start, int end) {
		attributeValue = addNewParameter(start, end);
	}

	@Override
	public short getNodeType() {
		return DOMNode.DTD_ATT_LIST_NODE;
	}

	/**
	 * Add another internal attlist declaration to the list of children.
	 * 
	 * An ATTLIST decl can internally declare multiple declarations, see top of file.
	 * This will add another one to its list of additional declarations.
	 */
	void addAdditionalAttDecl(DTDAttlistDecl child) {
		if(internalChildren == null) {
			internalChildren = new ArrayList<DTDAttlistDecl>();
		}
		internalChildren.add(child);
	}

	public ArrayList<DTDAttlistDecl> getInternalChildren() {
		return internalChildren;
	}

	/**
	 * Returns true if this node's parent is the Doctype node.  
	 * 
	 * 
	 * This is used because an Attlist declaration can have multiple
	 * attribute declarations within a tag that are each represented
	 * by this class.
	 */
	public boolean isRootAttlist() {
		return this.parent.isDoctype();
	}

}
