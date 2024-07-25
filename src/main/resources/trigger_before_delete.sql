create or replace function sony.tbd_author()
RETURNS TRIGGER AS $$
BEGIN
UPDATE sony.book SET author_id=NULL
WHERE author_id=OLD.id;
RETURN OLD;
END;
$$ LANGUAGE plpgsql;
create or replace TRIGGER before_delete_author
BEFORE DELETE ON sony.authors
FOR EACH ROW
EXECUTE FUNCTION sony.tbd_author();