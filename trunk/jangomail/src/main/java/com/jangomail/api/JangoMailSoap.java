/**
 * JangoMailSoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.jangomail.api;

public interface JangoMailSoap extends java.rmi.Remote {

    /**
     * Creates a user account. Returns the User ID for the new user
     * account.
     */
    public java.lang.String createAccount(java.lang.String firstName, java.lang.String lastName, java.lang.String username, java.lang.String emailAddress, java.lang.String phoneNumber, java.lang.String privateLabel, java.lang.String secretToken) throws java.rmi.RemoteException;

    /**
     * Creates a local database profile.
     */
    public java.lang.String createLocalDBProfile(java.lang.String username, java.lang.String password, java.lang.String profileName, java.lang.String connectionString, java.lang.String SQLQuery) throws java.rmi.RemoteException;

    /**
     * Adds a single e-mail address to your unsubscribe list. Returns
     * a string.
     */
    public java.lang.String addUnsubscribe(java.lang.String username, java.lang.String password, java.lang.String emailAddress) throws java.rmi.RemoteException;

    /**
     * Checks unsubscribe list for presence of specified e-mail address.
     * Returns a string.
     */
    public java.lang.String checkUnsubscribe(java.lang.String username, java.lang.String password, java.lang.String emailAddress) throws java.rmi.RemoteException;

    /**
     * Removes a single e-mail address from the unsubscribe list.
     * Returns a string.
     */
    public java.lang.String deleteUnsubscribe(java.lang.String username, java.lang.String password, java.lang.String emailAddress) throws java.rmi.RemoteException;

    /**
     * Adds a single e-mail address to your bounce list. Returns a
     * string.
     */
    public java.lang.String addBounce(java.lang.String username, java.lang.String password, java.lang.String emailAddress) throws java.rmi.RemoteException;

    /**
     * Checks for the presence of the specified e-mail address in
     * the bounce list. Returns a string.
     */
    public java.lang.String checkBounce(java.lang.String username, java.lang.String password, java.lang.String emailAddress) throws java.rmi.RemoteException;

    /**
     * Removes a single e-mail address from the bounce list. Returns
     * a string.
     */
    public java.lang.String deleteBounce(java.lang.String username, java.lang.String password, java.lang.String emailAddress) throws java.rmi.RemoteException;

    /**
     * Adds a new Group to your account. Returns a string.
     */
    public java.lang.String addGroup(java.lang.String username, java.lang.String password, java.lang.String groupName) throws java.rmi.RemoteException;

    /**
     * Gets the list of all fields in the specified Group. Returns
     * a string.
     */
    public java.lang.String getGroupFieldList(java.lang.String username, java.lang.String password, java.lang.String groupName) throws java.rmi.RemoteException;

    /**
     * Gets the list of all unsubscribed e-mail addresses since the
     * specified date. Returns a string.
     */
    public java.lang.String getUnsubscribeList(java.lang.String username, java.lang.String password, java.lang.String since) throws java.rmi.RemoteException;

    /**
     * Gets the list of e-mail addresses that have bounced X times
     * since specified date, where X is a configurable parameter in your
     * account. Returns a string.
     */
    public java.lang.String getBounceListNormal(java.lang.String username, java.lang.String password, java.lang.String since) throws java.rmi.RemoteException;

    /**
     * Gets the list of e-mail addresses that have bounced at least
     * X times in total, and at least once since specified date. X is a configurable
     * parameter in your account. Returns a string.
     */
    public java.lang.String getBounceListNormal_IncludePast(java.lang.String username, java.lang.String password, java.lang.String since) throws java.rmi.RemoteException;

    /**
     * Gets the list of all e-mail addresses that have bounced at
     * least once since the specified date. Returns a string.
     */
    public java.lang.String getBounceListAll(java.lang.String username, java.lang.String password, java.lang.String since) throws java.rmi.RemoteException;

    /**
     * Gets the list of all e-mail addresses that have bounced at
     * least once since the specified date. Includes SMTP Diagnostic Code
     * and Definitive columns. Returns a string.
     */
    public java.lang.String getBounceListAll2(java.lang.String username, java.lang.String password, java.lang.String since) throws java.rmi.RemoteException;

    /**
     * Gets the list of e-mail addresses that have bounced X times
     * since specified date, where X is a configurable parameter in your
     * account. Includes SMTP Diagnostic Code and Definitive columns. Returns
     * a string.
     */
    public java.lang.String getBounceListNormal2(java.lang.String username, java.lang.String password, java.lang.String since) throws java.rmi.RemoteException;

    /**
     * Gets the list of e-mail addresses that have bounced at least
     * X times in total, and at least once since specified date. X is a configurable
     * parameter in your account. Includes SMTP Diagnostic Code and Definitive
     * columns. Returns a string.
     */
    public java.lang.String getBounceListNormal_IncludePast2(java.lang.String username, java.lang.String password, java.lang.String since) throws java.rmi.RemoteException;

    /**
     * Deletes a Group given its ID number. Returns a string.
     */
    public java.lang.String deleteGroupByID(java.lang.String username, java.lang.String password, int groupID) throws java.rmi.RemoteException;

    /**
     * Updates a field for a group member. Returns a string
     */
    public java.lang.String groups_EditMember_ByAddress(java.lang.String username, java.lang.String password, java.lang.String groupName, java.lang.String emailAddress, java.lang.String fieldName, java.lang.String newFieldValue) throws java.rmi.RemoteException;

    /**
     * Updates a field for a Group member. Returns a string
     */
    public java.lang.String groups_EditMember_ByID(java.lang.String username, java.lang.String password, java.lang.String groupName, int groupMemberID, java.lang.String fieldName, java.lang.String newFieldValue) throws java.rmi.RemoteException;

    /**
     * Deletes a Group given its name. Returns a string.
     */
    public java.lang.String deleteGroupByName(java.lang.String username, java.lang.String password, java.lang.String groupName) throws java.rmi.RemoteException;

    /**
     * Adds a standard field to the specified Group. Returns a string.
     */
    public java.lang.String addGroupField(java.lang.String username, java.lang.String password, java.lang.String groupName, java.lang.String fieldName) throws java.rmi.RemoteException;

    /**
     * Adds a large text storage field to the specified Group.  Returns
     * a string.
     */
    public java.lang.String addGroupField_Big(java.lang.String username, java.lang.String password, java.lang.String groupName, java.lang.String fieldName) throws java.rmi.RemoteException;

    /**
     * Removes a field from the specified Group. Returns a string.
     */
    public java.lang.String deleteGroupField(java.lang.String username, java.lang.String password, java.lang.String groupName, java.lang.String fieldName) throws java.rmi.RemoteException;

    /**
     * Adds a member to the specified Group, via string input. Returns
     * a string.
     */
    public java.lang.String addGroupMemberString(java.lang.String username, java.lang.String password, java.lang.String groupName, java.lang.String emailAddress, java.lang.String fieldNames, java.lang.String fieldValues) throws java.rmi.RemoteException;

    /**
     * Adds a member to the specified Group, via array input. Returns
     * a string.
     */
    public java.lang.String addGroupMember(java.lang.String username, java.lang.String password, java.lang.String groupName, java.lang.String emailAddress, java.lang.String[] fieldNames, java.lang.String[] fieldValues) throws java.rmi.RemoteException;

    /**
     * Deletes a Group member from the specified Group. Returns a
     * string.
     */
    public java.lang.String deleteGroupMember(java.lang.String username, java.lang.String password, java.lang.String groupName, java.lang.String emailAddress) throws java.rmi.RemoteException;

    /**
     * Sends a mass e-mail with a plain text and/or HTML e-mail. Returns
     * a string.
     */
    public java.lang.String sendMassEmail(java.lang.String username, java.lang.String password, java.lang.String fromEmail, java.lang.String fromName, java.lang.String toGroups, java.lang.String toGroupFilter, java.lang.String toOther, java.lang.String toWebDatabase, java.lang.String subject, java.lang.String messagePlain, java.lang.String messageHTML, java.lang.String options) throws java.rmi.RemoteException;

    /**
     * Sends a previously saved mass e-mail to a new set of recipients.
     * Returns a string.
     */
    public java.lang.String sendMassEmailPrevious(java.lang.String username, java.lang.String password, int jobID, java.lang.String toGroups, java.lang.String toGroupFilter, java.lang.String toOther, java.lang.String toWebDatabase) throws java.rmi.RemoteException;

    /**
     * Sends a previously saved mass e-mail to a new set of recipients.
     * Returns a string.
     */
    public java.lang.String sendMassEmailPrevious2(java.lang.String username, java.lang.String password, int jobID, java.lang.String toGroups, java.lang.String toGroupFilter, java.lang.String toOther, java.lang.String toWebDatabase, java.lang.String options) throws java.rmi.RemoteException;

    /**
     * Sends a mass e-mail, given a raw message including MIME parts
     * and a MIME boundary. Returns a string.
     */
    public java.lang.String sendMassEmailRaw(java.lang.String username, java.lang.String password, java.lang.String fromEmail, java.lang.String fromName, java.lang.String toGroups, java.lang.String toGroupFilter, java.lang.String toOther, java.lang.String toWebDatabase, java.lang.String subject, java.lang.String rawMessage, java.lang.String boundary, java.lang.String options) throws java.rmi.RemoteException;

    /**
     * Deletes an unsent scheduled mass e-mail or an already sent
     * mass e-mail from your account. Returns a string.
     */
    public java.lang.String deleteMassEmail(java.lang.String username, java.lang.String password, int jobID) throws java.rmi.RemoteException;

    /**
     * Gets the reporting statistics on a particular mass e-mail.
     * Returns an array of integers.
     */
    public int[] getMassEmailReport(java.lang.String username, java.lang.String password, int jobID) throws java.rmi.RemoteException;

    /**
     * Gets the number of members in a Group. Returns an integer.
     */
    public int getGroupMemberCount(java.lang.String username, java.lang.String password, java.lang.String groupName) throws java.rmi.RemoteException;

    /**
     * Determines whether a particular e-mail address is in a Group.
     * Returns a boolean true/false.
     */
    public boolean isMemberInGroup(java.lang.String username, java.lang.String password, java.lang.String groupName, java.lang.String emailAddress) throws java.rmi.RemoteException;

    /**
     * Imports members into a Group from raw data. Returns a string.
     */
    public java.lang.String importGroupMembersFromData(java.lang.String username, java.lang.String password, java.lang.String groupName, java.lang.String fieldNames, java.lang.String importData, java.lang.String columnDelimiter, java.lang.String rowDelimiter, java.lang.String textQualifier) throws java.rmi.RemoteException;

    /**
     * Imports members into a Group from previously FTPd file. Returns
     * a string.
     */
    public java.lang.String importGroupMembersFromFile(java.lang.String username, java.lang.String password, java.lang.String groupName, java.lang.String fieldNames, java.lang.String fileName, java.lang.String columnDelimiter, java.lang.String rowDelimiter, java.lang.String textQualifier) throws java.rmi.RemoteException;

    /**
     * Retrieves list of recipients for a particular mass e-mail campaign.
     * Returns a string.
     */
    public java.lang.String reports_GetRecipients_String(java.lang.String username, java.lang.String password, int jobID) throws java.rmi.RemoteException;

    /**
     * Retrieves list of recipients for a particular mass e-mail campaign.
     * Returns a .NET DataSet.
     */
    public com.jangomail.api.Reports_GetRecipients_DataSetResponseReports_GetRecipients_DataSetResult reports_GetRecipients_DataSet(java.lang.String username, java.lang.String password, int jobID) throws java.rmi.RemoteException;

    /**
     * Retrieves list of recipients for a particular mass e-mail campaign.
     * Returns an XML document.
     */
    public com.jangomail.api.Reports_GetRecipients_XMLResponseReports_GetRecipients_XMLResult reports_GetRecipients_XML(java.lang.String username, java.lang.String password, int jobID) throws java.rmi.RemoteException;

    /**
     * Retrieves list of recipients that have opened a mass e-mail
     * campaign. Returns a .NET DataSet.
     */
    public com.jangomail.api.Reports_GetOpens_DataSetResponseReports_GetOpens_DataSetResult reports_GetOpens_DataSet(java.lang.String username, java.lang.String password, int jobID, java.lang.String sortBy, java.lang.String sortOrder, java.lang.String whichTime) throws java.rmi.RemoteException;

    /**
     * Retrieves list of recipients that have opened a mass e-mail
     * campaign. Returns an XML document.
     */
    public com.jangomail.api.Reports_GetOpens_XMLResponseReports_GetOpens_XMLResult reports_GetOpens_XML(java.lang.String username, java.lang.String password, int jobID, java.lang.String sortBy, java.lang.String sortOrder, java.lang.String whichTime) throws java.rmi.RemoteException;

    /**
     * Retrieves list of recipients that have opened a mass e-mail
     * campaign. Returns a string.
     */
    public java.lang.String reports_GetOpens_String(java.lang.String username, java.lang.String password, int jobID, java.lang.String sortBy, java.lang.String sortOrder, java.lang.String whichTime, java.lang.String rowDelimiter, java.lang.String colDelimiter, java.lang.String textQualifier) throws java.rmi.RemoteException;

    /**
     * Retrieves list of recipients that have clicked any link in
     * a mass e-mail campaign. Returns a .NET DataSet.
     */
    public com.jangomail.api.Reports_GetAllClicks_DataSetResponseReports_GetAllClicks_DataSetResult reports_GetAllClicks_DataSet(java.lang.String username, java.lang.String password, int jobID, java.lang.String sortBy, java.lang.String sortOrder) throws java.rmi.RemoteException;

    /**
     * Retrieves list of recipients that have clicked any link in
     * a mass e-mail campaign. Returns an XML document.
     */
    public com.jangomail.api.Reports_GetAllClicks_XMLResponseReports_GetAllClicks_XMLResult reports_GetAllClicks_XML(java.lang.String username, java.lang.String password, int jobID, java.lang.String sortBy, java.lang.String sortOrder) throws java.rmi.RemoteException;

    /**
     * Retrieves list of recipients that have clicked any link in
     * a mass e-mail campaign. Returns a string.
     */
    public java.lang.String reports_GetAllClicks_String(java.lang.String username, java.lang.String password, int jobID, java.lang.String sortBy, java.lang.String sortOrder, java.lang.String rowDelimiter, java.lang.String colDelimiter, java.lang.String textQualifier) throws java.rmi.RemoteException;

    /**
     * Retrieves list of recipients that have clicked a particular
     * link in a mass e-mail campaign. Returns a .NET DataSet.
     */
    public com.jangomail.api.Reports_GetClicksByURL_DataSetResponseReports_GetClicksByURL_DataSetResult reports_GetClicksByURL_DataSet(java.lang.String username, java.lang.String password, int jobID, java.lang.String URL, java.lang.String sortBy, java.lang.String sortOrder) throws java.rmi.RemoteException;

    /**
     * Retrieves list of recipients that have clicked a particular
     * link in a mass e-mail campaign. Returns an XML document.
     */
    public com.jangomail.api.Reports_GetClicksByURL_XMLResponseReports_GetClicksByURL_XMLResult reports_GetClicksByURL_XML(java.lang.String username, java.lang.String password, int jobID, java.lang.String URL, java.lang.String sortBy, java.lang.String sortOrder) throws java.rmi.RemoteException;

    /**
     * Retrieves list of recipients that have clicked a particular
     * link in a mass e-mail campaign. Returns a string.
     */
    public java.lang.String reports_GetClicksByURL_String(java.lang.String username, java.lang.String password, int jobID, java.lang.String URL, java.lang.String sortBy, java.lang.String sortOrder, java.lang.String rowDelimiter, java.lang.String colDelimiter, java.lang.String textQualifier) throws java.rmi.RemoteException;

    /**
     * Retrieves list of Groups in user's account. Returns a .NET
     * DataSet.
     */
    public com.jangomail.api.Groups_GetList_DataSetResponseGroups_GetList_DataSetResult groups_GetList_DataSet(java.lang.String username, java.lang.String password) throws java.rmi.RemoteException;

    /**
     * Retrieves list of Groups in user's account. Returns an XML
     * document.
     */
    public com.jangomail.api.Groups_GetList_XMLResponseGroups_GetList_XMLResult groups_GetList_XML(java.lang.String username, java.lang.String password) throws java.rmi.RemoteException;

    /**
     * Retrieves list of Groups in user's account. Returns a string.
     */
    public java.lang.String groups_GetList_String(java.lang.String username, java.lang.String password, java.lang.String rowDelimiter, java.lang.String colDelimiter, java.lang.String textQualifier) throws java.rmi.RemoteException;

    /**
     * Retrieves list of unsubscribed addresses for a particular mass
     * e-mail campaign. Returns a .NET DataSet.
     */
    public com.jangomail.api.Reports_GetUnsubscribesByCampaign_DataSetResponseReports_GetUnsubscribesByCampaign_DataSetResult reports_GetUnsubscribesByCampaign_DataSet(java.lang.String username, java.lang.String password, int jobID) throws java.rmi.RemoteException;

    /**
     * Retrieves list of unsubscribed addresses for a particular mass
     * e-mail campaign. Returns a string.
     */
    public java.lang.String reports_GetUnsubscribesByCampaign_String(java.lang.String username, java.lang.String password, int jobID, java.lang.String rowDelimiter, java.lang.String colDelimiter, java.lang.String textQualifier) throws java.rmi.RemoteException;

    /**
     * Retrieves list of unsubscribed addresses for a particular mass
     * e-mail campaign. Returns an XML document.
     */
    public com.jangomail.api.Reports_GetUnsubscribesByCampaign_XMLResponseReports_GetUnsubscribesByCampaign_XMLResult reports_GetUnsubscribesByCampaign_XML(java.lang.String username, java.lang.String password, int jobID) throws java.rmi.RemoteException;

    /**
     * Retrieves list of bounced addresses for a particular mass e-mail
     * campaign. Returns a .NET DataSet.
     */
    public com.jangomail.api.Reports_GetBouncesByCampaign_DataSetResponseReports_GetBouncesByCampaign_DataSetResult reports_GetBouncesByCampaign_DataSet(java.lang.String username, java.lang.String password, int jobID) throws java.rmi.RemoteException;

    /**
     * Retrieves list of bounced addresses for a particular mass e-mail
     * campaign. Returns an XML document.
     */
    public com.jangomail.api.Reports_GetBouncesByCampaign_XMLResponseReports_GetBouncesByCampaign_XMLResult reports_GetBouncesByCampaign_XML(java.lang.String username, java.lang.String password, int jobID) throws java.rmi.RemoteException;

    /**
     * Retrieves list of bounced addresses for a particular mass e-mail
     * campaign. Returns a string.
     */
    public java.lang.String reports_GetBouncesByCampaign_String(java.lang.String username, java.lang.String password, int jobID, java.lang.String rowDelimiter, java.lang.String colDelimiter, java.lang.String textQualifier) throws java.rmi.RemoteException;

    /**
     * Retrieves list of bounced addresses for a particular mass e-mail
     * campaign. Includes SMTP Diagnostic Code and Definitive columns. Returns
     * a .NET DataSet.
     */
    public com.jangomail.api.Reports_GetBouncesByCampaign_DataSet2ResponseReports_GetBouncesByCampaign_DataSet2Result reports_GetBouncesByCampaign_DataSet2(java.lang.String username, java.lang.String password, int jobID) throws java.rmi.RemoteException;

    /**
     * Retrieves list of bounced addresses for a particular mass e-mail
     * campaign. Includes SMTP Diagnostic Code and Definitive columns. Returns
     * an XML document.
     */
    public com.jangomail.api.Reports_GetBouncesByCampaign_XML2ResponseReports_GetBouncesByCampaign_XML2Result reports_GetBouncesByCampaign_XML2(java.lang.String username, java.lang.String password, int jobID) throws java.rmi.RemoteException;

    /**
     * Retrieves list of bounced addresses for a particular mass e-mail
     * campaign. Includes SMTP Diagnostic Code and Definitive columns. Returns
     * a string.
     */
    public java.lang.String reports_GetBouncesByCampaign_String2(java.lang.String username, java.lang.String password, int jobID, java.lang.String rowDelimiter, java.lang.String colDelimiter, java.lang.String textQualifier) throws java.rmi.RemoteException;

    /**
     * Retrieves list of forwarded from and to addresses for a particular
     * mass e-mail campaign. Returns a .NET DataSet.
     */
    public com.jangomail.api.Reports_GetForwards_DataSetResponseReports_GetForwards_DataSetResult reports_GetForwards_DataSet(java.lang.String username, java.lang.String password, int jobID) throws java.rmi.RemoteException;

    /**
     * Retrieves list of forwarded from and to addresses for a particular
     * mass e-mail campaign. Returns an XML document.
     */
    public com.jangomail.api.Reports_GetForwards_XMLResponseReports_GetForwards_XMLResult reports_GetForwards_XML(java.lang.String username, java.lang.String password, int jobID) throws java.rmi.RemoteException;

    /**
     * Retrieves list of forwarded from and to addresses for a particular
     * mass e-mail campaign. Returns a string.
     */
    public java.lang.String reports_GetForwards_String(java.lang.String username, java.lang.String password, int jobID, java.lang.String rowDelimiter, java.lang.String colDelimiter, java.lang.String textQualifier) throws java.rmi.RemoteException;

    /**
     * Retrieves list of replies for a particular mass e-mail campaign.
     * Returns a .NET DataSet.
     */
    public com.jangomail.api.Reports_GetReplies_DataSetResponseReports_GetReplies_DataSetResult reports_GetReplies_DataSet(java.lang.String username, java.lang.String password, int jobID) throws java.rmi.RemoteException;

    /**
     * Retrieves list of replies for a particular mass e-mail campaign.
     * Returns a string.
     */
    public java.lang.String reports_GetReplies_String(java.lang.String username, java.lang.String password, int jobID, java.lang.String rowDelimiter, java.lang.String colDelimiter, java.lang.String textQualifier) throws java.rmi.RemoteException;

    /**
     * Retrieves list of replies for a particular mass e-mail campaign.
     * Returns an XML document.
     */
    public com.jangomail.api.Reports_GetReplies_XMLResponseReports_GetReplies_XMLResult reports_GetReplies_XML(java.lang.String username, java.lang.String password, int jobID) throws java.rmi.RemoteException;

    /**
     * Retrieves mass e-mail campaign information, including job IDs
     * and subject lines for a particular date range. Returns a .NET DataSet.
     */
    public com.jangomail.api.Reports_GetMassEmailInformation_DataSetResponseReports_GetMassEmailInformation_DataSetResult reports_GetMassEmailInformation_DataSet(java.lang.String username, java.lang.String password, java.lang.String beginDate, java.lang.String endDate, java.lang.String sortBy, java.lang.String sortOrder) throws java.rmi.RemoteException;

    /**
     * Retrieves mass e-mail campaign information, including job IDs
     * and subject lines for a particular date range. Returns an XML document.
     */
    public com.jangomail.api.Reports_GetMassEmailInformation_XMLResponseReports_GetMassEmailInformation_XMLResult reports_GetMassEmailInformation_XML(java.lang.String username, java.lang.String password, java.lang.String beginDate, java.lang.String endDate, java.lang.String sortBy, java.lang.String sortOrder) throws java.rmi.RemoteException;

    /**
     * Retrieves mass e-mail campaign information, including job IDs
     * and subject lines for a particular date range. Returns a string.
     */
    public java.lang.String reports_GetMassEmailInformation_String(java.lang.String username, java.lang.String password, java.lang.String beginDate, java.lang.String endDate, java.lang.String sortBy, java.lang.String sortOrder, java.lang.String rowDelimiter, java.lang.String colDelimiter, java.lang.String textQualifier) throws java.rmi.RemoteException;

    /**
     * Retrieves mass e-mail campaign information, including job IDs,
     * subject lines, and campaign identifiers for a particular date range.
     * Returns a .NET DataSet.
     */
    public com.jangomail.api.Reports_GetMassEmailInformation_DataSet2ResponseReports_GetMassEmailInformation_DataSet2Result reports_GetMassEmailInformation_DataSet2(java.lang.String username, java.lang.String password, java.lang.String beginDate, java.lang.String endDate, java.lang.String sortBy, java.lang.String sortOrder) throws java.rmi.RemoteException;

    /**
     * Retrieves mass e-mail campaign information, including job IDs,
     * subject lines, and campaign identifiers for a particular date range.
     * Returns an XML document.
     */
    public com.jangomail.api.Reports_GetMassEmailInformation_XML2ResponseReports_GetMassEmailInformation_XML2Result reports_GetMassEmailInformation_XML2(java.lang.String username, java.lang.String password, java.lang.String beginDate, java.lang.String endDate, java.lang.String sortBy, java.lang.String sortOrder) throws java.rmi.RemoteException;

    /**
     * Retrieves mass e-mail campaign information, including job IDs,
     * subject lines, and campaign identifiers for a particular date range.
     * Returns a string.
     */
    public java.lang.String reports_GetMassEmailInformation_String2(java.lang.String username, java.lang.String password, java.lang.String beginDate, java.lang.String endDate, java.lang.String sortBy, java.lang.String sortOrder, java.lang.String rowDelimiter, java.lang.String colDelimiter, java.lang.String textQualifier) throws java.rmi.RemoteException;

    /**
     * Retrieves list of unsubscribed addresses for all mass e-mail
     * campaigns. Returns a .NET DataSet.
     */
    public com.jangomail.api.Reports_GetUnsubscribesAll_DataSetResponseReports_GetUnsubscribesAll_DataSetResult reports_GetUnsubscribesAll_DataSet(java.lang.String username, java.lang.String password) throws java.rmi.RemoteException;

    /**
     * Retrieves list of unsubscribed addresses for all mass e-mail
     * campaigns. Returns a String.
     */
    public java.lang.String reports_GetUnsubscribesAll_String(java.lang.String username, java.lang.String password, java.lang.String rowDelimiter, java.lang.String colDelimiter, java.lang.String textQualifier) throws java.rmi.RemoteException;

    /**
     * Retrieves list of unsubscribed addresses for all mass e-mail
     * campaigns. Returns an XML document.
     */
    public com.jangomail.api.Reports_GetUnsubscribesAll_XMLResponseReports_GetUnsubscribesAll_XMLResult reports_GetUnsubscribesAll_XML(java.lang.String username, java.lang.String password) throws java.rmi.RemoteException;

    /**
     * Retrieves a Group member by the member's e-mail address. Returns
     * a .NET DataSet.
     */
    public com.jangomail.api.Groups_GetMember_ByAddress_DataSetResponseGroups_GetMember_ByAddress_DataSetResult groups_GetMember_ByAddress_DataSet(java.lang.String username, java.lang.String password, java.lang.String groupName, java.lang.String emailAddress) throws java.rmi.RemoteException;

    /**
     * Retrieves a Group member by the member's e-mail address. Returns
     * an XML document.
     */
    public com.jangomail.api.Groups_GetMember_ByAddress_XMLResponseGroups_GetMember_ByAddress_XMLResult groups_GetMember_ByAddress_XML(java.lang.String username, java.lang.String password, java.lang.String groupName, java.lang.String emailAddress) throws java.rmi.RemoteException;

    /**
     * Retrieves a Group member by the member's numeric ID. Returns
     * a .NET DataSet.
     */
    public com.jangomail.api.Groups_GetMember_ByID_DataSetResponseGroups_GetMember_ByID_DataSetResult groups_GetMember_ByID_DataSet(java.lang.String username, java.lang.String password, java.lang.String groupName, int emailID) throws java.rmi.RemoteException;

    /**
     * Retrieves number of sent messages from a particular account
     * within a specified time period. Returns an integer.
     */
    public int reports_GetSentMessageCount(java.lang.String username, java.lang.String password, java.lang.String beginningDate, java.lang.String endingDate) throws java.rmi.RemoteException;

    /**
     * Retrieves a Group member by the member's numeric ID. Returns
     * an XML document.
     */
    public com.jangomail.api.Groups_GetMember_ByID_XMLResponseGroups_GetMember_ByID_XMLResult groups_GetMember_ByID_XML(java.lang.String username, java.lang.String password, java.lang.String groupName, int emailID) throws java.rmi.RemoteException;

    /**
     * Retrieves members of a Group Returns a .NET DataSet.
     */
    public com.jangomail.api.Groups_GetMembers_DataSetResponseGroups_GetMembers_DataSetResult groups_GetMembers_DataSet(java.lang.String username, java.lang.String password, java.lang.String groupName) throws java.rmi.RemoteException;

    /**
     * Retrieves members of a Group Returns a string
     */
    public java.lang.String groups_GetMembers_String(java.lang.String username, java.lang.String password, java.lang.String groupName, java.lang.String rowDelimiter, java.lang.String colDelimiter, java.lang.String textQualifier) throws java.rmi.RemoteException;

    /**
     * Retrieves members of a Group Returns a string
     */
    public com.jangomail.api.Groups_GetMembers_XMLResponseGroups_GetMembers_XMLResult groups_GetMembers_XML(java.lang.String username, java.lang.String password, java.lang.String groupName) throws java.rmi.RemoteException;
}
