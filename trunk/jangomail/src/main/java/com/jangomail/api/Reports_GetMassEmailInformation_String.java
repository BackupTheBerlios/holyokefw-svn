/**
 * Reports_GetMassEmailInformation_String.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.jangomail.api;

public class Reports_GetMassEmailInformation_String  implements java.io.Serializable {
    private java.lang.String username;

    private java.lang.String password;

    private java.lang.String beginDate;

    private java.lang.String endDate;

    private java.lang.String sortBy;

    private java.lang.String sortOrder;

    private java.lang.String rowDelimiter;

    private java.lang.String colDelimiter;

    private java.lang.String textQualifier;

    public Reports_GetMassEmailInformation_String() {
    }

    public Reports_GetMassEmailInformation_String(
           java.lang.String username,
           java.lang.String password,
           java.lang.String beginDate,
           java.lang.String endDate,
           java.lang.String sortBy,
           java.lang.String sortOrder,
           java.lang.String rowDelimiter,
           java.lang.String colDelimiter,
           java.lang.String textQualifier) {
           this.username = username;
           this.password = password;
           this.beginDate = beginDate;
           this.endDate = endDate;
           this.sortBy = sortBy;
           this.sortOrder = sortOrder;
           this.rowDelimiter = rowDelimiter;
           this.colDelimiter = colDelimiter;
           this.textQualifier = textQualifier;
    }


    /**
     * Gets the username value for this Reports_GetMassEmailInformation_String.
     * 
     * @return username
     */
    public java.lang.String getUsername() {
        return username;
    }


    /**
     * Sets the username value for this Reports_GetMassEmailInformation_String.
     * 
     * @param username
     */
    public void setUsername(java.lang.String username) {
        this.username = username;
    }


    /**
     * Gets the password value for this Reports_GetMassEmailInformation_String.
     * 
     * @return password
     */
    public java.lang.String getPassword() {
        return password;
    }


    /**
     * Sets the password value for this Reports_GetMassEmailInformation_String.
     * 
     * @param password
     */
    public void setPassword(java.lang.String password) {
        this.password = password;
    }


    /**
     * Gets the beginDate value for this Reports_GetMassEmailInformation_String.
     * 
     * @return beginDate
     */
    public java.lang.String getBeginDate() {
        return beginDate;
    }


    /**
     * Sets the beginDate value for this Reports_GetMassEmailInformation_String.
     * 
     * @param beginDate
     */
    public void setBeginDate(java.lang.String beginDate) {
        this.beginDate = beginDate;
    }


    /**
     * Gets the endDate value for this Reports_GetMassEmailInformation_String.
     * 
     * @return endDate
     */
    public java.lang.String getEndDate() {
        return endDate;
    }


    /**
     * Sets the endDate value for this Reports_GetMassEmailInformation_String.
     * 
     * @param endDate
     */
    public void setEndDate(java.lang.String endDate) {
        this.endDate = endDate;
    }


    /**
     * Gets the sortBy value for this Reports_GetMassEmailInformation_String.
     * 
     * @return sortBy
     */
    public java.lang.String getSortBy() {
        return sortBy;
    }


    /**
     * Sets the sortBy value for this Reports_GetMassEmailInformation_String.
     * 
     * @param sortBy
     */
    public void setSortBy(java.lang.String sortBy) {
        this.sortBy = sortBy;
    }


    /**
     * Gets the sortOrder value for this Reports_GetMassEmailInformation_String.
     * 
     * @return sortOrder
     */
    public java.lang.String getSortOrder() {
        return sortOrder;
    }


    /**
     * Sets the sortOrder value for this Reports_GetMassEmailInformation_String.
     * 
     * @param sortOrder
     */
    public void setSortOrder(java.lang.String sortOrder) {
        this.sortOrder = sortOrder;
    }


    /**
     * Gets the rowDelimiter value for this Reports_GetMassEmailInformation_String.
     * 
     * @return rowDelimiter
     */
    public java.lang.String getRowDelimiter() {
        return rowDelimiter;
    }


    /**
     * Sets the rowDelimiter value for this Reports_GetMassEmailInformation_String.
     * 
     * @param rowDelimiter
     */
    public void setRowDelimiter(java.lang.String rowDelimiter) {
        this.rowDelimiter = rowDelimiter;
    }


    /**
     * Gets the colDelimiter value for this Reports_GetMassEmailInformation_String.
     * 
     * @return colDelimiter
     */
    public java.lang.String getColDelimiter() {
        return colDelimiter;
    }


    /**
     * Sets the colDelimiter value for this Reports_GetMassEmailInformation_String.
     * 
     * @param colDelimiter
     */
    public void setColDelimiter(java.lang.String colDelimiter) {
        this.colDelimiter = colDelimiter;
    }


    /**
     * Gets the textQualifier value for this Reports_GetMassEmailInformation_String.
     * 
     * @return textQualifier
     */
    public java.lang.String getTextQualifier() {
        return textQualifier;
    }


    /**
     * Sets the textQualifier value for this Reports_GetMassEmailInformation_String.
     * 
     * @param textQualifier
     */
    public void setTextQualifier(java.lang.String textQualifier) {
        this.textQualifier = textQualifier;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Reports_GetMassEmailInformation_String)) return false;
        Reports_GetMassEmailInformation_String other = (Reports_GetMassEmailInformation_String) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.username==null && other.getUsername()==null) || 
             (this.username!=null &&
              this.username.equals(other.getUsername()))) &&
            ((this.password==null && other.getPassword()==null) || 
             (this.password!=null &&
              this.password.equals(other.getPassword()))) &&
            ((this.beginDate==null && other.getBeginDate()==null) || 
             (this.beginDate!=null &&
              this.beginDate.equals(other.getBeginDate()))) &&
            ((this.endDate==null && other.getEndDate()==null) || 
             (this.endDate!=null &&
              this.endDate.equals(other.getEndDate()))) &&
            ((this.sortBy==null && other.getSortBy()==null) || 
             (this.sortBy!=null &&
              this.sortBy.equals(other.getSortBy()))) &&
            ((this.sortOrder==null && other.getSortOrder()==null) || 
             (this.sortOrder!=null &&
              this.sortOrder.equals(other.getSortOrder()))) &&
            ((this.rowDelimiter==null && other.getRowDelimiter()==null) || 
             (this.rowDelimiter!=null &&
              this.rowDelimiter.equals(other.getRowDelimiter()))) &&
            ((this.colDelimiter==null && other.getColDelimiter()==null) || 
             (this.colDelimiter!=null &&
              this.colDelimiter.equals(other.getColDelimiter()))) &&
            ((this.textQualifier==null && other.getTextQualifier()==null) || 
             (this.textQualifier!=null &&
              this.textQualifier.equals(other.getTextQualifier())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getUsername() != null) {
            _hashCode += getUsername().hashCode();
        }
        if (getPassword() != null) {
            _hashCode += getPassword().hashCode();
        }
        if (getBeginDate() != null) {
            _hashCode += getBeginDate().hashCode();
        }
        if (getEndDate() != null) {
            _hashCode += getEndDate().hashCode();
        }
        if (getSortBy() != null) {
            _hashCode += getSortBy().hashCode();
        }
        if (getSortOrder() != null) {
            _hashCode += getSortOrder().hashCode();
        }
        if (getRowDelimiter() != null) {
            _hashCode += getRowDelimiter().hashCode();
        }
        if (getColDelimiter() != null) {
            _hashCode += getColDelimiter().hashCode();
        }
        if (getTextQualifier() != null) {
            _hashCode += getTextQualifier().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Reports_GetMassEmailInformation_String.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://api.jangomail.com/", ">Reports_GetMassEmailInformation_String"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("username");
        elemField.setXmlName(new javax.xml.namespace.QName("http://api.jangomail.com/", "Username"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("password");
        elemField.setXmlName(new javax.xml.namespace.QName("http://api.jangomail.com/", "Password"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("beginDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://api.jangomail.com/", "BeginDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("endDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://api.jangomail.com/", "EndDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sortBy");
        elemField.setXmlName(new javax.xml.namespace.QName("http://api.jangomail.com/", "SortBy"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sortOrder");
        elemField.setXmlName(new javax.xml.namespace.QName("http://api.jangomail.com/", "SortOrder"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rowDelimiter");
        elemField.setXmlName(new javax.xml.namespace.QName("http://api.jangomail.com/", "RowDelimiter"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("colDelimiter");
        elemField.setXmlName(new javax.xml.namespace.QName("http://api.jangomail.com/", "ColDelimiter"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("textQualifier");
        elemField.setXmlName(new javax.xml.namespace.QName("http://api.jangomail.com/", "TextQualifier"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
