/*
 * for KMD HTTP API
 * API for KMD (Key Management Daemon)
 *
 * OpenAPI spec version: 0.0.1
 * Contact: contact@algorand.com
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package com.algorand.algosdk.kmd.model;

import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.ObjectUtils;

/**
 * APIV1POSTWalletInitRequest is the request for &#x60;POST /v1/wallet/init&#x60;
 */
public class InitWalletHandleTokenRequest {

  @SerializedName("wallet_id")
  private String walletId = null;

  @SerializedName("wallet_password")
  private String walletPassword = null;
  public InitWalletHandleTokenRequest walletId(String walletId) {
    this.walletId = walletId;
    return this;
  }

  

  /**
  * Get walletId
  * @return walletId
  **/
  public String getWalletId() {
    return walletId;
  }
  public void setWalletId(String walletId) {
    this.walletId = walletId;
  }
  public InitWalletHandleTokenRequest walletPassword(String walletPassword) {
    this.walletPassword = walletPassword;
    return this;
  }

  

  /**
  * Get walletPassword
  * @return walletPassword
  **/
  public String getWalletPassword() {
    return walletPassword;
  }
  public void setWalletPassword(String walletPassword) {
    this.walletPassword = walletPassword;
  }
  @Override
  public boolean equals(java.lang.Object o) {
  if (this == o) {
    return true;
  }
  if (o == null || getClass() != o.getClass()) {
    return false;
  }
    InitWalletHandleTokenRequest initWalletHandleTokenRequest = (InitWalletHandleTokenRequest) o;
    return ObjectUtils.equals(this.walletId, initWalletHandleTokenRequest.walletId) &&
    ObjectUtils.equals(this.walletPassword, initWalletHandleTokenRequest.walletPassword);
  }

  @Override
  public int hashCode() {
    return ObjectUtils.hashCodeMulti(walletId, walletPassword);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InitWalletHandleTokenRequest {\n");
    
    sb.append("    walletId: ").append(toIndentedString(walletId)).append("\n");
    sb.append("    walletPassword: ").append(toIndentedString(walletPassword)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}
