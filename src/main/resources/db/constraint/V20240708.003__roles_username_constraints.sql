CREATE FUNCTION prevent_username_update() RETURNS TRIGGER AS $$
BEGIN
    IF
NEW.username <> OLD.username THEN
        RAISE EXCEPTION 'Username cannot be changed';
END IF;
RETURN NEW;
END;
$$
LANGUAGE plpgsql;

CREATE TRIGGER prevent_username_update_trigger
    BEFORE UPDATE
    ON user_account
    FOR EACH ROW
    EXECUTE FUNCTION prevent_username_update();

CREATE FUNCTION prevent_basic_role_deletion() RETURNS TRIGGER AS $$
BEGIN
    IF
OLD.is_basic THEN
        RAISE EXCEPTION 'Cannot delete basic roles';
END IF;
RETURN OLD;
END;
$$
LANGUAGE plpgsql;

CREATE TRIGGER prevent_basic_role_deletion_trigger
    BEFORE DELETE
    ON role
    FOR EACH ROW
    EXECUTE FUNCTION prevent_basic_role_deletion();
