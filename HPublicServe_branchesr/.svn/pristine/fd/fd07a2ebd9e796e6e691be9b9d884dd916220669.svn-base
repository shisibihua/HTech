DROP TRIGGER IF EXISTS trigger_user;
create trigger trigger_user after delete on user 
for each row  
begin   
    delete  from user_user2role where user_id=old.user_id;
	  delete  from ad_user2device where user_id=old.user_id;
    delete  from ad_campus2user where user_id=old.user_id;
end;

DROP TRIGGER IF EXISTS trigger_user_role;
create trigger trigger_user_role after delete on user_role 
for each row  
begin   
    delete  from user_user2role where role_id=old.role_id;
    delete  from user_role2type where role_id=old.role_id;
    delete  from user_role2permission where role_id=old.role_id;
end;
