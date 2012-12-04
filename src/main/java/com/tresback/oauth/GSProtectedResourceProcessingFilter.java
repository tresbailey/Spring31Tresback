/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tresback.oauth;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth.common.OAuthException;
import org.springframework.security.oauth.common.signature.OAuthSignatureMethod;
import org.springframework.security.oauth.common.signature.SignatureSecret;
import org.springframework.security.oauth.common.signature.UnsupportedSignatureMethodException;
import org.springframework.security.oauth.provider.ConsumerAuthentication;
import org.springframework.security.oauth.provider.filter.ProtectedResourceProcessingFilter;
import org.springframework.security.oauth.provider.token.OAuthProviderToken;

/**
 *
 * @author rbailey
 */
public class GSProtectedResourceProcessingFilter extends ProtectedResourceProcessingFilter {
  
    private final Log log = LogFactory.getLog(getClass());
    
  /**
   * Validate the signature of the request given the authentication request.
   *
   * @param authentication The authentication request.
   */
   @Override
  protected void validateSignature(ConsumerAuthentication authentication) throws AuthenticationException {
      log.info("Trying to validate the signature");
    SignatureSecret secret = authentication.getConsumerDetails().getSignatureSecret();
    String token = authentication.getConsumerCredentials().getToken();
    OAuthProviderToken authToken = null;
    if (token != null && !"".equals(token)) {
      authToken = getTokenServices().getToken(token);
    }

    String signatureMethod = authentication.getConsumerCredentials().getSignatureMethod();
    OAuthSignatureMethod method;
    try {
      method = getSignatureMethodFactory().getSignatureMethod(signatureMethod, secret, authToken != null ? authToken.getSecret() : null);
    }
    catch (UnsupportedSignatureMethodException e) {
      throw new OAuthException(e.getMessage(), e);
    }

    String signatureBaseString = authentication.getConsumerCredentials().getSignatureBaseString();
    String signature = authentication.getConsumerCredentials().getSignature();
    if (log.isDebugEnabled()) {
      log.info("Verifying signature " + signature + " for signature base string " + signatureBaseString + " with method " + method.getName() + ".");
    }
    log.info("signing the request with the hmac method"+ signatureBaseString);
    signatureBaseString = method.sign(signatureBaseString);
    log.info("verifying with method: "+ method +", "+ signatureBaseString +", "+ signature);
    
    method.verify(signatureBaseString, signature);
  }
    
}
