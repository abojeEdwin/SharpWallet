truncate table wallet_transaction;
truncate table Account;
truncate table Profile;

INSERT INTO Profile(id, email , first_name , last_name , phone , address) VALUES
    (1, 'abojeedwin@gmail.com' , 'Edwin' , 'Aboje' , '09096042212' , '17 olatunji street');

INSERT INTO Account(id, account_name , account_number , pin , balance, profile_id) VALUES
    (1, 'customer', '09096042212' , '$2b$10$wtVxl9BOEPe13gHaleoULeZnq629BkMffghD3ABz2o1v5GsnYQbay', 0 , 1 );

INSERT INTO wallet_transaction(id, amount, status , account_id , description , recipient_name) VALUES
    ( 1, 1000.00 , 'PROCESSING' , 1, 'Bills' , 'Hannah')