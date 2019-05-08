### Transfer manager application

API Rest excercise to handle money transfer between accounts

### Dependencies

* Jetty server version 9.4 to create standalone server on 8080 port
* Jersey version 2.7 for JAX-RS rest services
* Lombok version 1.18 to avoid boilerplate code
* In memory database H2
* Hiberate core version 5.2 to handle persistence
* Junit for unit tests

### Rest endpoints

| Endpoint				| HTTP verb| Purpose			| Other options
|:-------:|:-------:|:-------:|:-------:|
| /accounts			        | POST	   | Create account		  |Provides location URI of created resource |
| /accounts/{account-id}| PUT      | Update account     |                                          |
| /accounts/{account-id}| GET      | Get account by id  |                                          |
| /transactions         | POST     | Create transacction|Provides location URI of created resource |

### Status codes

| Status code    | Resource               | Comment                |
|:-------:       | :-------:              | :-------:              |
| 201            | accounts,transacctions | used in POST action    |
| 200            | accounts               | used in PUT, GET action|
| 400            | transactions           | used in POST action to express errors in transaction operation|
| 404            | accounts               | when a given account does not exists |

### Payload examples

#### Create account

```json
{
	"owner" : "User 12345",
	"balance": 15000
}
=== response ===
{
    "balance": 15000,
    "id": "caeb85ab-9ed4-4e10-b954-7e1ab49c735b",
    "owner": "User 12345"
}
```
#### Create transaction

Given the following created accounts

```json
=== Origin account ===
{
    "balance": 15000,
    "id": "caeb85ab-9ed4-4e10-b954-7e1ab49c735b",
    "owner": "User 12345"
}
=== Destination account ===
{
    "balance": 8500,
    "id": "26e34c75-50c6-4602-b5f4-13b61279a9af",
    "owner": "User 0099"
}

=== Transaction payload ===
{
	"originAccount": "caeb85ab-9ed4-4e10-b954-7e1ab49c735b",
	"destinationAccount": "26e34c75-50c6-4602-b5f4-13b61279a9af",
	"amount": 1000
}

=== Response ===
{
    "amount": 1000,
    "destinationAccount": "26e34c75-50c6-4602-b5f4-13b61279a9af",
    "id": "971a7802-7f16-43f2-a4e7-30e34b0450b9",
    "originAccount": "caeb85ab-9ed4-4e10-b954-7e1ab49c735b"
}
```
(Account balances can be checked using GET endpoint /account/{accountId}

### Business rules

#### Account not found

When the origin/destination account is not found, the system provides the following message with status code 404

```json
{
    "message": "Account caeb85ab-9ed4-4e10-b954-7e1ab49c735 not found"
}
```
#### Transaction amount negative

When the transaction amount is negative, the system provides the following message with status code 400

```json
{
    "message": "Transaction amount can not be negative"
}
```
#### Insufficient funds on origin account

When the origin account does not have sufficient founds, the system provides the following message with status code 400

```json
{
    "message": "The origin account does not have sufficient funds"
}
```

### Easy to add new business rules

If is required to add new rules you just have to:

1. Create a class implementing TransferRule interface
2. Include you logic and throw the business rule exception AccountException("Message")
3. Include the new rule in TransferRuleValidator class

Example

```java
public class PositiveAmountRule implements TransferRule {

	@Override
	public void validate(TransactionDTO transaction) throws AccountException {
		if (transaction.getAmount().compareTo(BigDecimal.ZERO) < 0) {
			throw new AccountException("Transaction amount can not be negative");
		}
	}

}

public class TransferRuleValidator {
	
	public static void validate(TransactionDTO transaction) {
		
		List<TransferRule> rules = new ArrayList<>();
		rules.add(new PositiveAmountRule());
		
		rules.forEach(rule -> rule.validate(transaction));
	}

}
```





