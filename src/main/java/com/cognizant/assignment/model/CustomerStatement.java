
package com.cognizant.assignment.model;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "transactionReference",
    "accountNumber",
    "startBalance",
    "mutation",
    "description",
    "endBalance"
})
public class CustomerStatement {

    @JsonProperty("transactionReference")
    private String transactionReference;
    @JsonProperty("accountNumber")
    private String accountNumber;
    @JsonProperty("startBalance")
    private String startBalance;
    @JsonProperty("mutation")
    private String mutation;
    @JsonProperty("description")
    private String description;
    @JsonProperty("endBalance")
    private String endBalance;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("transactionReference")
    public String getTransactionReference() {
        return transactionReference;
    }

    @JsonProperty("transactionReference")
    public void setTransactionReference(String transactionReference) {
        this.transactionReference = transactionReference;
    }

    @JsonProperty("accountNumber")
    public String getAccountNumber() {
        return accountNumber;
    }

    @JsonProperty("accountNumber")
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @JsonProperty("startBalance")
    public String getStartBalance() {
        return startBalance;
    }

    @JsonProperty("startBalance")
    public void setStartBalance(String startBalance) {
        this.startBalance = startBalance;
    }

    @JsonProperty("mutation")
    public String getMutation() {
        return mutation;
    }

    @JsonProperty("mutation")
    public void setMutation(String mutation) {
        this.mutation = mutation;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("endBalance")
    public String getEndBalance() {
        return endBalance;
    }

    @JsonProperty("endBalance")
    public void setEndBalance(String endBalance) {
        this.endBalance = endBalance;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(transactionReference).append(accountNumber).append(startBalance).append(mutation).append(description).append(endBalance).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof CustomerStatement) == false) {
            return false;
        }
        CustomerStatement rhs = ((CustomerStatement) other);
        return new EqualsBuilder().append(transactionReference, rhs.transactionReference).append(accountNumber, rhs.accountNumber).append(startBalance, rhs.startBalance).append(mutation, rhs.mutation).append(description, rhs.description).append(endBalance, rhs.endBalance).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
