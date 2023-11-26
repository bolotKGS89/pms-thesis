# pms-thesis
The purpose of this project is create a payment system which is attached to Metaverse and helps to process and forward payments to third-party systems such as **PayPal**, **Visa** and **Amazon Pay**.

The architecture is a microservice-based architecture. It consists of following microservices: **PayPal**, **Amazon Pay**, **Visa**, **Spring Cloud REST API gateway**, **E-wallet**, **User Management System**

Each payment gateway has a set of endpoints which provide a certain functionality

## PayPal

 - **POST /paypal/create** - Initiates a payment transaction.
 - **GET /paypal/retrieve/{id}** - Retrieves details about a specific payment order, including its status and other relevant information.
 - **POST /paypal/capture/{id}** - Executes (captures) a payment after the user approves it. This completes the payment process.
 - **POST /paypal/refund/{id}** - Initiates a refund for a captured payment. Useful for processing refunds in an e-wallet system.
 - **GET /paypal/transactions** - List all transactions done from the account

## Stripe/Visa
 - **POST /visa/create** - Triggers PaymentIntent for creating a payment transaction
 - **GET /visa/retrieve/{id}** - Retrieves the details of a PaymentIntent that has previously been created.
 - **POST /visa/capture/{id}** - Capture the funds of an existing uncaptured PaymentIntent when its status is requires_capture
 - **POST /visa/refund/{id}** - Refund a previously created PaymentIntent that’s not refunded yet. Funds are refunded to the credit or debit card that’s originally charged.
 - **GET /visa/transactions** - Returns a list of PaymentIntents.