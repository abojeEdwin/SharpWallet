truncate table Account cascade;
truncate table Profile cascade;
truncate table Transaction cascade;

INSERT INTO Profile(id, email , first_name , last_name , phone , address) VALUES
    (1, 'abojeedwin@gmail.com' , 'Edwin' , 'Aboje' , '09096042212' , '17 olatunji street');

INSERT INTO Account(id, account_name , account_number , pin , balance, profile_id) VALUES
    (1, 'customer', '09096042212' , '12345', 0 , 1 );

INSERT INTO Transaction(id, amount, status , account_id , description , recipient_name) VALUES
    (1 , 1000 , 'PROCESSING' , 1, 'Bills' , 'Hannah')