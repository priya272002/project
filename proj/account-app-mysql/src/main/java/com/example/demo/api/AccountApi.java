package com.example.demo.api;

import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.AccountNotFoundException;
import com.example.demo.exception.ui.ErrorResponse;
import com.example.demo.model.Account;
import com.example.demo.service.AccountService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/accounts")
@Validated
public class AccountApi {

	private final AccountService accountService;

	@ExceptionHandler(AccountNotFoundException.class)
	public ErrorResponse handleException(com.example.demo.exception.AccountNotFoundException e) {

		ErrorResponse response = new ErrorResponse();
		response.setMessage(e.getMessage());
		response.setStatusCode(HttpStatus.BAD_REQUEST.value());
		response.setToOfError(System.currentTimeMillis());
		return response;
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, List<String>>> handleValidationErrors(MethodArgumentNotValidException ex) {
		List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage)
				.collect(Collectors.toList());
		return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	private Map<String, List<String>> getErrorsMap(List<String> errors) {
		Map<String, List<String>> errorResponse = new HashMap<>();
		errorResponse.put("errors", errors);
		return errorResponse;
	}

	@PostMapping
	public ResponseEntity<Account> createAccount(@Valid @RequestBody Account account) {
		return ResponseEntity.status(HttpStatus.CREATED).body(accountService.createAccount(account));
	}

	@GetMapping(produces = { "application/json","application/xml"})
	public ResponseEntity<List<Account>> listAccounts() {
		return ResponseEntity.status(HttpStatus.OK).body(accountService.getAllAccounts());
	}

	@GetMapping("/{accountNumber}")
	public ResponseEntity<?> getAccountByNumber(@PathVariable("accountNumber") String accountNumber)
			throws com.example.demo.exception.AccountNotFoundException {
		return new ResponseEntity<Account>(accountService.getAccountByaccountNumber(accountNumber), HttpStatus.OK);
	}

	@PutMapping("/{accountNumber}")
	public ResponseEntity<?> updateAccount(@PathVariable("accountNumber") String accountNumber,
			@RequestBody Account account) throws AccountNotFoundException {
		return ResponseEntity.status(HttpStatus.ACCEPTED)
				.body(accountService.updateAccountByAccountNumber(accountNumber, account));
	}

	@DeleteMapping("/{accountNumber}")
	public ResponseEntity<?> deleteAccount(@PathVariable("accountNumber") String accountNumber)
			throws AccountNotFoundException {
		accountService.deleteAccountByAccountNumber(accountNumber);
		return ResponseEntity.status(HttpStatus.OK).body("deletion succeed");
	}

}