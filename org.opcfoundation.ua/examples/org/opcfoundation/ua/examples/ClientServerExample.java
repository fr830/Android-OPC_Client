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
package org.opcfoundation.ua.examples;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Random;

import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.opcfoundation.ua.application.Application;
import org.opcfoundation.ua.application.Client;
import org.opcfoundation.ua.application.Server;
import org.opcfoundation.ua.application.SessionChannel;
import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.builtintypes.StatusCode;
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.builtintypes.Variant;
import org.opcfoundation.ua.common.DebugLogger;
import org.opcfoundation.ua.common.ServiceFaultException;
import org.opcfoundation.ua.common.ServiceResultException;
import org.opcfoundation.ua.core.ActivateSessionRequest;
import org.opcfoundation.ua.core.ActivateSessionResponse;
import org.opcfoundation.ua.core.CallMethodRequest;
import org.opcfoundation.ua.core.CallMethodResult;
import org.opcfoundation.ua.core.CallRequest;
import org.opcfoundation.ua.core.CallResponse;
import org.opcfoundation.ua.core.CancelRequest;
import org.opcfoundation.ua.core.CancelResponse;
import org.opcfoundation.ua.core.CloseSessionRequest;
import org.opcfoundation.ua.core.CloseSessionResponse;
import org.opcfoundation.ua.core.CreateSessionRequest;
import org.opcfoundation.ua.core.CreateSessionResponse;
import org.opcfoundation.ua.core.EndpointConfiguration;
import org.opcfoundation.ua.core.EndpointDescription;
import org.opcfoundation.ua.core.MethodServiceSetHandler;
import org.opcfoundation.ua.core.SessionServiceSetHandler;
import org.opcfoundation.ua.core.SignatureData;
import org.opcfoundation.ua.core.StatusCodes;
import org.opcfoundation.ua.core.UserTokenPolicy;
import org.opcfoundation.ua.examples.certs.ExampleKeys;
import org.opcfoundation.ua.transport.EndpointServer;
import org.opcfoundation.ua.transport.UriUtil;
import org.opcfoundation.ua.transport.endpoint.EndpointServiceRequest;
import org.opcfoundation.ua.transport.security.CertificateValidator;
import org.opcfoundation.ua.transport.security.HttpsSecurityPolicy;
import org.opcfoundation.ua.transport.security.KeyPair;
import org.opcfoundation.ua.transport.security.SecurityMode;
import org.opcfoundation.ua.utils.EndpointUtil;

public class ClientServerExample {

	public static void main(String[] args)
	throws Exception
	{        
		// Load Log4j configurations from external file
		PropertyConfigurator.configure( ClientServerExample.class.getResource("log.properties") );
		// Create Logger
		Logger myLogger = Logger.getLogger( ClientServerExample.class ); 
		
		///// Server Application /////////////		
		Application serverApplication = new Application();
		MyServerExample myServer = new MyServerExample(serverApplication);
		
		// Attach listener (debug logger) to each binding
		DebugLogger debugLogger = new DebugLogger( myLogger );
		for (EndpointServer b : myServer.getEndpointBindings().getEndpointServers())
			b.addConnectionListener( debugLogger );		
		//////////////////////////////////////		


		
		//////////////  CLIENT  //////////////
		// Load Client's Application Instance Certificate from file
		KeyPair myClientApplicationInstanceCertificate = ExampleKeys.getCert("ClientServerExample");
		// Create Client
		Client myClient = Client.createClientApplication( myClientApplicationInstanceCertificate );
		Application myClientApplication = myClient.getApplication();
		myClientApplication.getHttpsSettings().setHostnameVerifier( SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER );
		myClientApplication.getHttpsSettings().setCertificateValidator( CertificateValidator.ALLOW_ALL );
		myClientApplication.getHttpsSettings().setHttpsSecurityPolicies(HttpsSecurityPolicy.ALL);
		
		
		//////////////////////////////////////		
		
		// Get Endpoints like this
		String uri;
		uri = "opc.tcp://localhost:8666/UAExample";
		uri = "https://localhost:8443/UAExample";
		
		SessionChannel myChannel = null;
		try {
			myClient.setTimeout( 10000 );
			EndpointDescription[] endpoints = myClient.discoverEndpoints(uri, "");
		
			endpoints = EndpointUtil.selectByProtocol(endpoints, UriUtil.getTransportProtocol(uri));
			
			// Connect the application
			myChannel = myClient.createSessionChannel( endpoints[0] );
			// Activate session
			myChannel.activate();
			//////////////////////////////////////

			/////////////  EXECUTE  //////////////		
			CallRequest callRequest = new CallRequest();
			CallMethodRequest methodRequest = new CallMethodRequest();
			callRequest.setMethodsToCall( new CallMethodRequest[] {methodRequest} );
			methodRequest.setMethodId( MyServerExample.LIST_SOLVERS );
			CallResponse res = myChannel.Call( callRequest );
			System.out.println( res );
			//////////////////////////////////////
		} 
		
		finally {
			/////////////  SHUTDOWN  /////////////
			// Close client's channel
			if ( myChannel != null ) myChannel.close();
			// Close the server by unbinding all endpoints 
			myServer.getApplication().close();
			//////////////////////////////////////		
		}
		
	}
	
	
}

class MyServerExample extends Server implements MethodServiceSetHandler, SessionServiceSetHandler {
	
	public static final NodeId LIST_SOLVERS = new NodeId(2, "ListSolvers");
	
	public MyServerExample(Application application) throws CertificateException, IOException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, ServiceResultException
	{
		super(application);
		// Add method service set
		addServiceHandler( this );
		
		// Add Client Application Instance Certificate validator - Accept them all (for now)
		application.getOpctcpSettings().setCertificateValidator( CertificateValidator.ALLOW_ALL );
		application.getHttpsSettings().setCertificateValidator( CertificateValidator.ALLOW_ALL );
		
		// The HTTPS SecurityPolicies are defined separate from the endpoint securities
		application.getHttpsSettings().setHttpsSecurityPolicies(HttpsSecurityPolicy.ALL);

		// Peer verifier
		application.getHttpsSettings().setHostnameVerifier( SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER );
		
		// Load Servers's Application Instance Certificate...
		KeyPair myServerApplicationInstanceCertificate = ExampleKeys.getCert("ClientServerExample"); 
		application.addApplicationInstanceCertificate( myServerApplicationInstanceCertificate );
		// ...and HTTPS certificate
		KeyPair myHttpsCertificate = ExampleKeys.getHttpsCert("ClientServerExample"); 
		application.getHttpsSettings().setKeyPair( myHttpsCertificate );
		
		// Add User Token Policies
		addUserTokenPolicy( UserTokenPolicy.ANONYMOUS );
		addUserTokenPolicy( UserTokenPolicy.SECURE_USERNAME_PASSWORD );
		
		// Create an endpoint for each network interface
		String hostname = EndpointUtil.getHostname();
		String bindAddress, endpointAddress;
		for (String addr : EndpointUtil.getInetAddressNames()) {
			bindAddress = "https://"+addr+":8443/UAExample";
			endpointAddress = "https://"+hostname+":8443/UAExample";
			System.out.println(endpointAddress+" bound at "+bindAddress);
			// The HTTPS ports are using NONE OPC security 
			bind(bindAddress, endpointAddress, SecurityMode.NONE);
			
			bindAddress = "opc.tcp://"+addr+":8666/UAExample";
			endpointAddress = "opc.tcp://"+hostname+":8666/UAExample";
			System.out.println(endpointAddress+" bound at "+bindAddress);
			bind(bindAddress, endpointAddress, SecurityMode.ALL);
		}
		
		//////////////////////////////////////
		
	}

	@Override
	public void onCall(EndpointServiceRequest<CallRequest, CallResponse> callRequest)
			throws ServiceFaultException {
		
		CallResponse response = new CallResponse();
		CallMethodRequest[] reqs = callRequest.getRequest().getMethodsToCall();
		CallMethodResult[] results = new CallMethodResult[ reqs.length ];
		response.setResults( results );
		
		// Iterate all calls
		for (int i=0; i<reqs.length; i++) {
			CallMethodRequest req = reqs[i];
			CallMethodResult result = results[i] = new CallMethodResult();
			
			NodeId methodId = req.getMethodId();
			if ( LIST_SOLVERS.equals(methodId) ) {				
				String[] solvers = { "sovler1", "solver2", "solver3" };				
				Variant solversInVariant = new Variant( solvers );				
				result.setOutputArguments( new Variant[] {solversInVariant} );
				result.setStatusCode( StatusCode.GOOD );				
			}
			
			else {
				// Unknown method
				result.setStatusCode( new StatusCode( StatusCodes.Bad_MethodInvalid ) );
			}
			
		}
		
		callRequest.sendResponse(response);

	}

	@Override
	public void onCreateSession(EndpointServiceRequest<CreateSessionRequest, CreateSessionResponse> msgExchange) throws ServiceFaultException {
		CreateSessionRequest req = msgExchange.getRequest();
		CreateSessionResponse res = new CreateSessionResponse();
		byte[] token = new byte[32];
		byte[] nonce = new byte[32];
		Random r = new Random();
		r.nextBytes( nonce );
		r.nextBytes( token );
		SignatureData signatureData = new SignatureData();
		//signatureData.setAlgorithm(Algorithm)
		res.setAuthenticationToken( new NodeId(0, token) );
		EndpointConfiguration endpointConfiguration = EndpointConfiguration.defaults();
		res.setMaxRequestMessageSize( UnsignedInteger.valueOf( Math.max( endpointConfiguration.getMaxMessageSize(), req.getMaxResponseMessageSize().longValue()) ) );
		res.setRevisedSessionTimeout( Math.max( req.getRequestedSessionTimeout() , 60*1000) );
		res.setServerCertificate( getApplication().getApplicationInstanceCertificates()[0].getCertificate().encodedCertificate );
		res.setServerEndpoints( this.getEndpointDescriptions() );
		res.setServerNonce( nonce );
		res.setServerSignature( signatureData );
		res.setServerSoftwareCertificates( getApplication().getSoftwareCertificates() );
		res.setSessionId( new NodeId(0, "Client") );
		msgExchange.sendResponse(res);
	}

	@Override
	public void onActivateSession(EndpointServiceRequest<ActivateSessionRequest, ActivateSessionResponse> msgExchange) throws ServiceFaultException {
		ActivateSessionResponse res = new ActivateSessionResponse();
		byte[] nonce = new byte[32];
		Random r = new Random();
		r.nextBytes( nonce );
		res.setServerNonce(nonce);
		res.setResults(new StatusCode[]{ StatusCode.GOOD});
		msgExchange.sendResponse(res);
	}

	@Override
	public void onCloseSession(EndpointServiceRequest<CloseSessionRequest, CloseSessionResponse> msgExchange) throws ServiceFaultException {
		CloseSessionResponse res = new CloseSessionResponse();
		msgExchange.sendResponse(res);
		}

	@Override
	public void onCancel(EndpointServiceRequest<CancelRequest, CancelResponse> msgExchange) throws ServiceFaultException {
		// TODO Auto-generated method stub
		
	}
	
}
