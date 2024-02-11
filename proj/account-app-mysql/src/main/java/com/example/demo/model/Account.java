package com.example.demo.model;

import javax.validation.constraints.Email;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "account_table")
@Getter
@Setter
public class Account {
	@Id
	// @GeneratedValue(strategy = GenerationType.AUTO)
	@NotBlank(message = "account number can not be blank")
	@Column(name = "account_number")
	private String accountNumber;
	@NotBlank(message = "account holder name can not blank")
	@Length(min = 5, max = 16, message = "account holder name is wrong")
	@Column(name = "account_holder_name")
	private String accountHolderName;
	@Column(name = "account_holder_address")
	private String accountHolderAddress;
	@Email(message = "invalid email",regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
	@Column(name = "account_holder_email")
	private String email;
	@Column(name="account_balance")
	private double balance;
	public Account(String accountHolderName, String accountHolderAddress, String email) {
		super();
		this.accountHolderName = accountHolderName;
		this.accountHolderAddress = accountHolderAddress;
		this.email = email;
	}

}