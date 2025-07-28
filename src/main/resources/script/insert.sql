truncate table Account cascade;
truncate table Profile cascade;
truncate table Transaction cascade;

INSERT INTO Profile(id, email , firstName , lastname , phone , address) VALUES
    (200, 'abojeedwin@gmail.com' , 'Edwin' , 'Aboje' , '09096041561' , '17, olatunji street');

INSERT INTO Account(id, accountName , accountNumber , pin , balance,profileId) VALUES
    (100, 'customer', '09096041561' , '12345', 0 , 200 );

INSERT INTO Transaction(id, amount, status , accountId , description , recipientName) VALUES
    (' ' , 1000 , 'PROCESSING' , 100, 'Bills' , 'Hannah')