INSERT INTO requester (id, username,password,designated_officer) VALUES (1,'John','12345678',false);
INSERT INTO requester (id, username,password,designated_officer) VALUES (2,'Felix','12345678',false);
INSERT INTO requester (id, username,password,designated_officer) VALUES (3,'William','12345678',true);
INSERT INTO requester (id, username,password,designated_officer) VALUES (4,'Sam','12345678',true);
INSERT INTO requester (id, username,password,designated_officer) VALUES (5,'Peter','12345678',true);

INSERT INTO aetos (id, username,password) VALUES (1,'mark','12345678');
INSERT INTO aetos (id, username,password) VALUES (2,'joshua','12345678');
INSERT INTO aetos (id, username,password) VALUES (3,'ryan','12345678');
INSERT INTO aetos (id, username,password) VALUES (4,'zayn','12345678');

-- INSERT INTO task (id, assest_id,company_name,vehicle_no,driver_name,
--                    driver_psa_pass_no,description,attachments,
--                    validated,time_validated,authorised,time_authorised,
--                    verified,time_verified) VALUES (1,1,'Company','vehicleNo','driverName','driver_psa_pass_no',
--                              'description','attachments',false,null,false,null,
--                              false,null);