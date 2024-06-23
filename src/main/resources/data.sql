DELETE FROM sys_user;

INSERT INTO sys_user (id, name, age, phone,password,address,email,deleted) VALUES
                                                (1,'AJone', 18,'15319834567','admin@123','北京市朝阳区大望路','test1@baomidou.com',0),
                                                (2, 'AWang', 28, '15319834567','admin@123','北京市海淀区清华园','test1@baomidou.com',0),
                                                (3, 'ASan', 33, '15319834567','admin@123','北京市大兴区机场','test1@baomidou.com',0),
                                                (4, 'AChen', 12, '15319834567','admin@123','北京市区大望路','test1@baomidou.com',0),
                                                (5, 'ADong', 22, '15319834567','admin@123','北京市朝阳区大望路','test1@baomidou.com',0);